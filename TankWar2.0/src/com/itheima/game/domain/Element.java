package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.DrawUtils;
//元素类
public abstract class Element {
	//属性
	//1.坐标
	protected int x;
	protected int y;
	//2.宽和高
	protected int width;
    protected int height;
	
    //空参构造
    public Element() {
    }
    
	//构造方法，带参构造
	public Element(int x,int y) {
		this.x=x;
		this.y=y;
		
		
	}
	
	//常用方法
	//1.绘制元素
	public abstract void draw() ;
	//返回元素的渲染级别
	public int getOrder() {
		return 0;
	}
	
	}
	
	//2.挨打

