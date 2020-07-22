package com.example.second.controller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.second.R;
import com.example.second.model.Question;
import com.example.second.repository.Repository;

import java.util.List;

public class QuestionListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private Repository mRepository = Repository.getRepository();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_question_list, container, false);
        init(view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRecyclerView.setAdapter(new QuestionAdapter(mRepository.getQuestionList()));
        return view;
    }

    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view_contaner);
    }

    private class QuestionHolder extends RecyclerView.ViewHolder {
        private TextView mText;
        private CheckBox mAnswer;
        private CheckBox mCheat;
        private TextView mColor;

        public QuestionHolder(@NonNull View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.list_row_question_text);
            mColor = itemView.findViewById(R.id.list_row_question_color);
            mCheat = itemView.findViewById(R.id.list_row_checkBox_cheat);
            mAnswer = itemView.findViewById(R.id.list_row_checkBox_answer);
        }
        public void questionBinder(Question question){
            mText .setText( question.getmText());
            mAnswer.setChecked(question.ismAnswerTrue());
            mCheat .setChecked(question.ismIsCheatable());
            mColor .setText(String.valueOf(question.getmTextColor()));
            mAnswer.setEnabled(false);
            mCheat.setEnabled(false);
        }
    }

    private class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder>{
        private List<Question> mQuestionList ;

        public QuestionAdapter(List<Question> questionList) {
            mQuestionList = questionList;
        }

        public List<Question> getQuestionList() {
            return mQuestionList;
        }


        @NonNull
        @Override
        public QuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new QuestionHolder(LayoutInflater.from(getActivity()).inflate(R.layout.list_row_question,
                    parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull QuestionHolder holder, int position) {
            holder.questionBinder(mQuestionList.get(position));
        }

        @Override
        public int getItemCount() {
            return mQuestionList.size();
        }
    }
}