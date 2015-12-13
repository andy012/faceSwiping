package com.face.util;

import com.face.data.youtu.Base.DetectionFaceResponse;
import com.face.service.youtu.sign.Base64Util;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * Created by andy on 12/12/15.
 */
public class CutPicture {
    public static String imgToBase64String(BufferedImage bufferedImage) {
//        final ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(img, "png", java.util.Base64.getEncoder().wrap(os));
//            return os.toString(StandardCharsets.ISO_8859_1.name());
//        } catch (final IOException ioe) {
//            throw new UncheckedIOException(ioe);
//        }
        ByteArrayOutputStream bao=new ByteArrayOutputStream();

        try {
            ImageIO.write(bufferedImage, "png",bao);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Base64Util.encode(bao.toByteArray());
    }
    public static String cropImage(BufferedImage bufferedImage,
                          int x,int y,int width,int height) throws IOException {



        x=x-10;
        x= x>=0 ? x:0;
        y=y-10;
        y= y>0 ? y:0;
        width=width+20;
        width = width < bufferedImage.getWidth()? width:bufferedImage.getWidth();
        height=height+20;
        height= height < bufferedImage.getHeight()? height:bufferedImage.getHeight();

        BufferedImage bi=bufferedImage.getSubimage(x, y, width, height);

        try {
            ImageIO.write(bi, "png", new File(x+"-"+y+"-"+width+"file.png"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return imgToBase64String(bi);
    }


    public static BufferedImage getBufferImageByUser(String imageUrl) throws IOException {
        BufferedImage bufferedImage=null;
        InputStream input=null;
        try {
            input= new URL(imageUrl).openStream();
            //iis = ImageIO.createImageInputStream(input);
            bufferedImage=ImageIO.read(input);//.read(iis);


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(input!=null){
                input.close();
            }
        }
        return bufferedImage;
    }

    public static void findAllFaces(String imageUrl,DetectionFaceResponse detectionFaceResponse) throws IOException {
        //ImageInputStream iis=null;

        try {
            //iis = ImageIO.createImageInputStream(input);
            BufferedImage bufferedImage=getBufferImageByUser(imageUrl);//.read(iis);
            for(DetectionFaceResponse.FaceEntity faceEntity:detectionFaceResponse.getFace()) {
                faceEntity.setBase64(cropImage(bufferedImage, faceEntity.getX(), faceEntity.getY(), faceEntity.getWidth(), faceEntity.getHeight()));
                //System.out.println(cropImage(bufferedImage, 10, 10, 20, 20));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {

       // findAllFaces("http://7xoz2q.com1.z0.glb.clouddn.com/git_3.jpg");

    }


}
