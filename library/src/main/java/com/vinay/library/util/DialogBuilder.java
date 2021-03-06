package com.vinay.library.util;

import android.content.DialogInterface;
import android.support.annotation.DrawableRes;

public class DialogBuilder {

    private String title;
    private String message;

    private String positiveButtonText = "OK";
    private String negativeButtonText;
    private String neutralButtonText;

    private DialogInterface.OnClickListener onPositiveCLick;
    private DialogInterface.OnClickListener onNegativeClick;
    private DialogInterface.OnClickListener onNeutralClick;

    private boolean cancellable;
    private int icon = 0;
    private String[] strArray;
    private DialogInterface.OnClickListener onItemSelectListener;

    /**
     * @param title              : Dialog title
     * @param message            : Dialog message
     * @param positiveButtonText : Dialog's positive button text
     */
    public DialogBuilder(String title, String message, String positiveButtonText) {
        this.title = title;
        this.message = message;
        this.positiveButtonText = positiveButtonText;
    }

    /**
     * Retuns neutral button text.
     *
     * @return String : Neutral button text
     */
    public String getNeutralButtonText() {
        return neutralButtonText;
    }

    /**
     * Sets Neutral button test
     *
     * @param neutralButtonText String
     */
    public void setNeutralButtonText(String neutralButtonText) {
        this.neutralButtonText = neutralButtonText;
    }

    /**
     * Returns onClick listener for Neutral Button
     *
     * @return DialogInterface.OnClickListener
     */
    public DialogInterface.OnClickListener getOnNeutralClick() {
        return onNeutralClick;
    }

    /**
     * Sets onClick listener for Neutral Button
     *
     * @param onNeutralClick DialogInterface.OnClickListener
     */
    public void setOnNeutralClick(DialogInterface.OnClickListener onNeutralClick) {
        this.onNeutralClick = onNeutralClick;
    }


    /**
     * Returns title of alert dialog
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title of alert dialog
     *
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns message of alert dialog
     *
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message of alert dialog
     *
     * @param message String
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Returns Positive button text of alert dialog
     *
     * @return String
     */
    public String getPositiveButtonText() {
        return positiveButtonText;
    }

    /**
     * Sets positive button text of alert dialog
     *
     * @param positiveButtonText String
     */
    public void setPositiveButtonText(String positiveButtonText) {
        this.positiveButtonText = positiveButtonText;
    }

    /**
     * Returns Positive button onClick listener of alert dialog
     *
     * @return DialogInterface.OnClickListener
     */
    public DialogInterface.OnClickListener getOnPositiveCLick() {
        return onPositiveCLick;
    }

    /**
     * Sets positive button onClick listener of alert dialog
     *
     * @param onPositiveCLick DialogInterface.OnClickListener
     */
    public void setOnPositiveCLick(DialogInterface.OnClickListener onPositiveCLick) {
        this.onPositiveCLick = onPositiveCLick;
    }

    /**
     * Returns Negative button text of alert dialog
     *
     * @return String
     */
    public String getNegativeButtonText() {
        return negativeButtonText;
    }

    /**
     * Sets negative button text of alert dialog
     *
     * @param negativeButtonText String
     */
    public void setNegativeButtonText(String negativeButtonText) {
        this.negativeButtonText = negativeButtonText;
    }

    /**
     * Returns Negative button onClick listener of alert dialog
     *
     * @return DialogInterface.OnClickListener
     */
    public DialogInterface.OnClickListener getOnNegativeClick() {
        return onNegativeClick;
    }

    /**
     * Sets negative button onClick listener of alert dialog
     *
     * @param onNegativeClick DialogInterface.OnClickListener
     */
    public void setOnNegativeClick(DialogInterface.OnClickListener onNegativeClick) {
        this.onNegativeClick = onNegativeClick;
    }

    /**
     * Check dialog cancelable state
     *
     * @return boolean
     */
    public boolean isCancellable() {
        return cancellable;
    }

    /**
     * Sets dialog cancellable state
     *
     * @param cancellable boolean
     */
    public void setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
    }

    /**
     * Returns icon id (from drawable resource) of alert dialog
     *
     * @return int
     */
    public int getIcon() {
        return icon;
    }

    /**
     * Sets icon of the alert dialog
     *
     * @param icon int
     */
    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }

    public void setItems(String[] strArray, DialogInterface.OnClickListener onClickListener) {

        this.strArray = strArray;
        this.onItemSelectListener = onClickListener;
    }

    public String[] getStrArray() {
        return strArray;
    }

    public void setStrArray(String[] strArray) {
        this.strArray = strArray;
    }

    public DialogInterface.OnClickListener getOnItemSelectListener() {
        return onItemSelectListener;
    }

    public void setOnItemSelectListener(DialogInterface.OnClickListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }
}
