package aw.app.kupemi;

import org.json.JSONObject;

/**
 * Created by User on 3/5/14.
 * Candidate wrapper backed by JSONObject
 */
public class Candidate {
	JSONObject data;

	public Candidate(JSONObject candidate) {
		this.data = candidate;

	}
}
