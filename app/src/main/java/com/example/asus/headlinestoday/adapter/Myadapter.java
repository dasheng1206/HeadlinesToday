package com.example.asus.headlinestoday.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.headlinestoday.R;
import com.example.asus.headlinestoday.bean.Bean_tuijain;
import com.example.asus.headlinestoday.bean.Tuijian_Bean;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/9/12.
 */

public class Myadapter extends BaseAdapter {
    private Context context;
    // private List<Bean_tuijain.ResultBean.DataBean> list = new ArrayList<>();
    private List<Tuijian_Bean.DataBean> list = new ArrayList<>();
    private final int ONE = 0;
    private final int TWO = 1;
    private final int THREE = 2;

    private final ImageLoader mLoader;
    // private Bean_tuijain.ResultBean.DataBean mDataBean;
    private Tuijian_Bean.DataBean mDataBean;


    public Myadapter(Context context, List<Tuijian_Bean.DataBean> list) {
        this.context = context;
        this.list = list;
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this.context);
        mLoader = ImageLoader.getInstance();
        mLoader.init(configuration);

    }

    public void addData(List<Tuijian_Bean.DataBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void upData(List<Tuijian_Bean.DataBean> list) {
        this.list.clear();
        addData(list);
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getImage_list() != null) {
            return THREE;
        } else if (list.get(position).getLarge_image_list() != null) {
            return TWO;
        } else {
            return ONE;
        }
    }

//    @Override
//    public int getItemViewType(int i) {
//        if (list.get(i).getThumbnail_pic_s02() != null) {
//            return THREE;
//        } else if (list.get(i).getThumbnail_pic_s() != null) {
//            return TWO;
//        } else {
//            return ONE;
//        }
//    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        int type = getItemViewType(i);

        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        ViewHolder3 holder3 = null;
        if (convertView == null) {
            switch (type) {
                case ONE:
                    convertView = View.inflate(context, R.layout.item_one, null);
                    holder1 = new ViewHolder1(convertView);
                    convertView.setTag(holder1);
                    break;
                case TWO:
                    convertView = View.inflate(context, R.layout.item_two, null);
                    holder2 = new ViewHolder2(convertView);
                    convertView.setTag(holder2);
                    break;
                case THREE:
                    convertView = View.inflate(context, R.layout.item_three, null);
                    holder3 = new ViewHolder3(convertView);
                    convertView.setTag(holder3);
                    break;

                default:
                    break;
            }
        } else {
            switch (type) {
                case ONE:
                    holder1 = (ViewHolder1) convertView.getTag();
                    break;
                case TWO:
                    holder2 = (ViewHolder2) convertView.getTag();
                    break;
                case THREE:
                    holder3 = (ViewHolder3) convertView.getTag();
                    break;

                default:
                    break;
            }
        }
        mDataBean = list.get(i);
        switch (type) {
            case ONE:
                holder1.textView1.setText(mDataBean.getTitle() + "");
                // holder1.textView2.setText(mDataBean.getMedia_info().getName().toString());
                //  holder1.textView3.setText(mDataBean.getDate());

                break;
            case TWO:
                holder2.textView2_1.setText(mDataBean.getTitle() + "");
                // holder2.textView2_2.setText(mDataBean.getMedia_info().getName()+"");
                //holder2.textView2_3.setText(mDataBean.getDate());
                mLoader.displayImage(mDataBean.getLarge_image_list().get(0).getUrl(), holder2.mImageView2_1);

                break;
            case THREE:
                holder3.textView3_1.setText(mDataBean.getTitle() + "");
                //holder3.textView3_2.setText(mDataBean.getMedia_info().getName()+"");
                // holder3.textView3_3.setText(mDataBean.getDate());
                mLoader.displayImage(mDataBean.getImage_list().get(0).getUrl(), holder3.mImageView3_1);
                mLoader.displayImage(mDataBean.getImage_list().get(1).getUrl(), holder3.mImageView3_2);
                mLoader.displayImage(mDataBean.getImage_list().get(2).getUrl(), holder3.mImageView3_3);


                break;

            default:
                break;
        }
        return convertView;
    }


    public static class ViewHolder1 {
        public View rootView;
        public TextView textView1, textView2, textView3;

        public ViewHolder1(View rootView) {
            this.rootView = rootView;
            this.textView3 = (TextView) rootView.findViewById(R.id.text1_3);
            this.textView2 = (TextView) rootView.findViewById(R.id.text1_2);
            this.textView1 = (TextView) rootView.findViewById(R.id.text1_1);

        }
    }

    public static class ViewHolder2 {
        public View rootView;
        public TextView textView2_1, textView2_2, textView2_3;
        private ImageView mImageView2_1;

        public ViewHolder2(View rootView) {
            this.rootView = rootView;
            this.textView2_3 = (TextView) rootView.findViewById(R.id.text2_3);
            this.textView2_2 = (TextView) rootView.findViewById(R.id.text2_2);
            this.textView2_1 = (TextView) rootView.findViewById(R.id.text2_1);
            mImageView2_1 = (ImageView) rootView.findViewById(R.id.image2_1);
        }
    }

    public static class ViewHolder3 {
        public View rootView;
        public TextView textView3_1, textView3_2, textView3_3;
        private ImageView mImageView3_1, mImageView3_2, mImageView3_3;

        public ViewHolder3(View rootView) {
            this.rootView = rootView;
            this.textView3_3 = (TextView) rootView.findViewById(R.id.text3_3);
            this.textView3_2 = (TextView) rootView.findViewById(R.id.text3_2);
            this.textView3_1 = (TextView) rootView.findViewById(R.id.text3_1);
            mImageView3_3 = (ImageView) rootView.findViewById(R.id.image3_3);
            mImageView3_2 = (ImageView) rootView.findViewById(R.id.image3_2);
            mImageView3_1 = (ImageView) rootView.findViewById(R.id.image3_1);
        }
    }
}
