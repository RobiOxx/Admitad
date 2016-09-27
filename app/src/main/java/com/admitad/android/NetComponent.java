package com.admitad.android;

import com.admitad.android.activity.ActivityBase;
import com.admitad.android.ui.fragment.FragmentBase;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class})
@Singleton
public interface NetComponent {
    ActivityBase inject(ActivityBase activity);
    FragmentBase inject(FragmentBase fragment);
}
