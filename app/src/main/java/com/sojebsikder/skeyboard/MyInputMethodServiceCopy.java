//package com.sojebsikder.skeyboard;
//
//import android.inputmethodservice.InputMethodService;
//import android.inputmethodservice.Keyboard;
//import android.inputmethodservice.KeyboardView;
//import android.text.TextUtils;
//import android.view.View;
//import android.view.inputmethod.InputConnection;
//
//public class MyInputMethodServiceCopy extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
//
//    private KeyboardView kv;
//    private Keyboard keyboard;
//
//    private boolean caps = false;
//
//    @Override
//    public View onCreateInputView() {
//        // get the KeyboardView and add our Keyboard layout to it
//        KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard_view, null);
//        Keyboard keyboard = new Keyboard(this, R.xml.number_pad);
//        keyboardView.setKeyboard(keyboard);
//        keyboardView.setOnKeyboardActionListener(this);
//        return keyboardView;
//    }
//
//    @Override
//    public View onCreateInputView() {
//        // get the KeyboardView and add our Keyboard layout to it
//        kv = (KeyboardView)getLayoutInflater().inflate(R.layout.keyboard, null);
//        keyboard = new Keyboard(this, R.xml.qwerty);
//        kv.setKeyboard(keyboard);
//        kv.setOnKeyboardActionListener(this);
//        return kv;
//    }
//
//    @Override
//    public void onPress(int i) {
//
//    }
//
//    @Override
//    public void onRelease(int i) {
//
//    }
//
//    @Override
//    public void onKey(int i, int[] ints) {
//        InputConnection ic = getCurrentInputConnection();
//        if (ic == null) return;
//        switch (i) {
//            case Keyboard.KEYCODE_DELETE:
//                CharSequence selectedText = ic.getSelectedText(0);
//                if (TextUtils.isEmpty(selectedText)) {
//                    // no selection, so delete previous character
//                    ic.deleteSurroundingText(1, 0);
//                } else {
//                    // delete the selection
//                    ic.commitText("", 1);
//                }
//                break;
//            default:
//                char code = (char) i;
//                ic.commitText(String.valueOf(code), 1);
//        }
//    }
//
//    @Override
//    public void onText(CharSequence charSequence) {
//
//    }
//
//    @Override
//    public void swipeLeft() {
//
//    }
//
//    @Override
//    public void swipeRight() {
//
//    }
//
//    @Override
//    public void swipeDown() {
//
//    }
//
//    @Override
//    public void swipeUp() {
//
//    }
//}
