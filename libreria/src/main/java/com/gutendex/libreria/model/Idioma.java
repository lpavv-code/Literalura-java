package com.gutendex.libreria.model;

public enum Idioma {
  es("Español"),
  en("Inglés"),
  fr("Francés"),
  pt("Portugués"),
  zh("Chino"),
  nn("Otro idioma no registrado");

  private String idiomaGTX;

  Idioma(String idiomaGTX){
    this.idiomaGTX=idiomaGTX;
  }

  public static Idioma stringEnum(String text){
    for(Idioma idioma:Idioma.values()){
      if(idioma.name().equalsIgnoreCase(text)){
        return idioma;
      }
    }
    return nn;
  }

  public static void listarIdiomas(){
    for (Idioma idioma:Idioma.values()){
      System.out.println(idioma.name()+" - "+idioma.getIdioma());
    }
  }

  public String getIdioma() {
    return idiomaGTX;
  }
}
