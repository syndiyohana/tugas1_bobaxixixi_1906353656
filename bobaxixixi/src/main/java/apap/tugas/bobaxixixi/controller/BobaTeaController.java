package apap.tugas.bobaxixixi.controller;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import apap.tugas.bobaxixixi.model.StoreModel;
import apap.tugas.bobaxixixi.model.ToppingModel;
import apap.tugas.bobaxixixi.service.BobaTeaService;
import apap.tugas.bobaxixixi.service.ToppingService;
import apap.tugas.bobaxixixi.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BobaTeaController {

    @Qualifier("bobaTeaServiceImpl")
    @Autowired
    BobaTeaService bobaTeaService;

    @Qualifier("toppingServiceImpl")
    @Autowired
    private ToppingService toppingService;

    @Qualifier("storeServiceImpl")
    @Autowired
    private StoreService storeService;

    @GetMapping("/boba/add")
    public String addBobaTeaForm(Model model){
        model.addAttribute("boba", new BobaTeaModel());

        List<ToppingModel> listTopping = toppingService.getListTopping();
        model.addAttribute("listTopping",listTopping);
        return "form-add-boba";
    }

    @PostMapping("/boba/add")
    public String addBobaTeaSubmit(
            @ModelAttribute BobaTeaModel boba,
            Model model
    ){
        bobaTeaService.addBobaTea(boba);
        model.addAttribute("boba", boba);
        return "add-boba";
    }

    @GetMapping("/boba")
    public String viewAllBobaTea(
            Model model
    ){
        model.addAttribute("listBobaTea", bobaTeaService.getListBobaTea());
        return "viewall-bobatea";
    }

    @GetMapping("/boba/update/{idBobaTea}")
    public String updateSBobaTeaForm(@PathVariable Long idBobaTea, Model model) {
        BobaTeaModel boba = bobaTeaService.getBobaTeaByIdBobaTea(idBobaTea);
        model.addAttribute("boba", boba);
        List<StoreModel> listStore = boba.getListStore();
        model.addAttribute("boba",boba);
        if (listStore!=null){
            for(StoreModel store : listStore){
                if (LocalTime.now().isAfter(store.getJamBuka()) && LocalTime.now().isBefore(store.getJamTutup())) {
                    return "error-update-boba";
                }
            }
        }
        List<ToppingModel> listTopping = toppingService.getListTopping();
        model.addAttribute("listTopping",listTopping);


        return "form-update-boba";
    }

    @PostMapping("/boba/update/{idBobaTea}")
    public String updateBobaTeaSubmit(@ModelAttribute BobaTeaModel boba, Model model) {
        bobaTeaService.updateBobaTea(boba);
        model.addAttribute("boba", boba);
        return "update-boba";
    }

    @GetMapping("/boba/delete/{idBobaTea}")
    public String deleteBioskop(@PathVariable Long idBobaTea, Model model) {
        BobaTeaModel boba = bobaTeaService.getBobaTeaByIdBobaTea(idBobaTea);
        model.addAttribute("boba", boba);
        if(boba.getListStore()!=null){
            model.addAttribute("boba", boba);
            return "error-delete-boba";
        }
        bobaTeaService.deleteBobaTea(boba);
        return "delete-boba";
    }

    @GetMapping("/search")
    public String searchVarian(
            @RequestParam(value = "namaBobaTea", required = false, defaultValue = "a") String namaBobaTea,
            @RequestParam(value = "namaTopping", required = false, defaultValue = "b") String namaTopping,
            Model model
    ) {
//        BobaTeaModel boba = bobaTeaService.getBobaTeaByIdBobaTea();
        List<ToppingModel> listTopping = toppingService.getListTopping();
        List<BobaTeaModel> listBobaTea = bobaTeaService.getListBobaTea();
        List<BobaTeaModel> bobaList = bobaTeaService.getBobaTeaByNameTopping(namaBobaTea,namaTopping);

        model.addAttribute("bobaList",bobaList );
        model.addAttribute("listTopping", listTopping);
        model.addAttribute("listBobaTea", listBobaTea);

        return "search-bobatea";
    }

    @GetMapping("/filter/manager")
    public String filterManager(
            @RequestParam(value = "namaBobaTea", required = false, defaultValue = "a") String namaBobaTea,
            Model model
    ) {
        List<BobaTeaModel> listBobaTea = bobaTeaService.getListBobaTea();
        List<BobaTeaModel> bobaList = bobaTeaService.getBobaTeaByName(namaBobaTea);
        List<StoreModel> storeList = new ArrayList<>();
        if (bobaList != null){
            for(BobaTeaModel boba : bobaList) {
                BobaTeaModel bobatea = boba;
                for (StoreModel store : bobatea.getListStore()) {
                    if (!storeList.contains(store)) {
                        storeList.add(store);
                    }
                }
            }
        }


        model.addAttribute("storeList",storeList);
        model.addAttribute("listBobaTea", listBobaTea);

        return "filter-manager";
    }

    @GetMapping("/boba/{idBobaTea}/assign-store")
    public String assignStore(@PathVariable Long idBobaTea, Model model) {
        BobaTeaModel boba = bobaTeaService.getBobaTeaByIdBobaTea(idBobaTea);
        List<StoreModel> listStore = storeService.getStoreList();

        model.addAttribute("boba", boba);
        model.addAttribute("listStore", listStore);
        return "form-assign-store";
    }

    @PostMapping("/boba/{idBobaTea}/assign-store")
    public String assignStoreSubmit(@ModelAttribute BobaTeaModel boba, @PathVariable Long idBobaTea, Model model) {
        List<StoreModel> list = boba.getListStore();
        if(list!=null){
            for (StoreModel store : list){
                bobaTeaService.assignStore(idBobaTea,store);
            }
        }

        BobaTeaModel Boba = bobaTeaService.getBobaTeaByIdBobaTea(idBobaTea);
        model.addAttribute("boba", Boba);
        model.addAttribute("listStore", list);

        return "assign-store";
    }
}

