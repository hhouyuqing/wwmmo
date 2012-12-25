package au.com.codeka.warworlds.ctrl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import au.com.codeka.warworlds.game.ChatActivity;
import au.com.codeka.warworlds.model.ChatManager;
import au.com.codeka.warworlds.model.ChatMessage;

/**
 * This control displays the mini chat window, which displays recent chat
 * messages on each screen.
 */
public class MiniChatView extends RelativeLayout {
    private Context mContext;

    private ScrollView mScrollView;
    private LinearLayout mMsgsContainer;
    private MessageAddedListener mMessageAddedListener;

    public MiniChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        if (this.isInEditMode()) {
            return;
        }

        int id = 1;
        this.setBackgroundColor(Color.argb(0xaa, 0, 0, 0));

        Resources r = getResources();
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, r.getDisplayMetrics());
        LayoutParams lp = new LayoutParams(size, size);

        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mScrollView = new ScrollView(mContext);
        mScrollView.setLayoutParams(lp);
        mScrollView.setId(id++);
        addView(mScrollView);

        lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mMsgsContainer = new LinearLayout(mContext);
        mMsgsContainer.setOrientation(LinearLayout.VERTICAL);
        mMsgsContainer.setLayoutParams(lp);
        mMsgsContainer.setId(id++);
        mScrollView.addView(mMsgsContainer);

        mMessageAddedListener = new MessageAddedListener();
        ChatManager.getInstance().addMessageAddedListener(mMessageAddedListener);

        refreshMessages();

        mMsgsContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ChatActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void refreshMessages() {
        mMsgsContainer.removeAllViews();

        ChatMessage[] msgs = ChatManager.getInstance().getLastMessages(10);
        for(int i = msgs.length - 1; i >= 0; i--) {
            ChatMessage msg = msgs[i];
            if (msg == null) {
                continue;
            }

            appendMessage(msg);
        }
    }

    private void appendMessage(ChatMessage msg) {
        TextView tv = new TextView(mContext);
        tv.setText(msg.getMessage());
        mMsgsContainer.addView(tv);

        // need to wait for it to settle before we scroll again
        mScrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 1);
    }

    class MessageAddedListener implements ChatManager.MessageAddedListener {
        @Override
        public void onMessageAdded(final ChatMessage msg) {
            // needs to posted on the UI thread, which this is probably not...
            MiniChatView.this.post(new Runnable() {
                @Override
                public void run() {
                    appendMessage(msg);
                }
            });
        }
    }
}
