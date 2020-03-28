package nithra.calender.marriage.services.searchdialog;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.searchdialog.adapters.ContactModelAdapter;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.FilterResultListener;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;
import nithra.calender.marriage.services.searchdialog.core.Searchable;

public class ContactSearchDialogCompat1<T extends Searchable> extends BaseSearchDialogCompat<T> {
    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;
    OnAddItem onAddItem;

    public static LinearLayout add_lay;

    public ContactSearchDialogCompat1(Context context, String title, String searchHint,
                                      @Nullable Filter filter, ArrayList<T> items,
                                      SearchResultListener<T> searchResultListener) {
        super(context, items, filter, null, null);
        init(title, searchHint, searchResultListener);
        onAddItem = (OnAddItem)context;
    }

    private void init(String title, String searchHint,
                      SearchResultListener<T> searchResultListener) {
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
    }

    public interface OnAddItem{
        void itemAdd();
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        TextView txtTitle = view.findViewById(R.id.txt_title);
        final EditText searchBox = view.findViewById(getSearchBoxId());
        add_lay = view.findViewById(getLayoutViewId());
        txtTitle.setText(mTitle);
        searchBox.setHint(mSearchHint);
        /*view.findViewById(R.id.dummy_background)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });*/
        final ContactModelAdapter adapter = new ContactModelAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, getItems());
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {

                ((ContactModelAdapter) getAdapter())
                        .setSearchTag(searchBox.getText().toString())
                        .setItems(items);
                if (items.size()==0){
                    add_lay.setVisibility(View.VISIBLE);
                }else {
                    add_lay.setVisibility(View.GONE);
                }
            }
        });

        txtTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        add_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddItem.itemAdd();
                dismiss();
            }
        });
        setAdapter(adapter);
    }

    public ContactSearchDialogCompat1 setTitle(String title) {
        mTitle = title;
        return this;
    }

    public ContactSearchDialogCompat1 setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        return this;
    }

    public ContactSearchDialogCompat1 setSearchResultListener(
            SearchResultListener<T> searchResultListener) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return R.layout.search_dialog_compat;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_items;
    }

    @IdRes
    protected int getLayoutViewId() {
        return R.id.add_lay;
    }


}
