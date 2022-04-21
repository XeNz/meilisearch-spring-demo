package be.xentricator.meilisearchdemo.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
