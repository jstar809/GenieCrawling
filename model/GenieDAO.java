package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GenieDAO {
   Connection conn;
   PreparedStatement pstmt;
   final String sql_insert = "INSERT INTO GENIE VALUES((SELECT NVL(MAX(GNUM),0)+1 FROM GENIE),?,?,?,?)";
   final String sql_selectAll = "SELECT * FROM GENIE ORDER BY VIEWNUM DESC";
   final String sql_selectAll_title = "SELECT * FROM GENIE WHERE UPPER(TITLE) LIKE UPPER('%'||?||'%')";
   final String sql_selectAll_artist = "SELECT * FROM GENIE WHERE UPPER(ARTIST) LIKE UPPER('%'||?||'%')";
   final String sql_selectOne = "SELECT * FROM GENIE WHERE GNUM=?";
   final String sql_sample = "SELECT COUNT(*) AS CNT FROM GENIE";
   final String sql_update = "UPDATE GENIE SET TITLE=?, ARTIST=?, ALBUM=?, VIEWNUM=VIEWNUM+1 WHERE GNUM=?";
   // Crawling data
   public boolean insert(GenieVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_insert);
         pstmt.setString(1, vo.getTitle()); // 첫번째
         pstmt.setString(2, vo.getArtist()); // 두번째
         pstmt.setString(3, vo.getAlbum()); // 세번째 value
         pstmt.setInt(4, vo.getViewnum()); // 네번째 value

         pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return true;
   }
   public boolean update(GenieVO vo) {
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_update);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getArtist());
			pstmt.setString(3, vo.getAlbum());
			pstmt.setInt(4, vo.getGnum());
			pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

   // 차트 출력
   // selectAll 이므로 배열리스트 output, GenieVo vo input
   public ArrayList<GenieVO> selectAll(GenieVO vo) {
      // 차트를 받아오기 위해 JDBC연결
      conn = JDBCUtil.connect();
      ArrayList<GenieVO> datas = new ArrayList<GenieVO>();
      try {
         // stmt(Statement)
         // 일단 만들고, 직접 쿼리문(SQL문)을 메소드의 인자로 전달
         // pstmt(PreparedStatement)
         // 만들 당시에 쿼리문(SQL문)을 전달하고,
         // 이후에 값을 메소드의 인자로 전달
         pstmt = conn.prepareStatement(sql_selectAll);
         ResultSet rs = pstmt.executeQuery();
         // rs.next()를 무한반복 , 안나올때까지
         while (rs.next()) {
            GenieVO data = new GenieVO();
            data.setGnum(rs.getInt("gnum"));
            data.setTitle(rs.getString("title"));
            data.setArtist(rs.getString("artist"));
            data.setAlbum(rs.getString("album"));
            data.setViewnum(rs.getInt("viewnum"));
            datas.add(data);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      // 선언한 배열 datas에 저장
      return datas;
   }

   // 노래이름으로 검색
   public ArrayList<GenieVO> selectAll_title(GenieVO vo) {
      // 차트를 받아오기 위해 JDBC연결
      conn = JDBCUtil.connect();
      ArrayList<GenieVO> datas = new ArrayList<GenieVO>();
      try {
         // stmt(Statement)
         // 일단 만들고, 직접 쿼리문(SQL문)을 메소드의 인자로 전달
         // pstmt(PreparedStatement)
         // 만들 당시에 쿼리문(SQL문)을 전달하고,
         // 이후에 값을 메소드의 인자로 전달
    	 
         pstmt = conn.prepareStatement(sql_selectAll_title);
         pstmt.setString(1, vo.getTitle());
         ResultSet rs = pstmt.executeQuery();
         // rs.next()를 무한반복 , 안나올때까지
         while (rs.next()) {
            GenieVO data = new GenieVO();
            data.setGnum(rs.getInt("gnum"));
            data.setTitle(rs.getString("title"));
            data.setArtist(rs.getString("artist"));
            data.setAlbum(rs.getString("album"));
            data.setViewnum(rs.getInt("viewnum"));
            datas.add(data);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      // 선언한 배열 datas에 저장
      return datas;
   }

   // 가수이름으로 검색
   public ArrayList<GenieVO> selectAll_artist(GenieVO vo) {
      // 차트를 받아오기 위해 JDBC연결
      conn = JDBCUtil.connect();
      ArrayList<GenieVO> datas = new ArrayList<GenieVO>();
      try {
         // stmt(Statement)
         // 일단 만들고, 직접 쿼리문(SQL문)을 메소드의 인자로 전달
         // pstmt(PreparedStatement)
         // 만들 당시에 쿼리문(SQL문)을 전달하고,
         // 이후에 값을 메소드의 인자로 전달
         pstmt = conn.prepareStatement(sql_selectAll_artist);
         pstmt.setString(1, vo.getArtist());
         ResultSet rs = pstmt.executeQuery();
         // rs.next()를 무한반복 , 안나올때까지
         while (rs.next()) {
            GenieVO data = new GenieVO();
            data.setGnum(rs.getInt("gnum"));
            data.setTitle(rs.getString("title"));
            data.setArtist(rs.getString("artist"));
            data.setAlbum(rs.getString("album"));
            data.setViewnum(rs.getInt("viewnum"));
            datas.add(data);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      // 선언한 배열 datas에 저장
      return datas;
   }

   // 노래 검색
   // 노래 들 중 하나를 선택하는 것이라 output GenieVO 선택
   public GenieVO selectOne(GenieVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_selectOne);
         pstmt.setInt(1, vo.getGnum());
         ResultSet rs = pstmt.executeQuery();
         // selectone 은 하나가나오는것이 고정이라 while 대신 if문 상용
         
         if (rs.next()) {
            GenieVO data = new GenieVO();
            data.setGnum(rs.getInt("gnum"));
            data.setTitle(rs.getString("Title"));
            data.setArtist(rs.getString("artist"));
            data.setAlbum(rs.getString("album"));
            data.setViewnum(rs.getInt("viewnum"));
            return data;
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
      return null;
   }

   // 샘플데이터가 있는지?
   public boolean hasSample(GenieVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_sample);
         ResultSet rs = pstmt.executeQuery();
         rs.next();
         int cnt = rs.getInt("CNT");
         if (cnt >= 1) {
            return true;
         }
         return false;
      } catch (SQLException e) {
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn);
      }
   }
}