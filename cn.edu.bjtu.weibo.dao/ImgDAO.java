package cn.edu.bjtu.weibo.dao;

import java.util.List;

/**
 * 
 * @author ji
 *
 */
public interface ImgDAO {
	/**
	 * 
	 * @param time
	 * @param imgOrUrl
	 * @param ImgThurl
	 * @return the imgurl id
	 */
	String  insert(String time,String imgOrUrl,String ImgThurl);
	String  getimgOrUrl(String imgid);
	String  getimgThUrl(String imgid);
	String  getLikeNumber(String imgid);
	List<String> getLikeList(String imgid);

	boolean updateLikeList(String imgid,String userid);
	boolean delete(String imgid);

	
}
