package com.damgrupo5.rescatapetsfinal.ClasesApoyo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class CambiarTipos {

    public Drawable StringABitmap(String fotoString, Context context){


        byte[] decodedBytes = Base64.decode(fotoString, Base64.DEFAULT);
        Drawable drwCambiado = new BitmapDrawable(context.getResources(), BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length));

        return drwCambiado;
    }

    public Bitmap ImagentoBitmap(ImageView imageView){

        Bitmap bitResult;
        Drawable drawable = imageView.getDrawable();

        if(drawable instanceof BitmapDrawable){
            // Si el drawable es un BitmapDrawable, extrae el Bitmap
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            bitResult = bitmapDrawable.getBitmap();
        }else {
            // Si el drawable no es un BitmapDrawable, crea un nuevo Bitmap basado en el ancho y alto del ImageView
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitResult = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitResult);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
        return bitResult;
    }

    public String BitmaptoString(Bitmap bitmap){
        String fotoString;
        // Convierte el bitmap en un array de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        fotoString = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return fotoString;
    }


}
