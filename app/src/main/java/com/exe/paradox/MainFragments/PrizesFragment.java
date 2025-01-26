package com.exe.paradox.MainFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exe.paradox.Adapters.PrizesAdapter;
import com.exe.paradox.Models.User;
import com.exe.paradox.R;
import com.exe.paradox.Tools.Method;
import com.exe.paradox.databinding.FragmentPrizesBinding;
import com.exe.paradox.rest.api.APIMethods;
import com.exe.paradox.rest.api.interfaces.APIResponseListener;
import com.exe.paradox.rest.response.PrizesRP;


public class PrizesFragment extends Fragment {

    FragmentPrizesBinding binding;
    PrizesRP prizesRP;

    public PrizesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPrizesBinding.inflate(inflater, container, false);
        loadPrizes();
        return binding.getRoot();
    }

    private void loadPrizes() {
        if (prizesRP != null) {
            updateUI();
            return;
        }

        APIMethods.getPrizes(new APIResponseListener<PrizesRP>() {
            @Override
            public void success(PrizesRP response) {
                binding.progressBar.setVisibility(View.GONE);
                prizesRP = response;
                updateUI();
            }

            @Override
            public void fail(String code, String message, String redirectLink, boolean retry, boolean cancellable) {
                binding.progressBar.setVisibility(View.GONE);
                Method.showFailedAlert(getActivity(), code + " - " + message);
            }
        });

    }

    private void updateUI() {
        PrizesAdapter adapter = new PrizesAdapter(getActivity(), prizesRP);
        binding.progressBar.setVisibility(View.GONE);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerView.setVisibility(View.VISIBLE);
    }
}