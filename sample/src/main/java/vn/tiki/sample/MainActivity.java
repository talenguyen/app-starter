package vn.tiki.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.OnClick;
import intents.Intents;
import vn.tiki.sample.base.BaseActivity;
import vn.tiki.sample.collectionview.CollectionViewActivity;
import vn.tiki.sample.login.LoginActivity;
import vn.tiki.sample.productlist.ProductListingActivity;

public class MainActivity extends BaseActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
  }

  @OnClick(R.id.btOpenExtraInjection)
  public void openExtraInjection(View view) {
    final Intent intent = Intents.extraInjectionActivity(this)
        .name("Giang")
        .age(29)
        .make();
    startActivity(intent);
  }

  @OnClick(R.id.btOpenLogin)
  public void openLogin(View view) {
    startActivity(LoginActivity.intent(this));
  }

  @OnClick(R.id.btOpenProductListing)
  public void openProductListing(View view) {
    startActivity(ProductListingActivity.intent(this));
  }

  @OnClick(R.id.btOpenCollectionView)
  public void openCollectionView(View view) {
    startActivity(CollectionViewActivity.intent(this));
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_cart, menu);
    return true;
  }
}
