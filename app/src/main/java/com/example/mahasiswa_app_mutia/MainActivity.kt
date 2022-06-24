package com.example.mahasiswa_app_mutia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.mahasiswa_app_mutia.Adapter.DataAdapter
import com.example.mahasiswa_app_mutia.Config.NetworkModule
import com.example.mahasiswa_app_mutia.Model.action.ResponseAction
import com.example.mahasiswa_app_mutia.Model.getdata.DataItem
import com.example.mahasiswa_app_mutia.Model.getdata.ResponseGetData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        showData()
    }

    private  fun  showData(){
        val lisMahasiswa = NetworkModule.service().getData()
        lisMahasiswa.enqueue(object : Callback<ResponseGetData>{

            override fun onResponse(
                call: Call<ResponseGetData>,
                response: Response<ResponseGetData>
            ) {
                if (response.isSuccessful){

                    val item = response.body()?.data
                    val adapter = DataAdapter(item as ArrayList<DataItem>?, object : DataAdapter.OnClickListener{
                        override fun detail(item: DataItem?) {

                            val intent = Intent(applicationContext, InputActivity::class.java)
                            intent.putExtra("data", item)
                            startActivity(intent)
                        }

                        override fun hapus(item: DataItem?) {
                            AlertDialog.Builder(this@MainActivity).apply {
                                setTitle("Hapus Data")
                                setMessage("Apakah Anda Yakin Menghapus Data ?")
                                setPositiveButton("Hapus"){ dialog, which ->
                                    hapusData(item?.idMahasiswa)
                                    dialog.dismiss()
                                }
                                setNegativeButton("Batal"){ dialog, which ->
                                    dialog.dismiss()
                                }
                            }.show()
                        }
                    })

                    list.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun hapusData(idMahasiswa: String?) {

        val  hapus = NetworkModule.service().deleteData(idMahasiswa ?: "")
        hapus.enqueue(object : Callback<ResponseAction>{

            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext, "Data Berhasil Dihapus", Toast.LENGTH_LONG).show()
                showData()
            }
            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message,Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onResume() {
        super.onResume()
        showData()
    }
}