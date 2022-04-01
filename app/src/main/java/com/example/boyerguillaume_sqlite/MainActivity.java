package com.example.boyerguillaume_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.StrictMode;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.AlertDialog.Builder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    ResultSet rst=null;
    public static Connection conn = null;
    private Button valide = null;
    private EditText login = null;
    private EditText mdp = null;
    private EditText confMDp = null;
    private EditText commentaire;
    private CheckBox lundi = null;
    private CheckBox mardi = null;
    private CheckBox mercredi = null;
    private CheckBox jeudi = null;
    private CheckBox vendredi = null;
    private RadioGroup niveau = null;
    private RadioButton debutant = null;
    private RadioButton intermediaire = null;
    private RadioButton haut = null;
    private Spinner cbb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.setThreadPolicy(new
                StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog() // Enregistre un message à logcat
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath() //l'application se bloque, fonctionne à //la fin de toutes les sanctions permises
                .build());

        //appel de la connexion
        MysqlConnexion();

//Déclarations

        this.valide = (Button) findViewById(R.id.valide);
        this.login = (EditText) findViewById(R.id.pseudo_saisie);
        this.mdp = (EditText) findViewById(R.id.mdp_saisie);
        this.confMDp = (EditText) findViewById(R.id.confirm_mdp_saisie);
        this.commentaire = (EditText)findViewById(R.id.Commentaire_cadre);
        this.cbb = (Spinner)findViewById(R.id.Combobox_Asso);

        this.niveau = (RadioGroup) findViewById(R.id.radioGroup);
        this.debutant = (RadioButton) findViewById(R.id.debutant);
        this.intermediaire = (RadioButton) findViewById(R.id.intermediaire);
        this.haut = (RadioButton) findViewById(R.id.Haut);


        this.lundi = (CheckBox) findViewById(R.id.lundi);
        this.mardi = (CheckBox) findViewById(R.id.mardi);
        this.mercredi = (CheckBox) findViewById(R.id.mercredi);
        this.jeudi = (CheckBox) findViewById(R.id.jeudi);
        this.vendredi = (CheckBox) findViewById(R.id.vendredi);
        this.valide.setOnClickListener(ValideListener);

        String query = "SELECT Association FROM association";

        try
        {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> arrayList = new ArrayList<String>();
            while(rs.next())
            {
                String asso = rs.getString("Association");
                arrayList.add(asso);
            }
            String[] tab = arrayList.toArray(new String[0]);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            cbb.setAdapter(arrayAdapter);
        }
        catch (Exception e)
        {
            Log.d("Erreur adapter", e.getMessage());
        }
        cbb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String asso_nom = cbb.getSelectedItem().toString();
                Toast.makeText(MainActivity.this, asso_nom, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private View.OnClickListener ValideListener = new View.OnClickListener() {
        public void onClick(View v) {
            int resultatSelect = 2;
            String t0 = login.getText().toString();
            String t1 = mdp.getText().toString();
            String t2 = confMDp.getText().toString();
            String disponibilité = "";
            String niv_sport ="";
            if (lundi.isChecked())
                disponibilité += disponibilité+"lundi ";
            if (mardi.isChecked())
                disponibilité += disponibilité+"mardi ";
            if (mercredi.isChecked())
                disponibilité += disponibilité+"mercredi ";
            if (jeudi.isChecked())
                disponibilité += disponibilité+"jeudi ";
            if (vendredi.isChecked())
                disponibilité += disponibilité+"vendredi";

            if (debutant.isChecked())
                niv_sport = debutant.getText().toString();
            if (intermediaire.isChecked())
                niv_sport = intermediaire.getText().toString();
            if (haut.isChecked())
                niv_sport = haut.getText().toString();
            try //NE FONCTIONNE PAS OU PARTIELLEMENT (INSERT DEFAILLANT)
            {
                Toast.makeText(MainActivity.this, "Le try marche", Toast.LENGTH_LONG).show();
                String sql = "INSERT INTO utilisateur (Pseudo, Mdp, Disponibilité, Niveau, Commentaire, FK_Asso) " +
                        "VALUES ("+ login.getText().toString()+","+mdp.getText().toString()+","+disponibilité+","+
                        niv_sport+","+commentaire.getText().toString()+",1);";
                Statement pstm = conn.createStatement();
                ResultSet resultSet1 = pstm.executeQuery(sql);

               /* String sql2 = "SELECT id_Asso FROM association WHERE Association = ?";
                PreparedStatement pstm2 = conn.prepareStatement(sql2);
                pstm2.setString(1,"Musique");
                ResultSet resultSet2 = pstm2.executeQuery();
                while(resultSet2.next())
                {
                    resultatSelect = resultSet2.getInt(1);
                }*/
                 if (t1.equals(t2))
                    {
                            Toast.makeText(MainActivity.this, "Ca marche bien", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Mots de passe différents", Toast.LENGTH_SHORT).show();
                    }
            }
            catch (Exception e )
            {
                Log.i("erreur check pseudo", e.getMessage());
            }
        }
    };

    private void MysqlConnexion(){
        String jdbcURL = "jdbc:mysql://10.4.253.85:3306/bddinscription";
        String user = "BOYER_G";
        String passwd = " ";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcURL,user,passwd);
            Toast.makeText(MainActivity.this, "Connection : OK", Toast.LENGTH_LONG).show();

        } catch ( ClassNotFoundException e) {
            Toast.makeText(MainActivity.this, "Driver manquant." + e.getMessage().toString(), Toast.LENGTH_LONG).show();

        } catch ( java.sql.SQLException ex ) {
            Toast.makeText(MainActivity.this, "Connexion au serveur impossible." + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
            Log.d("error", "SQLException: " + ex.getMessage());
            Log.d("error","SQLState: " + ex.getSQLState());
            Log.d("error","VendorError: " + ex.getErrorCode());
        }
    } // fin de MysqlConnection


}//Fin de classe