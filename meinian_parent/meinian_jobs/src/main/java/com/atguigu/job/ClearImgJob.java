package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
//import org.apache.log4j.Logger;
import com.atguigu.utils.QiniuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Iterator;
import java.util.Set;

/**
 * @author steve
 * @since 2021-07-02 20:28
 */

@SuppressWarnings("all")
public class ClearImgJob {

    private static final Logger log = LoggerFactory.getLogger(ClearImgJob.class);


    @Autowired
    private JedisPool jedisPool;

    /**
     * 半夜 2 点删除图片，维护网站
     */
    public void clearImg() {
        //Redis Sdiff 命令返回第一个集合与其他集合之间的差异，也可以认为说第一个集合中独有的元素。
        Set<String> set = jedisPool.getResource().sdiff(
                RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES
        );

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            String pic = iterator.next();
            log.debug("待删除的图片 = {}" , pic);
            System.out.println("待删除的图片 = {}" + pic);
            //删除七牛云图片服务器中的图片文件
            QiniuUtils.deleteFileFromQiniu(pic);
            // 删除 Redis 中的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,pic);

        }
    }


}
