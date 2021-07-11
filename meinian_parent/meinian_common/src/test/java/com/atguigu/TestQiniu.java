package com.atguigu;

import com.atguigu.utils.QiniuUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class TestQiniu {

    String accessKey = "27GGTEcuI4aN_j0muEPptiiuIUckYkSZXYJSF102";
    String secretKey = "vhHfwOiyrrA0CSBbDAiZJNYCPLbfFDUP_livuxuz";
    String bucket = "java-steve";

    // 上传本地文件
    @Test
    public void uploadFile() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        //如果是Windows情况下，格式是 D:\\qiniu\\test.png，可支持中文
        String localFilePath = "D:\\02.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    // 删除空间中的文件
    @Test
    public void deleteFile() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释

        // 带删除的文件名
        String key = "11.jpeg";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    /**
     * 上传音频
     */
    @Test
    public void testMP3(){
        String filePath = "d:\\Users\\pro13\\Music\\荒人邪影-孙敬凡.mp3";
        QiniuUtils.upload2Qiniu(filePath,"music");
        System.out.println("上传成功");
    }

    @Test
    public void testMp4(){
        String filePath = "d:\\Users\\pro13\\Videos\\1.mp4";
        QiniuUtils.upload2Qiniu(filePath,"video");
        System.out.println("上传成功");
    }

    private Integer a ;
    private int i;

    @Test
    public void test(){

        System.out.println("a = " + a);
        System.out.println("i = " + i);
    }



}