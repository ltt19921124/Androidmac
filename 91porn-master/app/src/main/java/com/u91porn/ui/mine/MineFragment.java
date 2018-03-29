package com.u91porn.ui.mine;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;
import com.sdsmdg.tastytoast.TastyToast;
import com.u91porn.R;
import com.u91porn.data.model.User;
import com.u91porn.ui.BaseFragment;
import com.u91porn.ui.about.AboutActivity;
import com.u91porn.ui.download.DownloadActivity;
import com.u91porn.ui.favorite.FavoriteActivity;
import com.u91porn.ui.history.HistoryActivity;
import com.u91porn.ui.main.MainActivity;
import com.u91porn.ui.proxy.ProxySettingActivity;
import com.u91porn.ui.setting.SettingActivity;
import com.u91porn.ui.user.UserLoginActivity;
import com.u91porn.utils.UserHelper;
import com.u91porn.utils.constants.Constants;
import com.u91porn.utils.constants.Keys;
import com.u91porn.widget.ObservableScrollView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author flymegoc
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = MineFragment.class.getSimpleName();
    @BindView(R.id.tv_nav_username)
    TextView tvNavUsername;
    @BindView(R.id.tv_nav_last_login_time)
    TextView tvNavLastLoginTime;
    @BindView(R.id.tv_nav_last_login_ip)
    TextView tvNavLastLoginIp;
    @BindView(R.id.mine_list)
    QMUIGroupListView mineList;
    Unbinder unbinder;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ov_setting_wrapper)
    ObservableScrollView observableScrollView;
    private String myFavoriteStr;
    private String proxyStr;
    public String myDownloadStr;
    private String viewHistoryStr;
    private String nightModeStr;
    private String aboutMeStr;
    private String moreSettingStr;

    private int scrollYPosition = 0;
    private QMUICommonListItemView openProxyItemWithSwitch;


    public MineFragment() {

        // Required empty public constructor
    }

    public static MineFragment getInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        imageView.setOnClickListener(this);
        observableScrollView.setOnScollChangedListener(new ObservableScrollView.OnScollChangedListener() {
            @Override
            public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
                scrollYPosition = y;
            }
        });
        observableScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                int scrollYPosition = dataManager.getSettingScrollViewScrollPosition();
                if (scrollYPosition > 0) {
                    dataManager.setSettingScrollViewScrollPosition(0);
                    observableScrollView.scrollTo(0, scrollYPosition);
                }
            }
        }, 200);
        initMineSection();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpUserInfo(user);
        String proxyStr = dataManager.getProxyIpAddress();
        int proxyPort = dataManager.getProxyPort();
        updateProxySetUI(proxyStr, proxyPort);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initStr();
    }

    private void initStr() {
        myFavoriteStr = getString(R.string.my_collect);
        proxyStr = getString(R.string.proxy_setting);
        myDownloadStr = getString(R.string.my_download);
        viewHistoryStr = getString(R.string.history_views);
        nightModeStr = getString(R.string.night_mode);
        aboutMeStr = getString(R.string.about_me);
        moreSettingStr = getString(R.string.more_setting);
    }

    private void initMineSection() {

        boolean openNightMode = dataManager.isOpenNightMode();
        QMUICommonListItemView openNightModeItemWithSwitch = mineList.createItemView(nightModeStr);
        openNightModeItemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        openNightModeItemWithSwitch.getSwitch().setChecked(openNightMode);
        openNightModeItemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dataManager.setOpenNightMode(isChecked);
                dataManager.setSettingScrollViewScrollPosition(scrollYPosition);
                AppCompatDelegate.setDefaultNightMode(isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(Keys.KEY_SELECT_INDEX, 4);
                startActivity(intent);
                activity.finish();
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        boolean openProxy = dataManager.isOpenHttpProxy();
        openProxyItemWithSwitch = mineList.createItemView(proxyStr);
        openProxyItemWithSwitch.setOrientation(QMUICommonListItemView.VERTICAL);
        final String proxyHost = dataManager.getProxyIpAddress();
        final int port = dataManager.getProxyPort();
        if (TextUtils.isEmpty(proxyHost) || port == 0) {
            openProxyItemWithSwitch.setDetailText("长按设置");
        } else {
            openProxyItemWithSwitch.setDetailText(proxyHost + " : " + port);
        }

        openProxyItemWithSwitch.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_SWITCH);
        openProxyItemWithSwitch.getSwitch().setChecked(openProxy);
        openProxyItemWithSwitch.getSwitch().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showMessage("暂时取消设置", TastyToast.WARNING);
                    buttonView.setChecked(false);
                    dataManager.setOpenHttpProxy(false);
                    return;
                }
                if (isChecked) {
                    showMessage("长按可设置哟", TastyToast.INFO);
                }
                if (TextUtils.isEmpty(proxyHost) || port == 0) {
                    buttonView.setChecked(false);
                    dataManager.setOpenHttpProxy(false);
                    return;
                }
                dataManager.setOpenHttpProxy(isChecked);
            }
        });

        QMUICommonListItemView favoriteItemWithChevron = mineList.createItemView(myFavoriteStr);
        favoriteItemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView downloadItemWithChevron = mineList.createItemView(myDownloadStr);
        downloadItemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUICommonListItemView viewHistoryItemWithChevron = mineList.createItemView(viewHistoryStr);
        viewHistoryItemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        mineList.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NORMAL);

        QMUIGroupListView.newSection(context)
                .addItemView(favoriteItemWithChevron, this)
                .addItemView(downloadItemWithChevron, this)
                .addItemView(viewHistoryItemWithChevron, this)
                .addItemView(openNightModeItemWithSwitch, null)
                .addTo(mineList);

        QMUIGroupListView.newSection(context)
                .addItemView(openProxyItemWithSwitch, null, new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Intent intent = new Intent(context, ProxySettingActivity.class);
                        startActivityWithAnimotion(intent);
                        return false;
                    }
                })
                .addTo(mineList);

        QMUICommonListItemView moreSettingItemWithChevron = mineList.createItemView(moreSettingStr);
        moreSettingItemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(context)
                .addItemView(moreSettingItemWithChevron, this)
                .addTo(mineList);

        QMUICommonListItemView aboutItemWithChevron = mineList.createItemView(aboutMeStr);
        aboutItemWithChevron.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON);

        QMUIGroupListView.newSection(context)
                .addItemView(aboutItemWithChevron, this)
                .addTo(mineList);
    }

    public void updateProxySetUI(String proxyStr, int proxyPort) {
        if (!TextUtils.isEmpty(proxyStr) && proxyPort > 0) {
            openProxyItemWithSwitch.setDetailText(proxyStr + " : " + proxyPort);
        }
        boolean openProxy = dataManager.isOpenHttpProxy();
        openProxyItemWithSwitch.getSwitch().setChecked(openProxy);
    }

    private void userImageViewClick() {
        if (UserHelper.isUserInfoComplete(user)) {
            return;
        }
        Intent intent = new Intent(context, UserLoginActivity.class);
        startActivityForResultWithAnimotion(intent, Constants.USER_LOGIN_REQUEST_CODE);
    }

    @SuppressLint("SetTextI18n")
    private void setUpUserInfo(User user) {

        if (!UserHelper.isUserInfoComplete(user)) {
            tvNavUsername.setText("请登录");
            tvNavLastLoginTime.setText("---");
            tvNavLastLoginIp.setText("---");
            return;
        }

        if (!TextUtils.isEmpty(user.getStatus())) {
            String status = user.getStatus().contains("正常") ? "正常" : "异常";
            tvNavUsername.setText(user.getUserName() + "(" + status + ")");
        }
        if (!TextUtils.isEmpty(user.getLastLoginTime())) {
            tvNavLastLoginTime.setText(user.getLastLoginTime().replace("(如果你觉得时间不对,可能帐号被盗)", ""));
        }
        tvNavLastLoginIp.setText(user.getLastLoginIP());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.USER_LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            setUpUserInfo(user);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        if (v instanceof QMUICommonListItemView) {
            String string = String.valueOf(((QMUICommonListItemView) v).getText());
            actionClickList(string);
        } else {
            switch (v.getId()) {
                case R.id.imageView:
                    userImageViewClick();
                    break;
                default:
            }
        }
    }

    private void actionClickList(String content) {
        if (TextUtils.isEmpty(content)) {
            return;
        }

        if (content.equals(myFavoriteStr)) {
            if (!UserHelper.isUserInfoComplete(user)) {
                Intent intent = new Intent(context, UserLoginActivity.class);
                intent.putExtra(Keys.KEY_INTENT_LOGIN_FOR_ACTION, UserLoginActivity.LOGIN_ACTION_FOR_LOOK_MY_FAVORITE);
                startActivityForResultWithAnimotion(intent, Constants.USER_LOGIN_REQUEST_CODE);
                return;
            }
            Intent intent = new Intent(context, FavoriteActivity.class);
            startActivityWithAnimotion(intent);
        } else if (content.equals(myDownloadStr)) {
            Intent intent = new Intent(context, DownloadActivity.class);
            startActivityWithAnimotion(intent);
        } else if (content.equals(viewHistoryStr)) {
            Intent intent = new Intent(context, HistoryActivity.class);
            startActivityWithAnimotion(intent);
        } else if (content.equals(aboutMeStr)) {
            Intent intent = new Intent(context, AboutActivity.class);
            startActivityWithAnimotion(intent);
        } else if (content.equals(moreSettingStr)) {
            Intent intent = new Intent(context, SettingActivity.class);
            startActivityWithAnimotion(intent);
        }
    }
}
