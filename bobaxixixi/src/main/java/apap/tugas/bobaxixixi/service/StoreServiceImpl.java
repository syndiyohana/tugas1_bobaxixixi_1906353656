package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.repository.StoreDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
@Transactional

public class StoreServiceImpl implements StoreService{

    @Autowired
    StoreDB storeDB;

    @Autowired
    StoreDB bobaTeaDB;

    @Override
    public void addStore(StoreModel store){
        storeDB.save(store);
    }

    @Override
    public void updateStore(StoreModel store){
        storeDB.save(store);
    }

    @Override
    public List<StoreModel> getStoreList(){
        return storeDB.findAll(Sort.by(Sort.Direction.ASC, "namaStore"));
    }

    @Override
    public StoreModel getStoreByIdStore(Long idStore){
        Optional<StoreModel> store = storeDB.findByidStore(idStore);
        if (store.isPresent()){
            return store.get();
        }
        return null;
    }
//
    @Override
    public void deleteStore(StoreModel store){
        if (checkError(store) == null) {
            storeDB.delete(store);
        }
    }

    @Override
    public String checkError(StoreModel store) {
        if (LocalTime.now().isAfter(store.getJamBuka()) && LocalTime.now().isBefore(store.getJamTutup())) {
            return store.getNamaStore() + " is currently open and can't be deleted!";
        }
        if (store.getListBobaTea().size() > 0) {
            return store.getNamaStore() + " still has Boba Tea and can't be deleted!";
        }
        return null;
    }

    @Override
    public void assignBoba(Long idStore, BobaTeaModel boba){
        Optional<StoreModel> store = storeDB.findByidStore(idStore);
        StoreModel Store = store.get();
        Store.getListBobaTea().add(boba);

    }
}

