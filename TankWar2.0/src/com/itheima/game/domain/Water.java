package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import com.itheima.game.business.Blockable;
//水墙类
public class Water extends Element implements Blockable{
	//属性

	//构造方法，带参构造
	public Water(int x,int y) {
		super(x, y);
		//设置图片的宽和高
		try {
			int[]size= DrawUtils.getSize("res\\img\\water.gif");
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
			DrawUtils.draw("res\\img\\water.gif", x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	//2.挨打
}
