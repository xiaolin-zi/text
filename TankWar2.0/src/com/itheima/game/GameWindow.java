package com.itheima.game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.itheima.game.Window;
import org.itheima.game.utils.DrawUtils;
import org.itheima.game.utils.SoundUtils;
import org.lwjgl.input.Keyboard;

import com.itheima.game.business.Attackable;
import com.itheima.game.business.Blockable;
import com.itheima.game.business.Destroyable;
import com.itheima.game.business.Hitable;
import com.itheima.game.business.Moveable;
import com.itheima.game.domain.Blast;
import com.itheima.game.domain.Bullet;
import com.itheima.game.domain.Direction;
import com.itheima.game.domain.Element;
import com.itheima.game.domain.Grass;
import com.itheima.game.domain.MyTank;
import com.itheima.game.domain.Steel;
import com.itheima.game.domain.Wall;
import com.itheima.game.domain.Water;

public class GameWindow extends Window {

	CopyOnWriteArrayList<Element> list = new CopyOnWriteArrayList<>();// 元素集合,注意与ArrayList的区别

	MyTank mt;//己方坦克类
	
	public GameWindow(String title, int width, int height, int fps) {
		super(title, width, height, fps);
		// TODO 自动生成的构造函数存根
	}

	// 窗体加载时执行
	@Override
	protected void onCreate() {
		// TODO 自动生成的方法存根
		
		//播放背景音乐
		try {
			SoundUtils.play("res\\snd\\start.wav");
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		// 创建一堆砖墙
		for (int i = 0; i < Config.WIDTH / 64 - 1; i++) {
			Wall wall = new Wall(64 * i, 64);
			//list.add(wall);
			addElement(wall);
		}
		// 创建一堆水墙
		for (int i = 1; i < Config.WIDTH / 64; i++) {
			Water water = new Water(64 * i, 64 * 3);
			//list.add(water);
			addElement(water);
		}
		// 创建一堆铁墙
		for (int i = 0; i < Config.WIDTH / 64 -1; i++) {
			Steel steel =new Steel(64*i, 64*5);
			//list.add(steel);
			addElement(steel);
		}
		// 创建一堆草坪
		for (int i = 1; i < Config.WIDTH / 64; i++) {
			Grass grass =new Grass(64*i, 64*7);
			//list.add(grass);
			addElement(grass);
		}
		/*
		 * 需求：让坦克隐藏在草丛中
		 * 简单做法：先绘制坦克，再绘制草坪，扩展性差，不推荐
		 * 
		 * 推荐做法：Comparator比较器接口实现
		 *我们可以这样做，每让集合中添加一个元素，就按照渲染级别进行排序，渲染级别越高，元素越后。
		 *这样，最终绘制元素的时候，渲染级别高的元素肯定最后绘制。
		 *大白话：安装渲染级别对元素进行升序排序
		 *
		 */
		//创建坦克类对象
		mt=new MyTank(Config.WIDTH/2 -32,Config.HEIGHT-64);
		//list.add(mt);
		addElement(mt);
	}

	// 鼠标监听事件
	@Override
	protected void onMouseEvent(int key, int x, int y) {
		// TODO 自动生成的方法存根

	}

	// 键盘监听事件
	@Override
	protected void onKeyEvent(int key) {
		// TODO 自动生成的方法存根

		switch (key) {
		case Keyboard.KEY_UP:
		case Keyboard.KEY_W:
			mt.move(Direction.UP);
			break;
		case Keyboard.KEY_DOWN:
		case Keyboard.KEY_S:
			mt.move(Direction.DOWN);
			break;
		case Keyboard.KEY_LEFT:
		case Keyboard.KEY_A:
			mt.move(Direction.LEFT);
			break;
		case Keyboard.KEY_RIGHT:
		case Keyboard.KEY_D:
			mt.move(Direction.RIGHT);
			break;
		case Keyboard.KEY_RETURN:
			Bullet shot=mt.shot();
			if(shot!=null) {
			//list.add(shot);
			addElement(shot);
			}
		default:
			break;
		}
	}

	// 实时刷新
	@Override
	protected void onDisplayUpdate() {
		// TODO 自动生成的方法存根
		//绘制元素
		for(Element element:list) {
			element.draw();
		}
		//销毁需要销毁的事物
		for(Element e:list) {
			//如果遍历到的元素是具有销毁功能的事物，就判断是否销毁
			if(e instanceof Destroyable) {
				boolean flag=((Destroyable)e).isDestroy();
				if(flag) {
					list.remove(e);
					//销毁该元素时需要获取销毁时该元素返回的爆炸物
					Blast blast=((Destroyable)e).showDestroy();
					if(blast!=null) {
						addElement(blast);
					}
				}
			}
		}
		//校验坦克有没有撞上
		for(Element e1:list) {
			for(Element e2:list) {
				//e1是具有移动功能的事物，e2是具有阻挡功能的事物
				if(e1 instanceof Moveable && e2 instanceof Blockable) {
				   boolean flag=((Moveable)e1).checkHit((Blockable)e2);
				   if(flag) {
					   break;
				   }
				}
				
			}
		}
		//校验子弹和铁墙有没有撞上
		for(Element e1:list) {
			for(Element e2:list) {
				if(e1 instanceof Attackable && e2 instanceof Hitable) {
					boolean flag=((Attackable)e1).checkAttack((Hitable)e2);
					if(flag) {
						list.remove(e1);
						//爆炸特效
						Blast blast=((Hitable)e2).showAttack();
						addElement(blast);
					}
				}
			}
		}
	}

	//往集合中添加元素
	public void addElement(Element e) {
		list.add(e);
		list.sort(new Comparator<Element>() {

			@Override
			public int compare(Element e1, Element e2) {
				// 前减后升序，后减前降序
				return e1.getOrder()-e2.getOrder();
			}
			
		});
	}
	
}
