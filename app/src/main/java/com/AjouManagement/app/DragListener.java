package com.AjouManagement.app;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DragListener implements View.OnDragListener {

    interface Listener {
        void setEmptyListTop(boolean visibility);

        void setEmptyListBottom(boolean visibility);
    }

    private boolean isDropped = false;
    private Listener listener;

    DragListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                View enteredView = v;
                enteredView.animate()
                        .scaleX(1.1f)
                        .scaleY(1.1f)
                        .setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                enteredView.animate()
                                        .scaleX(1.0f)
                                        .scaleY(1.0f)
                                        .setDuration(200)
                                        .start();
                            }
                        })
                        .start();
                break;
            case DragEvent.ACTION_DROP:
                isDropped = true;
                int positionTarget = -1;

                View viewSource = (View) event.getLocalState();
                int viewId = v.getId();
                final int flItem = R.id.frame_layout_item;
//                final int tvEmptyListTop = R.id.tvEmptyListTop;
//                final int tvEmptyListBottom = R.id.tvEmptyListBottom;
                final int rvTop = R.id.rv;
                final int rvBottom = R.id.rv_addRoutine;

                switch (viewId) {
                    case flItem:
//                    case tvEmptyListTop:
//                    case tvEmptyListBottom:
                    case rvTop:
                    case rvBottom:

                        RecyclerView target;
                        switch (viewId) {
//                            case tvEmptyListTop:
                            case rvTop:
                                target = (RecyclerView) v.getRootView().findViewById(rvTop);
                                break;
//                            case tvEmptyListBottom:
                            case rvBottom:
                                target = (RecyclerView) v.getRootView().findViewById(rvBottom);
                                break;
                            default:
                                target = (RecyclerView) v.getParent();
                                positionTarget = (int) v.getTag();
                        }

                        if (viewSource != null) {
                            RecyclerView source = (RecyclerView) viewSource.getParent();

                            MainAdapter adapterSource = (MainAdapter) source.getAdapter();
                            int positionSource = (int) viewSource.getTag();
                            int sourceId = source.getId();

                            MainData list = adapterSource.getList().get(positionSource);
                            ArrayList<MainData> listSource = adapterSource.getList();

                            listSource.remove(positionSource);
                            adapterSource.updateList(listSource);
                            adapterSource.notifyDataSetChanged();

                            MainAdapter adapterTarget = (MainAdapter) target.getAdapter();
                            ArrayList<MainData> customListTarget = adapterTarget.getList();
                            if (positionTarget >= 0) {
                                customListTarget.add(positionTarget, list);
                                Log.d("onDrag", String.valueOf(positionTarget));
                            }
                            else {
                                customListTarget.add(list);
                            }
                            adapterTarget.updateList(customListTarget);
                            adapterTarget.notifyDataSetChanged();

                            if (sourceId == rvBottom && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListBottom(true);
                            }
//                            if (viewId == tvEmptyListBottom) {
//                                listener.setEmptyListBottom(false);
//                            }
                            if (sourceId == rvTop && adapterSource.getItemCount() < 1) {
                                listener.setEmptyListTop(true);
                            }
//                            if (viewId == tvEmptyListTop) {
//                                listener.setEmptyListTop(false);
//                            }
                        }
                        break;
                }
                break;
        }

        if (!isDropped && event.getLocalState() != null) {
            ((View) event.getLocalState()).setVisibility(View.VISIBLE);

        }
        return true;
    }

    private void applyItemMoveAnimation(RecyclerView recyclerView, int fromPosition, int toPosition) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof MainAdapter) {
            MainAdapter mainAdapter = (MainAdapter) adapter;
            mainAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    }
}
