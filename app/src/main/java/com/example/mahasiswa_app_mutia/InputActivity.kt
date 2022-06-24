package com.example.mahasiswa_app_mutia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mahasiswa_app_mutia.Config.NetworkModule
import com.example.mahasiswa_app_mutia.Model.action.ResponseAction
import com.example.mahasiswa_app_mutia.Model.getdata.DataItem
import com.example.mahasiswa_app_mutia.R
import kotlinx.android.synthetic.main.activity_input.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)

        val getData = intent.getParcelableExtra<DataItem>("data")

        if (getData != null){
            etNama.setText(getData.mahasiswaNama)
            etNohp.setText(getData.mahasiswaNohp)
            etAlamat.setText(getData.mahasiswaAlamat)

            btn1.text = "Update"
        }

        when (btn1.text){
            "Update" -> {
                btn1.setOnClickListener {
                    updateData(getData?.idMahasiswa, etNama.text.toString(), etNohp.text.toString(), etAlamat.text.toString())
                }

            }
            else ->{
                btn1.setOnClickListener {
                    inputData(etNama.text.toString(), etNohp.text.toString(), etAlamat.text.toString())
                }
            }
        }

//        btn1.setOnClickListener {
//            inputData(etNama.text.toString(), etNohp.text.toString(), etAlamat.text.toString())
//        }

        btn2.setOnClickListener {
            finish()
        }
    }

    private fun inputData(mahasiswa_nama : String?, mahasiswa_nohp : String?, Mahasiswa_alamat : String?){

        val input = NetworkModule.service().insertData(mahasiswa_nama ?: "",mahasiswa_nohp ?: "",Mahasiswa_alamat ?: "")
        input.enqueue(object : Callback<ResponseAction>{

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Berhasil Di Simpan", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun updateData(id_mahasiswa : String?, mahasiswa_nama : String?, mahasiswa_nohp : String?, Mahasiswa_alamat : String?){

        val input = NetworkModule.service().updateData(id_mahasiswa ?: "", mahasiswa_nama ?: "",mahasiswa_nohp ?: "",Mahasiswa_alamat ?: "")
        input.enqueue(object : Callback<ResponseAction>{

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Berhasil Di Update", Toast.LENGTH_LONG).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}