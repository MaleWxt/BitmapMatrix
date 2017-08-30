# BitmapMatrix
这个是bitmap裁剪，缩放等操作。使用的时候很方便。


###使用的时候直接复制对应的方法即可，有个按比例缩放的方法，你需要传入缩放的比例
```Java
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
    ```
