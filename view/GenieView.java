package view;

import java.util.ArrayList;
import java.util.Scanner;

import model.GenieVO;

public class GenieView {
	Scanner sc = new Scanner(System.in);
	public int num = 0; // 사용자 입력 변수

	// 정수 입력
	public int inputInt() {
		while (true) {
			try {
				num = sc.nextInt();
				if (num > 0) {
					return num;
				}
				System.out.println("음수는 입력받을 수 없습니다.\n");
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("다시 입력해주세요");
				System.out.println();
			}
		}
	}

	// 문자열 입력
	public String inputString() {
		while (true) {
			try {
				String str = sc.next();
				return str;
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("다시 입력해주세요");
				System.out.println();
			}
		}
	}

	// 첫화면
	public void startView() {
		System.out.println("\n================ Genie ===============");
		System.out.println("1. 로그인 2. 회원가입 3. 프로그램 종료");
	}

	// 항목 선택 출력문
	public void choice() {
		System.out.print("항목 선택 : ");
	}

	// 로그인/회원가입 아이디
	public void userID() {
		System.out.print("아이디 입력 : ");
	}

	// 로그인/회원가입 비밀번호
	public void userPW() {
		System.out.print("비밀번호 입력 : ");
	}

	// 입력정보 불일치
	public void wrongInput() {
		System.out.println("입력하신 정보가 일치하지 않습니다.");
		System.out.println();
	}

	// 접속 성공
	public void loginSuccess() {
		System.out.println("로그인 완료");
		System.out.println();
	}

	// 접속 실패
	public void loginFail() {
		System.out.println("로그인 실패");
		System.out.println();
	}

	// 회원가입 이름
	public void userName() {
		System.out.print("이름 입력 : ");
	}

	// 동일한 ID존재
	public void sameId() {
		System.out.println("동일한 아이디가 존재합니다. 다시 시도하세요.");
		System.out.println();
	}

	// 노래 정보 출력 멘트
	public void title_ment() {
		System.out.println("\n★★★★선택한 노래 정보★★★★");
	}

	// 회원가입 성공멘트
	public void welcomeMsg() {
		System.out.println("지니에 오신 것을 환영합니다!");
		System.out.println();
	}

	// Y/N
	public String scoreQ() {
		String ans;
		while (true) {
			System.out.print("입력하신 정보가 맞습니까?(Y/N) : ");
			ans = sc.next();
			ans = ans.toUpperCase();
			if (ans.equals("Y") || ans.equals("N")) {
				break;
			}
			System.out.println("Y or N만 입력 가능합니다.");
			System.out.println();
		}
		return ans;
	}

	// 메인화면으로 이동
	public void goMain() {
		System.out.println("메인화면으로 돌아갑니다");
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000); // 1000==1초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
		System.out.println();
	}

	// 이전화면으로 이동
	public void goback() {
		System.out.println("이전화면으로 돌아갑니다");
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000); // 1000==1초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println();
	}

	/////////////////////// Genie 메뉴화면///////////////
	public void genieMain() {
		System.out.println("========= Genie =========");
		System.out.println("1. 노래차트");
		System.out.println("2. 검색");
		System.out.println("3. 마이페이지");
		System.out.println("4. 노래 추천");
		System.out.println("5. 로그아웃");
	}

	///////////////////////// 지니 차트 출력
	public void showChart(ArrayList<GenieVO> data) {
		if (data.size() == 0) {
			System.out.println("출력할 데이터가 없습니다");
			System.out.println();
			return;
		}
		System.out.println("============ 노래차트 TOP 50 ============");
		for (int i = 0; i < data.size(); i++) {
			System.out.println((i + 1) + "번");
			System.out.println("곡 명 : " + data.get(i).getTitle());
			System.out.println("가수 명 : " + data.get(i).getArtist());
			System.out.println("앨범 : " + data.get(i).getAlbum());
			System.out.println("조회수 : " + data.get(i).getViewnum());
			System.out.println();
		}
		System.out.println("노래차트 출력완료");
		System.out.println();
	}

	////////////////////////////// 검색 메뉴
	public void searchMain() {
		System.out.println("\n============ 검색 메뉴============");
		System.out.println("1. 노래검색");
		System.out.println("2. 가수검색");
		System.out.println("3. 처음으로 돌아가기");
	}

	// 검색
	public void search() {
		System.out.print("검색 : ");
	}

	public void searchFail() {
		System.out.println("검색 실패");
		System.out.println();
	}

	////////////////////////////// 마이페이지

	public void myPage() {
		System.out.println("\n========== 마이 페이지 ==========");
		System.out.println("1. 최근 조회한 노래정보");
		System.out.println("2. 회원탈퇴");
		System.out.println("3. 마이 페이지 종료");
	}

	// 최근 조회한 노래정보
	public void recentSong(GenieVO vo) {
		System.out.println();
		if (vo == null) {
			System.out.println("최근 검색한 노래가 없습니다.");
			System.out.println();
			return;
		}
		System.out.println("회원님의 최근 검색한 노래 목록입니다.");
		System.out.println("===========================");
		System.out.println("곡 명 : " + vo.getTitle());
		System.out.println("가 수 : " + vo.getArtist());
		System.out.println("최근 검색 목록 조회완료");
		System.out.println("===========================");
		System.out.println();
	}
	// 회원가입 직후 검색한 노래가 없을때 -----★★★★★★★★★ 수정(7.20)
		public void noSearch() {
			System.out.println("아직까지 조회하신 노래가 없습니다.");
		}

	// 회원탈퇴 선택 재확인
	public String userWithdrawal() {
		String ans;
		while (true) {
			System.out.print("정말로 탈퇴하실건가요ㅠㅠ?(Y/N) : ");
			ans = sc.next();
			ans = ans.toUpperCase();
			if (ans.equals("Y") || ans.equals("N")) {
				break;
			}
			System.out.println("Y or N만 입력 가능합니다.");
			System.out.println();
		}
		return ans;
	}

	// 탈퇴 완료
	public void withdrawlComplete() {
		System.out.println("회원 탈퇴가 완료되었습니다!");
		System.out.println();
	}

	// 탈퇴 실패
	public void withdrawlFail() {
		System.out.println("회원 탈퇴를 중단합니다");
		System.out.println();
	}

	// 회원탈퇴 비밀번호 입력 남은 횟수
	public void leftChance(int chance) {
		System.out.println("3번 불일치하면 마이페이지 메인으로 이동 (남은 입력 가능 횟수 : " + chance);
	}

	///////////////////////////// 노래추천
	public void recommendSong(GenieVO vo) {
		System.out.println("\n회원님이 좋아하시는 곡을 뽑아봤어요!");
		System.out.println("추천곡 : " + vo.getTitle() + "  가수 : " + vo.getArtist());
		System.out.println();
	}

	///////////////////////////// 로그아웃
	public void userLogout() {
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000); // 1000==1초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("로그아웃");
		System.out.println();
	}

	///////////////////////////// 종료
	public void end() {
		for (int i = 0; i < 3; i++) {
			System.out.print(".");
			try {
				Thread.sleep(1000); // 1000==1초
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("프로그램 종료합니다");
	}

	// 검색결과 보기
	public void search_view(ArrayList<GenieVO> datas) {
		try {
			for (int i = 0; i < datas.size(); i++) {
				System.out.println("노래 번호 : " + datas.get(i).getGnum() + "번");
				System.out.println("곡     명 : " + datas.get(i).getTitle());
				System.out.println("가 수  명 : " + datas.get(i).getArtist());
				System.out.println("앨     범 : " + datas.get(i).getAlbum());
				System.out.println("조 회  수 : " + datas.get(i).getViewnum());
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("에러발생!");
			e.printStackTrace();
		}
	}

	// 변경할 때 나오는 노래 목록들
	public void change_view(GenieVO vo) {
		try {
			System.out.println("번호 : " + vo.getGnum());
			System.out.println("노래 : " + vo.getTitle());
			System.out.println("가수 : " + vo.getArtist());
			System.out.println("앨범 : " + vo.getAlbum() + "\n");
		} catch (Exception e) {
			System.out.println("에러발생!");
			e.printStackTrace();
		}
	}

	// 노래검색후 최종 선택 목록
	public void pickSong(GenieVO vo) {
		System.out.println("회원님이 선택하시는 곡을 재생하겠습니다!");
		System.out.println("노래 번호 : " + vo.getGnum());
		System.out.println("곡     명 : " + vo.getTitle());
		System.out.println("가 수  명 : " + vo.getArtist());
		System.out.println("조 회  수 : " + (vo.getViewnum() + 1));
		System.out.println();
	}

	// 노래 선택하기
	public void pick_Gnum() {
		System.out.print("노래번호 입력 : ");
	}

	// 검색한 단어가 차트에 없다면?
	public void search_fail() {
		System.out.println("검색한 정보가 없습니다.");
		System.out.println();
	}
}