package bbs.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 */
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 받기 (dir, filename)
		String dir = request.getParameter("dir");
		String filename = request.getParameter("filename");
		
		//dir은 파일이 저장된 위치를 의미한다. 이것을 절대경로화 시키자.
		String realPath = getServletContext().getRealPath("resources/" + dir);

		//전체경로를 만들어서 File객체 생성
		String fullPath = realPath + System.getProperty("file.separator") + filename;
		File f = new File(fullPath);
		
		//존재 여부 확인 후 존재할 때만 다운로드한다.
		if (f.exists()) {
			byte[] buf = new byte[4096];
			int size = -1;

			FileInputStream fis = null;
			BufferedInputStream bis = null;

			ServletOutputStream sos = null;
			BufferedOutputStream bos = null;

			try {
				response.setContentType("application/x-msdownload");
				response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes(), "8859_1"));
				
				fis = new FileInputStream(f);
				bis = new BufferedInputStream(fis);
				
				sos = response.getOutputStream();
				bos = new BufferedOutputStream(sos);
				
				while ((size = bis.read(buf)) != -1) {
					bos.write(buf, 0, size);
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fis != null) {
						fis.close();
					}
					if (bis != null) {
						bis.close();
					}
					if (sos != null) {
						sos.close();
					}
					if (bos != null) {
						bos.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
