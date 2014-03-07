package aw.app.kupemi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import aw.app.kupemi.adapters.AnswersAdapter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends Activity {
	private static final int NUM_OF_ANSWER = 4;
	TextView mQuestion;
	TextView mScore;
	TextView mMultiplier;
	LinearLayout mQWrap;
	ImageView mQThumb;
	ListView mOptions;
	Button mSkip;
	ArrayList<JSONObject> candidates;
	int score = 0;
	int correctStreak = 0;
	int multiplier = 1;
	private ArrayList<JSONObject> candidatesWithPhoto;
	private AnswersAdapter mAnswersAdapter;
	private int correctAnswer;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		candidates = new ArrayList<JSONObject>();
		candidatesWithPhoto = new ArrayList<JSONObject>();
		mAnswersAdapter = new AnswersAdapter(this, new ArrayList<String>());

		mQuestion = (TextView) findViewById(R.id.question);
		mScore = (TextView) findViewById(R.id.score);
		mMultiplier = (TextView) findViewById(R.id.multiplier);
		mQWrap = (LinearLayout) findViewById(R.id.question_wrapper);
		mQThumb = (ImageView) findViewById(R.id.question_thumb);
		mOptions = (ListView) findViewById(R.id.options);
		mSkip = (Button) findViewById(R.id.skipper);
		mOptions.setAdapter(mAnswersAdapter);
		mOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				if(i == correctAnswer) {
					score += multiplier * 1;
					correctStreak++;
				} else {
					score -= 1;
					correctStreak = 0;
					multiplier = 1;
				}

				if(correctStreak == 3) {
					multiplier++;
				}

				showNextQuestion();
			}
		});

		mSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				showNextQuestion();
			}
		});

		//Get some simple data to generate our questions
		Api.getCandidates(100, 0, "provinsi=31",  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				super.onSuccess(response);

				try {
					JSONArray candidatesJSON = response.getJSONObject("data").getJSONObject("results").getJSONArray("caleg");
					for(int i = 0; i < candidatesJSON.length(); i++) {
						JSONObject candidate = candidatesJSON.getJSONObject(i);
						candidates.add(candidatesJSON.getJSONObject(i));

						if(candidate.has("foto_url") && !TextUtils.isEmpty(candidate.getString("foto_url"))) {
							candidatesWithPhoto.add(candidate);
						}
					}
					Rujak.d(candidatesWithPhoto.size());

					mSkip.setVisibility(View.VISIBLE);
					mQWrap.setVisibility(View.VISIBLE);
					showNextQuestion();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
//		Api.getProvinces();
//		Api.getProvinceById(12);
	}

	public void showNextQuestion() {
		Random r = new Random();
		JSONObject candidate = candidatesWithPhoto.get(r.nextInt(candidatesWithPhoto.size()));

		mScore.setText(String.valueOf(score));
		mMultiplier.setText("x " + String.valueOf(multiplier));

		try {
			String url = candidate.getString("foto_url");
			int side = (int) Math.ceil(getResources().getDisplayMetrics().density) * 180;
			url = "http://pemilu-backend.appspot.com/remote/image_proxy?url=" + Uri.encode(url) + "&side=" + side;
			Picasso.with(this).load(url).into(mQThumb);

			Rujak.d(url);
			mQuestion.setText("Kandidat dengan foto di sebelah kiri bernama:");

			correctAnswer = r.nextInt(4);

			ArrayList<String> answers = new ArrayList<String>();
			for(int i = 0; i < NUM_OF_ANSWER; i++) {
				if(i == correctAnswer) {
					answers.add(candidate.getString("nama"));
				} else {
					answers.add(candidates.get(r.nextInt(candidates.size())).getString("nama"));
				}
			}
			mAnswersAdapter.setAnswers(answers);
		} catch(JSONException e) {
			showNextQuestion();
		}


	}
}
