package com.admitad.android.system;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.admitad.android.R;
import com.admitad.android.ui.fragment.FragmentBase;

import javax.inject.Inject;

public class FragmentSystem {

    @Inject
    public FragmentSystem() { }

    public enum FragmentEnum {
        FragmentMain("fragment_main_stage");

        private final String stageId;

        FragmentEnum(final String stageId) {
            this.stageId = stageId;
        }

        public String getStageId() {
            return stageId;
        }

    }

    public void openFragment(FragmentManager manager, FragmentEnum stage,
                             int layout, boolean isReplace, boolean isBack) {
        FragmentTransaction transaction = manager.beginTransaction();

        FragmentBase fragment = null;

        switch (stage) {

        }

        fragment.setName(stage.getStageId());

        transaction.addToBackStack(null);
        if (!isBack) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        } else {
            transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
        }

        if (!isReplace) transaction.add(layout, fragment, stage.getStageId());
        else transaction.replace(layout, fragment, stage.getStageId());

        transaction.commitAllowingStateLoss();
    }

}
