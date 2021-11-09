package com.spaiowenta.joystick;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class JoystickStyle extends Touchpad.TouchpadStyle {
    Drawable knobIdle;
    Drawable knobMoving;
    Drawable back;
    Drawable dot;

    public JoystickStyle(Drawable knobIdle, Drawable knobMoving, Drawable back, Drawable dot) {
        this.knobIdle = knobIdle;
        this.knobMoving = knobMoving;
        this.back = back;
        this.dot = dot;

        knob = knobIdle;
        knob.setMinHeight(108);
        knob.setMinWidth(108);
    }

    public JoystickStyle(TextureRegion knobIdle, TextureRegion knobMoving, TextureRegion back, TextureRegion dot) {
        this(getDrawable(knobIdle), getDrawable(knobMoving), getDrawable(back), getDrawable(dot));
    }

    private static Drawable getDrawable(TextureRegion texture) {
        return new TextureRegionDrawable(texture);
    }

    public void setMoving(boolean value) {
        knob = value ? knobMoving : knobIdle;
    }

}
