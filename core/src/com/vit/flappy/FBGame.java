package com.vit.flappy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import fbHelpers.AssetLoader;
import screens.GameScreen;

public class FBGame extends Game{

	@Override
	public void create() {
		Gdx.app.log("FBGame", "Created");
		AssetLoader.load();
		setScreen(new GameScreen());
	}

	public void dispose(){
		super.dispose();
		AssetLoader.dispose();
	}
}
