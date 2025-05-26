import java.time.LocalDateTime;
import java.util.*;

public class UrnaElectoral {
    LinkedList<Candidato> listaCandidatos;
    Stack<Voto> historialVotos;
    Queue<Voto> votosReportados;
    private int idCounter = 0;

    public UrnaElectoral() {
        this.listaCandidatos = new LinkedList<>();
        this.historialVotos = new Stack<>();
        this.votosReportados = new LinkedList<>();
    }

    public boolean verificarVotante(Votante votante) {
        return votante.yaVoto();
    }

    public int generarID() {
        return idCounter++;
    }

    public int registrarVoto(Votante votante, int candidatoID) {

        //verificar si el votante ya votó
        if (verificarVotante(votante)) {
            System.out.println("El votante ya esta registrado");
            return -1; //no se registro debido a que ya voto
        }

        Candidato candidatoEscogido = null; //variable para guardar el objeto dentro de la listaCandidatos
        for (int i = 0; i < listaCandidatos.size(); i++) {
            Candidato candidato1 = listaCandidatos.get(i);
            if (candidato1.getId() == candidatoID) {
                candidatoEscogido = candidato1;
                break;
            }
        }
        if (candidatoEscogido == null) {
            System.out.println(" El candidato no se encontro ");
            return -1; //no se encontro asi que no hay registro del voto
        }
        Voto votoNuevo = new Voto(generarID(), votante.getId(), candidatoEscogido.getId(), LocalDateTime.now().toString());

        //agregar voto al candidato y al historial de votos
        candidatoEscogido.agregarVoto(votoNuevo);
        historialVotos.push(votoNuevo);

        //marcar como votado
        votante.marcarComoVotado();
        return votoNuevo.getId();//se registro el voto
    }

    public boolean reportarVoto(Candidato candidato, int idVoto) {
        Queue<Voto> votosCandidato = candidato.getVotosRecibidos();

        //verificar si el voto ya esta en la cola de reportados
        for (Voto voto : votosReportados) {
            if (voto.getId() == idVoto) {
                System.out.println("El voto ya fue reportado");
                return false; //no se reporto, ya existe en la cola
            }
        }

        //buscar el voto en cola candidato y moverlo a cola reportar
        Voto votoReport = null; //variable para guardar el voto a reportar

        for (Voto voto : votosCandidato) {
            if (voto.getId() == idVoto) {
                votoReport = voto;
                break; //se encontro el voto
            }
        }
        //remover voto de cola del candidato y agregar a cola de reportados
        if (votoReport != null) { //la variable no esta vacia pq se encontro y asigno el voto
            votosCandidato.remove(votoReport);
            votosReportados.add(votoReport);
            Stack<Voto> pilaTemporal= new Stack<>();
            boolean removido = false;
            while(!historialVotos.isEmpty()){
                Voto actual=historialVotos.pop();
                if (actual.getId() == idVoto && !removido){ //si es el voto a reportar no se agrega a la pila temporal
                    removido= true;
                }else{
                    pilaTemporal.push(actual); //si no es el voto buscado se agrega a la pila temporal
                }
            }
            while(!pilaTemporal.isEmpty()){
                historialVotos.push(pilaTemporal.pop());//se agregan todos los votos menos el buscado
            }
            return true; //se removio y reporto el voto
        } else {
            System.out.println(" El voto no se encontro en la cola del candidato");
            return false; //al no encontrarse no se remueve ni se pasa a la otra cola
        }
    }

    public Map<Integer, Integer> obtenerResultados() {
        Map<Integer, Integer> resultadosCandidatos = new HashMap<>();
        for(Candidato candidato : listaCandidatos) {
            resultadosCandidatos.put(candidato.getId(), candidato.getVotosRecibidos().size()); //conteo de votos x candidato
        }

        return resultadosCandidatos;
    }

}
