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
public class Root {
    public ArrayList<Result> results;
    public ArrayList<Object> image_results;
    public int total;
    public ArrayList<String> answers;
    public double ts;
    public String device_region;
    public String device_type;
}
