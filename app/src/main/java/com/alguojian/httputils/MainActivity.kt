package com.alguojian.httputils

import android.os.Bundle
import com.shuwtech.commonsdk.CommonApiService
import com.shuwtech.commonsdk.rx.RxTransfer
import com.shuwtech.commonsdk.ui.CommonBaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : CommonBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        aaa.setOnClickListener {
            aaaa()
        }
    }

    private fun aaaa(){

        compose(RxTransfer.withState(CommonApiService.INSTANCE.myCollectionActivityList(mapOf("UserId" to "104","IsState" to "0","LastDataCreateTime" to "")),true,this)
            .subscribe{
                println("----------$it")
            })
    }
}
