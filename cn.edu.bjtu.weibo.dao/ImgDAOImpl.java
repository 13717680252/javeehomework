package cn.edu.bjtu.weibo.dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class ImgDAOImpl implements ImgDAO {
	Jedis jedis=null;
	public ImgDAOImpl() {
		jedis = new Jedis("localhost", 6379);
	}

	@Override
	public String insert(String time, String imgOrUrl, String ImgThurl) {
		long count=jedis.incr("imgNumber");
		String str = String.format("%05d", count);
		String key = "image:img" + str + ":";
		jedis.set(key+"time",time);
		jedis.set(key+"imgOrUrl", imgOrUrl);
		jedis.set(key="imgThUrl", ImgThurl);
		jedis.set(key+"likeNumber", "0");
		return null;
	}

	@Override
	public String getimgOrUrl(String imgid) {
		
		return jedis.get("image:"+imgid+"imgOrUrl");
	}

	@Override
	public String getimgThUrl(String imgid) {
		return jedis.get("image:"+imgid+"imgThUrl");
	}

	@Override
	public String getLikeNumber(String imgid) {
		return jedis.get("image:"+imgid+"likeNumber");
	}

	@Override
	public List<String> getLikeList(String imgid) {
		return jedis.lrange("image:"+imgid+"like",0,-1);
	}


	@Override
	public boolean delete(String imgid) {
		Set<String> set = jedis.keys("image:"+imgid+ ":*");
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
	public boolean updateLikeList(String imgid,String userid) {
	jedis.lpush("image:"+imgid+"like", userid);
		return true;
	}


}
