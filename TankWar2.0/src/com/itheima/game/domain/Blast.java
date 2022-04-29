package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;

import com.itheima.game.business.Destroyable;
import com.itheima.game.business.Hitable;

public class Blast extends Element implements Destroyable{

	//属性
	//爆炸物图片路径
	private String[] arr= {"res\\img\\blast_1.gif","res\\img\\blast_2.gif",
			"res\\img\\blast_3.gif","res\\img\\blast_4.gif","res\\img\\blast_5.gif",
			"res\\img\\blast_6.gif","res\\img\\blast_7.gif","res\\img\\blast_8.gif"};
	//记录索引，表示绘制哪张爆炸物图片
	private int index;
	
	//定义一个变量，用来记录爆炸物是否需要销毁
	boolean isDestroy;
	//构造方法
	//爆炸物坐标根据铁墙坐标计算
	public Blast(Hitable hit) {
		this(hit,false);
		//创建销毁状态爆炸物时播放声音
		try {
			SoundUtils.play("res\\snd\\blast.wav");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//成员方法
	@Override
	public void draw() {
		//获取要绘制的图片，绘制完绘制下一张
		String res =arr[index++];
		//索引越界处理
		if(index>=arr.length) {
			index=0;//index归0，说明爆炸物图片已经打印一圈，这个时候，销毁爆炸物对象即可
			isDestroy=true;//记录爆炸物的销毁状态
			return;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//返回爆炸物的销毁状态，即是否需要销毁
	//true销毁，false不销毁
	public boolean isDestroy() {
		return isDestroy;
	}
	//flag为true挨打，为false销毁。
	public Blast(Hitable hit,boolean flag) {
		Element element=(Element)hit;
		//获取挨打事物的坐标和宽高
		int x1=element.x;
		int y1=element.y;
		int w1=element.width;
		int h1=element.height;
		
		//获取爆炸物图片的宽和高
		try {
			int[]size=DrawUtils.getSize("res\\img\\blast_1.gif");
			width=size[0];
			height=size[1];
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//计算爆炸物的坐标
		x=x1+(w1-width)/2;//铁墙的x+铁墙的宽的一半-爆炸物的宽的一半
		y=y1+(h1-height)/2;//铁墙的y+铁墙的高的一半-爆炸物的高的一半
		
		//如果挨打，就修改数组arr中图片的个数
		if(flag) {
			arr=new String[] {"res\\img\\blast_1.gif","res\\img\\blast_2.gif",
					"res\\img\\blast_3.gif","res\\img\\blast_4.gif"};
		}
		//元素挨打声音
		try {
			SoundUtils.play("res\\snd\\hit.wav");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	//这里直接return一个null就可以了，我本身就是爆炸物，所以销毁的时候直接从集合移除就可以了，不用返回一个爆炸物
	@Override
	public Blast showDestroy() {
		// TODO 自动生成的方法存根
		return null;
	}
	
}
