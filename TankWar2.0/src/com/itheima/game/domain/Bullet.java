package com.itheima.game.domain;

import java.io.IOException;

import org.itheima.game.utils.CollsionUtils;
import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;

import com.itheima.game.Config;
import com.itheima.game.business.Attackable;
import com.itheima.game.business.Destroyable;
import com.itheima.game.business.Hitable;

public class Bullet extends Element implements Attackable,Destroyable{

	//子弹速度
	private int speed=3;
	//用来记录子弹移动方向
	private Direction direction;
	
	public Bullet(MyTank mt) {
		//子弹坐标是根据坦克的坐标来算的
		//获取坦克的坐标和宽高,以及移动方向
		int tankX=mt.x;
		int tankY=mt.y;
		int tankWidth=mt.width;
		int tankHeight=mt.height;
		this.direction=mt.getDirection();
		
		//获取子弹的宽和高
		try {
			int []size=DrawUtils.getSize("res\\img\\bullet_u.gif");
			width=size[0];
			height=size[1];
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//计算子弹的坐标，加入四舍五入的元素，理解就ok了
		/*
		 * switch(direction) { case UP: x=Math.round(tankX +(tankWidth-width)/2.0f);
		 * y=Math.round(tankHeight-height/2.0f); break; case DOWN:
		 * x=Math.round(tankX+(tankWidth-width)/2.0f);
		 * y=Math.round(tankY+tankHeight-height/2.0f); break; case LEFT:
		 * //x=tankX-width; x=tankX-width;
		 * y=Math.round(tankHeight+(tankHeight-height)/2.0f); break; case RIGHT:
		 * x=Math.round(tankHeight+tankWidth-width/2.0f);
		 * y=Math.round(tankHeight+(tankHeight-height)/2.0f); break; default: break; }
		 */
		method1(tankX, tankY, tankWidth, tankHeight);
		//播放声音
		try {
			SoundUtils.play("res\\snd\\fire.wav");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	//普通的坐标计算方法
	private void method1(int tankX, int tankY, int tankWidth, int tankHeight) {
		switch(direction) {
		case UP:
			x=tankX +(tankWidth-width)/2;
			y=tankY-height/2;
			break;
		case DOWN:
			x=tankX+(tankWidth-width)/2;
			y=tankY+tankHeight-height/2;
			break;
		case LEFT:
			//x=tankX-width/2;
			x=tankX-width;
			y=tankY+(tankHeight-height)/2;
			break;
		case RIGHT:
			x=tankX+tankWidth-width/2;
			y=tankY+(tankHeight-height)/2;
			break;
		default:
				break;
		}
	}

	@Override
	public void draw() {
		// TODO 自动生成的方法存根
		//子弹的方向应该和坦克的方向一致
		String res="";
		switch(direction) {
		case UP:
			res="res\\img\\bullet_u.gif";
			y-=speed;
			break;
		case DOWN:
			res="res\\img\\bullet_d.gif";
			y+=speed;
			break;
		case LEFT:
			res="res\\img\\bullet_l.gif";
			x-=speed;
			break;
		case RIGHT:
			res="res\\img\\bullet_r.gif";
			x+=speed;
			break;
		default:
			break;
		}
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
     
	//是否需要销毁子弹
	public boolean isDestroy() {
		//判断子弹是否出界，出界true
		//我们认为子弹完全出界才销毁
		if(x<0-width||x>Config.WIDTH||y<0-height||y>Config.HEIGHT) {
			return true;
		}
		return false;
	}
	//校验子弹是否打到铁墙上
	//true：撞上，false：没有
	public  boolean checkAttack(Hitable hit) {
		Element element=(Element)hit;
		//获取铁墙的坐标和宽高
		int x1=element.x;
		int y1=element.y;
		int w1=element.width;
		int h1=element.height;
		//撞上
		boolean flag=CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x, y, width, height);
		return flag;
	}


	@Override
	public Blast showDestroy() {
		//这里直接return一个null就可以了，我本身就是子弹，所以销毁的时候直接从集合移除就可以了，不用返回一个爆炸物
		return null;
	}

	
	
}
