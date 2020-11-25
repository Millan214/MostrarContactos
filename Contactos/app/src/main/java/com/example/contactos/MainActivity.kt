package com.example.contactos

import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            button.setOnClickListener{
                obtenerDatos()
            }
        }else{
            ActivityCompat.requestPermissions(this , arrayOf(android.Manifest.permission.READ_CONTACTS),1)
        }
    }

    fun obtenerDatos(){

        //Crear la proyección de datos que queremos sacar
        val PROJECTION: Array<String> = arrayOf(
                ContactsContract.Data.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.TYPE
        )

        //Filtro para que me salgan solo los contactos que tengan numero
        val SELECTION: String = "${ContactsContract.Data.MIMETYPE}='" +
                "${ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE}' AND " +
                "${ContactsContract.CommonDataKinds.Phone.NUMBER} IS NOT NULL"
        //Otro tipo de filtro --> "${ContactsContract.Data._ID = jamon}"

        //Ordenados por display name ascendentemente
        val SORT_ORDER = "${ContactsContract.Data.DISPLAY_NAME} ASC"

        //Crear el Cursor
        val cursor : Cursor? = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                PROJECTION,
                SELECTION,
                null,
                SORT_ORDER
        )

        var personas:ArrayList<Contacto>?=null
        personas = ArrayList()
        if (cursor != null) {
            while (cursor.moveToNext()){
                personas?.add(
                        Contacto(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2)
                        )
                )
            }
        }

        val lista = mutableListOf<Contacto>()
        lista.addAll(personas)

        val listview = findViewById<ListView>(R.id.listView)
        listview.adapter = MiAdaptador(this, R.layout.row, personas)

    }

    private class MiAdaptador(var mContext: Context, var resources: Int, var personas: ArrayList<Contacto>): ArrayAdapter<Contacto>(mContext,resources,personas){

        //Render cada fila
        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

            val layoutInflater:LayoutInflater = LayoutInflater.from(mContext)
            val view:View = layoutInflater.inflate(resources,null)

            val imageView: ImageView = view.findViewById(R.id.imagen)
            val nombre: TextView = view.findViewById(R.id.tvNombre)
            val numero: TextView = view.findViewById(R.id.tvNumero)

            var persona:Contacto = personas[position]
            cargarImagen(imageView)

            nombre.text = persona.nombre
            numero.text = persona.numero

            return view
        }

        fun cargarImagen(imageView:ImageView) {
            val rnds = (0..13).random()

            when(rnds){
                0 -> imageView.setImageResource(R.mipmap.ic_ardilla_round)
                1 -> imageView.setImageResource(R.mipmap.ic_esponja_round)
                2 -> imageView.setImageResource(R.mipmap.ic_castor_round)
                3 -> imageView.setImageResource(R.mipmap.ic_delfin_round)
                4 -> imageView.setImageResource(R.mipmap.ic_dori_round)
                5 -> imageView.setImageResource(R.mipmap.ic_loro_round)
                6 -> imageView.setImageResource(R.mipmap.ic_mapache_round)
                7 -> imageView.setImageResource(R.mipmap.ic_pollito2_round)
                8 -> imageView.setImageResource(R.mipmap.ic_pulpo_round)
                9 -> imageView.setImageResource(R.mipmap.ic_simba_round)
                10 -> imageView.setImageResource(R.mipmap.ic_pez_round)
                11 -> imageView.setImageResource(R.mipmap.ic_pollito_round)
                13 -> imageView.setImageResource(R.mipmap.ic_gatomeme_round)
                else -> imageView.setImageResource(R.mipmap.ic_ardilla_round)
            }

        }

    }

}