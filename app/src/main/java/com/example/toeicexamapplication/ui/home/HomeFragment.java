package com.example.toeicexamapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.toeicexamapplication.R;
import com.example.toeicexamapplication.account.MyProfileActivity;
import com.example.toeicexamapplication.account.RankingActivity;
import com.example.toeicexamapplication.databinding.FragmentHomeBinding;
import com.example.toeicexamapplication.grammar.GrammarActivity;
import com.example.toeicexamapplication.listening.ListeningActivity;
import com.example.toeicexamapplication.reading.ReadingActivity;
import com.example.toeicexamapplication.vocabulary.VocabularyActivity;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private LinearLayout lVocabulary, lGrammer, lListening, lReading, lRanking, lAccount;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        getWidgets();
        handleEvent();

        return root;
    }

    private void handleEvent() {
        lVocabulary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), VocabularyActivity.class);
                startActivity(intent);
            }
        });

        lGrammer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), GrammarActivity.class);
                startActivity(intent);
            }
        });

        lListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ListeningActivity.class);
                startActivity(intent);
            }
        });

        lReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ReadingActivity.class);
                startActivity(intent);
            }
        });

        lRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
            }
        });

        lAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), MyProfileActivity.class);
                startActivity(intent);
            }
        });
    }


    private void getWidgets() {
        lVocabulary = root.findViewById(R.id.layout_vocabulary);
        lGrammer = root.findViewById(R.id.layout_grammar);
        lListening = root.findViewById(R.id.layout_listening);
        lReading = root.findViewById(R.id.layout_reading);
        lRanking = root.findViewById(R.id.layout_ranking);
        lAccount = root.findViewById(R.id.layout_account);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}