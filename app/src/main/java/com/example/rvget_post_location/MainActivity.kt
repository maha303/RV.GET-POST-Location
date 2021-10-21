package com.example.rvget_post_location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var edName:EditText
    private lateinit var edLocations:EditText
    private lateinit var btnSave:Button
    private lateinit var edGL:EditText
    private lateinit var btnGet:Button
    private lateinit var tv:TextView

 //   var pk:Int =0
 //  var name=""
   // var location=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edName=findViewById(R.id.edName)
        edLocations=findViewById(R.id.edLocation)

        btnSave=findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            var f=UserItem(edName.text.toString(),edLocations.text.toString(),0)
            addUsers(f,onResult={
                edName.setText("")
                edLocations.setText("")
                Toast.makeText(this@MainActivity,"Saved &&", Toast.LENGTH_LONG).show()
            })
        }
        edGL=findViewById(R.id.edGL)
        tv=findViewById(R.id.tv)
        btnGet=findViewById(R.id.btnGet)

        btnGet.setOnClickListener {
           var name=edGL.text.toString()

            val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
            apiInterface?.getUser()?.enqueue(object :Callback<List<UserItem>>{
                var flag=false
                override fun onResponse(call: Call<List<UserItem>>, response: Response<List<UserItem>>) {

                val res=response.body()!!
                 //   val my=StringBuilder()
                   for (i in res){
                       if(i.name!!.lowercase()==name.toString().lowercase()){
                           tv.text=i.location
                           flag=true
                       }
                   }
                    if(!flag){
                        tv.text=""
                        Toast.makeText(this@MainActivity,"Cant Find ",Toast.LENGTH_LONG).show()
                    }
               }
                override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                }
            })
        }
    }
    private fun addUsers(f:UserItem,onResult:()->Unit){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        apiInterface?.addUser(f)?.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                 onResult()            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {
                 onResult()            }
        })
    }
}