package com.example.sb;



import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {

    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_nav_view);

        NavHostFragment navHostFragment =
                (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        navController = navHostFragment.getNavController();

        // Tab 点击切换页面
        //还没实现 ...
//        bottomNav.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.navigation_profile:
//                    navController.navigate(R.id.profileFragment);
//                    return true;
//                case R.id.navigation_health:
//                    navController.navigate(R.id.dataInputFragment);
//                    return true;
//                case R.id.navigation_exercise:
//                    navController.navigate(R.id.exerciseRecordFragment);
//                    return true;
//                case R.id.navigation_meal:
//                    navController.navigate(R.id.mealRecordFragment);
//                    return true;
//            }
//            return false;
//        });

        return view;
    }
}
