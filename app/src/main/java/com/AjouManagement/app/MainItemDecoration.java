package com.AjouManagement.app;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainItemDecoration extends RecyclerView.ItemDecoration {

    private final int mSpace;

    public MainItemDecoration(int space){
        this.mSpace = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
            outRect.top = mSpace;
    }
}
