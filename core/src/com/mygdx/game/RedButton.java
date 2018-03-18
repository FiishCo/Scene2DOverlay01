package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RedButton extends Actor {

    public Viewport mViewport;
    public Texture mTexture;
    public boolean mPressed;

    public Vector2 mInitPos;

    public void init(Viewport viewport, Vector2 pos) {
        this.mViewport = viewport;

        this.mInitPos = new Vector2(pos);

        this.setX(pos.x);
        this.setY(pos.y);

        this.mTexture = new Texture(Gdx.files.internal("RedButton.png"));

        this.setBounds(this.getX(), this.getY(), mTexture.getWidth(), mTexture.getHeight());
        this.setTouchable(Touchable.enabled);

        this.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                mPressed = true;
                MoveToAction action = new MoveToAction();
                action.setPosition(mInitPos.x, mInitPos.y);
                action.setDuration(1.0f);
                return true;
            }
        });
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(mTexture, this.getX(), this.getY());
    }

    public void dispose() {
        mTexture.dispose();
    }
}
