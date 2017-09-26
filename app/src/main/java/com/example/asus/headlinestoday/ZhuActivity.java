package com.example.asus.headlinestoday;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.example.asus.headlinestoday.bean.LeftMenu;
import com.example.asus.headlinestoday.fragment.Bendi_fragment;
import com.example.asus.headlinestoday.fragment.Keji_fragment;
import com.example.asus.headlinestoday.fragment.Remen_fragment;
import com.example.asus.headlinestoday.fragment.Shehui_fragment;
import com.example.asus.headlinestoday.fragment.Shipin_fragment;
import com.example.asus.headlinestoday.fragment.Tuijian_fragment;
import com.example.asus.headlinestoday.fragment.Yule_fragment;
import com.example.asus.headlinestoday.guide.MainActivity;
import com.example.asus.headlinestoday.qqlogin.Qqlogin;


import java.util.ArrayList;
import java.util.List;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class ZhuActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private ListView lv;
    private ImageView zuola;
    private List<LeftMenu> list;
    private TabLayout tabLayout;
    private LinearLayout yejian;
    private boolean flags;
    private SharedPreferences.Editor edit;
    //默认是日间模式
    private SharedPreferences sharedPreferences;
    private List<ChannelBean> clist;
    private String jsonStr;
    private EventHandler eventHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //夜间存储状态值
        nightcun();
        lv = (ListView) findViewById(R.id.listview);
        //频道管理
        ImageView img = (ImageView) findViewById(R.id.img_pin);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (clist == null) {//判断集合中是否已有数据，没有则创建
                    clist = new ArrayList<>();
                    //第一个是显示的条目，第二个参数是否显示
                    clist.add(new ChannelBean("推荐", true));
                    clist.add(new ChannelBean("热门", true));
                    clist.add(new ChannelBean("本地", true));
                    clist.add(new ChannelBean("视屏", true));
                    clist.add(new ChannelBean("社会", true));
                    clist.add(new ChannelBean("娱乐", false));
                    clist.add(new ChannelBean("科技", false));
                    clist.add(new ChannelBean("汽车", false));
                    clist.add(new ChannelBean("体育", false));
                    clist.add(new ChannelBean("财经", false));
                    clist.add(new ChannelBean("军事", false));
                    clist.add(new ChannelBean("国际", false));
                    clist.add(new ChannelBean("段子", false));
                    clist.add(new ChannelBean("趣图", false));
                    clist.add(new ChannelBean("健康", false));
                    clist.add(new ChannelBean("美女", false));
                    ChannelActivity.startChannelActivity(ZhuActivity.this, clist);
                } else if (jsonStr != null) {//当判断保存的字符串不为空的时候，直接加载已经有了的字符串
                    ChannelActivity.startChannelActivity(ZhuActivity.this, jsonStr);
                }


            }

        });


        //Tablayout Viewpager关联 设置tablayout头文字
        tablayoutViewpager(tabLayout);
        //侧拉
        cela();


        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，
        //并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        // SMSSDK.setAskPermisionOnReadContact(boolShowInDialog);
        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable) data;
                    String msg = throwable.getMessage();
                    Toast.makeText(ZhuActivity.this, msg, Toast.LENGTH_SHORT).show();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };
        //添加头布局 第三方登录

        toubuju();

        //尾布局 添加离线下载 夜间模式 设置
        weipuju();
    }

    //夜间存储状态值
    private void nightcun() {
        sharedPreferences = getSharedPreferences("flag", MODE_PRIVATE);
        edit = sharedPreferences.edit();
        flags = sharedPreferences.getBoolean("flags", true);
        if (flags) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.NightAppTheme);
        }
        setContentView(R.layout.zhu_activity);
    }

    //Tablayout Viewpager关联 设置tablayout头文字
    private void tablayoutViewpager(TabLayout tabLayout) {
        viewPager = (ViewPager) findViewById(R.id.ViewPager_vp);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        //viewpager适配
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new Tuijian_fragment();
                    case 1:
                        return new Remen_fragment();
                    case 2:
                        return new Bendi_fragment();
                    case 3:
                        return new Shipin_fragment();
                    case 4:
                        return new Shehui_fragment();
                    case 5:
                        return new Yule_fragment();
                    case 6:
                        return new Keji_fragment();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 7;
            }
        });
        //viewpager tablayout 关联
        tabLayout.setupWithViewPager(viewPager);
        //给tablayout设置头标题
        TabLayout.Tab tab1 = tabLayout.getTabAt(0);
        tab1.setText("推荐");
        TabLayout.Tab tab2 = tabLayout.getTabAt(1);
        tab2.setText("热门");
        TabLayout.Tab tab3 = tabLayout.getTabAt(2);
        tab3.setText("本地");
        TabLayout.Tab tab4 = tabLayout.getTabAt(3);
        tab4.setText("视频");
        TabLayout.Tab tab5 = tabLayout.getTabAt(4);
        tab5.setText("社会");
        TabLayout.Tab tab6 = tabLayout.getTabAt(5);
        tab6.setText("娱乐");
        TabLayout.Tab tab7 = tabLayout.getTabAt(6);
        tab7.setText("科技");
    }

    //侧拉
    private void cela() {
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        zuola = (ImageView) findViewById(R.id.zuocela);

        //点击左上角按钮左侧拉划出
        zuola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        //添加主界面
        getSupportFragmentManager().beginTransaction().replace(R.id.ViewPager_vp, new Tuijian_fragment()).commit();

        //侧拉listview添加选项

        list = new ArrayList<>();
        list.add(new LeftMenu("   好友动态", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   与我相关", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   我的头条", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   我的话题", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   收藏", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   活动", R.drawable.loveicon_textpage));
        list.add(new LeftMenu("   商城", R.drawable.loveicon_textpage));
        lv.setAdapter(new MyLiftAdapter());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ZhuActivity.this, "asdfghjkl", Toast.LENGTH_SHORT).show();
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawer.setClickable(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    //添加头布局 第三方登录
    private void toubuju() {
        //添加头布局
        View Heade = View.inflate(this, R.layout.cela_img, null);
        ImageView qq = Heade.findViewById(R.id.image_qq);
        ImageView weixin = Heade.findViewById(R.id.image_weixin);
        ImageView xinlang = Heade.findViewById(R.id.image_xinlang);
        Button shouji =(Button) Heade.findViewById(R.id.shouji);

        lv.addHeaderView(Heade);
        //第三方点击事件
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ZhuActivity.this, Qqlogin.class);
                startActivity(intent);
                Toast.makeText(ZhuActivity.this, "QQ登录", Toast.LENGTH_SHORT).show();

            }
        });
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhuActivity.this, "微信登录", Toast.LENGTH_SHORT).show();
            }
        });
        xinlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhuActivity.this, "新浪", Toast.LENGTH_SHORT).show();
            }
        });
        shouji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ZhuActivity.this, "新浪", Toast.LENGTH_SHORT).show();
                //打开注册界面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(eventHandler);
                //显示注册的面板
                registerPage.show(ZhuActivity.this);
            }
        });
        // 注册监听
       SMSSDK.registerEventHandler(eventHandler);

    }
    @Override

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }


    //尾布局 添加离线下载 夜间模式 设置
    private void weipuju() {
        //添加尾布局
        View Foote = View.inflate(this, R.layout.cela_wei, null);
        LinearLayout xiazai = Foote.findViewById(R.id.xiazai);
        yejian = Foote.findViewById(R.id.yejian);
        LinearLayout shezhi = Foote.findViewById(R.id.shezhi);
        lv.addFooterView(Foote);
        //wei点击事件
        xiazai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhuActivity.this, "下载", Toast.LENGTH_SHORT).show();
            }
        });

        yejian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flags) {
                    edit.putBoolean("flags", false);
                } else {
                    edit.putBoolean("flags", true);
                }
                edit.commit();
                recreate();
                Toast.makeText(ZhuActivity.this, "夜间模式启动", Toast.LENGTH_SHORT).show();
            }
        });
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhuActivity.this, "设置", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //侧拉listview适配器
    class MyLiftAdapter extends BaseAdapter {
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
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(ZhuActivity.this, R.layout.cela_inem, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) convertView.findViewById(R.id.cela_text);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.cela_img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(list.get(i).getName());
            viewHolder.imageView.setImageResource(list.get(i).getImage());
            return convertView;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }

    //频道管理回调的方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE) {
            jsonStr = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
        }
    }
}
