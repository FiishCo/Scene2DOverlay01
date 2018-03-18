package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class MainScreen extends ScreenAdapter {

    public Viewport mViewport;
    public BlueButton mBlue;
    public RedButton mRed;

    public Stage mStageBlue;
    public Stage mStageRed;

    public ShapeRenderer mRenderer;

    public boolean mRedActive = false;
    public boolean mRedLeaving = false;

    @Override
    public void show() {
        mViewport = new ScreenViewport();

        mBlue = new BlueButton();
        mBlue.init(mViewport, new Vector2(0, 0));
        mBlue.setTouchable(Touchable.enabled);

        mRed = new RedButton();
        mRed.init(mViewport, new Vector2(0, 0));
        mRed.setTouchable(Touchable.enabled);

        mStageBlue = new Stage(mViewport);
        mStageRed = new Stage(mViewport);

        mStageBlue.addActor(mBlue);
        mStageRed.addActor(mRed);

        Gdx.input.setInputProcessor(mStageBlue);

        mRenderer = new ShapeRenderer();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl20.glClearColor(1, 1, 1, 1);

        mStageBlue.act(delta);
        mStageBlue.draw();

        if (mBlue.mPressed || mRedActive) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            mRenderer.begin(ShapeRenderer.ShapeType.Filled);
            mRenderer.setColor(0.25f, 0.25f, 0.25f, 0.6f);
            mRenderer.rect(0, 0, mViewport.getScreenWidth(), mViewport.getScreenHeight());
            mRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        if (mBlue.mPressed) {
            MoveToAction action = new MoveToAction() {
                @Override
                protected void end() {
                    mRedActive = true;
                    mBlue.mPressed = false;
                    Gdx.input.setInputProcessor(mStageRed);
                }
            };
            action.setPosition(mViewport.getScreenWidth() / 2 - mRed.mTexture.getWidth() / 2, mViewport.getScreenHeight() / 2 - mRed.mTexture.getHeight() / 2);
            action.setDuration(1.0f);

            mRed.addAction(action);

            mStageRed.act(delta);
            mStageRed.draw();
        }

        if (mRedActive) {
            mStageRed.act(delta);
            mStageRed.draw();
        }

        if (mRed.mPressed) {
            mRedLeaving = true;
            mRedActive = false;

            MoveToAction action = new MoveToAction() {
                @Override
                protected void end() {
                    mRed.mPressed = false;
                    Gdx.input.setInputProcessor(mStageBlue);
                }
            };
            action.setPosition(mViewport.getScreenWidth() / 2 - mRed.mTexture.getWidth() / 2, mViewport.getScreenHeight() + 250);
            action.setDuration(1.0f);

            mRed.addAction(action);
            mStageRed.act(delta);
            mStageRed.draw();
        }

    }

    @Override
    public void resize(int width, int height) {
        mViewport.update(width, height, true);

        mBlue.init(mViewport, new Vector2(mViewport.getScreenWidth() / 2 - mBlue.mTexture.getWidth() / 2, mViewport.getScreenHeight() / 2 - mBlue.mTexture.getHeight() / 2));

        mRed.init(mViewport, new Vector2(mViewport.getScreenWidth() / 2 - mRed.mTexture.getWidth() / 2, mViewport.getScreenHeight() + 250));
    }

    @Override
    public void dispose() {
        mRenderer.dispose();
        mBlue.dispose();
        mRed.dispose();
    }

}
