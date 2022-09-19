package model;

public class GenieVO {
	private int Gnum;
	private String title;
	private String artist;
	private String album;
	private int viewnum;


	public int getGnum() {
		return Gnum;
	}



	public void setGnum(int gnum) {
		Gnum = gnum;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getViewnum() {
		return viewnum;
	}

	public void setViewnum(int viewnum) {
		this.viewnum = viewnum;
	}

	@Override
	public String toString() {
		return "GenieVO [num=" + Gnum + ", title=" + title + ", artist=" + artist + ", album=" + album + ", viewnum="
				+ viewnum + "]";
	}




}