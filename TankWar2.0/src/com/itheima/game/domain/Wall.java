package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;
//砖墙类

import com.itheima.game.business.Blockable;
import com.itheima.game.business.Destroyable;
import com.itheima.game.business.Hitable;
public class Wall extends Element implements Blockable,Hitable,Destroyable{
	//属性
	//血量
	private int blood=2;
	//构造方法，带参构造
	public Wall(int x,int y) {
		super(x, y);
		
		//设置图片的宽和高
		try {
			int[]size= DrawUtils.getSize("res\\img\\wall.gif");
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
			DrawUtils.draw("res\\img\\wall.gif", x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	@Override
	public Blast showAttack() {
		// TODO 自动生成的方法存根
		blood--;
		return new Blast(this,true);
	}

	@Override
	public boolean isDestroy() {
		// TODO 自动生成的方法存根
		return blood<=0;
	}

	@Override
	public Blast showDestroy() {
		// TODO 自动生成的方法存根
		return new Blast(this);
	}
	
	//2.挨打
}
