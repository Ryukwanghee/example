package com.sample.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

@Component //스프링 빈으로 자동 등록되게 하려고 컴포넌트 사용, 스캔되도록하는 것
public class FileDownloadView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		// 값을 꺼냄. 컨트롤러에서 모델에 담은 값 , 모델객체에서 View한테 값을 전달해줘서 꺼내 쓸 수 있는 것
		String directory = (String) model.get("directory");
		String filename = (String) model.get("filename");
		
		File file = (File) model.get("file");
		
		// application/octet-stream - 일반적인 바이너리 데이터에 대한 콘텐츠 타입이다.(그림 영상 압축파일 등등)
		setContentType("application/octet-stream");
		// 응답메시지의 헤더부에 다운로드되는 첨부파일의 이름을 설정한다.
		// attachment; 는 브라우저에서 파일을 열지 않고, 항상 다운로드되게 한다.  무조건 다운이 되게 하는 것(화면이 안열리고)
		// URLEncoder.encode(text, encoding) 은 텍스트를 지정된 인코딩 방식으로 변환시킨다.
		// 텍스트에 한글이 포함되어 있는 경우 utf-8방식으로 인코딩(변환)하지 않으면 한글이 전부 깨진다.
		// Content-Disposition 부가적인 정보를 나타냄
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getName(), "utf-8"));
		
		// 파일을 읽어오는 입력스트림 객체를 생성한다.
		InputStream in = new FileInputStream(file);
		// 브라우저와 연결된 출력스트림을 획득한다. 
		OutputStream out = response.getOutputStream();
		
		// 입력스트림으로 읽은 데이터를 출력스트림으로 복사해서 출력시킨다.
		IOUtils.copy(in, out);
	}
}
