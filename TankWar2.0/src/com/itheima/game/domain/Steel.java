package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;

import com.itheima.game.business.Blockable;
import com.itheima.game.business.Destroyable;
import com.itheima.game.business.Hitable;
//铁墙类
public class Steel extends Element implements Blockable,Hitable,Destroyable{
	//属性
    //血量
	private int blood=5;
	//构造方法，带参构造
	public Steel(int x,int y) {
		super(x, y);
		//设置图片的宽和高
		try {
			int[]size= DrawUtils.getSize("res\\img\\steel.gif");
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
			DrawUtils.draw("res\\img\\steel.gif", x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	
	/*
	 * 获取一个爆炸物
	 * 返回爆炸物对象
	 */
	public Blast showAttack() {
		//true表示挨打状态，画部分爆炸物的图片
		blood--;
		return new Blast(this,true);
	}

	//血量低于0的时候销毁

	@Override
	public boolean isDestroy() {
		// TODO 自动生成的方法存根
		return blood<=0;
	}

	@Override
	//铁墙销毁时返回一个爆炸物
	public Blast showDestroy() {
		// TODO 自动生成的方法存根
		//return new Blast(this,false);//false表示销毁状态，绘制全部图片
		return new Blast(this);
		
	}
	
	//2.挨打
}
