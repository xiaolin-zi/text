package com.itheima.game.business;
//具有攻击能力的事物
public interface Attackable {

	//校验具有攻击能力的事物是否和具有挨打能力的事物撞上
	public abstract boolean checkAttack(Hitable hit);
}
