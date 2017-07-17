package mobile.beweb.fondespierre.apprenantstest.Adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.MainActivity;
import mobile.beweb.fondespierre.apprenantstest.R;

import static android.support.constraint.R.id.parent;

public class ListApprenantAdapter extends RecyclerView.Adapter<ListApprenantAdapter.ListApprenantAdapterViewHolder> {
//We create the different variables that we need to work
    private JSONArray apprenantsData;
    private String nom, prenom, skill;
    private Context context;
    private final ListApprenantAdapterOnClickHandler mClickHandler;

    /**
     * The constructor of the Adapter
     * @param clickHandler
     * @return void
     */
    public ListApprenantAdapter(ListApprenantAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    /**
     * This is an interface that we implement on MainActivty to use the Click Event
     * @return void
     */
    public interface ListApprenantAdapterOnClickHandler {
        void onClick(JSONObject apprenantDetail);
    }

    public class ListApprenantAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mApprenantNomTextView;
        public final TextView mApprenantPrenomTextView;
        public final TextView mApprenantSkillTextView;

        /**
         * @return void
         * @param view
         */
        public ListApprenantAdapterViewHolder(View view) {
            super(view);
            mApprenantNomTextView = (TextView) view.findViewById(R.id.laItem_textview_nom);
            mApprenantPrenomTextView = (TextView) view.findViewById(R.id.laItem_textview_prenom);
            mApprenantSkillTextView = (TextView) view.findViewById(R.id.laItem_textview_skill);
            view.setOnClickListener(this);
        }

        /**
         * @return void
         * @param v
         */
        @Override
        public void onClick(View v) {
            Log.d("click", "toto");
            int adapterPosition = getAdapterPosition();
            try {
                JSONObject apprenantDetails = apprenantsData.getJSONObject(adapterPosition);
                mClickHandler.onClick(apprenantDetails);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param viewGroup
     * @param viewType
     * @return void
     */
    @Override
    public ListApprenantAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.listeapprenantitem;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new ListApprenantAdapterViewHolder(view);
    }

    /**
     * This methid is used to refresh the RecyclerView
     * @param listApprenantAdapterViewHolder
     * @param position
     * @return void
     */
    @Override
    public void onBindViewHolder(ListApprenantAdapterViewHolder listApprenantAdapterViewHolder, int position) {
        try {
//We fetch a row of the JSONarray in apprenant
            JSONObject apprenant;
            apprenant = apprenantsData.getJSONObject(position);
//We create 3 variable with nom, prenom and skill in the row apprenant
            nom = apprenant.getString("nom");
            prenom = apprenant.getString("prenom");
            skill = apprenant.getString("skill");

        } catch (final JSONException e) {

        }
//We modify the text in the different Textview
        listApprenantAdapterViewHolder.mApprenantNomTextView.setText(nom);
        listApprenantAdapterViewHolder.mApprenantPrenomTextView.setText(prenom);
        listApprenantAdapterViewHolder.mApprenantSkillTextView.setText(skill);
    }

    /**
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        if (null == apprenantsData) return 0;
        return apprenantsData.length();
    }

    /**
     * @return void
     * @param apprenantsData
     */
    public void setWeatherData(JSONArray apprenantsData) {
        this.apprenantsData = apprenantsData;
        notifyDataSetChanged();
    }

}