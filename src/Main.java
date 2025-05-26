import java.util.LinkedList;
import java.util.Queue;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
//Crear candidatos
        Candidato candidato1 = new Candidato(1, "Kast", "Republicano");
        Candidato candidato2 = new Candidato(2, "Kaiser", "Libertario");
        Candidato candidato3 = new Candidato(3, "Artes", "Independiente");

//Crear urna electoral
        UrnaElectoral urna1 = new UrnaElectoral();

//Agregar candidatos
        urna1.listaCandidatos.add(candidato1);
        urna1.listaCandidatos.add(candidato2);
        urna1.listaCandidatos.add(candidato3);

//Crear votantes
        Votante votante1 = new Votante(10,"juan");
        Votante votante2 = new Votante(20, "Elisa");
        Votante votante3 = new Votante(30,"Jose");

//registrar votos y obtener ids
        int idVoto1 = urna1.registrarVoto(votante1,1); //0
        int idVoto2 = urna1.registrarVoto(votante2,2); //1
        int idVoto3 = urna1.registrarVoto(votante3,1); //2

//Mostrar resultados de la votacion antes de remover sera 1=2 2=1 3=0
        Map<Integer, Integer> resultados = urna1.obtenerResultados();
        System.out.println(resultados);

//voto duplicado
        urna1.registrarVoto(votante3,2);

//reportar voto
        urna1.reportarVoto(candidato1,idVoto1);
        urna1.reportarVoto(candidato2,idVoto2);
        urna1.reportarVoto(candidato1,idVoto3);

//Mostrar resultados de la votacion despues de reportar votos  1=0 2=0 3=0
        Map<Integer, Integer> resultados2 = urna1.obtenerResultados();
        System.out.println(resultados2);

    }
}
