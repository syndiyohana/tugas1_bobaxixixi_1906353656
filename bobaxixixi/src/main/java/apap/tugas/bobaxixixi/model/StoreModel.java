package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "store")

public class StoreModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStore;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String namaStore;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String alamatStore;

    @NotNull
    @Size(max = 10)
    @Column(nullable = false)
    private String kodeStore;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jamBuka;

    @NotNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime jamTutup;

//    //Relasi dengan Manager
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name= "manager")
    private ManagerModel manager;

    //Relasi dengan BobaTea
    @ManyToMany
    @JoinTable(
            name = "store_bobatea",
            joinColumns = @JoinColumn(name = "id_store"),
            inverseJoinColumns = @JoinColumn(name = "id_boba")
    )
    List<BobaTeaModel> listBobaTea;
}
