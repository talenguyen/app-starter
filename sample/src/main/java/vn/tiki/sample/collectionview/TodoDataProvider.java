package vn.tiki.sample.collectionview;

import io.reactivex.Observable;
import java.util.ArrayList;
import java.util.List;
import vn.tiki.collectionview.DataProvider;
import vn.tiki.collectionview.ListData;
import vn.tiki.collectionview.Paging;

public class TodoDataProvider implements DataProvider<String> {

  private static final int PER_PAGE = 5;
  private static final int LAST_PAGE = 10;

  @Override public Observable<ListData<String>> fetchNewest() {
    return Observable.fromCallable(() -> generateItems(1));
  }

  @Override public Observable<ListData<String>> fetch(int page) {
    return Observable.fromCallable(() -> generateItems(page));
  }

  private ListData<String> generateItems(int page) throws Exception {
    Thread.sleep(1000);
    if (System.currentTimeMillis() % 2 == 0) {
      throw new Exception("Error");
    }
    final int startIndex = (page - 1) * PER_PAGE;
    final List<String> result = new ArrayList<>(PER_PAGE);
    for (int i = 0; i < PER_PAGE; i++) {
      final int index = i + startIndex;
      result.add("Item " + index);
    }
    final Paging paging = new PagingImpl(LAST_PAGE * PER_PAGE, page, LAST_PAGE);
    return new ListDataImpl<>(result, paging);
  }
}
