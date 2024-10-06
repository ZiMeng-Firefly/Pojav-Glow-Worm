package net.kdt.pojavlaunch.prefs.screens;

import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.SwitchPreference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.firefly.utils.PGWTools;
import com.firefly.ui.dialog.CustomDialog;

import net.kdt.pojavlaunch.R;
import net.kdt.pojavlaunch.Tools;

public class LauncherPreferenceMiscellaneousFragment extends LauncherPreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle b, String str) {
        addPreferencesFromResource(R.xml.pref_misc);
        Preference driverPreference = requirePreference("zinkPreferSystemDriver");
        if (!Tools.checkVulkanSupport(driverPreference.getContext().getPackageManager())) {
            driverPreference.setVisible(false);
        }
        SwitchPreference useSystemVulkan = requirePreference("zinkPreferSystemDriver", SwitchPreference.class);
        useSystemVulkan.setOnPreferenceChangeListener((p, v) -> {
            boolean isAdreno = PGWTools.isAdrenoGPU();
            if (isAdreno) {
                onCheckGPUDialog(p);
                return false;
            }
            return true;
        });
    }

    private void onCheckGPUDialog(Preference pre) {
        new CustomDialog.Builder(getContext())
                .setTitle("No No No No No!")
                .setMessage("text")
                .setConfirmListener(R.string.preference_rendererexp_alertdialog_done, customView -> {
                    ((SwitchPreference) pre).setChecked(true);
                    return true;
                })
                .setCancelListener(R.string.preference_rendererexp_alertdialog_cancel, customView -> true)
                .setCancelable(false)
                .build()
                .show();
    }

}
