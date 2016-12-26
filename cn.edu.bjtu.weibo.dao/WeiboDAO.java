package cn.edu.bjtu.weibo.dao;

import java.util.List;
//去请大家传weiboId的时候传这种格式的“Weibo:w00001”,谢谢

import cn.edu.bjtu.weibo.model.Weibo;

public interface WeiboDAO {

	 int getTotalWeiboNumber();
	 List<String> getTotalWeibo();
	 
	 /**
	  * create a new weibo in the system, it will automatically update the number total weibo Number
	  * @param weibo
	  * @return
	  */
	 String insertNewWeibo(Weibo weibo);
	 boolean deleteWeibo(String weiboId);
	 Weibo getWeibo(String weiboId);
	 
	 /**
	  * weibo content related
	  * @param weiboId
	  * @return
	  */
	 String getOwner(String weiboId);
	 
	 String getContent(String weiboId);
	 boolean updateContent(String weiboId, String content);
	
	 String getSendTime(String weiboId);
	 
	 /**
	  * weibo attached picture related operation
	  * @param weiboId
	  * @param picId
	  * @return
	  */
	 boolean insertWeiboPicture(String weiboId, String picId);
	 boolean deleteWeiboPicture(String weiboId, String picId);
	 List<String> getWeiboPicurl(String weiboId);
	 
	/**
	 * get the like list, which contain all the user id who like this weibo, when insert or delete, the like number will be automatically updated
	 * @param weiboId
	 * @param userId
	 * @return
	 */
	 boolean insertLikeList(String weiboId,String userId);
	 boolean deleteLikeList(String weiboId,String userId);
	 List<String> getLikeList(String weiboId, int pageIndex, int numberPerPage);
	 int getLikeNumber(String weiboId);
	
	 /**
	  * get the comment list which contain all the commentId which comment this weibo
	  * @param weiboId
	  * @param commentId
	  * @return
	  */
	 boolean insertCommentList(String weiboId,String commentId);
	 boolean deleteCommentList(String weiboId,String commentId);
	 List<String> getCommentList(String weiboId, int pageIndex, int numberPerPage);
	 int getCommentNumber(String weiboId);
	 
	 /**
	  * when forward this weibo, it means another user just send a new weibo with this weibo attached in the end
	  * @param weiboId
	  * @param forwardWeiboId
	  * @return
	  */
	 boolean insertForwardList(String weiboId,String forwardWeiboId);
	 boolean deleteForwardList(String weiboId,String forwardWeiboId);
	 List<String> getForwardList(String weiboId, int pageIndex, int numberPerPage);
	 int getForwardNumber(String weiboId);
	 
	 boolean deleteCommentFromWeibo(String fromWeiboId, String commentId);
	 
	 List<String> getAtUserList(String weiboId);
	 
	 List<String> getTopicList(String weiboId);
}

