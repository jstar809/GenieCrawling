package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//로그인 회원가입 회원탈퇴
public class MemberDAO {
   Connection conn;
   PreparedStatement pstmt;

   final String sql_login = "SELECT * FROM MEMBER WHERE MID=?";
   final String sql_insert = "INSERT INTO MEMBER VALUES((SELECT NVL(MAX(MNUM),0)+1 FROM MEMBER),?,?,?)";
   final String sql_delete = "DELETE FROM MEMBER WHERE MPW=?";
   final String sql_selectAll = "SELECT * FROM MEMBER";

   // 로그인
   public MemberVO login(MemberVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_login);
         pstmt.setString(1, vo.getMid());
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) {
            if (rs.getString("Mpw").equals(vo.getMpw())) {
               MemberVO data = new MemberVO();
               data.setMnum(rs.getInt("Mnum"));
               data.setMid(rs.getString("mid"));
               data.setMname(rs.getString("mname"));
               data.setMpw(rs.getString("mpw"));
               // 로그인 성공
               return data;
            }
            // 비밀번호 불일치로 실패
            return null;
         }
         // 로그인 실패
      } catch (SQLException e) {
         e.printStackTrace();
      }
      return null;
   }

   // 회원가입
   public boolean insert(MemberVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_login); // 아이디 중복 체크
         pstmt.setString(1, vo.getMid()); //
         ResultSet rs = pstmt.executeQuery();
         if (rs.next()) { // 중복된 아이디 값 존재하면 중단
            return false;
         }

         pstmt = conn.prepareStatement(sql_insert);
         pstmt.setString(1, vo.getMid()); // 아이디
         pstmt.setString(2, vo.getMpw()); // 비밀번호
         pstmt.setString(3, vo.getMname()); // 이름
         pstmt.executeUpdate();
//         System.out.println("로그 : 실행 완료");
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }

   // 회원 정보 삭제 >> 비밀번호 입력 시 탈퇴
   public boolean delete(MemberVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_delete);
         pstmt.setString(1, vo.getMpw());
         int res = pstmt.executeUpdate();
         if(res <= 0) {
        	 return false;
         }
         // 회원삭제 완료
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }
}