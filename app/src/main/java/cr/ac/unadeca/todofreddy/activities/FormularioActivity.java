package cr.ac.unadeca.todofreddy.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cr.ac.unadeca.todofreddy.R;
import cr.ac.unadeca.todofreddy.database.models.TodoTable;

public class FormularioActivity extends AppCompatActivity {

    private TextView lblNombre;
    private TextView lblActividad;
    private EditText txtNombre;
    private EditText txtActividad;
    private Button BtnGuardar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);//R.recursos de la aplicacion un enlace
        // setContentView que disenio se va utilizar en el desarollo de la aplicaion
        lblNombre=findViewById(R.id.lblNombre);
        lblActividad=findViewById(R.id.lblActividad);
        txtNombre=findViewById(R.id.txtNombre);
        txtActividad=findViewById(R.id.txtActividad);
        BtnGuardar=findViewById(R.id.BtnGuardar);

        BtnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardar();
            }
        });

    }

    private boolean validacion(){
        boolean send = true;
        if(txtNombre.getText().toString().isEmpty()){
            send= false;

        }
        if(txtActividad.getText().toString().isEmpty()){
            send= false;
        }
        return  send;

    }
    private void guardar(){
        if (validacion()){
            TodoTable registro = new TodoTable();
            registro.nombre = txtNombre.getText().toString();
            registro.actividad= txtActividad.getText().toString();
            registro.save();
            finish();

        }else{
            Toast.makeText(this, getResources().getString(R.string.error_valid), Toast.LENGTH_SHORT).show();//llamada estatica llamar un objeto sin hacer uno nuevo
        }
    }

}

