package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.kdt.pojavlaunch.R;
import net.kdt.pojavlaunch.Tools;
import net.kdt.pojavlaunch.extra.ExtraConstants;
import net.kdt.pojavlaunch.extra.ExtraCore;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AboutFragment extends Fragment {
    public static final String TAG = "ABOUT_FRAGMENT";
    private static final String GITHUB_URL_PLT = "https://github.com/PojavLauncherTeam";
    private static final String GITHUB_URL_VF = "https://github.com/Vera-Firefly";
    private static final String GITHUB_URL_MOV = "https://github.com/MovTery";
    private static final String GITHUB_URL_EURYA = "https://github.com/Eurya2233369";
    private static final String GITHUB_URL_COLORYR = "https://github.com/Coloryr";
    private static final String GITHUB_URL_MIO = "https://github.com/ShirosakiMio";
    private static final String GITHUB_URL_T = "https://github.com/Tungstend";
    public AboutFragment() {
        super(R.layout.fragment_about);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button mContributorButton1 = view.findViewById(R.id.contributor_pojavteam);
        Button mContributorButton2 = view.findViewById(R.id.contributor_vera_firefly);
        Button mContributorButton3 = view.findViewById(R.id.contributor_movtery);
        Button mContributorButton4 = view.findViewById(R.id.contributor_eurya2233369);
        Button mContributorButton5 = view.findViewById(R.id.contributor_coloryr);
        Button mContributorButton6 = view.findViewById(R.id.contributor_mio);
        Button mContributorButton7 = view.findViewById(R.id.contributor_tungstend);

        mContributorButtin1.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_PLT));
        mContributorButtin2.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_VF));
        mContributorButtin3.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_MOV));
        mContributorButtin4.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_EURYA));
        mContributorButtin5.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_COLORYR));
        mContributorButtin6.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_MIO));
        mContributorButtin7.setOnClickListener(v -> Tools.openURL(requireActivity(), GITHUB_URL_T));

        mContributorButtin2.setOnLongClickListener((v) -> {
            Tools.openURL(requireActivity(), Tools.URL_HOME);
            return true;
        });

    }

}
