/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;
import estg.ipp.pt.tp02_conferencesystem.dashboards.Dashboard;
import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class Menu {
    
    /**
     * nome do ficheiro a ser importado
     */
    private String nomeFicheiro;
    
    /**
     * opcao escolhida no menu
     */
    private int opcao;
    
    /**
     * dashboard a ser implementada
     */
    private Dashboard dashboard;
    
    /**
     * conferencia que recebe os dados importados
     */
    private ConcretoConference cc;

    /**
     * Método construtor para a classe Menu
     */
    public Menu() {
        this.opcao = 0;
        this.nomeFicheiro = "";
        this.dashboard = new Dashboard();
        this.cc = new ConcretoConference();
    }


    /**
     * Menu usado na main classe
     * @throws IOException Se o ficheiro não abrir corretamente
     */
    public void mainMenu() throws IOException{
        
        do {
            System.out.println("1. Import file\n2. View report\n3. Show chart\n0. Sair");
            do {
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                this.opcao = Integer.parseInt(br.readLine());
            } while (this.opcao < 0 && this.opcao > 3);
            
            switch(this.opcao) {
                case 1:
                    System.out.println("Insira o nome do ficheiro terminando em .json: ");
                    BufferedReader fileReader = new BufferedReader(new InputStreamReader(System.in));
                    this.nomeFicheiro = fileReader.readLine();
                    cc = Import();
                    break;
                case 2:
                    visualizarRelatorio();
                    break;
                case 3:
                    //O método não foi implementado devido a não conseguirmos 
                    //ordenar o ficheiro json e consequentemente não pesquisamos em relação ao metodo render
            }
        } while(opcao != 0);
    }    
    
    /**
     * Método que permite visualizar o relatório do ficheiro importado
     */
    public void visualizarRelatorio() {
        Statistics[] s1 = cc.getNumberOfParticipantsBySession();
        Statistics[] s2 = cc.getNumberOfSessionsByRoom();
        
        System.out.println("Number of participants by session:");
        for (int i = 0 ; i < s1.length && s1[i] != null ; i++) {
            System.out.println(s1[i].getDescription() + ": " + (int)s1[i].getValue() + " participant(s)");
        }

        System.out.println("\nNumber of sessions by room:");
        for (int i = 0 ; i < s2.length && s2[i] != null ; i++) {
            System.out.println(s2[i].getDescription() + ": " + (int)s2[i].getValue() + " session(s)");
        }
    } 
    
    /**
     * Método que permite importar os dados de um ficheiro json para a api
     * @return uma conferência preenchida com os respetivos dados
     */
    public ConcretoConference Import(){
   
        JSONParser jsonParser = new JSONParser();
        ConcretoConference cc1 = new ConcretoConference();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        
        try {
            FileReader reader = new FileReader(this.nomeFicheiro);
            JSONObject obj = (JSONObject) jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj.get("conferences");
            String conferenceState = (String)jsonObject.get("ConferenceState");
            cc1.setId((int) (long) jsonObject.get("id"));
            cc1.setName((String)jsonObject.get("nome"));
            cc1.setField((String)jsonObject.get("field"));
            cc1.setYear((int) (long)jsonObject.get("year"));
            cc1.setState(ConferenceState.IN_PROGRESS);
            
            JSONArray participants = (JSONArray) jsonObject.get("participants");
            for (int i = 0 ; i < participants.size() ; i++) {
                JSONObject jsonParticipant = (JSONObject) participants.get(i);
                String participantType = (String)jsonParticipant.get("participantType");
                if (jsonParticipant.get("Tipo").equals("Student")) {
                    if (participantType.equals("VISITANT")) {
                        Student stu1 = new Student((String)jsonParticipant.get("curso"), (int) (long)jsonParticipant.get("anoLetivo"), (int) (long)jsonParticipant.get("id"), (String)jsonParticipant.get("name"), (String)jsonParticipant.get("bio"), ParticipantType.VISITANT);
                        try {
                            cc1.checkIn(stu1);
                        } catch (ConferenceException ex) {
                            ex.getMessage();
                        }
                    } else if (participantType.equals("SPEAKER")) {
                        Student stu1 = new Student((String)jsonParticipant.get("curso"), (int) (long)jsonParticipant.get("anoLetivo"), (int) (long)jsonParticipant.get("id"), (String)jsonParticipant.get("name"), (String)jsonParticipant.get("bio"), ParticipantType.SPEAKER);
                        try {
                            cc1.checkIn(stu1);
                        } catch (ConferenceException ex) {
                            ex.getMessage();
                        }
                    }
                    
                } else if (jsonParticipant.get("Tipo").equals("Professor")) {
                    if (participantType.equals("VISITANT")) {
                        Professor prof1 = new Professor((String)jsonParticipant.get("grauDeEscolaridade"), (String)jsonParticipant.get("topico"), (int)jsonParticipant.get("id"), (String)jsonParticipant.get("name"), (String)jsonParticipant.get("bio"), ParticipantType.VISITANT);
                        try {
                            cc1.checkIn(prof1);
                        } catch (ConferenceException ex) {
                            ex.getMessage();
                        }
                    } else if (participantType.equals("SPEAKER")) {
                        Professor prof1 = new Professor((String)jsonParticipant.get("grauDeEscolaridade"), (String)jsonParticipant.get("topico"), (int)jsonParticipant.get("id"), (String)jsonParticipant.get("name"), (String)jsonParticipant.get("bio"), ParticipantType.SPEAKER);
                        try {
                            cc1.checkIn(prof1);
                        } catch (ConferenceException ex) {
                            ex.getMessage();
                        }
                    }
                }
            }
           
            JSONArray sessions = (JSONArray) jsonObject.get("sessions");
            cc1.setState(ConferenceState.ON_EDITING);
            for (int i = 0 ; i < sessions.size() ; i++) {
                JSONObject jsonSession = (JSONObject) sessions.get(i);
                JSONObject jsonRoom = (JSONObject) jsonSession.get("room");
                ConcretoRoom room = new ConcretoRoom((int) (long)jsonRoom.get("id"), (String)jsonRoom.get("name"), (int) (long)jsonRoom.get("numberOfSeats"));
                LocalDateTime dateTime = LocalDateTime.parse((CharSequence) jsonSession.get("startTime"), formatter);
                ConcretoSession session = new ConcretoSession((int) (long)jsonSession.get("id"), (String)jsonSession.get("name"), dateTime, (String)jsonSession.get("sessionTheme"), room, (int) (long)jsonSession.get("duration"));
                try {
                    
                    cc1.addSession(session);
                } catch (ConferenceException ex) {
                    ex.getMessage();
                }
                JSONArray presentations = (JSONArray) jsonSession.get("ConcretePresentations");
                for (int j = 0 ; j < presentations.size() ; j++) {
                    JSONObject jsonPresentation = (JSONObject) presentations.get(j);
                    String presentationState = (String)jsonPresentation.get("presentationState");
                    if (presentationState.equals("NOT_PRESENTED")) {
                        JSONObject presenter = (JSONObject) jsonPresentation.get("presenter");
                        if (presenter.get("Tipo").equals("Professor")) {
                            Professor profPresenter = new Professor((String)presenter.get("grauDeEscolaridade"), (String)presenter.get("topico"), (int) (long)presenter.get("id"), (String)presenter.get("name"), (String)presenter.get("bio"), ParticipantType.SPEAKER);
                            ConcretoPresentation cp1 = new ConcretoPresentation((int) (long)jsonPresentation.get("id"), (String)jsonPresentation.get("title"), (int) (long)jsonPresentation.get("duration") , PresentationState.NOT_PRESENTED, profPresenter);
                            try {
                                session.addPresentation(cp1);
                            } catch (SessionException ex) {
                                ex.getMessage();
                            }
                        } else if (presenter.get("Tipo").equals("Student")) {
                            Student stuPresenter = new Student((String)presenter.get("curso"), (int) (long)presenter.get("anoLetivo"), (int) (long)presenter.get("id"), (String)presenter.get("name"), (String)presenter.get("bio"), ParticipantType.SPEAKER);
                            ConcretoPresentation cp1 = new ConcretoPresentation((int) (long)jsonPresentation.get("id"), (String)jsonPresentation.get("title"), (int) (long)jsonPresentation.get("duration") , PresentationState.NOT_PRESENTED, stuPresenter);  
                            try {
                                session.addPresentation(cp1);
                                session.getNumberOfPresentations();
                            } catch (SessionException ex) {
                                ex.getMessage();
                            }
                        }
                                              
                    } else if (presentationState.equals("PRESENTED")) {
                        JSONObject presenter = (JSONObject) jsonPresentation.get("presenter");
                        if (presenter.get("Tipo").equals("Professor")) {
                            Professor profPresenter = new Professor((String)presenter.get("grauDeEscolaridade"), (String)presenter.get("topico"), (int) (long)presenter.get("id"), (String)presenter.get("name"), (String)presenter.get("bio"), ParticipantType.SPEAKER);
                            ConcretoPresentation cp1 = new ConcretoPresentation((int) (long)jsonPresentation.get("id"), (String)jsonPresentation.get("title"), (int) (long)jsonPresentation.get("duration") , PresentationState.PRESENTED, profPresenter);  
                            try {
                                session.addPresentation(cp1);
                            } catch (SessionException ex) {
                                ex.getMessage();
                            }
                        } else if (presenter.get("Tipo").equals("Student")) {
                            Student stuPresenter = new Student((String)presenter.get("curso"), (int) (long)presenter.get("anoLetivo"), (int) (long)presenter.get("id"), (String)presenter.get("name"), (String)presenter.get("bio"), ParticipantType.SPEAKER);
                            ConcretoPresentation cp1 = new ConcretoPresentation((int) (long)jsonPresentation.get("id"), (String)jsonPresentation.get("title"), (int) (long)jsonPresentation.get("duration") , PresentationState.PRESENTED, stuPresenter);  
                            try {
                                session.addPresentation(cp1);
                            } catch (SessionException ex) {
                                ex.getMessage();
                            }
                        }
                    }
                }
            }

            cc1.setState(ConferenceState.ON_EDITING);
            while(!cc1.getState().toString().equals(conferenceState)) {
                cc1.changeState();
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cc1;
    }
    
}
