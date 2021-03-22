package repository.utilities;

import model.Utility;

import java.util.List;

public interface UtilityRepository {

    boolean save(Utility utility);

    List<Utility> findAll();

    void removeAll();
}
