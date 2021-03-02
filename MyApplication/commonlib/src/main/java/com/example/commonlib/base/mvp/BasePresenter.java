package com.example.commonlib.base.mvp;

import java.lang.ref.WeakReference;

/**
 * mvp basepresent
 * @param <M>
 * @param <V>
 */
public abstract class BasePresenter<M extends IModel, V extends IView> implements IPresenter {
    protected final String TAG = this.getClass().getSimpleName();
    private M mModel;
    private V mView;
    WeakReference<IView> iViewWeakReference;

    public BasePresenter(){
        mModel = createModel();
        onStart();
    }

    public BasePresenter(M model, V mView) {
        this.mModel = model;
        this.mView = mView;
        iViewWeakReference= new WeakReference<IView>(mView);
        onStart();
    }

    public BasePresenter(V rootView) {
        mModel = createModel();
        this.mView = rootView;
        iViewWeakReference= new WeakReference<IView>(mView);
        onStart();
    }

    public M getModel() {
        return mModel;
    }

    public V getView() {
        return mView;
    }

    public abstract void onStart();

    public abstract M createModel();

    /**
     * 添加view接口
     * @param view
     */
    public void attachView(V view){
        this.mView = view;
    }

    /**
     * 移除view接口
     */
    public void detachView() {
        if(iViewWeakReference!=null) {
            iViewWeakReference.clear();
        }
    }

}
