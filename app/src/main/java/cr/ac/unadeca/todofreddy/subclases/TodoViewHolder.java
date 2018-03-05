package cr.ac.unadeca.todofreddy.subclases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import cr.ac.unadeca.todofreddy.R;

/**
 * Created by Freddy on 2/19/2018.
 */

public class TodoViewHolder extends RecyclerView.ViewHolder {

    public HtmlTextView html;
    public ImageView borrar;




    public TodoViewHolder(View itemView) {
        super(itemView);

        html=itemView.findViewById(R.id.html_text);
        borrar=itemView.findViewById(R.id.delete);

    }
}
