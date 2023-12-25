package com.sojebsikder.skeyboard;

import android.app.Dialog;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.os.Build;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import net.hasnath.ridmikparser.RidmikParser;

public class MyInputMethodService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private InputMethodManager mInputMethodManager;
    private KeyboardView kv;
    private Keyboard keyboard;
    private Keyboard symbolKeyboard;
    private Keyboard emojiKeyboard;
    private Keyboard banglaKeyboard;

    private boolean caps = false;

    @Override
    public View onCreateInputView() {
        // get the KeyboardView and add our Keyboard layout to it
        mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);

        keyboard = new Keyboard(this, R.xml.qwerty);
        symbolKeyboard = new Keyboard(this, R.xml.symbols);
        emojiKeyboard = new Keyboard(this, R.xml.emoji);
        banglaKeyboard = new Keyboard(this, R.xml.qwerty_bangla_keyboard);

        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        return kv;
    }

    @Override
    public void onPress(int i) {

    }

    @Override
    public void onRelease(int i) {

    }

    private IBinder getToken() {
        final Dialog dialog = getWindow();
        if (dialog == null) {
            return null;
        }
        final Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        return window.getAttributes().token;
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);

        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                break;
            case -2:
                // change to symbol keyboard
                if (kv.getKeyboard() == symbolKeyboard) {
                    kv.setKeyboard(keyboard);
                } else {
                    kv.setKeyboard(symbolKeyboard);
                }
                kv.setShifted(false);
                kv.invalidateAllKeys();
                break;
            case -3:
                // change to emoji keyboard
                kv.setKeyboard(emojiKeyboard);

                kv.invalidateAllKeys();
                break;
            case 45:
                // change keyboard to bangla
                if (kv.getKeyboard() == banglaKeyboard) {
                    kv.setKeyboard(keyboard);
                } else {
                    kv.setKeyboard(banglaKeyboard);
                }
                kv.setShifted(false);
                kv.invalidateAllKeys();
                break;

            default:
                char code = (char) primaryCode;
                if (Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                // get back to shift key off
                caps = false;
                keyboard.setShifted(false);
                kv.invalidateAllKeys();
                // commit text

                //RidmikParser parser = new RidmikParser();
                //String bangla = parser.toBangla(String.valueOf(code));

                ic.commitText(String.valueOf(code), 1);
        }
    }

    private void playClick(int keyCode) {
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case Keyboard.KEYCODE_DONE:
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public View onCreateCandidatesView() {
        return super.onCreateCandidatesView();
    }

    @Override
    public void onFinishInputView(boolean finishingInput) {
        kv.setKeyboard(keyboard);
        kv.setShifted(false);
        kv.invalidateAllKeys();
    }

    @Override
    public void onText(CharSequence charSequence) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
