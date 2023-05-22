package com.example.bb;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Header {

    private ImageView headerImageLeft;
    private TextView headerTitle;
    private ImageView headerImageRight;
    private HeaderClickListener listener;

    public interface HeaderClickListener {
        void onImageLeftClick();
        void onImageRightClick();
    }

    public Header(View view,HeaderClickListener listener) {
        this.listener = listener;

        headerImageLeft = view.findViewById(R.id.header_image_left);
        headerTitle = view.findViewById(R.id.header_title);
        headerImageRight = view.findViewById(R.id.header_image_right);

        headerImageLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageLeftClick();
            }
        });
                headerImageRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onImageRightClick();
            }
        });

    }

    public void setTitle(String title) {
        headerTitle.setText(title);
    }

    public void setImageLeft(int resId) {
        headerImageLeft.setImageResource(resId);
    }

    public void setImageRight(int resId) {
        headerImageRight.setImageResource(resId);
    }
}

