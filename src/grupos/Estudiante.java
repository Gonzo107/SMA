package grupos;


import jade.content.*;
import jade.util.leap.*;
import jade.core.*;

/**
* Protege name: Estudiante
* @author ontology bean generator
* @version 2016/04/15, 00:38:37
*/
public class Estudiante implements Concept {

   /**
* Protege name: correo
   */
   private String correo;
   public void setCorreo(String value) { 
    this.correo=value;
   }
   public String getCorreo() {
     return this.correo;
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
* Protege name: edad
   */
   private int edad;
   public void setEdad(int value) { 
    this.edad=value;
   }
   public int getEdad() {
     return this.edad;
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

   /**
* Protege name: telefono
   */
   private String telefono;
   public void setTelefono(String value) { 
    this.telefono=value;
   }
   public String getTelefono() {
     return this.telefono;
   }

}
