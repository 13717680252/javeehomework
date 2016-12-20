package cn.edu.bjtu.weibo.dao;

import java.util.List;
//去请大家传weiboId的时候传这种格式的“Weibo:w00001”,谢谢
public interface WeiboDAOInterface {
	public String getOwner(String weiboId);
	
	public String getContent(String weiboId);
	public boolean updateContent(String weiboId, String content);
	
	public String getTime(String weiboId);
	
	public List<String> getWeiboPicurlOr(String weiboId);
	
	public List<String> getWeiboPicurlTh(String weiboId);
	
	public String getLikeNumber(String weiboId);
	
	public String getCommentNumber(String weiboId);
	
	public String getForwardNumber(String weiboId);
	
	public List<String> getLikeLIst(String weiboId);
	
	public List<String> getForwardList(String weiboId);
	
	public List<String> getCommentList(String weiboId);
	
  public List<String> getLikeLIst(String weiboId, int pageIndex, int numberPerPage);
	
	public List<String> getForwardList(String weiboId, int pageIndex, int numberPerPage);
	
	public List<String> getCommentList(String weiboId, int pageIndex, int numberPerPage);
	
	public boolean deleteWeibo(String weiboId);
	
	public boolean addLikeNumber(String weiboId,String LikeName);
	
	public boolean CreateWeibo(String Owner,String Content,String time,String PicurlOr,String PicurlTh);
	
	public boolean addComment(String weiboId,String content);
	
	public boolean addForward(String weiboId,String FOrwardName);
}
