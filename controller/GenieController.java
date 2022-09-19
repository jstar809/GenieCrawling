package controller;

import java.util.ArrayList;

import model.GenieDAO;
import model.GenieVO;
import model.MemberDAO;
import model.MemberVO;
import model.MypageDAO;
import model.MypageVO;
import view.GenieView;

public class GenieController {
   GenieDAO gDAO;
   MemberDAO mDAO;
   MypageDAO pDAO;
   GenieView view;

   public GenieController() {
      gDAO = new GenieDAO();
      mDAO = new MemberDAO();
      pDAO = new MypageDAO();
      view = new GenieView();
      if (!gDAO.hasSample(null)) { // SELECT COUNT(*) FROM MEMBER
         GenieCrawling.sample();
      }
   }

   public void startApp() {
      while (true) {
         view.startView();
         view.choice();
         view.inputInt();
         if (view.num == 1) { // 로그인
            MemberVO mVO = new MemberVO();
            view.userID(); // 아이디 입력
            String id = view.inputString();
            view.userPW(); // 비밀번호 입력
            String pw = view.inputString();
            mVO.setMid(id); // 입력받은 id mVO의 Gid에 저장
            mVO.setMpw(pw); // 입력받은 pw mvo의 Gpw에 저장
            MemberVO logincheck = mDAO.login(mVO); // login함수 로그인 시도
            if (logincheck == null) { // 객체가 없다면 로그인 실패로 간주하여 처음화면으로 이동.
               continue;
            }
            mVO.setMnum(logincheck.getMnum()); // ★★★★★로그인 회원정보 mVO 저장
            view.welcomeMsg();
            boolean flag = false; // 로그인 화면으로 돌아가기(회원 탈퇴 등)
            while (true) {
               if (flag) { // 회원탈퇴하면
                  break;
               }

               view.genieMain();
               view.choice();
               view.inputInt();
               if (view.num == 1) { // 노래 차트 출력
                  GenieVO vo = new GenieVO();
                  view.showChart(gDAO.selectAll(vo)); // 노래 차트 출력
               } else if (view.num == 2) { // 노래검색
                  view.searchMain();
                  view.choice();
                  view.inputInt();
                  if (view.num == 1) { // 노래검색
                     GenieVO vo = new GenieVO();
                     view.search();
                     String title = view.inputString();
                     vo.setTitle(title); // vo의 title에 뷰 메소드를 활용해 적은 문자열 저장!
                     // vo = gDAO.selectAll(vo); // gDAO의 selectOne함수에 방금 넣은 vo를 준 결과를 vo에 저장!
                     ArrayList<GenieVO> gArray = new ArrayList<GenieVO>(); // 유효성 검사를 하기 위해 배열 생성
                     gArray = gDAO.selectAll_title(vo);
                     view.search_view(gArray); // gDAO의 selectOne함수에 방금 넣은 vo를 준 결과를 vo에 저장!
                     if (gArray.size() == 0) { // 검색한 결과가 없은경우 메인메뉴로 이동
                        view.search_fail();
                        continue;
                     }
                     GenieVO vo1 = new GenieVO();
                     view.pick_Gnum();
                     int num = view.inputInt(); // 필터링된 목록 중에서 원하는 노래 하나 선택하기
                     vo1.setGnum(num);
                     vo1 = gDAO.selectOne(vo1); // selectOne으로 한가지 검색
                     gDAO.update(vo1); // 조회수를 1증가시켜준다.
                     view.title_ment(); // "선택한 노래 정보"멘트 출력
                     view.pickSong(vo1);
                     MypageVO myVO = new MypageVO();
                     myVO.setMembernum(mVO.getMnum()); // ★★★★★ 회원정보 저장
                     myVO.setGnum(vo1.getGnum()); // ★★★★★들은 노래정보 저장
                     if (pDAO.selectOne(myVO) == null) {
                        pDAO.insert(myVO); // ★★★★★ 회원정보가 있다면 insert
                     } else {
                        pDAO.update(myVO); // ★★★★★있다면 update
                     }
                     // view.search_view(vo); // 검색한 노래 정보 출력해주기
                  } else if (view.num == 2) { // 가수검색
                     GenieVO vo = new GenieVO();
                     view.search();
                     String artist = view.inputString();
                     vo.setArtist(artist);
                     ArrayList<GenieVO> gArray = new ArrayList<GenieVO>(); // 유효성 검사를 하기 위해 배열 생성
                     gArray = gDAO.selectAll_artist(vo);
                     view.search_view(gArray); // gDAO의 selectOne함수에 방금 넣은 vo를 준 결과를 vo에 저장!
                     if (gArray.size() == 0) { // 검색한 결과가 없은경우 메인메뉴로 이동
                        view.search_fail();
                        continue;
                     }
                     GenieVO vo1 = new GenieVO();
                     view.pick_Gnum();
                     int num = view.inputInt();
                     vo1.setGnum(num);
                     vo1 = gDAO.selectOne(vo1);
                     gDAO.update(vo1); // 조회수를 1증가시켜준다.
                     view.title_ment(); // "선택한 노래 정보"멘트 출력
                     view.pickSong(vo1);
                     MypageVO myVO = new MypageVO();
                     myVO.setMembernum(mVO.getMnum()); // ★★★★★ 회원정보 저장
                     myVO.setGnum(vo1.getGnum()); // ★★★★★들은 노래정보 저장
                     if (pDAO.selectOne(myVO) == null) {
                        pDAO.insert(myVO); // ★★★★★ 회원정보가 있다면 insert
                     } else {
                        pDAO.update(myVO); // ★★★★★있다면 update
                     }
                     // view.search_view(vo);
                  } else if (view.num == 3) { // 처음으로 돌아가기
                     view.goback();
                     continue;
                  }
                  // ---------------------------------------------------------------------
               } else if (view.num == 3) { // 마이페이지
                  while (true) {
                     if (flag) { // 회원이 아니면 마이페이지 사용못함
                        break;
                     }

                     view.myPage(); // 마이페이지 메뉴
                     view.choice();
                     view.inputInt(); // 번호 입력

                     if (view.num == 1) { // 1. 최근 조회한 노래한 정보
                        MypageVO pVO = new MypageVO();
                        pVO.setMembernum(mVO.getMnum());
                        pVO = pDAO.selectOne(pVO); 
                        if(pVO == null) {  // 유효성 검사 : 회원가입 후 검색한 노래가 없다면
                           view.recentSong(null);   // 최근 조회한 노래정보 XXX
                           continue;
                        }
                        GenieVO gVO = new GenieVO();
                        gVO.setGnum(pVO.getGnum());
                        view.recentSong(gDAO.selectOne(gVO)); // 노래의 PK로 노래 정보 조회
                     } else if (view.num == 2) { // 2. 회원 탈퇴 ---------★★★★★★★★★★★★★ 수정(7.20)
                        String ans = view.userWithdrawal(); // 탈퇴 여부 확인
                        if (ans.equals("N")) { // 탈퇴여부 : N 입력
                           view.goback(); // 마이페이지 메인으로 이동
                           continue;
                        }
                        MemberVO mvo = new MemberVO();
                        // 탈퇴여부 : Y를 입력하면 --> 비밀번호가 일치해야 탈퇴
                        int chance = 3; // 비밀번호 입력 --> 3회의 기회
                        for (chance = 3; chance > 0; chance--) {
                           view.leftChance(chance); // 남은 입력 가능 횟수 안내
                           view.userPW();
                           String password = view.inputString(); // 비밀번호 입력
                           mvo.setMpw(password);
                           mvo.setMnum(mVO.getMnum()); // 사용자 PK -> 마이데이터에서 삭제

                           MypageVO pVO = new MypageVO();
                           pVO.setMembernum(mvo.getMnum());

                           // MEMBER, MYPAGE에서 모두 삭제 필요
                           if (mDAO.delete(mvo)) { // MEMBER 에서 삭제 --> MYPAGE에서 삭제
                              pVO = pDAO.selectOne(pVO);
                              // 1. MYPAGE에 저장 --> MYPAGE에서도 삭제 
                              // 2. 회원가입 후 노래를 한번도 검색하지 않은 경우 --> MYPAGE에 저장XX --> MYPAGE 삭제 내용 XX
                              boolean delFlag = (pVO != null && pDAO.delete(pVO)) || pVO == null;
                              if(delFlag) {
                                 view.withdrawlComplete();
                                 view.goMain();
                                 flag = true;  // 처음 로그인 화면으로 돌아가기
                                 break;
                              }
                           }
                           view.wrongInput(); // 입력정보 불일치
                        }
                        if (chance == 0) { // 3회 연속 비밀번호 불일치
                           view.withdrawlFail(); // 회원탈퇴 중단
                           view.goback(); // 마이 페이지 메인으로 이동
                           continue;
                        }
                     } else if (view.num == 3) { // 3. 마이페이지 종료
                        view.goMain();
                        break;
                     }
                  }
               } else if (view.num == 4) { // 노래 추천
                  MypageVO pVO = new MypageVO();
                  pVO.setMembernum(mVO.getMnum()); // 회원 PK를 통해

                  // 1 ~ 5위의 노래 중
                  GenieVO gVO = pDAO.selectRandom(pVO); // 최근 검색한 노래 제외 후
                  // 랜덤 노래 추천 1곡

                  // 로그 : 랜덤 노래 추천 수행 여부
                  // if(gVO == null) {
                  // System.out.println("로그 : 추천 노래 없음");
                  // view.goMain();
                  // continue;
                  // }
                  view.recommendSong(gVO); // 노래 추천
               } else if (view.num == 5) { // 로그아웃
                  break;
               }
            }
         } else if (view.num == 2) { // 회원가입
            MemberVO mVO = new MemberVO();
            view.userID();
            String userID = view.inputString();
            view.userPW();
            String userPW = view.inputString();
            view.userName();
            String userName = view.inputString();
            mVO.setMid(userID);
            mVO.setMpw(userPW);
            mVO.setMname(userName);
            if (!(mDAO.insert(mVO))) {
               view.sameId();
               continue;
            }
            view.welcomeMsg();
         } else if (view.num == 3) {
            view.end();
            break;
         }
      }

   }

}