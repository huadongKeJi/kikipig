package com.kikipig.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageVerificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Random random = new Random();
	 private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
	     if(fc > 255)
	         fc=255;
	     if(bc > 255)
	         bc=255;
	     int r=fc + random.nextInt(bc - fc);
	     int g=fc + random.nextInt(bc - fc);
	     int b=fc + random.nextInt(bc - fc);
	     return new Color(r, g, b);
	 }
	 public void createCode(HttpServletRequest request, HttpServletResponse response) {
        // 在内存中创建图象
        int width=120, height=40; // 图象大小
        BufferedImage image=new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics2D g=image.createGraphics();
        g.setColor(new Color(255, 255, 255)); // 设定背景色
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.BOLD, 32)); // 设定字体
        Color c = getRandColor(160, 200);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(c);
        for(int i=0; i < 30; i++) {
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            int xl=random.nextInt(12);
            int yl=random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 添加噪点  
//        float yawpRate = 0.05f;// 噪声率  
//        int area = (int) (yawpRate * width * height);  
//        for (int i = 0; i < area; i++) {  
//            int x = random.nextInt(width);  
//            int y = random.nextInt(height);  
//            int rgb = getRandomIntColor();  
//            image.setRGB(x, y, rgb);  
//        }  
      //随机生成不同的字体、字体样式和字体大小
        String[] fontName = {"微软雅黑","黑体","Georgia","Verdana","Arial","Comic Sans MS","Lucida Console"};
        int [] fontEffect = {Font.PLAIN, Font.ITALIC, Font.BOLD};
        int [] fontSize = {28, 30, 32, 26};
        Font[] fonts = new Font[fontName.length*fontEffect.length*fontSize.length];
        int fontsIndex=0;
        for(String str: fontName){
            for(int effect: fontEffect){
                for(int size : fontSize){
                    Font font = new Font(str, effect, size);
                    fonts[fontsIndex]=font;
                    fontsIndex = fontsIndex +1;
                }
            }
        }
        String s="abcdefghijknpqrstuvxyzABCDEFGHJKLNPQRSTUVXYZ23456789"; // 设置备选验证码
        String sRand=""; // 用随机产生的颜色将验证码绘制到图像中。
        int length = 4;  // 设置默认生成4个验证码
        for(int i=0;i<length;i++){
        	g.setColor(new Color(20+random.nextInt(110), 20+random.nextInt(110), 20+random.nextInt(110))); // 生成随机颜色(因为是做前景，所以偏深)
            g.setFont(fonts[random.nextInt(fonts.length)]);  //调用上方的随机字体
            String ch = String.valueOf(s.charAt(random.nextInt(s.length())));
            //设置字体旋转
            int zhuan = random.nextInt(20);
            int fzhuan = -random.nextInt(20);
            g.rotate(Math.toRadians(zhuan),25*(i-1),20);     /// 坐标系顺时针转
            g.rotate(Math.toRadians(fzhuan),25*(i-1),20);    /// 坐标系逆时针转
            sRand+=ch;
            g.drawString(ch, 18 * i + 15, 30); //将认证码用 drawString 函数显示到图象里
            g.rotate(Math.toRadians(-1*zhuan),25*(i-1),20);
            g.rotate(Math.toRadians(-1*fzhuan),25*(i-1),20);
        } 
//	        //在认证码的上端画一条不规则的线
//	        int visit[] = new int[6];  
//	        for (int i = 0; i < visit.length; i++) {  
//	            visit[i] = 1 + (int) (Math.random() * 10);  
//	        }
//	        g.setColor(Color.BLACK);  
//	        int drawHigh[] = new int[6];  
//	        int drawwidth[] = new int[6];  
//	        //折点坐标  
//	        for (int i = 0; i < 6; i++) {  
//	           drawHigh[i] = 40 - (int) (Math.ceil(visit[i] * 3.8));  
//	           drawwidth[i] = 5 + i * 17;  
//	        }  
//	        g.setStroke(new BasicStroke(3.0f));  //折线粗细
//	        g.setPaint(Color.gray);//折线的颜色 
//	        g.drawPolyline(drawwidth, drawHigh, 6);  //画折线 
        	request.getSession().setAttribute("image_validate_code", sRand);
	        // 图象生效
	        g.dispose();
	        try {
	            ImageIO.write(image, "JPEG", response.getOutputStream()); // 输出图象到页面
	        } catch(IOException e) {
	        }
	    }
	 
//	    private static int getRandomIntColor() {  
//	        int[] rgb = getRandomRgb();  
//	        int color = 0;  
//	        for (int c : rgb) {  
//	            color = color << 8;  
//	            color = color | c;  
//	        }  
//	        return color;  
//	    }  
//	    private static int[] getRandomRgb() {  
//	        int[] rgb = new int[3]; 
//	        Random random = new Random();
//	        for (int i = 0; i < 3; i++) {  
//	            rgb[i] = random.nextInt(255);  
//	        }  
//	        return rgb;  
//	    }  
	@Override
	protected void doGet(HttpServletRequest reqeust,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(reqeust, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// 告诉客户端，输出的格式  
		// 设置页面不缓存
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        createCode(request, response);
	}
}