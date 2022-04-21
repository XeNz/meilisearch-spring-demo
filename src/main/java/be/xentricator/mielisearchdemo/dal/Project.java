package be.xentricator.mielisearchdemo.dal;

import be.xentricator.mielisearchdemo.dal.interfaces.SearchEngineIndexableEntity;
import be.xentricator.mielisearchdemo.listeners.JpaEventAwareSearchEngineIndexableEntityListener;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@EntityListeners(JpaEventAwareSearchEngineIndexableEntityListener.class)
public class Project extends SearchEngineIndexableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", nullable = false)
    private Contact contact;
}
