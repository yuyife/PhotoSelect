package com.yuyife.photoselect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class PhotoTestActivity extends AppCompatActivity {

    private static final int PICK_PHOTO = 1;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_test_activity);
        image = (ImageView) findViewById(R.id.main_image);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoTestActivity.this, PhotoPickerActivity.class);
                intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);//是否相机显示
                intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);//是否单选模式
                intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, PhotoPickerActivity.DEFAULT_NUM);//默认最大数
                startActivityForResult(intent, PICK_PHOTO);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                Glide.with(this)
                        .load(result.get(0))
                        .into(image);
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (CheckPermissions.isNeedCheck) {
            CheckPermissions.checkPermissions(this, CheckPermissions.needPermissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == CheckPermissions.PERMISSON_REQUESTCODE) {
            if (!CheckPermissions.verifyPermissions(paramArrayOfInt)) {
                CheckPermissions.showMissingPermissionDialog(this);
                CheckPermissions.isNeedCheck = false;
            }
        }
    }
}
