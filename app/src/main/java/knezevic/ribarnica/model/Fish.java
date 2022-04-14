package knezevic.ribarnica.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "fish")
public class Fish {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name="species")
    @NonNull
    private String name;
    private int speciesType;
    private String weigth;
    private String putanjaSlika;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getSpeciesType() {
        return speciesType;
    }

    public void setSpeciesType(int speciesType) {
        this.speciesType = speciesType;
    }

    public String getPutanjaSlika() {
        return putanjaSlika;
    }

    public void setPutanjaSlika(String putanjaSlika) {
        this.putanjaSlika = putanjaSlika;
    }

    public String getWeigth() {
        return weigth;
    }

    public void setWeigth(String weigth) {
        this.weigth = weigth;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
