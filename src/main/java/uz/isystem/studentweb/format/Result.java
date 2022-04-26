package uz.isystem.studentweb.format;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    public String title;
    public String link;
    public String description;
    public ArrayList<AdditionalLink> additional_links;
    public Cite cite;
}
