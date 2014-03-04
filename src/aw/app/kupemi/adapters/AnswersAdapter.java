package aw.app.kupemi.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 3/5/14.
 */
public class AnswersAdapter extends BaseAdapter {
	private ArrayList<String> answers;
	private Context mContext;

	public AnswersAdapter(Context mContext, ArrayList<String> answers) {
		this.answers = answers;
		this.mContext = mContext;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return answers.size();
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup viewGroup) {
		if(view == null) {
			view = new TextView(mContext);
		}

		((TextView) view).setText(answers.get(pos));

		return view;
	}
}
