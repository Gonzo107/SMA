package agentes;

import java.io.StringReader;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.io.IOException;
import java.io.PrintWriter;
import grupos.*;
 
import jade.util.leap.List;
import jade.util.leap.ArrayList;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.DFService;
import jade.domain.FIPAException;
 
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.sl.*;
import jade.content.onto.*;
 
 
public class Programador extends Agent {
 
    private Codec codec = new SLCodec();
    private Ontology ontologia = CalendarOntology.getInstance();
 
 
        class WaitPingAndReplyBehaviour extends SimpleBehaviour {
      private boolean finished = false;
 
      public WaitPingAndReplyBehaviour(Agent a) {
        super(a);
      }
 
      public void action() {
 
          
    System.out.println("\nEsperando estudiantes");
 
    MessageTemplate mt = MessageTemplate.and(
            MessageTemplate.MatchLanguage(codec.getName()),
            MessageTemplate.MatchOntology(ontologia.getName()));
        ACLMessage  msg = blockingReceive(mt);
 
        try {
 
        if(msg != null){
        if(msg.getPerformative() == ACLMessage.NOT_UNDERSTOOD){
                System.out.println("Mensaje NOT UNDERSTOOD recibido");
            }
        else{
            if(msg.getPerformative()== ACLMessage.INFORM){
 
            ContentElement ce = getContentManager().extractContent(msg);
            if (ce instanceof BD){
                // Recibido un INFORM con contenido correcto
                BD bd = (BD) ce;
                
                Grupo of = bd.getGrupos();
                System.out.println("Mensaje recibido:");
                System.out.println("Nombre del grupo: " + of.getNombre());
                List integrantes= of.getEstudiantes();
                for(int i=0;i<integrantes.size();i++){
                 Estudiante e = (Estudiante) integrantes.get(i);
                System.out.println("Nombre estudiante: " + e.getNombre());
                }
                }
            else{
                // Recibido un INFORM con contenido incorrecto
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                reply.setContent("( UnexpectedContent (expected ping))");
                send(reply);
            }
            }
            else {
                // Recibida una performativa incorrecta
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+")( expected (inform)))");
                send(reply);
            }
        }
        }else{
        //System.out.println("No message received");
        }
 
         }
         catch (jade.content.lang.Codec.CodecException ce) {
               System.out.println(ce);
        }
        catch (jade.content.onto.OntologyException oe) {
            System.out.println(oe);
        }
     }
 
      public boolean done() {
        return finished;
      }
 
  }
    
    
    class EnviarMensajeBehaviour extends SimpleBehaviour {
 
      private boolean finished = false;
 
    public EnviarMensajeBehaviour(Agent a) {
        super(a);
    }
 
    public void action() {
        try
    {
        System.out.println("\nRealizado por: \nAldo Esteban Garces Correa \nAndres Felipe Gonzalez Bermudez \nWilson Daniel Ospina Leon");
            System.out.println("\nIntroduce el nombre del agente interface (el nombre dado al Agente al lanzar el -container): ");
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            String respuesta = buff.readLine();
            AID r = new AID();
            r.setLocalName(respuesta);
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setSender(getAID());
            msg.addReceiver(r);
            msg.setLanguage(codec.getName());
               msg.setOntology(ontologia.getName());
 
            System.out.println("\nIntroduce el NOMBRE del grupo:");
            respuesta = buff.readLine();
            Grupo g = new Grupo();
            g.setNombre(respuesta);
            System.out.println("\nIntroduce el NOMBRE del 1er estudiante:");
            respuesta = buff.readLine();
            Estudiante e1 = new Estudiante();
            e1.setNombre(respuesta);
            System.out.println("\nIntroduce el NOMBRE del 2er estudiante:");
            respuesta = buff.readLine();
            Estudiante e2 = new Estudiante();
            e2.setNombre(respuesta);
            ArrayList lista= new ArrayList();
            lista.add(e1);
            lista.add(e2);
            g.setEstudiantes(lista);
            BD bd = new BD();
            bd.setGrupos(g);
            getContentManager().fillContent(msg, bd);
 
            send(msg);
           }
           catch (java.io.IOException io)
            {System.out.println(io);
        }
        catch (jade.content.lang.Codec.CodecException ce) {
               System.out.println(ce);
        }
        catch (jade.content.onto.OntologyException oe) {
            System.out.println(oe);
        }
    catch (Exception e){
        System.out.println("\n\n... Terminando ...");
        finished=true;
    }
    }
 
    public boolean done() {
 
     return true;
 
    }
    } // Fin de la clase EnviarMensajeBehaviour
 
    protected void setup() {
 
    /** Registrarse en el DF */
    DFAgentDescription dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType("Programador");
    sd.setName(getName());
    sd.setOwnership("ARNOIA");
    dfd.setName(getAID());
    dfd.addServices(sd);
    try {
    DFService.register(this,dfd);
    } catch (FIPAException e) {
        System.err.println(getLocalName()+" registration with DF unsucceeded. Reason: "+e.getMessage());
        doDelete();
    }
 
    getContentManager().registerLanguage(codec);
    getContentManager().registerOntology(ontologia);
 
    EnviarMensajeBehaviour EnviarBehaviour = new EnviarMensajeBehaviour(this);
    addBehaviour(EnviarBehaviour);
    }
 
   protected void takeDown() {
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }
}
 
