package com.gaston.git.cqrsjava7.responsibility.task;

import com.gaston.git.cqrsjava7.core.handling.ResponsibilityTask;

/**
 * Este es un comando
 * @author gaston
 */
public class TestTaskCommand extends ResponsibilityTask {

    public String key;
    public String value;

    public TestTaskCommand(String key, String value) {
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
