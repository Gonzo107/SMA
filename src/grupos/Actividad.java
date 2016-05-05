package grupos;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Actividad
* @author ontology bean generator
* @version 2016/04/15, 00:38:37
*/
public class Actividad implements Concept {

   /**
* Protege name: prioridad
   */
   private int prioridad;
   public void setPrioridad(int value) { 
    this.prioridad=value;
   }
   public int getPrioridad() {
     return this.prioridad;
   }

   /**
* Protege name: maxEstudiantes
   */
   private int maxEstudiantes;
   public void setMaxEstudiantes(int value) { 
    this.maxEstudiantes=value;
   }
   public int getMaxEstudiantes() {
     return this.maxEstudiantes;
   }

   /**
* Protege name: lugar
   */
   private String lugar;
   public void setLugar(String value) { 
    this.lugar=value;
   }
   public String getLugar() {
     return this.lugar;
   }

   /**
* Protege name: nombre
   */
   private String nombre;
   public void setNombre(String value) { 
    this.nombre=value;
   }
   public String getNombre() {
     return this.nombre;
   }

   /**
* Protege name: objetivo
   */
   private String objetivo;
   public void setObjetivo(String value) { 
    this.objetivo=value;
   }
   public String getObjetivo() {
     return this.objetivo;
   }

   /**
* Protege name: limiteDias
   */
   private int limiteDias;
   public void setLimiteDias(int value) { 
    this.limiteDias=value;
   }
   public int getLimiteDias() {
     return this.limiteDias;
   }

   /**
* Protege name: id
   */
   private String id;
   public void setId(String value) { 
    this.id=value;
   }
   public String getId() {
     return this.id;
   }

}
