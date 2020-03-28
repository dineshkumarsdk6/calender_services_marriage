package nithra.calender.marriage.services.searchdialog.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.searchdialog.StringsHelper;
import nithra.calender.marriage.services.searchdialog.core.BaseSearchDialogCompat;
import nithra.calender.marriage.services.searchdialog.core.SearchResultListener;
import nithra.calender.marriage.services.searchdialog.core.Searchable;


public class ContactModelAdapter<T extends Searchable>
        extends RecyclerView.Adapter<ContactModelAdapter.ViewHolder> {
    protected Context mContext;
    private List<T> mItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int mLayout;
    private SearchResultListener mSearchResultListener;
    private AdapterViewBinder<T> mViewBinder;
    private String mSearchTag;
    private boolean mHighlightPartsInCommon = true;
    private String mHighlightColor = "#FFED2E47";
    private BaseSearchDialogCompat mSearchDialog;

    public ContactModelAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context,layout,null, items);
    }

    public ContactModelAdapter(Context context, AdapterViewBinder<T> viewBinder,
                               @LayoutRes int layout, List<T> items) {
        this(context,layout,viewBinder, items);
    }

    public ContactModelAdapter(Context context, @LayoutRes int layout,
                               @Nullable AdapterViewBinder<T> viewBinder,
                               List<T> items) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ContactModelAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
        this.mViewBinder = viewBinder;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mLayoutInflater.inflate(mLayout, parent, false);
        convertView.setTag(new ViewHolder(convertView));
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ContactModelAdapter.ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }
    private void initializeViews(final T object, final ContactModelAdapter.ViewHolder holder,
                                 final int position) {
        if(mViewBinder != null)
            mViewBinder.bind(holder, object, position);
//        LinearLayout root = holder.getViewById(R.id.root);
        TextView text = holder.getViewById(android.R.id.text1);
        text.setTextColor(getColor(R.color.searchDialogResultColor));

        if(mSearchTag != null && mHighlightPartsInCommon)
            text.setText(StringsHelper.highlightLCS(object.getTitle(), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
        else text.setText(object.getTitle());

        if (mSearchResultListener != null)
            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchResultListener.onSelected(mSearchDialog, object, position);
                }
            });
    }

    public SearchResultListener getSearchResultListener(){
        return mSearchResultListener;
    }
    public void setSearchResultListener(SearchResultListener searchResultListener){
        this.mSearchResultListener = searchResultListener;
    }

    public ContactModelAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public ContactModelAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public ContactModelAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public ContactModelAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mBaseView;

        public ViewHolder(View view) {
            super(view);
            mBaseView = view;
        }

        public View getBaseView() {
            return mBaseView;
        }
        public <T> T getViewById(@IdRes int id){
            return (T)mBaseView.findViewById(id);
        }
        public void clearAnimation(@IdRes int id)
        {
            mBaseView.findViewById(id).clearAnimation();
        }
    }
    
    public interface AdapterViewBinder<T> {
        void bind(ViewHolder holder, T item, int position);
    }

    private int getColor(@ColorRes int colorResId){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return mContext.getResources().getColor(colorResId, null);
        } else return mContext.getResources().getColor(colorResId);
    }
}