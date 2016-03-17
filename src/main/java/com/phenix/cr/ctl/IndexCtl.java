package com.phenix.cr.ctl;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.phenix.cr.service.IndexService;
import com.phenix.cr.util.Configure;

@Controller
@RequestMapping("/index")
public class IndexCtl {
	public static byte[] lock = new byte[1];
	@Autowired
	private IndexService indexService;

	@RequestMapping("")
	public String init() {
		return "index";
	}

	@RequestMapping("report/{batchId}")
	public String init(HttpServletRequest request, @PathVariable String batchId) {
		Map<String, Object> allIndex = indexService.calcAllIndex(batchId);
		request.setAttribute("allIndex", allIndex);
		request.setAttribute("batchId", batchId);
		return "report/view";
	}

	@RequestMapping("report")
	public String reportIndex() {
		return "report/index";
	}

	@RequestMapping("uploadFile")
	public String uploadFile(@RequestParam("data") MultipartFile file,
			Model model) throws Exception {
		String fileName = file.getOriginalFilename().substring(
				file.getOriginalFilename().indexOf("."));
		String className = file.getOriginalFilename().substring(0,
				file.getOriginalFilename().indexOf("."));
		if (!(fileName.endsWith(".xlsx") || fileName.endsWith(".xls"))) {
			throw new RuntimeException("请上传excel文件");
		}
		File f = File.createTempFile(fileName, fileName);
		saveFileFromInputStream(file.getInputStream(), f.getParent(),
				f.getName());
		String uuid = indexService.importValues(className, f);
		f.deleteOnExit();
		return "redirect:report/" + uuid;
	}

	private void saveFileFromInputStream(InputStream stream, String path,
			String filename) throws IOException {
		FileOutputStream fs = new FileOutputStream(path + "/" + filename);
		byte[] buffer = new byte[1024 * 1024];
		int byteread = 0;
		while ((byteread = stream.read(buffer)) != -1) {
			fs.write(buffer, 0, byteread);
			fs.flush();
		}
		fs.close();
		stream.close();
	}

	@RequestMapping("report/{type}/{batchId}")
	public String report(HttpServletRequest request, @PathVariable String type,
			@PathVariable String batchId) throws Exception {
		Map<String, Object> allIndex = indexService.calcAllIndex(batchId);
		ObjectMapper om = new ObjectMapper();
		request.setAttribute("allIndex", om.writeValueAsString(allIndex));

		return "report/hicharts/" + type;
	}

	@RequestMapping("export-report/{type}/{batchId}")
	public void exportreport(HttpServletRequest request,
			HttpServletResponse response, @PathVariable String type,
			@PathVariable String batchId) throws Exception {
		String fileName = type + "-" + batchId + ".jpg";
		String folder = System.getProperty("java.io.tmpdir");
		File fd = new File(folder);
		File f = new File(fd, fileName);
		if (!f.exists()
				|| System.currentTimeMillis() - f.lastModified() > 1000 * 60 * 60) { // 保存一个小时
			String serverUrl = Configure.getInstance()
					.getProperty("SERVER_URL");
			String wkhtmltopdf = Configure.getInstance().getProperty(
					"WKHTMLTOPDF_PATH");
			String cmd = wkhtmltopdf
					+ " --no-stop-slow-scripts --javascript-delay 5000 --crop-y 0"
					+ " --debug-javascript " + serverUrl + "/index/report/"
					+ type + "/" + batchId + " " + f.getAbsolutePath();
			synchronized (lock) {
				runShell(cmd);
			}
		}
		response.setContentType("image/jpg");
		sendFile(response, f);
		f.deleteOnExit();
	}

	private void sendFile(HttpServletResponse response, File f)
			throws FileNotFoundException, IOException {
		InputStream inputStream = new FileInputStream(f);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		byte[] b = new byte[1024];
		int length;
		while ((length = inputStream.read(b)) > 0) {
			os.write(b, 0, length);
		}
		os.flush();
		os.close();
		inputStream.close();
	}

	/**
	 * 运行shell
	 * 
	 * @param shStr
	 *            需要执行的shell
	 */
	public static void runShell(String shStr) throws Exception {
		System.out.println(shStr);
		Process process = Runtime.getRuntime().exec(shStr);
		BufferedReader br1 = new BufferedReader(new InputStreamReader(
				process.getErrorStream()));
		logProcess(br1);
		BufferedReader br = new BufferedReader(new InputStreamReader(
				process.getInputStream()));
		logProcess(br);
		process.waitFor();
	}

	/**
	 * 记录进程信息
	 * 
	 * @param br
	 */
	private static void logProcess(final BufferedReader br) {
		Thread r2 = new Thread() {
			public void run() {
				try {
					String inline;
					while ((inline = br.readLine()) != null) {
						System.out.println(inline);
					}
				} catch (Exception e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
		};
		r2.start();
	}

}
