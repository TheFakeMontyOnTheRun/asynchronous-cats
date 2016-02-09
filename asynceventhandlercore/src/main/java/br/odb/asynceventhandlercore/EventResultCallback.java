package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public interface EventResultCallback {
    void onFailure();
    void onSuccess();
}
