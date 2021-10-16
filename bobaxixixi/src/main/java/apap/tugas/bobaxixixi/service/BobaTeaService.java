package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;

import java.util.List;

public interface BobaTeaService {
    void addBobaTea(BobaTeaModel boba);
    List<BobaTeaModel> getListBobaTea();
    //Method untuk mengupdate store
    void updateBobaTea(BobaTeaModel boba);
    //method untuk mendapatkan data sebuah store berdasarkan id store
    BobaTeaModel getBobaTeaByIdBobaTea(Long idBoba);

    //Method untuk hapus Bioskop
    void deleteBobaTea(BobaTeaModel boba);

    List<BobaTeaModel> getBobaTeaByNameTopping(String name, String topping);

    List<BobaTeaModel> getBobaTeaByName(String name);

    void assignStore(Long idBobaTea, StoreModel store);
}
