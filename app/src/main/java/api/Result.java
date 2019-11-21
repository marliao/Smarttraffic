package api;

import java.util.List;

public interface Result<T> {
    List<T> getList();

    void refresh(List<T> list);

    void refresh();
}
