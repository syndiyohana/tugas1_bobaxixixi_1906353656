package apap.tugas.bobaxixixi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
@Table(name = "boba_tea")

public class BobaTeaModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBobaTea;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false)
    private String namaBobaTea;

    @NotNull
    @Column(nullable = false)
    private Integer hargaBobaTea;

    @NotNull
    @Column(nullable = false)
    private Integer sizeBobaTea;

    @NotNull
    @Column(nullable = false)
    private Integer iceLevel;

    @NotNull
    @Column(nullable = false)
    private Integer sugarLevel;

    //Relasi dengan StoreModel
    @ManyToMany(mappedBy = "listBobaTea")
    List<StoreModel> listStore;

    //Relasi dengan ToppingModel
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_topping", referencedColumnName = "idTopping", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ToppingModel topping;
}

