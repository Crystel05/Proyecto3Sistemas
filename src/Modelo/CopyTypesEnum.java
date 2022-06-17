package Modelo;

public enum CopyTypesEnum {

    VIRTUAL_REAL("Ruta virtual a ruta real"),
    REAL_VIRTUAL("Ruta real a ruta virtual"),
    VIRTUAL_VIRTUAL("Ruta virtual a ruta virtual");

    private String name;

    private CopyTypesEnum(String s) {
        this.name = s;
    }

    public String getTypeEnum(){
        return this.name;
    }
}
