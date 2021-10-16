package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.model.ManagerModel;
import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.service.StoreService;
import apap.tugas.bobaxixixi.service.ManagerService;
import apap.tugas.bobaxixixi.service.BobaTeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import static org.apache.commons.lang3.RandomStringUtils.*;
import java.util.ArrayList;

@Controller
public class StoreController {

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    private BobaTeaService bobaTeaService;

    @Qualifier("managerServiceImpl")
    @Autowired
    private ManagerService managerService;

    // routing URL yang diinginkan
    @GetMapping("/store/add")
    public String addStoreForm(Model model) {

        // Add variabel id store ke "idStore" untuk dirender ke dalam thymeleaf
        model.addAttribute("idStore", new StoreModel());

        List<ManagerModel> listManager = managerService.getListManager();
        model.addAttribute("listManager",listManager);

//        List<FilmModel> listFilm = filmService.getListFilm();
//        model.addAttribute("listFilm", listFilm);

        // return view template yang digunakan
        return "form-add-store";
    }

    @PostMapping("/store/add")
    public String addStoreSubmit(@ModelAttribute StoreModel store, Model model) {
        StringBuilder input1 = new StringBuilder();
        String nama = store.getNamaStore().substring(0, 3).toUpperCase();
        input1.append(nama);
        input1.reverse();
        String name = input1.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalTime waktuBuka = store.getJamBuka();
        LocalTime waktuTutup = store.getJamTutup();
        String jamBuka = waktuBuka.format(formatter);
        int hour = waktuTutup.get(ChronoField.CLOCK_HOUR_OF_DAY)/10;
        String jamTutup = String.valueOf(hour);
        String random = randomAlphabetic(2).toUpperCase();
        String kode = "SC"+name+jamBuka+jamTutup+random;
        store.setKodeStore(kode);
        storeService.addStore(store);
        StoreModel Store = storeService.getStoreByIdStore(store.getIdStore());
        model.addAttribute("idStore", store.getIdStore());
        model.addAttribute("Store", Store);

        return "add-store";
    }

    @GetMapping("/store")
    public String listStore(Model model) {
        // Mendapatkan semua store
        List<StoreModel> listStore = storeService.getStoreList();

        // Add variable semua BioskopModel ke 'listBioskop' untuk dirender dalam thymeleaf
        model.addAttribute("listStore", listStore);

        // Return view template yang diinginkan
        return "viewall-store";
    }

    @GetMapping("/store/{idStore}")
    public String viewDetailBioskop(@PathVariable Long idStore, Model model) {
        // Mendapatkan bioskop sesuai dengan idBioskop
        StoreModel store= storeService.getStoreByIdStore(idStore);
        model.addAttribute("idStore", idStore);

        model.addAttribute("store", store);
        model.addAttribute("manager", store.getManager());
        model.addAttribute("listBobaTea", store.getListBobaTea());

        return "view-store";
    }
//
    @GetMapping("/store/update/{idStore}")
    public String updateStoreForm(@PathVariable Long idStore, Model model) {
        StoreModel store = storeService.getStoreByIdStore(idStore);
        model.addAttribute("idStore", idStore);

        model.addAttribute("store", store);
        if (LocalTime.now().isAfter(store.getJamBuka()) && LocalTime.now().isBefore(store.getJamTutup())) {
            return "error-update";
        }

        List<ManagerModel> listManager = managerService.getListManager();
        model.addAttribute("listManager",listManager);

        return "form-update-store";
    }

    @PostMapping("/store/update/{idStore}")
    public String updateStoreSubmit(@ModelAttribute StoreModel store, Model model) {
        StringBuilder input1 = new StringBuilder();
        String nama = store.getNamaStore().substring(0, 3).toUpperCase();
        input1.append(nama);
        input1.reverse();
        String name = input1.toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        LocalTime waktuBuka = store.getJamBuka();
        LocalTime waktuTutup = store.getJamTutup();
        String jamBuka = waktuBuka.format(formatter);
        int hour = waktuTutup.get(ChronoField.CLOCK_HOUR_OF_DAY)/10;
        String jamTutup = String.valueOf(hour);
        String random = randomAlphabetic(2).toUpperCase();
        String kode = "SC"+name+jamBuka+jamTutup+random;
        store.setKodeStore(kode);
        storeService.updateStore(store);
        model.addAttribute("store", store);
        return "update-store";
    }
//
    @GetMapping("/store/delete/{idStore}")
    public String deleteBioskop(@PathVariable Long idStore, Model model) {
        StoreModel store = storeService.getStoreByIdStore(idStore);

        storeService.deleteStore(store);
        model.addAttribute("store", store);
        model.addAttribute("errorMessage", storeService.checkError(store));
        return "delete-store";
    }

    @GetMapping("/store/{idStore}/assign-boba")
    public String assignBoba(@PathVariable Long idStore, Model model) {
        StoreModel store = storeService.getStoreByIdStore(idStore);
        List<BobaTeaModel> listBobaTea = bobaTeaService.getListBobaTea();

        model.addAttribute("store", store);
        model.addAttribute("listBobaTea", listBobaTea);
        return "form-assign-boba";
    }

    @PostMapping("/store/{idStore}/assign-boba")
    public String assignBobaSubmit(@ModelAttribute StoreModel store, @PathVariable Long idStore, Model model) {
        List<BobaTeaModel> list = store.getListBobaTea();
        if(list!=null){
            for (BobaTeaModel boba : list){
                storeService.assignBoba(idStore,boba);
            }
        }

        StoreModel Store = storeService.getStoreByIdStore(idStore);
        model.addAttribute("store", Store);
        model.addAttribute("listBobaTea", list);

        return "assign-boba";
    }

}
