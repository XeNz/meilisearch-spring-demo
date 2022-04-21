package be.xentricator.mielisearchdemo.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto implements Serializable {
    private  Long id;
    private  String title;
    private  ContactDto contact;
}
