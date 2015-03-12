package com.gaston.git.cqrsjava7.responsibility.task;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 *
 * @author gaston
 */
public class TestTaskQuery extends ResponsibilityTask {

    public String key;
    public String value;

    public TestTaskQuery(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "key-value [" + key + " " + value + "]";
    }
}
