package com.miguelcatalan.materialsearchview.utils;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.view.View;

/**
 * @author Miguel Catalan Bañuls
 */
public class AnimationUtil {

    public static final int ANIMATION_DURATION_SHORT = 150;
    public static final int ANIMATION_DURATION_MEDIUM = 400;
    public static final int ANIMATION_DURATION_LONG = 800;

    public interface AnimationListener {
        /**
         * @return true to override parent. Else execute Parent method
         */
        boolean onAnimationStart(final View view);

        boolean onAnimationEnd(final View view);

        boolean onAnimationCancel(final View view);
    }

    public static void crossFadeViews(final View showView, final View hideView) {
        crossFadeViews(showView, hideView, ANIMATION_DURATION_SHORT);
    }

    public static void crossFadeViews(final View showView, final View hideView, final int duration) {
        fadeInView(showView, duration);
        fadeOutView(hideView, duration);
    }

    public static void fadeInView(final View view) {
        fadeInView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeInView(final View view, final int duration) {
        fadeInView(view, duration, null);
    }

    public static void fadeInView(final View view, final int duration, final AnimationListener listener) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        ViewPropertyAnimatorListener vpListener = null;

        if (listener != null) {
            vpListener = new ViewPropertyAnimatorListener() {
                @Override
                public void onAnimationStart(final View view) {
                    if (!listener.onAnimationStart(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(true);
                    }
                }

                @Override
                public void onAnimationEnd(final View view) {
                    if (!listener.onAnimationEnd(view)) {
                        //execute Parent MEthod
                        view.setDrawingCacheEnabled(false);
                    }
                }

                @Override
                public void onAnimationCancel(final View view) {
                    if (!listener.onAnimationCancel(view)) {
                        //execute Parent MEthod
                    }
                }
            };
        }
        ViewCompat.animate(view).alpha(1f).setDuration(duration).setListener(vpListener);
    }

    public static void fadeOutView(final View view) {
        fadeOutView(view, ANIMATION_DURATION_SHORT);
    }

    public static void fadeOutView(final View view, final int duration) {
        fadeOutView(view, duration, null);
    }

    public static void fadeOutView(final View view, final int duration, final AnimationListener listener) {
        ViewCompat.animate(view).alpha(0f).setDuration(duration).setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(final View view) {
                if (listener == null || !listener.onAnimationStart(view)) {
                    //execute Parent MEthod
                    view.setDrawingCacheEnabled(true);
                }
            }

            @Override
            public void onAnimationEnd(final View view) {
                if (listener == null || !listener.onAnimationEnd(view)) {
                    //execute Parent MEthod
                    view.setVisibility(View.GONE);
                    //view.setAlpha(1f);
                    view.setDrawingCacheEnabled(false);
                }
            }

            @Override
            public void onAnimationCancel(final View view) {
                if (listener == null || !listener.onAnimationCancel(view)) {
                    //execute Parent MEthod
                }
            }
        });
    }
}