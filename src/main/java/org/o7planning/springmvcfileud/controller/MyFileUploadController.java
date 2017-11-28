package org.o7planning.springmvcfileud.controller;
 
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
 
import org.o7planning.springmvcfileud.form.MyUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
 
@Controller
public class MyFileUploadController {
 
   // Phương thức này được gọi mỗi lần có Submit.
   @InitBinder
   public void initBinder(WebDataBinder dataBinder) {
       Object target = dataBinder.getTarget();
       if (target == null) {
           return;
       }
       System.out.println("Target=" + target);
 
       if (target.getClass() == MyUploadForm.class) {
  
           // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
           dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
       }
   }
  
   // GET: Hiển thị trang form upload
   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
   public String uploadOneFileHandler(Model model) {
 
       MyUploadForm myUploadForm = new MyUploadForm();
       model.addAttribute("myUploadForm", myUploadForm);
 
       // Forward to "/WEB-INF/pages/uploadOneFile.jsp".
       return "uploadOneFile";
   }
  
   // POST: Sử lý Upload
   @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
   public String uploadOneFileHandlerPOST(HttpServletRequest request, //
								           Model model, //
								           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) throws IOException {
 
       return this.doUpload(request, model, myUploadForm);
 
   }
  
   // GET: Hiển thị trang form upload
   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
   public String uploadMultiFileHandler(Model model) {
 
       MyUploadForm myUploadForm = new MyUploadForm();
       model.addAttribute("myUploadForm", myUploadForm);
 
       // Forward to "/WEB-INF/pages/uploadMultiFile.jsp".
       return "uploadMultiFile";
   }
  
   // POST: Sử lý Upload
   @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
   public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
           Model model, //
           @ModelAttribute("myUploadForm") MyUploadForm myUploadForm) throws IOException {
 
       return this.doUpload(request, model, myUploadForm);
 
   }
 
   private String doUpload(HttpServletRequest request, Model model, //
           MyUploadForm myUploadForm) throws IOException {
 
	   int responseCode = 0;
	   
       String description = myUploadForm.getDescription();
       System.out.println("Description: " + description);
 
       // Thư mục gốc upload file.
       String uploadRootPath = request.getServletContext().getRealPath("upload");
       System.out.println("uploadRootPath=" + uploadRootPath);
 
       File uploadRootDir = new File(uploadRootPath);
       //
       // Tạo thư mục gốc upload nếu nó không tồn tại.
       if (!uploadRootDir.exists()) {
           uploadRootDir.mkdirs();
       }
       CommonsMultipartFile[] fileDatas = myUploadForm.getFileDatas();
       //
       List<File> uploadedFiles = new ArrayList<File>();
       
       
     /*  DataInputStream in = new DataInputStream(request.getInputStream());
		int formDataLength = request.getContentLength();
		byte dataBytes[] = new byte[formDataLength];
		
		int byteRead = 0;
		int totalBytesRead = 0;
		while (totalBytesRead < formDataLength) {				
			byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			if(byteRead > 0) { //IGROUP-2080
				totalBytesRead += byteRead;
			} //IGROUP-2080
		}
*/
       /*Properties systemProps = System.getProperties();
       systemProps.put("javax.net.ssl.keyStorePassword","passwordForKeystore");
       systemProps.put("javax.net.ssl.keyStore","pathToKeystore.ks");
       systemProps.put("javax.net.ssl.trustStore", "pathToTruststore.ts");
       systemProps.put("javax.net.ssl.trustStorePassword","passwordForTrustStore");
       System.setProperties(systemProps);*/
       
       for (CommonsMultipartFile fileData : fileDatas) {
 
          /* // Tên file gốc tại Client.
           String name = fileData.getOriginalFilename();
           System.out.println("Client File Name = " + name);
 
           if (name != null && name.length() > 0) {
               try {
                   // Tạo file tại Server.
                   File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);
 
                   // Luồng ghi dữ liệu vào file trên Server.
                   BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                   stream.write(fileData.getBytes());
                   stream.close();
                   //
                   uploadedFiles.add(serverFile);
                   System.out.println("Write file: " + serverFile);
               } catch (Exception e) {
                   System.out.println("Error Write file: " + name);
               }
           }*/
    	   
    	   //---------------------------------------------------------------------------------
    	   
    	   String url = "https://uic.ipos.dev.integral.digital/ihub/service/rest/document/upload?customerName=dddd&policyNumber=ddd&endorsementNumber=dddd&documentType=Photographs&documentId=28112017";
    		String charset = "UTF-8";
    		String param = "value";
    		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
    		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
    		
    		URLConnection connection = null;
    		try {
    			connection = new URL(url).openConnection();
    			connection.setDoOutput(true);
    			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    			
    			try (
    			    OutputStream output = connection.getOutputStream();
    			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
    			) {

    		    // Send text file.
    		    writer.append("--" + boundary).append(CRLF);
    		    writer.append("Content-Disposition: form-data; name=\"textFile\"; filename=\"" + fileData.getOriginalFilename() + "\"").append(CRLF);
    		    writer.append("Content-Type: image/jpeg; charset=" + charset).append(CRLF); // Text file itself must be saved in this charset!
    		    writer.append(CRLF).flush();

    		    output.write(fileData.getBytes());
    		    output.flush(); 
    		    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.


    		    // End of multipart/form-data.
    		    writer.append("--" + boundary + "--").append(CRLF).flush();
    			}
    			
    			// Request is lazily fired whenever you need to obtain information about response.
    			 responseCode = ((HttpURLConnection) connection).getResponseCode();
    			String responseMess = ((HttpURLConnection) connection).getResponseMessage();
    			System.out.println(responseMess); // Should be 200}
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	   
    	   //---------------------------------------------------------------------------------
       }
       model.addAttribute("description", description);
       model.addAttribute("uploadedFiles", uploadedFiles);
       return "uploadResult";
   }
 
}