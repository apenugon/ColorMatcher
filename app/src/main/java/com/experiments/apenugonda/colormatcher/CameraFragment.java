package com.experiments.apenugonda.colormatcher;

import android.app.Activity;
import android.app.Fragment;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * A fragment that allows for getting Camera input
 */
public class CameraFragment extends Fragment {
    private CameraView mPreview;
    private Camera mCamera;
    private Activity thisActivity;

    public Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        }
        catch (Exception e) {
            // Alert that camera is not available
        }
        return c;
    }

    public CameraFragment() {
        mCamera = getCameraInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sample_camera_view, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            mCamera.reconnect();
        } catch (Exception e) {
            mCamera = getCameraInstance();
            Log.d("Camera", "Failed to reconnect to Camera: " + e.toString());
        }
        //Set orientation to always be vertical
        mCamera.setDisplayOrientation(90);

        mPreview = new CameraView(thisActivity, mCamera);
        FrameLayout layout = (FrameLayout)thisActivity.findViewById(R.id.camera_preview);
        layout.addView(mPreview);
    }

    @Override
    public void onPause() {
        super.onPause();

        mCamera.stopPreview();
        FrameLayout layout = (FrameLayout)thisActivity.findViewById(R.id.camera_preview);
        layout.removeView(mPreview);
        mCamera.release();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        thisActivity = activity;
        ((SelectColor) activity).onSectionAttached(1); //First Activity
        // TODO: Change call to use a stored number
    }
}