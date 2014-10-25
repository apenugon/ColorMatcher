package com.experiments.apenugonda.colormatcher;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;


/**
 * TODO: document your custom view class.
 */
public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // Get notified of creation and destruction
        mHolder = getHolder();
        mHolder.addCallback(this);

        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // Tell Camera where to draw preview
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("Error", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO: Release in activity
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Rotation events and other stuff
        if (mHolder.getSurface() == null) {
            return; //Doesn't exist
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            //ignore
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d("Error", "Error starting preview: " + e.getMessage());
        }
    }
}
