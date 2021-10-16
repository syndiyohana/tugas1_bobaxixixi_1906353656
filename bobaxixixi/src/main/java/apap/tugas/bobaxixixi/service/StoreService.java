package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import java.util.List;

public interface StoreService {
    //Method untuk menambah Store
    void addStore(StoreModel store);

    //Method untuk mengupdate store
    void updateStore(StoreModel store);

    //Method untuk mendapatkan daftar store yang telah tersimpan
    List<StoreModel> getStoreList();

    //method untuk mendapatkan data sebuah store berdasarkan id store
    StoreModel getStoreByIdStore(Long idStore);

    //Method untuk hapus Bioskop
    void deleteStore(StoreModel store);


    //Method untuk cek eror
    String checkError(StoreModel store);

    void assignBoba(Long idStore, BobaTeaModel boba);

}

