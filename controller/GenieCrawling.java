package controller;

import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.GenieDAO;
import model.GenieVO;

public class GenieCrawling {
	
	public static void sample() {
		
		final String url = "https://www.genie.co.kr/chart/top200";
		Document doc = null;
		try {
			doc = Jsoup.connect(url).get(); // connect를 통해 지니top200 사이트의 html문서를 불러옴
		} catch (IOException e) {
			e.printStackTrace();
		}

		String title1 = "a.title.ellipsis"; // 태그 or class, id명
		String artist1 = "a.artist.ellipsis";
		String album1 = "a.albumtitle.ellipsis";

		Elements eles = doc.select("div.music-list-wrap"); // 아래 정보들을 포함하는 div의 정보 가져온다
		Elements title2 = eles.select(title1); // 제목 정보 불러온다
		Elements artist2 = eles.select(artist1); // 가수 정보 불러온다
		Elements album2 = eles.select(album1); // 앨범 정보 불러온다

		GenieDAO gDAO = new GenieDAO();

		Iterator<Element> title3 = title2.iterator();
		Iterator<Element> artist3 = artist2.iterator();
		Iterator<Element> album3 = album2.iterator();

		while (title3.hasNext()) {
			String title4 = title3.next().text(); // 다음 요소(곡명)로 이동
			System.out.println("곡 명 : " + title4);
			String artist4 = artist3.next().text(); // 다음 요소(가수명)으로 이동
			System.out.println("가수 명 : " + artist4);
			String album4 = album3.next().text(); // 다음 요소(앨범명)으로 이동
			System.out.println("앨범 명 : " + album4);
			System.out.println();

			GenieVO vo = new GenieVO();
			vo.setTitle(title4); // vo의 title에 title4 데이터 삽입
			vo.setArtist(artist4); // vo의 artist에 artist4 데이터 삽입
			vo.setAlbum(album4); // vo의 album에 album4 데이터 삽입
			gDAO.insert(vo); // dao insert함수 호출
		}
		System.out.println("로그: DB에 저장완료!");
		System.out.println();

	
	}
}
