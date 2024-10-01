package net.kdt.pojavlaunch.fragments;

import android.os.Bundle;
import android.view.View;
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

        mContributorButtin1.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/PojavLauncherTeam"));
        mContributorButtin2.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/Vera-Firefly"));
        mContributorButtin3.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/MovTery"));
        mContributorButtin4.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/Eurya2233369"));
        mContributorButtin5.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/Coloryr"));
        mContributorButtin6.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/ShirosakiMio"));
        mContributorButtin7.setOnClickListener(v -> Tools.openURL(requireActivity(), "https://github.com/Tungstend"));

        mContributorButtin2.setOnLongClickListener((v) -> {
            Tools.openURL(requireActivity(), "https://github.com/Vera-Firefly/Pojav-Glow-Worm")
            return true;
        });

    }

}
