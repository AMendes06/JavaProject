/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;
import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Exporter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConferenceManagement implements Exporter, Serializable{
    
    /**
     * array de conferencias
     */
    private ConcretoConference[] conference;
    
    /**
     * contador para o array de conferencias
     */
    private int count;

    
    /**
     * Método construtor para a classe ConferenceManagement
     */
    public ConferenceManagement() {
        this.conference = new ConcretoConference[5];
        this.count = 0;
    }
    
/**
 * Método para exportar os dados presentes na memória
 * @return retorna ficheiro json serialized
 * @throws IOException Se o ficheiro não abre corretamente
 */
    
    /**
     * Método para exportar os dados presentes na memória
     * @return retorna ficheiro json serialized
     * @throws IOException Se o ficheiro não abre corretamente
     */
    @Override
    public String export() throws IOException {
          
        JSONArray conferences = new JSONArray();
        JSONObject finalReport = new JSONObject();
        JSONArray participants = new JSONArray();
        JSONObject conference = new JSONObject();
       
       
        for (int i = 0 ; i < count ; i++) {
            conference.put("id", this.conference[i].getId());
            conference.put("nome", this.conference[i].getName());
            conference.put("field", this.conference[i].getField());
            if (this.conference[i].getState().equals(ConferenceState.ON_EDITING)) {
                conference.put("ConferenceState", "ON_EDITING");
            } else if (this.conference[i].getState().equals(ConferenceState.IN_PROGRESS)) {
                conference.put("ConferenceState", "IN_PROGRESS");
            } else if (this.conference[i].getState().equals(ConferenceState.FINISHED)) {
                conference.put("ConferenceState", "FINISHED");
            }
            conference.put("year", this.conference[i].getYear());
            JSONArray sessions = new JSONArray();
            for (int j = 0 ; j < this.conference[i].getSessions().length && this.conference[i].getSessions()[j] != null ; j++) {
                JSONObject session = new JSONObject();
                JSONArray presentations = new JSONArray();
                session.put("id", this.conference[i].getSessions()[j].getId());
                session.put("name", this.conference[i].getSessions()[j].getName());
                for (int k = 0 ; k < this.conference[i].getSessions()[j].getNumberOfPresentations() ; k++) {
                    JSONObject presentation = new JSONObject();
                    JSONObject presenter = new JSONObject();
                    presentation.put("id", this.conference[i].getSessions()[j].getPresentations()[k].getId());
                    presentation.put("title", this.conference[i].getSessions()[j].getPresentations()[k].getTitle());
                    presentation.put("duration", this.conference[i].getSessions()[j].getPresentations()[k].getDuration());
                    if (this.conference[i].getSessions()[j].getPresentations()[k].getPresentationState() == PresentationState.NOT_PRESENTED) {
                        presentation.put("presentationState", "NOT_PRESENTED");
                    } else if (this.conference[i].getSessions()[j].getPresentations()[k].getPresentationState() == PresentationState.PRESENTED) {
                        presentation.put("presentationState", "PRESENTED");
                    }
                    if (this.conference[i].getSessions()[j].getPresentations()[k].getPresenter() instanceof Professor) {
                        Professor p1 = (Professor)this.conference[i].getSessions()[j].getPresentations()[k].getPresenter();
                        presenter.put("id", p1.getId());
                        presenter.put("name", p1.getName());
                        presenter.put("bio", p1.getBio());
                        if (p1.getTipoParticipante() == ParticipantType.SPEAKER) {
                            presenter.put("participantType", "SPEAKER");
                        } else if (p1.getTipoParticipante() == ParticipantType.VISITANT) {
                            presenter.put("participantType", "VISITANT");
                        }
                        presenter.put("grauDeEscolaridade", p1.getGrauescolaridade());
                        presenter.put("topico", p1.getTopico());
                        presenter.put("Tipo", "Professor");
                    } else if(this.conference[i].getSessions()[j].getPresentations()[k].getPresenter() instanceof Student){
                        Student s1 = (Student)this.conference[i].getSessions()[j].getPresentations()[k].getPresenter();
                        presenter.put("id", s1.getId());
                        presenter.put("name", s1.getName());
                        presenter.put("bio", s1.getBio());
                        if (s1.getTipoParticipante() == ParticipantType.SPEAKER) {
                            presenter.put("participantType", "SPEAKER");
                        } else if (s1.getTipoParticipante() == ParticipantType.VISITANT) {
                            presenter.put("participantType", "VISITANT");
                        }
                        presenter.put("curso", s1.getCurso());
                        presenter.put("anoLetivo", s1.getAnoLetivo());
                        presenter.put("Tipo", "Student");
                    }
                    
                    presentation.put("presenter", presenter);
                    presentations.add(presentation);                   
                }
                session.put("ConcretePresentations", presentations);
                session.put("startTime", this.conference[i].getSessions()[j].getStartTime().toString());
                session.put("sessionTheme", this.conference[i].getSessions()[j].getSessionTheme());
                JSONObject room = new JSONObject();
                room.put("id", this.conference[i].getSessions()[j].getRoom().getId());
                room.put("name", this.conference[i].getSessions()[j].getRoom().getName());
                room.put("numberOfSeats", this.conference[i].getSessions()[j].getRoom().getNumberOfSeats());
                session.put("room", room);
                session.put("duration", this.conference[i].getSessions()[j].getDuration());
                session.put("maxDuration", this.conference[i].getSessions()[j].getMaxDurationPerPresentation());
                sessions.add(session);
            }
            conference.put("sessions", sessions);
            for (int m = 0 ; m < this.conference[i].getParticipants().length && this.conference[i].getParticipants()[m] != null ; m++) {
                JSONObject participant = new JSONObject();
                if (this.conference[i].getParticipants()[m] instanceof Professor) {
                        Professor p2 = (Professor)this.conference[i].getParticipants()[m];
                        participant.put("id", p2.getId());
                        participant.put("name", p2.getName());
                        participant.put("bio", p2.getBio());
                        if (p2.getTipoParticipante() == ParticipantType.SPEAKER) {
                            participant.put("participantType", "SPEAKER");
                        } else if (p2.getTipoParticipante() == ParticipantType.VISITANT) {
                            participant.put("participantType", "VISITANT");
                        }
                        participant.put("grauDeEscolaridade", p2.getGrauescolaridade());
                        participant.put("topico", p2.getTopico());
                        participant.put("Tipo", "Professor");
                    } else if(this.conference[i].getParticipants()[m] instanceof Student){
                        Student s2 = (Student)this.conference[i].getParticipants()[m];
                        participant.put("id", s2.getId());
                        participant.put("name", s2.getName());
                        participant.put("bio", s2.getBio());
                        if (s2.getTipoParticipante() == ParticipantType.SPEAKER) {
                            participant.put("participantType", "SPEAKER");
                        } else if (s2.getTipoParticipante() == ParticipantType.VISITANT) {
                            participant.put("participantType", "VISITANT");
                        }
                        participant.put("curso", s2.getCurso());
                        participant.put("anoLetivo", s2.getAnoLetivo());
                        participant.put("Tipo", "Student");
                    }
                participants.add(participant);
            }
            conference.put("participants", participants);
        }
        
        
        
        finalReport.put("conferences", conference);
        
        FileWriter arq;
        
        arq = new FileWriter("Relatorio.json");
        arq.write(finalReport.toJSONString());
        arq.close();
        
        return finalReport.toJSONString();
        
    }
    
    
    /**
     * Adicionar uma conferência a um array de conferências
     * @param conference conferência a ser adicionada
     */
    public void addConference(ConcretoConference conference) {
        int pos = findConference(conference);
        
        if (pos != -1) {
            System.out.println("It is not possible to add this conference once it is already added.");
        } else {
            if (this.conference.length == this.count) {
                this.conference = expandConference(this.conference);
            }
            this.conference[this.count] = conference;
                this.count++;
        }
    }
    
    /**
     * Expandir o array de conferências de modo a se tornar ilimitado
     * @param conference array a ser expandido
     * @return o array com o novo tamanho
     */
    private ConcretoConference[] expandConference(ConcretoConference[] conference) {
        ConcretoConference[] conference_temp = new ConcretoConference[this.conference.length * 2];
        
        for (int i = 0 ; i < this.conference.length ; i++) {
            conference_temp[i] = conference[i];
        }
        
        return conference_temp;
    }
    
    /**
     * Procura uma conferência no array de conferências
     * @param conf conferência a ser procurada
     * @return i (posiçao no array) se já tiver uma conferência igual ou -1 se 
     * não tiver nenhuma igual no array
     */
    private int findConference(ConcretoConference conf){
        for(int i = 0 ; i < this.count ; i++){
            if(this.conference[i].equals(conf)){
                return i;
            } 
        }
        return -1;
    }
    
}
