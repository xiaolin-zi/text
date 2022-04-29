package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.CollsionUtils;
import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;

import com.itheima.game.Config;
import com.itheima.game.business.Blockable;
import com.itheima.game.business.Moveable;


public class MyTank extends Element implements Moveable{

	//属性
	//血量
	private int blood;
	//攻击力
	private int power;
	//移动方向
	private Direction direction=Direction.UP;//要有初始化值，小细节
	//移动速度
	private int speed=32;//要有初始化值，细节
	//记录最后一颗子弹的发射时间
	private long lastShotTime;
	
	//用来记录坦克不能移动的方向
	private Direction badDirection;
	//用来记录坦克不能移动的最小间隙
	private int badSpeed;
	
	public MyTank(int x, int y) {
		super(x, y);
		// TODO 自动生成的构造函数存根
		//设置坦克的宽和高
		try {
			int size[]= DrawUtils.getSize("res\\img\\tank_u.gif");
			width=size[0];
			height=size[1];
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//绘制坦克
	@Override
	public void draw() {
		// TODO 自动生成的方法存根
		//加入坦克炮口随向转动的功能
		String res ="";
		switch(direction) {
		case UP:
			res="res\\img\\tank_u.gif";
			break;
		case DOWN:
			res="res\\img\\tank_d.gif";
			break;
		case LEFT:
			res="res\\img\\tank_l.gif";
			break;
		case RIGHT:
			res="res\\img\\tank_r.gif";
			break;
		default:
			break;
		}
		try {
			DrawUtils.draw(res, x,y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//坦克移动方向，用户录入的键传过来的
	public void move(Direction direction) {
		//如果坦克不能移动的方向和传入的方向一致，就return，提示不能移动
		if(badDirection!=null&& badDirection==direction) {
			System.out.println("不能移动");
			//让坦克移动最小间隙
			switch(direction) {
			case UP:
				y-=badSpeed;
				break;
			case DOWN:
				y+=badSpeed;
				break;
			case LEFT:
				x-=badSpeed;
				break;
			case RIGHT:
				x+=badSpeed;
				break;
			default:
				break;
			}
			return;
		}
		
		//this.direction=direction;
		//小细节,如果传过来的方向和当前方向不一致，就把传来的方向赋值给坦克方向，然后return
		if(this.direction!=direction) {
			this.direction=direction;
			return;
		}
		switch(direction) {
		case UP:
			y-=speed;
			break;
		case DOWN:
			y+=speed;
			break;
		case LEFT:
			x-=speed;
			break;
		case RIGHT:
			x+=speed;
			break;
			
		default:
			break;
		
		}
		
		//越界处理
		if(x<0) {//左边不能出界
			x=0;
		}
		if(y<0) {//上边不能出界
			y=0;
		}
		if(x>Config.WIDTH-64) {
			x=Config.WIDTH-64;
		}
		if(y>Config.HEIGHT-64) {
			y=Config.HEIGHT-64;
		}
	}

	public Bullet shot() {
		//如果最后一颗子弹的发射时间和现在即将要发射的子弹时间间隔400ms就不发射子弹
		long nowTime =System.currentTimeMillis();
		if(nowTime-lastShotTime<400) {
			return null;
		}else {
			//lastShotTime变量值重置
			lastShotTime=nowTime;
			
		    return new Bullet(this);
	}
	}
	//获取坦克移动方向
	public Direction getDirection() {
		return direction;
	}
	/*
	 * 校验坦克对象和其他有阻挡功能的墙类有没有碰撞上
	 */
	public boolean checkHit(Blockable block) {
		Element element=(Element)block;//因为Bolckable接口有几个子类：Wall，Steel,Water,这些类是Element的子类
		//获取铁墙的坐标和宽高
		int x1=element.x;
		int y1=element.y;
		int w1=element.width;
		int h1=element.height;
		
		//预判坦克的下一步动作
		int x2=x;
		int y2=y;
		switch(direction) {
		case UP:
			y2-=speed;
			break;
		case DOWN:
			y2+=speed;
			break;
		case LEFT:
			x2-=speed;
			break;
		case RIGHT:
			x2+=speed;
			break;
		default:
			break;
		}
		
		//通过工具类CollisionUtils校验有没有碰撞,预判的坐标做校验
		boolean flag=CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2, width, height);
		//如果碰撞上，要记录坦克不能移动的方向
		if(flag) {
			//撞上了
			badDirection=direction;
			//一定要注意最小间隙的计算方式：是通过坦克当前的坐标计算的。
			switch(direction) {
			case UP:
				badSpeed=y-y1-h1;//坦克y-铁墙y-铁墙高
				break;
			case DOWN:
				badSpeed=y1-y-height;//铁墙y-坦克y-坦克高
				break;
			case LEFT:
				badSpeed=x-x1-w1;//坦克x-铁墙x-铁墙宽
				break;
			case RIGHT:
				badSpeed=x1-x-width;//铁墙x-坦克x-坦克宽
				break;
			default:
				break;
			}
		}else {
			//没撞上，方向,最小间隙重置
			badDirection=null;
			badSpeed=0;
		}
		//不管有没有碰撞上，都要返回
		return flag;
	}
}
