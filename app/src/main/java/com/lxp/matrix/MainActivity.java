package com.lxp.matrix;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private int width;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image);
        width = ScreenUtils.getScreenWidth(this);
        height = ScreenUtils.getScreenHeight(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.w_001);
        //根据新的尺寸缩放图片
//        cropNewBitmap(bitmap,width,(int)getResources().getDimension(R.dimen.conver_width));
        //根据比例缩放图片
//        cropScaleBitmap(bitmap,0.2f);
        //裁剪图片
//        cropBitmap(bitmap);
        //按比例缩放裁剪图片
        imageView.setImageBitmap(appointCrop(bitmap,width,(int)getResources().getDimension(R.dimen.conver_width),0.547f));
    }
    //
    public static Bitmap appointCrop( Bitmap toCrop, int width, int height,float corpScale) {
        if(toCrop == null) {
            return null;
        } else if(toCrop.getWidth() == width && toCrop.getHeight() == height) {
            return toCrop;
        } else {
            float dx = 0.0F;
            float dy = 0.0F;
            Matrix m = new Matrix();
            float scale;
            if(toCrop.getWidth() * height > width * toCrop.getHeight()) {
                scale = (float)height / (float)toCrop.getHeight();
                dx = ((float)width - (float)toCrop.getWidth() * scale) * corpScale;
            } else {
                scale = (float)width / (float)toCrop.getWidth();
                dy = ((float)height - (float)toCrop.getHeight() * scale) * corpScale;
            }

            m.setScale(scale, scale);
            m.postTranslate((float)((int)(dx + corpScale)), (float)((int)(dy + corpScale)));
            Bitmap result = Bitmap.createBitmap(width, height, getSafeConfig(toCrop));


            setAlpha(toCrop, result);
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint(6);
            canvas.drawBitmap(toCrop, m, paint);
            return result;
        }
    }
    private static Bitmap.Config getSafeConfig(Bitmap bitmap) {
        return bitmap.getConfig() != null?bitmap.getConfig(): Bitmap.Config.ARGB_8888;
    }
    @TargetApi(12)
    public static void setAlpha(Bitmap toTransform, Bitmap outBitmap) {
        if(Build.VERSION.SDK_INT >= 12 && outBitmap != null) {
            outBitmap.setHasAlpha(toTransform.hasAlpha());
        }

    }
    private void cropBitmap(Bitmap bitmap) {
        //图片宽和高
        int ivWidth = bitmap.getWidth();
        int ivHeight = bitmap.getHeight();

        Bitmap bm = Bitmap.createBitmap(bitmap,0,0,ivWidth,ivHeight/2);
        bitmap.recycle();
        bitmap = null;
        imageView.setImageBitmap(bm);
    }

    private void cropScaleBitmap(Bitmap bitmap, float v) {
        //图片宽和高
        int ivWidth = bitmap.getWidth();
        int ivHeight = bitmap.getHeight();
        //1.
        Matrix matrix = new Matrix();
        matrix.setScale(v,v);
        Bitmap bm = Bitmap.createBitmap(bitmap,0,0,ivWidth,ivHeight,matrix,true);
        bitmap.recycle();
        bitmap = null;
        imageView.setImageBitmap(bm);

    }

    private void cropNewBitmap(Bitmap bitmap,int newWidth,int newHeight) {
        //图片宽和高
        int ivWidth = bitmap.getWidth();
        int ivHeight = bitmap.getHeight();

        float scaleWidth = (float) newWidth / ivWidth;//屏幕比
        float scaleHeight = (float) newHeight / ivHeight ;
        Log.e("test","width:"+width+"--"+height+"---"+ivWidth+"----"+ivHeight+"---"+scaleWidth+"---"+scaleHeight+"----"+newWidth+"---"+newHeight);
        Matrix matrix = new Matrix();


        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap bm = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,false);
        bitmap.recycle();
        bitmap = null;
        imageView.setImageBitmap(bm);
    }
}
