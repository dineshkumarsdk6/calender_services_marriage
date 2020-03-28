package nithra.calender.marriage.services.searchdialog;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.searchdialog.adapters.SearchDialogAdapter;
import nithra.calender.marriage.services.searchdialog.core.BaseFilter;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.FilterResultListener;
import nithra.calender.marriage.services.searchdialog.core.OnPerformFilterListener;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;
import nithra.calender.marriage.services.searchdialog.core.Searchable;


public class SimpleSearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {
    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;

    private TextView mTxtTitle;
    private EditText mSearchBox;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    // In case you are doing process in another thread
    // and wanted to update the progress in that thread
    private Handler mHandler;

    public SimpleSearchDialogCompat(Context context, String title, String searchHint,
                                    @Nullable Filter filter, ArrayList<T> items,
                                    SearchResultListener<T> searchResultListener) {
        super(context, items, filter, null, null);
        init(title, searchHint, searchResultListener);
    }

    private void init(String title, String searchHint,
                      SearchResultListener<T> searchResultListener) {
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {
                ((SearchDialogAdapter) getAdapter())
                        .setSearchTag(mSearchBox.getText().toString())
                        .setItems(items);
            }
        });
        mHandler = new Handler();
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        mTxtTitle = view.findViewById(R.id.txt_title);
        mSearchBox = view.findViewById(getSearchBoxId());
        mRecyclerView = view.findViewById(getRecyclerViewId());
        mProgressBar = view.findViewById(R.id.progress);
        mTxtTitle.setText(mTitle);
        mSearchBox.setHint(mSearchHint);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);
        view.findViewById(R.id.dummy_background)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                    }
                });
        final SearchDialogAdapter adapter = new SearchDialogAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, getItems());
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(getFilterResultListener());
        setAdapter(adapter);
        mSearchBox.requestFocus();
        ((BaseFilter<T>) getFilter()).setOnPerformFilterListener(new OnPerformFilterListener() {
            @Override
            public void doBeforeFiltering() {
                setLoading(true);
            }

            @Override
            public void doAfterFiltering() {
                setLoading(false);
            }
        });
    }

    public SimpleSearchDialogCompat setTitle(String title) {
        mTitle = title;
        if (mTxtTitle != null)
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mTxtTitle.setText(mTitle);
                }
            });
        return this;
    }

    public SimpleSearchDialogCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        if (mSearchBox != null)
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mSearchBox.setHint(mSearchHint);
                }
            });
        return this;
    }

    public void setLoading(final boolean isLoading) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mProgressBar != null)
                    mRecyclerView.setVisibility(!isLoading ? View.VISIBLE : View.GONE);
                if (mRecyclerView != null)
                    mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    public SimpleSearchDialogCompat setSearchResultListener(
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

    public EditText getSearchBox() {
        return mSearchBox;
    }

    public String getTitle() {
        return mTitle;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }
}
