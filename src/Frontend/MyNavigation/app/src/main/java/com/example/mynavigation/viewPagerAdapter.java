package com.example.mynavigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class viewPagerAdapter extends PagerAdapter {
    Context context;
    int images[] = {
            R.drawable.imagem1,
            R.drawable.imagem2
    };
    int titulos[]= {
            R.string.titulo1,
            R.string.titulo2
    };
    int descricao[] = {
            R.string.descricao1,
            R.string.descricao1
    };

    public viewPagerAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return titulos.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView slideTituloImagem = (ImageView) view.findViewById(R.id.imagemSlide);
        TextView slideTitulos = (TextView) view.findViewById(R.id.textTitulo);
        TextView slideDesc = (TextView) view.findViewById(R.id.textDesc);

       slideTituloImagem.setImageResource(images[position]);
       slideTitulos.setText(titulos[position]);
       slideDesc.setText(descricao[position]);

       container.addView(view);

       return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
