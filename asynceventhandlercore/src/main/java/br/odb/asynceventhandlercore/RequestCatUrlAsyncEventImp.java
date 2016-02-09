package br.odb.asynceventhandlercore;

/**
 * Created by monty on 09/02/16.
 */
public class RequestCatUrlAsyncEventImp extends AbstractAsyncEvent {
    public RequestCatUrlAsyncEventImp(EventResultCallback callback) {
        super( callback );
    }

    @Override
    public void perform() {
        //http://random.cat/meow
    }
}
