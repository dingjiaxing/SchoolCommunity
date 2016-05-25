package cn.edu.shu.scommunity.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android_hddl_framework.util.ImageLoaderDisplay;
import android_hddl_framework.util.SharePreferenceUtils;
import android_hddl_framework.util.TLUtil;
import android_hddl_framework.view.PlayMoreDiaolog;
import cn.edu.shu.scommunity.R;
import cn.edu.shu.scommunity.activity.LoginActivity;
import cn.edu.shu.scommunity.model.UserInfo;

/**
 * Created by admin on 2016/4/14.
 */
public class MyInfoFragment extends Fragment implements View.OnClickListener{
    private ProgressDialog dialog;
    private ImageView iv_head;
    private TextView tv_title;
    private ImageButton btn_back;
    private RelativeLayout layout_person;
    private LinearLayout person_detail;
    private Button btn_exit;
    private boolean isLogin = false;
    private static final int EXIT_SUCCESS = 1000;
    private RelativeLayout layout_name;// 昵称
    private RelativeLayout layout_sex;// 性别
    private RelativeLayout layout_age;// 年龄
    private RelativeLayout layout_area;// 所在公司
    private RelativeLayout layout_userArea;// 所在区域
    private Context context;
    private PopupWindow popWindow;
    private LayoutInflater inflater;
    private EditText editText;
    private RadioGroup radiogroup_sex;
    private String sex;
    private TextView tv_name;
    private TextView tv_sex;
    private TextView tv_age;
    private TextView tv_area;
    private TextView tv_save;
    private TextView tv_phone;
    private TextView tv_userArea;
    private TextView tv_liveAddress;
    private PlayMoreDiaolog mPlayMoreDiaolog;
    private Bitmap upload_bitmap;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final String CACHE_DIR = "/aiwang/" + "images/";
    private static final int IMAGE_FROM_CAMERA = 0x0a1;
    private static final int IMAGE_FROM_PHOTOS = 0xfe2;
    public static final int PHOTORESOULT = 0xaf3;// 结果
    private UserInfo userInfo;
    View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_myinfo,null);
        findById();
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            //从相机获取到了照片路径
            case IMAGE_FROM_CAMERA:
                if (resultCode == Activity.RESULT_OK) {
                    startPhotoZoom(Uri.fromFile(getTempHeadFile()));
                }
                break;
            //从相册获取到了照片路径
            case IMAGE_FROM_PHOTOS:
                if (resultCode == Activity.RESULT_OK) {
                    startPhotoZoom(data.getData());
                }
                break;
//		拿到了图片结果
            case PHOTORESOULT:
                if (resultCode == Activity.RESULT_OK) {

                    upload_bitmap = BitmapFactory.decodeFile(getTempHeadFile()
                            .getPath());
                    //uploadUserAvatar(Bitmap2Bytes(upload_bitmap));
                    if (upload_bitmap == null)
                        return;
                    iv_head.setImageBitmap(upload_bitmap);
                }
                break;
        }
    }

    public void findById() {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("我的资料");
        btn_back = (ImageButton) view.findViewById(R.id.btn_back);
        layout_person= (RelativeLayout) view.findViewById(R.id.layout_person);
        person_detail = (LinearLayout) view.findViewById(R.id.person_detail);
        btn_exit = (Button) view.findViewById(R.id.exit_login_bt);
        tv_save=(TextView) view.findViewById(R.id.tv_register);
        tv_save.setText("保存");
        tv_save.setVisibility(View.GONE);
        btn_back.setVisibility(View.GONE);
        tv_save.setOnClickListener(this);
        layout_person.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_exit.setOnClickListener(this);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_sex = (TextView) view.findViewById(R.id.tv_sex);
        tv_age = (TextView) view.findViewById(R.id.tv_age);
        tv_area = (TextView) view.findViewById(R.id.tv_area);
        tv_phone=(TextView) view.findViewById(R.id.tv_phone);
        tv_userArea=(TextView) view.findViewById(R.id.tv_userArea);
        tv_liveAddress=(TextView) view.findViewById(R.id.tv_liveAddress);
        layout_name = (RelativeLayout) view.findViewById(R.id.layout_name);
        layout_sex = (RelativeLayout) view.findViewById(R.id.layout_sex);
        layout_age = (RelativeLayout) view.findViewById(R.id.layout_age);
        layout_area = (RelativeLayout) view.findViewById(R.id.layout_area);
        layout_userArea=(RelativeLayout)view.findViewById(R.id.layout_userArea);
        layout_name.setOnClickListener(this);
        layout_sex.setOnClickListener(this);
        layout_area.setOnClickListener(this);
        layout_age.setOnClickListener(this);
        layout_userArea.setOnClickListener(this);
        iv_head = (ImageView) view.findViewById(R.id.iv_person);
        iv_head.setOnClickListener(this);
    }
    public void setValue(){
        /*
        tv_name.setText(userInfo.getUsername()!=null?userInfo.getUsername():"");
        if(userInfo.getSex()==1)
        {
            tv_sex.setText("男");
        }else if (userInfo.getSex()==2) {
            tv_sex.setText("女");
        }else {
            tv_sex.setText("");
        }
        tv_sex.setText(userInfo.getSex()==1?"男":"女");
        tv_age.setText(userInfo.getAge()!=0?userInfo.getAge()+"":"");
        tv_area.setText(userInfo.getCompany()!=null?userInfo.getCompany():"");
        tv_phone.setText(userInfo.getMobile()!=null?userInfo.getMobile():"");
        tv_userArea.setText(userInfo.getUserArea()!=null?userInfo.getUserArea():"");
        tv_liveAddress.setText(userInfo.getLiveAddress()!=null?userInfo.getLiveAddress():"");
        */
//        ImageLoaderDisplay.displayImage(context, Constans.IMAGE_URL+userInfo.getHead(), iv_head);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.btn_back:
//                PersonInformationActivity.this.finish();
                break;
            case R.id.layout_person://选择头像
                showSelectDialog();
                break;
            case R.id.layout_name:// 选择昵称
                showEditTextPopWindow(layout_name, 0);
                break;
            case R.id.layout_sex:// 选择性别
                showEditTextPopWindow(layout_name, 1);
                break;
            case R.id.layout_age:// 选择年龄
                showEditTextPopWindow(layout_name, 2);
                break;
            case R.id.layout_area:// 选择所在公司
                showEditTextPopWindow(layout_name, 3);
                break;
            case R.id.layout_userArea:// 选择所在区域
                showEditTextPopWindow(layout_name, 4);
                break;
            case R.id.exit_login_bt:
                logout();
//                SharePreferenceUtils.remove("userinfo");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;
            case R.id.tv_register:
//                savePersonInfo();
                break;
        }

    }
    void logout(){

    }
    // 展示popwindow
    public void showEditTextPopWindow(View views, int type) {
        if (popWindow == null) {
            inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.bottom_edit, null);
            popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, true);
            initViewPop(view, type);
        }
        popWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);
        popWindow
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popWindow.showAtLocation(views, Gravity.CENTER, 0, 0);
    }
    // 处理逻辑
    public void initViewPop(View view, int type) {
        TextView tv_close = (TextView) view.findViewById(R.id.tv_close);
        TextView tv_yes = (TextView) view.findViewById(R.id.tv_yes);
        editText = (EditText) view.findViewById(R.id.edit_content);
        radiogroup_sex = (RadioGroup) view.findViewById(R.id.radiogroup_sex);
        if (type == 1) {
            radiogroup_sex.setVisibility(View.VISIBLE);
            editText.setVisibility(View.GONE);
        } else {
            radiogroup_sex.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
        }
        //对editText进行初始化，将用户的本来的放回到editText上让用户进行编辑
        switch(type){
            case 0:
                editText.setText(tv_name.getText().toString());
                break;
            case 1:
                RadioButton rb1=(RadioButton) radiogroup_sex.getChildAt(0);
                RadioButton rb2=(RadioButton) radiogroup_sex.getChildAt(1);
                if(tv_sex.getText().toString().equals("男")){
                    rb1.setChecked(true);
                    rb2.setChecked(false);
                }else{
                    rb1.setChecked(false);
                    rb2.setChecked(true);
                }
                break;
            case 2:
                editText.setText(tv_age.getText().toString());
                break;
            case 3:
                editText.setText(tv_area.getText().toString());
                break;
            case 4:
                editText.setText(tv_userArea.getText().toString());
                break;
            case 5:
                editText.setText(tv_liveAddress.getText().toString());
                break;
        }
        editText.requestFocus();	//让editText焦点在文本末尾
        tv_close.setOnClickListener(new Click(0, type));
        tv_yes.setOnClickListener(new Click(1, type));
        radiogroup_sex
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rbt_men:
                                sex = "男";
                                break;
                            case R.id.rbt_women:
                                sex = "女";
                                break;
                        }
                    }
                });
    }
    private void showSelectDialog() {
        List<String> mTitles = new ArrayList<String>();
        mTitles.add("从相册选择");
        mTitles.add("拍照");
        if (mPlayMoreDiaolog == null) {
            mPlayMoreDiaolog = new PlayMoreDiaolog(
                    getActivity(), mTitles);
        } else {
            mPlayMoreDiaolog.setmTitles(mTitles);
        }
        mPlayMoreDiaolog.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                switch (position) {
                    case 1:
                        String status = Environment.getExternalStorageState();
                        if (status.equals(Environment.MEDIA_MOUNTED)) {
                            getFromCamera();// 从相机获取
                        } else {
                            // 没有SD卡;
                            TLUtil.showToast(context, "手机没有SD卡");
                        }
                        break;
                    case 0:
                        getFromPhotos();// 从相相册获取
                        break;
                }
                mPlayMoreDiaolog.dismiss();
                mPlayMoreDiaolog = null;
            }
        });
        mPlayMoreDiaolog.show();
    }

    // 相机拍照
    private void getFromCamera() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(getTempHeadFile()));
            startActivityForResult(intent, IMAGE_FROM_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从相册选择
    private void getFromPhotos() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                IMAGE_UNSPECIFIED);
        startActivityForResult(intent, IMAGE_FROM_PHOTOS);

    }

    // 剪裁图片
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 130);
        intent.putExtra("outputY", 130);
        intent.putExtra("scale", true);
        Uri imageUri = Uri.fromFile(getTempHeadFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        try {
            startActivityForResult(intent, PHOTORESOULT);
        } catch (Exception e) {
        }
    }

    // 获取图片的路径
    private File getTempHeadFile() {
        File f = null;
        File head = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED))
            f = new File(android.os.Environment.getExternalStorageDirectory(),
                    CACHE_DIR);
        else
            f = getActivity().getCacheDir();

        if (!f.exists())
            f.mkdirs();
        else {
            if (f.isFile()) {
                f.deleteOnExit();
                f.mkdirs();
            }
        }
        head = new File(f, "myhead.jpg");
        if (!head.exists()) {
            try {
                head.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        f = null;
        return head;
    }
    private class Click implements View.OnClickListener {
        private int i;
        private int type;

        private Click(int i, int type) {
            this.i = i;
            this.type = type;
        }

        @Override
        public void onClick(View view) {
            switch (i) {
                case 0:
                    popWindow.dismiss();
                    popWindow = null;
                    break;
                case 1:
                    switch (type) {
                        case 0:
                            String et=editText.getText().toString().trim();
                            if(et.equals("")){
                                TLUtil.showToast(context, "昵称不能为空");
                            } else{
                                if(et.length()>6){
                                    TLUtil.showToast(context, "昵称不能超过6位");
                                }else{
                                    popWindow.dismiss();
                                    popWindow = null;
                                    tv_name.setText(editText.getText().toString());
                                }
                            }

                            break;
                        case 1:

                            popWindow.dismiss();
                            popWindow = null;
                            tv_sex.setText(sex);
                            break;
                        case 2:
                            popWindow.dismiss();
                            popWindow = null;
                            tv_age.setText(editText.getText().toString());
                            break;
                        case 3:
                            popWindow.dismiss();
                            popWindow = null;
                            tv_area.setText(editText.getText().toString());
                            break;
                        case 4:
                            popWindow.dismiss();
                            popWindow = null;
                            tv_userArea.setText(editText.getText().toString());
                            break;
                        case 5:
                            popWindow.dismiss();
                            popWindow = null;
                            tv_liveAddress.setText(editText.getText().toString());
                            break;
                        case 6:
                            popWindow.dismiss();
                            popWindow = null;
                            break;
                    }
                    break;

            }
        }
    }
}
