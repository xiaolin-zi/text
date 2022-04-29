package com.itheima.game;

public class App {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		//入口
		//GameWindow gWindow=new GameWindow("坦克大战", 800, 600, 60);
		GameWindow gw=new GameWindow(Config.TITLE, Config.WIDTH, Config.HEIGHT, Config.FPS);
		gw.start();//开始游戏
	}

}
