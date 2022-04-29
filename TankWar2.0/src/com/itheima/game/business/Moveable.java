package com.itheima.game.business;
//具有移动功能的事物
public interface Moveable {

	//校验
	public abstract boolean checkHit(Blockable block);
}
