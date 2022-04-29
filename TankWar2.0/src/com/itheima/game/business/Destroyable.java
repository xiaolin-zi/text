package com.itheima.game.business;
//销毁功能

import com.itheima.game.domain.Blast;

public interface Destroyable {
	//判断是否需要销毁
	public abstract boolean isDestroy();
	//获取一个元素销毁时候的爆炸物
	public abstract Blast showDestroy();
}
