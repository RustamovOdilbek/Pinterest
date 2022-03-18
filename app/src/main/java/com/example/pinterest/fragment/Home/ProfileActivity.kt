package com.example.pinterest.fragment.Home

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.example.pinterest.holder.EndlessRecyclerViewScrollListener
import com.example.pinterest.R
import com.example.pinterest.adapter.ImageAdapter
import com.example.pinterest.newtworking.ApiClient
import com.example.pinterst.model.ImageModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var iv_close: ImageView
    private lateinit var iv_image: ImageView
    private lateinit var iv_user: ImageView
    private lateinit var tv_userName: TextView
    private lateinit var tv_like: TextView
    private lateinit var tv_bio: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var iv_share: ImageView

    lateinit var list: ArrayList<ImageModel>
    lateinit var adapter: ImageAdapter
    private var per_page = 10
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var ll_save: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        iv_close = findViewById(R.id.iv_close)
        iv_image = findViewById(R.id.iv_image)
        iv_user = findViewById(R.id.iv_user)
        tv_userName = findViewById(R.id.tv_userName)
        tv_like = findViewById(R.id.tv_like)
        tv_bio = findViewById(R.id.tv_bio)
        recyclerView = findViewById(R.id.recyclerView)
        list = ArrayList()
        recyclerView = findViewById(R.id.recyclerView)
        adapter = ImageAdapter(this, list)
        ll_save = findViewById(R.id.ll_save)
        iv_share = findViewById(R.id.iv_share)

        apiImage()

        adapter.imageClick = {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("image", it)
            startActivity(intent)
        }

        ll_save.setOnClickListener {
            saveGallery(iv_image)
        }

        iv_share.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bitmapDrawable = iv_image.getDrawable() as BitmapDrawable
                val bitmap = bitmapDrawable.bitmap
                shareImageandText(bitmap)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun initViews(imageModel: ImageModel){
        Glide.with(iv_image.context).load(imageModel.urls.small).placeholder(ColorDrawable(Color.parseColor(imageModel.color))).into(iv_image)
        Glide.with(this).load(imageModel.user.profile_image.medium).into(iv_user)
        tv_userName.text = imageModel.user.first_name + " " + imageModel.user.last_name

        if (imageModel.user.total_likes !=  0) {
            tv_like.text = "Like " + imageModel.user.total_likes.toString()
        }else{
            tv_like.text = ""
        }

        tv_bio.text = imageModel.user.bio

        adapter = ImageAdapter(this, list)
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager
        recyclerView.adapter = adapter


        apiImageList(1)

        val scrollListener = object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager){
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                apiImageList(page+1)
            }
        }
        recyclerView.addOnScrollListener(scrollListener)

        adapter.imageClick = {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("image", it)
            startActivity(intent)
        }
    }

    fun apiImage(){
        val image = intent.getStringExtra("image")

        ApiClient.getApiInterface().getImage(image.toString())
            .enqueue(object : Callback<ImageModel>{
                override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                    initViews(response.body()!!)
                }
                override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                }

            })
    }

    private fun apiImageList(page: Int) {
        ApiClient.getApiInterface().getImages(page, per_page)
            .enqueue(object : Callback<List<ImageModel>>{
                override fun onResponse(
                    call: Call<List<ImageModel>>,
                    response: Response<List<ImageModel>>
                ) {
                    list.addAll(response.body() as ArrayList<ImageModel>)
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                }

            })
    }

    private fun saveGallery(iv_image: ImageView) {
        val bitmap = getScreenShotFromView(iv_image)

        if (bitmap != null){
            saveMediaToStorage(bitmap)
        }
    }

    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null

        try {
            screenshot = Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"

        var fos: OutputStream? = null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            this.contentResolver?.also { resolver ->

                val contentValues = ContentValues().apply {

                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this , "Captured View and saved to Gallery" , Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareImageandText(bitmap: Bitmap) {
        val uri: Uri? = getmageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    private fun getmageToShare(bitmap: Bitmap): Uri? {
        val imagefolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imagefolder.mkdirs()
            val file = File(imagefolder, "shared_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()
            uri = FileProvider.getUriForFile(this, "com.anni.shareimage.fileprovider", file)
        } catch (e: java.lang.Exception) {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_LONG).show()
        }
        return uri
    }
}