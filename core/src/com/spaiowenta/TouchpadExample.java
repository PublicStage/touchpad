package com.spaiowenta;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spaiowenta.joystick.JoystickContainer;
import com.spaiowenta.joystick.JoystickStyle;

public class TouchpadExample extends ApplicationAdapter {

    Stage stage;

    TextureAtlas atlas;

    Image ship;
    JoystickContainer joystickContainer;

    @Override
    public void create() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        atlas = new TextureAtlas("main.atlas");

        ship = new Image(atlas.findRegion("ship"));
        ship.setOrigin(Align.center);
        stage.addActor(ship);

        joystickContainer = new JoystickContainer(new JoystickStyle(atlas.findRegion("circle1"), atlas.findRegion("circle4"), atlas.findRegion("circle2"), atlas.findRegion("circle3")));

        joystickContainer.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				ship.clearActions();
				ship.addAction(Actions.rotateTo((joystickContainer.getAngle() - MathUtils.HALF_PI) * MathUtils.radiansToDegrees, 0.1f));
			}
		});

        joystickContainer.setFillParent(true);
        stage.addActor(joystickContainer);
    }

    @Override
    public void resize(int width, int height) {
        float screenWidth = 1280;
        float screenHeight = Gdx.graphics.getHeight() * screenWidth / Gdx.graphics.getWidth();

        stage.getViewport().setWorldSize((int) screenWidth, (int) screenHeight);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
        stage.getViewport().apply();

        ship.setPosition(stage.getWidth() / 2, stage.getHeight() / 2, Align.center);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        atlas.dispose();
    }
}
