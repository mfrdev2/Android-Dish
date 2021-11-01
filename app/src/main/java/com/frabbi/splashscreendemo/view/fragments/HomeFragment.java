package com.frabbi.splashscreendemo.view.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.frabbi.splashscreendemo.R;
import com.frabbi.splashscreendemo.applicaton.FavDishApplication;
import com.frabbi.splashscreendemo.databinding.FragmentHomeBinding;
import com.frabbi.splashscreendemo.model.entities.FavDish;
import com.frabbi.splashscreendemo.view.activities.AddActivity;
import com.frabbi.splashscreendemo.view.adapters.FavDishesAdapter;
import com.frabbi.splashscreendemo.viewmodel.FavDishViewModel;
import com.frabbi.splashscreendemo.viewmodel.FavDishViewModelFactory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FavDishViewModel favDishViewModel;
    private FragmentHomeBinding mBinding;
    private FavDishesAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //Here enabled OptionMenuOperation
        setHasOptionsMenu(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FavDishViewModelFactory factory = new FavDishViewModelFactory(FavDishApplication.repository);
        favDishViewModel = factory.create(FavDishViewModel.class);


        mBinding.rvDishesList.setLayoutManager(new GridLayoutManager(requireContext(),2));

        mAdapter  = new FavDishesAdapter(HomeFragment.this);
        favDishViewModel.getListData().observe(requireActivity(), new Observer<List<FavDish>>() {
            @Override
            public void onChanged(List<FavDish> favDishes) {
                if(favDishes != null){
                    if(!favDishes.isEmpty()){
                        mBinding.rvDishesList.setVisibility(View.VISIBLE);
                        mBinding.tvNoDishesAddedYet.setVisibility(View.GONE);
                        mAdapter.setDishesList(favDishes);
                    }else {
                        mBinding.rvDishesList.setVisibility(View.GONE);
                        mBinding.tvNoDishesAddedYet.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        mBinding.rvDishesList.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater,container,false);

        return mBinding.getRoot();
    }

    //Here created optionMenuItem.
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item_add,menu);
    }

    //Here selected optionMenuItem.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addMenuItemId:
                Intent intent = new Intent(requireActivity(), AddActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}