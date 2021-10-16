package apap.tugas.bobaxixixi.service;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.repository.BobaTeaDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BobaTeaServiceImpl implements BobaTeaService{

    @Autowired
    BobaTeaDB bobaTeaDB;

    @Override
    public void addBobaTea(BobaTeaModel boba) {
        bobaTeaDB.save(boba);
    }

    @Override public List<BobaTeaModel> getListBobaTea() {
        return bobaTeaDB.findAll();
    }

    @Override
    public void updateBobaTea(BobaTeaModel boba){
        bobaTeaDB.save(boba);
    }

    @Override
    public BobaTeaModel getBobaTeaByIdBobaTea(Long idBobaTea){
        Optional<BobaTeaModel> boba = bobaTeaDB.findByidBobaTea(idBobaTea);
        if (boba.isPresent()){
            return boba.get();
        }
        return null;
    }

    @Override
    public void deleteBobaTea(BobaTeaModel boba){
        bobaTeaDB.delete(boba);
    }

    @Override
    public List<BobaTeaModel> getBobaTeaByNameTopping(String name, String topping){
        List<BobaTeaModel> listBoba = new ArrayList<>();
        for (BobaTeaModel boba : bobaTeaDB.findAll()){
//             && topping.equals(boba.getTopping().getNamaTopping())
            if(name.equals(boba.getNamaBobaTea())){
                listBoba.add(boba);
                return listBoba;
            }
        }
        return null;
    }

    @Override
    public List<BobaTeaModel> getBobaTeaByName(String name){
        List<BobaTeaModel> listBoba = new ArrayList<>();
        for (BobaTeaModel boba : bobaTeaDB.findAll()){
            if(name.equals(boba.getNamaBobaTea())){
                listBoba.add(boba);
                return listBoba;
            }
        }
        return null;
    }

    @Override
    public void assignStore(Long idBobaTea, StoreModel store){
        Optional<BobaTeaModel> boba = bobaTeaDB.findByidBobaTea(idBobaTea);
        BobaTeaModel Boba = boba.get();
        Boba.getListStore().add(store);

    }
}
