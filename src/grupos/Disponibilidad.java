package grupos;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Disponibilidad
* @author ontology bean generator
* @version 2016/04/15, 00:38:37
*/
public class Disponibilidad implements Concept {

   /**
* Protege name: fecha
   */
   private String fecha;
   public void setFecha(String value) { 
    this.fecha=value;
   }
   public String getFecha() {
     return this.fecha;
   }

   /**
* Protege name: hora
   */
   private String hora;
   public void setHora(String value) { 
    this.hora=value;
   }
   public String getHora() {
     return this.hora;
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
