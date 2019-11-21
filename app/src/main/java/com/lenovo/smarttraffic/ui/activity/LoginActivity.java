package com.lenovo.smarttraffic.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lenovo.smarttraffic.App;
import com.lenovo.smarttraffic.MainActivity;
import com.lenovo.smarttraffic.R;
import com.lenovo.smarttraffic.util.HttpUtil;
import com.lenovo.smarttraffic.util.SpUtil;

import org.json.JSONObject;

import java.util.HashMap;

import static com.android.volley.Request.Method.POST;

/**
 * @author Amoly
 * @date 2019/4/11.
 * description：
 */
public class LoginActivity extends BaseActivity {

    private EditText mEditTextName;
    private EditText mEditTextPassword;
    private TextInputLayout mTextInputLayoutName;
    private TextInputLayout mTextInputLayoutPswd;
    private TextView tv1;
    private TextView tv2;
    private CheckBox jzpwdCB;


    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolBar(findViewById(R.id.toolbar), true, getString(R.string.login));

        mTextInputLayoutName = findViewById(R.id.textInputLayoutName);
        mTextInputLayoutPswd = findViewById(R.id.textInputLayoutPassword);

        mEditTextName = findViewById(R.id.editTextName);
        mTextInputLayoutName.setErrorEnabled(true);
        mEditTextPassword = findViewById(R.id.editTextPassword);
        mTextInputLayoutPswd.setErrorEnabled(true);
        Button loginButton = findViewById(R.id.loginBtn);
        initView();
        if (SpUtil.getBoolean("jzmm", false)) {
            jzpwdCB.setChecked(true);
            String user1 = SpUtil.getString(SpUtil.USERNAME, "user1");
            String string = SpUtil.getString(SpUtil.USERPWD, "123456");
            mEditTextName.setText(user1);
            mEditTextPassword.setText(string);
        }
        mEditTextName.addTextChangedListener(new TextWatcher() {
            @Override/*内容要改变之前调用*/
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /*从start位置开始，count个字符（空字符是0）将被after个字符替换*/

            }

            @Override/*内容要改变时调用*/
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*说明在s字符串中，从start位置开始的count个字符刚刚取代了长度为before的旧文本*/
            }

            @Override/*内容要改变之后调用*/
            public void afterTextChanged(Editable s) {
                //这个方法被调用，那么说明s字符串的某个地方已经被改变。
                String s1 = s.toString();
                if(!(TextUtils.isEmpty(s1))){
                    tv1.setVisibility(View.INVISIBLE);
                }
            }
        });

        mEditTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if(!(TextUtils.isEmpty(s1))){
                    tv2.setVisibility(View.INVISIBLE);
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditTextName.getText().toString();
                String pwd = mEditTextPassword.getText().toString();
                if(TextUtils.isEmpty(name)){
                    tv1.setVisibility(View.VISIBLE);
                }
                if(TextUtils.isEmpty(pwd)){
                    tv2.setVisibility(View.VISIBLE);
                }
                if(TextUtils.isEmpty(name)|TextUtils.isEmpty(pwd)){
                    return;
                }
                String str="user_login";
                HashMap<Object, Object> map = new HashMap<>();
                map.put("UserName",name);
                map.put("UserPwd",pwd);
                String url = "http://192.168.1.104:8088/transportservice/action/" + str + ".do";
//                map.put("UserName", SpUtil.getString(SpUtil.USERNAME, "user1"));
                JSONObject jsonObject = new JSONObject(map);
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(POST, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                           SpUtil.putBoolean("jzmm",jzpwdCB.isChecked());
                           SpUtil.putString(SpUtil.USERNAME,name);
                           SpUtil.putString(SpUtil.USERPWD,pwd);
                           SpUtil.putBoolean(SpUtil.ISLOGIN,true);
                           App.showToast("登录成功");
                           finish();
                           startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }else {
                            App.showToast("用户名密码错误");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e("TAG",volleyError.toString());
                    }
                });
                App.getQueue().add(jsonObjectRequest);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.wl_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.wl_set) {
            Intent intent = new Intent(LoginActivity.this, IPSetActivity.class);
            intent.putExtra("title", "IP设置");
            startActivity(intent);
        }
        return false;
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
        jzpwdCB = (CheckBox) findViewById(R.id.jzpwdCB);
    }
}
