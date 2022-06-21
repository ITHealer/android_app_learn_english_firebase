package com.example.toeicexamapplication.listening;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toeicexamapplication.R;

import java.util.List;

public class ListeningAdapter extends BaseAdapter {
    private Context context;
    private List<Listen_list> lsbode;

    public ListeningAdapter(Context context, List<Listen_list> lsbode) {
        this.context = context;
        this.lsbode = lsbode;
    }

    @Override
    public int getCount() {
        return lsbode.size();
    }

    @Override
    public Object getItem(int i) {
        return lsbode.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lsbode.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //hàm vẽ 1 item trên listview
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_listening, null);
        }

        //đọc item thứ i lưu vào biến bode
        Listen_list bode = lsbode.get(i);
        ImageView imgIcon = view.findViewById(R.id.imgIcon);
        ImageView imgBg = view.findViewById(R.id.imgBg);
        TextView de = view.findViewById(R.id.tvTenDe);
        //gán giá trị cho các control
        imgIcon.setImageResource(bode.getImgicon());
        imgBg.setImageResource(bode.getImgbg());
        de.setText(bode.getBode());
        return view;
    }
}
