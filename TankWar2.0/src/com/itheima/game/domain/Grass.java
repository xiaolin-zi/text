package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;
//草墙类
public class Grass extends Element{
	
	//构造方法，带参构造
	public Grass(int x,int y) {
		super(x, y);
		
		//设置图片的宽和高
		try {
			int[]size= DrawUtils.getSize("res\\img\\grass.gif");
			width=size[0];
			height=size[1];
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//常用方法
	//1.绘制砖块
	public void draw() {
		try {
			DrawUtils.draw("res\\img\\grass.gif", x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//设置草坪的级别
	@Override
	public int getOrder() {
		return 1;
	}
	
}
