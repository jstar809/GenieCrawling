package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MypageDAO {
   Connection conn;
   PreparedStatement pstmt;
   final String sql_insert = "INSERT INTO MYPAGE VALUES((SELECT NVL(MAX(MYNUM),0)+1 FROM MYPAGE), ?, ?)";
   final String sql_selectOne = "SELECT * FROM MYPAGE WHERE MEMBERNUM=?";
   final String sql_update = "UPDATE MYPAGE SET GNUM=? WHERE MEMBERNUM = ?";
   final String sql_random = "SELECT A.* FROM (SELECT RANK() OVER (ORDER BY VIEWNUM DESC), TITLE, ARTIST FROM (SELECT * FROM GENIE WHERE GNUM NOT IN(SELECT GNUM FROM MYPAGE) ORDER BY VIEWNUM desc )  WHERE (ROWNUM BETWEEN 1 AND 5)  ORDER BY DBMS_RANDOM.RANDOM)A WHERE ROWNUM<=1";
   final String sql_delete = "DELETE FROM MYPAGE WHERE MEMBERNUM=?";
   // sql문으로 지니 테이블에 있는 변수를 넣는 sql문 만들기
   // 최근 검색한 노래 지니 테이블에서 받아온것 저장
   public boolean insert(MypageVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_insert);
         pstmt.setInt(1, vo.getGnum()); // 첫번째
         pstmt.setInt(2, vo.getMembernum()); // 두번째
         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }

   public boolean update(MypageVO vo) {
      conn = JDBCUtil.connect();
         try {
            pstmt = conn.prepareStatement(sql_update);
            pstmt.setInt(1, vo.getGnum()); // 첫번째
            pstmt.setInt(2, vo.getMembernum()); // 두번째
            int res=pstmt.executeUpdate();
            if(res==0) {
                  return false;
               }
         } catch (SQLException e) {
            e.printStackTrace();
            return false;
         } finally {
            JDBCUtil.disconnect(pstmt, conn);
         }
         return true;
   }

   // 최근 검색한 노래 보여주기
   public MypageVO selectOne(MypageVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_selectOne);
         pstmt.setInt(1, vo.getMembernum());
         ResultSet rs = pstmt.executeQuery();
         // selectone 은 하나가나오는것이 고정이라 while 대신 if문 상용
         if (rs.next()) {
            MypageVO data = new MypageVO();
            data.setMynum(rs.getInt("mynum"));
            data.setGnum(rs.getInt("gnum"));
            data.setMembernum(rs.getInt("membernum"));
            return data;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return null;
   }

   // 노래 1 ~ 5위 랜덤 추천
   public GenieVO selectRandom(MypageVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_random);
//         pstmt.setInt(1, vo.getGnum());
         ResultSet rs = pstmt.executeQuery();
         // selectone 은 하나가나오는것이 고정이라 while 대신 if문 상용
         if (rs.next()) {
            GenieVO data = new GenieVO();
            data.setTitle(rs.getString("title"));
            data.setArtist(rs.getString("artist"));
            return data;
         }
         rs.close();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return null;
      // pk값을 비교해서
   }
   public boolean delete(MypageVO vo) {
		conn = JDBCUtil.connect();
		
		try {
			pstmt = conn.prepareStatement(sql_delete);
			pstmt.setInt(1, vo.getMembernum());
			int res = pstmt.executeUpdate();
			if(res <= 0) {
				return false;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}
}