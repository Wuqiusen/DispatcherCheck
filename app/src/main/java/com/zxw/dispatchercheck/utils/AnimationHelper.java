package com.zxw.dispatchercheck.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;



public class AnimationHelper {
    public static void crossfade(final View preView, View nextView, int time) {
        nextView.setVisibility(View.VISIBLE);
        ObjectAnimator.ofFloat(nextView, "alpha", 0, 1).setDuration(time).start();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(preView, "alpha", 1, 0).setDuration(time);
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                preView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        objectAnimator.start();
    }
}
