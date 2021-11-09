package com.spaiowenta.joystick;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class JoystickContainer extends WidgetGroup {

    private final float START_X = 178;
    private final float START_Y = 178;

    private Joystick joystick;

    public JoystickContainer(JoystickStyle style) {
        joystick = new Joystick(style);
        joystick.setOrigin(Align.center);
        joystick.setPosition(START_X, START_Y, Align.center);

        addActor(joystick);

        addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                joystick.setPosition(x, y, Align.center);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                joystick.notify(event, false);
                joystick.setDraggedPosition(x, y);

            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                joystick.setPosition(START_X, START_Y, Align.center);
                joystick.notify(event, false);
                joystick.stop();
            }
        });
    }

    public float getAngle() {
        return joystick.getAngle();
    }
}

