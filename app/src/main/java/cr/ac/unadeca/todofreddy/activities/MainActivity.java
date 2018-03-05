package cr.ac.unadeca.todofreddy.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;

import java.util.List;

import cr.ac.unadeca.todofreddy.R;
import cr.ac.unadeca.todofreddy.database.models.TodoTable;
import cr.ac.unadeca.todofreddy.database.models.TodoTable_Table;
import cr.ac.unadeca.todofreddy.subclases.TodoViewHolder;

public class MainActivity extends AppCompatActivity {//imagenes y cualquier tipo de vista

    private RecyclerView lista;
    private static Context QuickContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuickContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent actividad= new Intent(getApplicationContext(),FormularioActivity.class);//ejeccucion de otra actividad dentro de la actividad
                actividad.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(actividad);


            }
        });

        lista= findViewById(R.id.lista);
        lista.setLayoutManager(new LinearLayoutManager(this));

        List<TodoTable> info= SQLite.select().from(TodoTable.class).queryList();
        lista.setAdapter(new ToDoAdapter(info));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            List<TodoTable> info= SQLite.select().from(TodoTable.class).queryList();
            lista.setAdapter(new ToDoAdapter(info));
        }

        return super.onOptionsItemSelected(item);
    }
    public static class ToDoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
        private final List<TodoTable> listTodoTable;
        private final LayoutInflater inflater;

        public ToDoAdapter(List<TodoTable> listTodoTables) {
            this.inflater = LayoutInflater.from(QuickContext);
            this.listTodoTable = listTodoTables;
        }

        @Override
        public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {//cuando se crea la vista establecemos el disenio a usar, enlace con un disenio
            View view = inflater.inflate(R.layout.objeto, parent, false);
            return new TodoViewHolder(view);
        }

        public void animateTo(List<TodoTable> models) {//animar la informacion
            applyAndAnimateRemovals(models);
            applyAndAnimateAdditions(models);
            applyAndAnimateMovedItems(models);
        }

        private void applyAndAnimateRemovals(List<TodoTable> newModels) {
            for (int i = listTodoTable.size() - 1; i >= 0; i--) {
                final TodoTable model = listTodoTable.get(i);
                if (!newModels.contains(model)) {
                    removeItem(i);
                }
            }
        }

        private void applyAndAnimateAdditions(List<TodoTable> newModels) {
            for (int i = 0, count = newModels.size(); i < count; i++) {
                final TodoTable model = newModels.get(i);
                if (!listTodoTable.contains(model)) {
                    addItem(i, model);
                }
            }
        }

        private void applyAndAnimateMovedItems(List<TodoTable> newModels) {
            for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
                final TodoTable model = newModels.get(toPosition);
                final int fromPosition = listTodoTable.indexOf(model);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }

        public TodoTable removeItem(int position) {
            final TodoTable model = listTodoTable.remove(position);
            notifyItemRemoved(position);
            return model;
        }

        public void addItem(int position, TodoTable model) {
            listTodoTable.add(position, model);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final TodoTable model = listTodoTable.remove(fromPosition);
            listTodoTable.add(toPosition, model);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onBindViewHolder(final TodoViewHolder holder, final int position) {//bind=enlazar
            final TodoTable current = listTodoTable.get(position);
            holder.html.setHtml(ActividadaString(current),
                    new HtmlResImageGetter(holder.html));
            holder.html.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(current.estado < 2){
                        current.estado=2;

                    }else {
                        current. estado=1;
                    }
                    notifyDataSetChanged();
                }
            });
            holder.borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.delete();
                    removeItem(position);
                    notifyDataSetChanged();
                }
            });
            }



        private String ActividadaString(TodoTable todo){

            String color = "#7a2d44";
            if(todo.estado == 2) {
                color = "#042c6d";
            }

            String html = "<a><big><b> <font color=\""+ color +"\">" + todo.nombre + "</b></big>";
            html+= "<br>" + todo.actividad +"</a>";
            return html;
            }


        @Override
        public int getItemCount() {
            return listTodoTable.size();
        }
        }








    }


