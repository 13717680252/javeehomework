package cn.edu.bjtu.weibo.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class WeiboDAOImpl implements WeiboDAOInterface {
	Jedis jedis;

	public WeiboDAOImpl() {
		jedis = new Jedis("localhost", 6379);
	}

	@Override
	public String getOwner(String weiboId) {
		String key = weiboId + ":Owner";
		return jedis.get(key);
	}

	@Override
	public String getContent(String weiboId) {
		String key = weiboId + ":Weibocontent";
		return jedis.get(key);
	}

	@Override
	public boolean updateContent(String weiboId, String content) {
		String s = jedis.set(weiboId + ":Weibocontent", content);
		System.out.println(s);
		if (s == "OK")
			return true;
		return false;
	}

	@Override
	public String getTime(String weiboId) {
		String key = weiboId + ":WeiboTime";
		return jedis.get(key);
	}

	@Override
	public List<String> getWeiboPicurlOr(String weiboId) {
		String key = weiboId + ":WeiboPicurlOr";
		List<String> list = jedis.lrange(key, 0, -1);
		return list;
	}

	@Override
	public List<String> getWeiboPicurlTh(String weiboId) {
		String key = weiboId + ":WeiboPicurlTh";
		List<String> list = jedis.lrange(key, 0, -1);
		return list;
	}

	@Override
	public String getLikeNumber(String weiboId) {
		String key = weiboId + ":LikeNumber";
		return jedis.get(key);
	}

	@Override
	public String getCommentNumber(String weiboId) {
		String key = weiboId + ":CommentNumber";
		return jedis.get(key);
	}

	@Override
	public String getForwardNumber(String weiboId) {
		String key = weiboId + ":ForwardNumber";
		return jedis.get(key);
	}

	@Override
	public List<String> getLikeLIst(String weiboId) {
		String key = weiboId + ":LikeLIst";
		List<String> list = jedis.lrange(key, 0, -1);
		return list;
	}

	@Override
	public List<String> getForwardList(String weiboId) {
		String key = weiboId + ":ForwardList";
		List<String> list = jedis.lrange(key, 0, -1);
		return list;
	}

	@Override
	public List<String> getCommentList(String weiboId) {
		String key = weiboId + ":CommentList";
		List<String> list = jedis.lrange(key, 0, -1);
		return list;
	}

	@Override
	public boolean deleteWeibo(String weiboId) {
		Set<String> set = jedis.keys(weiboId + ":*");
		Iterator<String> it = set.iterator();
		int count = 0;
		while (it.hasNext()) {
			String keyStr = it.next();
			System.out.println(keyStr);
			jedis.del(keyStr);
			count++;
		}

		if (count == 0)
			return false;
		return true;
	}

	@Override
	public boolean addLikeNumber(String weiboId, String LikeName) {
		String key = weiboId + ":LikeNumber";
		jedis.incr(key);
		key = weiboId + ":LikeList";
		jedis.lpush(key, LikeName);
		return true;
	}

	@Override
	public boolean addComment(String weiboId, String content) {
		String key = weiboId + ":CommentNumber";
		jedis.incr(key);
		key = weiboId + ":CommentList";
		jedis.lpush(key, content);
		return false;
	}

	@Override
	public boolean addForward(String weiboId, String ForwardName) {
		String key = weiboId + ":ForwardNumber";
		jedis.incr(key);
		key = weiboId + ":ForwardList";
		jedis.lpush(key, ForwardName);
		return false;
	}

	@Override
	public boolean CreateWeibo(String Owner, String Content, String time,
			String PicurlOr, String PicurlTh) {
		String key = "WeiboCount";
		long count = jedis.incr(key);
		String str = String.format("%05d", count);
		key = "Weibo:w" + str + ":";
		jedis.set(key + "Owner", Owner);
		jedis.set(key + "WeiboId", str);
		jedis.set(key + "Weibocontent", Content);
		jedis.set(key + "WeiboTime", time);
		jedis.set(key + "WeibopicurlOr", PicurlOr);
		jedis.set(key + "WeibopicurlTh", PicurlTh);
		jedis.set(key + "CommentNumber","0");
		jedis.set(key + "ForwardNumber","0");
		jedis.set(key + "LikeNumber","0");
		return true;

	}

	@Override
	public List<String> getLikeLIst(String weiboId, int pageIndex,
			int numberPerPage) {
		String key = weiboId + ":LikeLIst";
		List<String> list = jedis.lrange(key, 0, -1);
		int start=pageIndex*numberPerPage;
	    int end=start+numberPerPage;
	    if(pageIndex<0||numberPerPage<=0||end>=list.size()){
	    	return null;
	    }
	    
		return list.subList(start, end);
	}

	@Override
	public List<String> getForwardList(String weiboId, int pageIndex,
			int numberPerPage) {
		String key = weiboId + ":ForwardList";
		List<String> list = jedis.lrange(key, 0, -1);
		int start=pageIndex*numberPerPage;
	    int end=start+numberPerPage;
	    if(pageIndex<0||numberPerPage<=0||end>=list.size()){
	    	return null;
	    }
	    
		return list.subList(start, end);
	}

	@Override
	public List<String> getCommentList(String weiboId, int pageIndex,
			int numberPerPage) {
		String key = weiboId + ":CommentList";
		List<String> list = jedis.lrange(key, 0, -1);
		int start=pageIndex*numberPerPage;
	    int end=start+numberPerPage;
	    if(pageIndex<0||numberPerPage<=0||end>=list.size()){
	    	return null;
	    }
	    
		return list.subList(start, end);
	}

}
