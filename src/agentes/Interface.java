package agentes;

import grupos.*;
import gui.principal;
import jade.util.leap.List;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*; 
import jade.content.*;
import jade.content.lang.*;
import jade.content.lang.Codec.CodecException;
import jade.content.lang.sl.*;
import jade.content.onto.*;
import jade.util.leap.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JFrame;
 
public class Interface extends Agent {
 
private final Codec CODEC = new SLCodec();
private final Ontology ONTOLOGIA = CalendarOntology.getInstance();    
private JFrame miventana;   
    
class EnviarMensajeBehaviour extends SimpleBehaviour {
 
    private boolean terminado = false;
 
    public EnviarMensajeBehaviour(Agent a) {
        super(a);
    }
 
    public void action() {
        try{
            System.out.println("\nRealizado por: \nAldo Esteban Garces Correa \nAndres Felipe Gonzalez Bermudez \nWilson Daniel Ospina Leon");
            System.out.println("\nIntroduce el nombre del agente interface (el nombre dado al Agente al lanzar el -container): ");
            BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
            String respuesta = buff.readLine();
            AID r = new AID();
            r.setLocalName(respuesta);
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setSender(getAID());
            msg.addReceiver(r);
            msg.setLanguage(CODEC.getName());
            msg.setOntology(ONTOLOGIA.getName());

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
        catch (IOException | jade.content.lang.Codec.CodecException | jade.content.onto.OntologyException io){
           System.out.println(io);
        }
        catch (Exception e){
            System.out.println("\n\n... Terminando ...");
            terminado = true;
        }
    }
 
    public boolean done() { 
     return true; 
    }
}
     
  class WaitPingAndReplyBehaviour extends SimpleBehaviour {
    private boolean TERMINADO = false;
 
    public WaitPingAndReplyBehaviour(Agent a) {
        super(a);
    }
    public void action() {
        
        System.out.println("\nEsperando estudiantes");
 
        MessageTemplate mt = MessageTemplate.and(
                MessageTemplate.MatchLanguage(CODEC.getName()),
                MessageTemplate.MatchOntology(ONTOLOGIA.getName()));
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
         catch (CodecException | OntologyException ce) {
               System.out.println(ce);
        }
     }
 
      public boolean done() {
        return TERMINADO;
      }
 
  } 
 
  @Override
  protected void setup() {
 
    getContentManager().registerLanguage(CODEC);
    getContentManager().registerOntology(ONTOLOGIA);
    miventana=new principal(this);
    miventana.setVisible(true);

    WaitPingAndReplyBehaviour PingBehaviour;
    PingBehaviour = new  WaitPingAndReplyBehaviour(this);
    addBehaviour(PingBehaviour);
 }
}