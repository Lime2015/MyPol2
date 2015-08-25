package com.lime.mypol.result;

import java.util.List;

/**
 * Created by seongsan on 2015-08-25.
 */
public class SearchGoogleMapResult {
    private List<String> results;
    private String status;

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
