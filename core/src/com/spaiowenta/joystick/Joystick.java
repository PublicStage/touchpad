package com.spaiowenta.joystick;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveToAligned;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

public class Joystick extends Group {

    private static final float WIDTH = 316;
    private static final float HEIGHT = 316;
    private static final float KNOB_SIZE = 108;
    private static final float CIRCLE_SIZE = 220;

    private final Vector2 vector = new Vector2();
    private boolean moving;
    private float angle;
    private float radius2;

    private final Touchpad touchpad;

    private final Image back;
    private final Image dot;
    private final JoystickStyle joystickStyle;

    private final Vector2 tmp = new Vector2();
    private final Vector2 tmp2 = new Vector2();


    public Joystick(JoystickStyle style) {
        this.joystickStyle = style;

        setSize(WIDTH, HEIGHT);

        back = new Image(style.back);
        back.setTouchable(Touchable.disabled);
        addActor(back);
        back.setPosition(getWidth() / 2, getHeight() / 2, Align.center);
        back.setOrigin(Align.center);

        dot = new Image(style.dot);
        dot.setVisible(false);
        back.setTouchable(Touchable.disabled);
        addActor(dot);
        dot.setPosition(getWidth() / 2, getHeight() / 2, Align.center);

        touchpad = new Touchpad(30, joystickStyle);
        addActor(touchpad);
        touchpad.setFillParent(true);

        touchpad.addListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (!moving && (touchpad.getKnobPercentX() != 0 || touchpad.getKnobPercentY() != 0)) {
                    moving = true;
                    dot.setVisible(true);
                    joystickStyle.setMoving(true);
                    event.cancel();
                }

            }
        });
    }

    public float getAngle() {
        return angle;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (moving) {
            //App.log("Moving " + touchpad.getKnobPercentX() + "x" + touchpad.getKnobPercentY());
            vector.set(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
            angle = vector.angleRad();
            radius2 = vector.len2();

            //joystickActor.move(angle);
        }
    }

    @Override
    public boolean notify(Event event, boolean capture) {
        return touchpad.notify(event, capture);
    }

    public void stop() {
        moving = false;
        dot.setVisible(false);
        joystickStyle.setMoving(false);

        //joystickActor.stop(angle);

        homeAnimation();
    }

    private void homeAnimation() {
        clearActions();
        addAction(sequence(alpha(0f), alpha(1, 0.3f)));
        back.clearActions();
        back.addAction(sequence(scaleTo(0.9f, 0.9f), scaleTo(1.1f, 1.1f, 0.2f), scaleTo(1f, 1f, 0.1f)));
    }

    public void setDraggedPosition(float x, float y) {
        if (radius2 == 1) {
            tmp.set(x, y).sub(tmp2.set((WIDTH - KNOB_SIZE) / 2, 0).rotateRad(angle));
            clearActions();
            addAction(moveToAligned(tmp.x, tmp.y, Align.center, 0.05f));
        }
    }
}
