package com.itheima.game.business;
//具有挨打能力的事物

import com.itheima.game.domain.Blast;

public interface Hitable {
	//返回爆炸物对象
	public abstract Blast showAttack();
}
