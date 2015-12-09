package test.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import sql.DataSourceTestConfig;
import sql.UserServiceConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.io.File;

/**
 * Created by andy on 12/7/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration( classes = {DataSourceTestConfig.class, UserServiceConfig.class}, loader = AnnotationConfigContextLoader.class)
public class QiniuTest {


    String ak="Ve1UX8HxkUbwUV2Qnzc2uCJ0iyWRP2MUTOPZrSRA";
    String sk="-jna1k0os4NuCeR8yV8xfJOeEBuuUkzF0kXoOgfY";
    @Test
    public void uploadFile(){
        Auth auth = Auth.create(ak, sk);

        UploadManager uploadManager=new UploadManager();

        Response response= null;
        try {
            File file=new File("/Users/andy/Documents/github/andy012/faceSwiping/src/main/resources/test.jpg");
            response = uploadManager.put(file, "12345", auth.uploadToken("face"));
        } catch (QiniuException e) {
            e.printStackTrace();
        }

        System.out.println(response.toString());
    }
       @Test
    public void downloadFile(){
        Auth auth = Auth.create(ak, sk);

        UploadManager uploadManager=new UploadManager();
        Response response= null;
        String url="http://7xozaz.com1.z0.glb.clouddn.com/12345";
        System.out.println(auth.privateDownloadUrl(url));
    }

}
