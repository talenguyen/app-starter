package vn.tale.sayit.ui.home

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.toolbar
import vn.tale.sayit.R
import vn.tale.sayit.base.BaseActivity
import vn.tiki.daggers.ActivityInjector
import vn.tiki.daggers.Daggers
import javax.inject.Inject

class HomeActivity : BaseActivity(), ActivityInjector {

  @Inject internal lateinit var ttsController: TtsController
  @Inject internal lateinit var inputController: InputController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    Daggers.inject(this)

    setContentView(R.layout.activity_home)

    setSupportActionBar(toolbar)

    toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    toolbar.setNavigationOnClickListener { onBackPressed() }

    inputController.onSayItClicks = { text -> ttsController.speech(text) }

    lifecycle.addObserver(ttsController)
    lifecycle.addObserver(inputController)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    ttsController.onActivityResult(requestCode, resultCode)
  }

}
