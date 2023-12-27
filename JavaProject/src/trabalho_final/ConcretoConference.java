package trabalho_final;

import estg.ipp.pt.tp02_conferencesystem.enumerations.ConferenceState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.ConferenceException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Conference;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConcretoConference implements Conference{
    
    /**
     * id da conferencia
     */
    private int id;
    
    /**
     * nome da conferencia
     */
    private String name;
    
    /**
     * campo da conferencia
     */
    private String field;
    
    /**
     * estado da conferencia
     */
    private ConferenceState State;
    
    /**
     * ano da conferencia
     */
    private int year;
    
    /**
     * array de sessoes pertencente à conferencia
     */
    private Session[] session;
    
    /**
     * array de participantes pertencente à conferencia
     */
    private Participant[] participant;
    
    /**
     * contador para o array de participantes da conferencia
     */
    private int countParticipant;
    
    /**
     * contador para o array de sessoes da conferencia
     */
    private int countSession;

    
    /**
     * Método construtor da classe ConcretoConference
     * @param id id da conferencia
     * @param name nome da conferência
     * @param field campo da conferência
     * @param State Estado da conferência
     * @param year Ano da conferência
     */
    public ConcretoConference(int id, String name, String field, ConferenceState State, int year) {
        this.id = id;
        this.name = name;
        this.field = field;
        this.State = State;
        this.year = year;
        this.session = new Session[30];
        this.participant = new Participant[30];
        this.countParticipant = 0;
        this.countSession = 0;
    }

    /**
     * Método de construtor vazio para a classe ConcretoConference
     */
    public ConcretoConference() {
        this.session = new Session[30];
        this.participant = new Participant[30];
        this.countParticipant = 0;
        this.countSession = 0;
    }

    /**
     * Setter para o id da conferencia
     * @param id novo id da conferencia
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setter para o nome da conferencia
     * @param name novo nome da conferencia
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter para o campo da conferencia
     * @param field novo campo da conferencia
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * Setter para o estado da conferencia
     * @param State novo estado da conferencia
     */
    public void setState(ConferenceState State) {
        this.State = State;
    }

    /**
     * Setter para o ano da conferencia
     * @param year novo ano da conferencia
     */
    public void setYear(int year) {
        this.year = year;
    }    

    /**
     * Getter para o id da conferencia
     * @return o id da conferencia
     */
    public int getId() {
        return id;
    }
    
    /**
     * Getter para o nome da conferência
     * @return o nome da conferência
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter para o campo da conferência
     * @return o campo da conferência
     */
    @Override
    public String getField() {
        return this.field;
    }

    /**
     * Getter para o estado da conferência
     * @return o estado atual da conferência
     */
    @Override
    public ConferenceState getState() {
        return this.State;
    }

    /**
     * Método com a finalidade de mudar o estado de uma conferência
     */
    @Override
    public void changeState() {
        if (this.State == ConferenceState.ON_EDITING) {
            this.State = ConferenceState.IN_PROGRESS;
        } else if (this.State == ConferenceState.IN_PROGRESS) {
            this.State = ConferenceState.FINISHED;
        } else {
            this.State = ConferenceState.FINISHED;
        }
    }

    /**
     * Getter para o ano da conferência. O ano deverá ser maior ou igual ao ano corrente
     * @return o ano da conferência se respeitar as condições e -1 se não as respeitar.
     */
    @Override
    public int getYear() {
        if(this.year >= LocalDateTime.now().getYear()){
            return this.year;
        }
        return -1;
    }

    /**
     * Método usado para adicionar uma sessão ao array de sessões
     * @param sn sessão a ser adicionada ao array
     * @return true se a sessão for inserida, false se a sessão já existir
     * @throws ConferenceException 1. Se o estado da conferência não está ON_EDITING 
     * 2. Se a sessão passada é nula
     * 3. Se a hora de começo e a duração coincide 
     * com esses atributos de outra sessão presente no array
     * 4. se o apresentador da sessão passada como argumento coincide em com o apresentador
     * de outra sessão em algum intervalo de tempo
     * 5. Se uma apresentação da sessão passada já está presente em alguma sessão do array
     */
    @Override
    public boolean addSession(Session sn) throws ConferenceException {
        int pos = findSession(sn);
        
        if (this.State != ConferenceState.ON_EDITING) {
            System.out.println("EXC1");
            throw new ConferenceException("Conference is not on editing state!");
        } else if (sn == null) {
            System.out.println("EXC2");
            throw new ConferenceException("This session is null!");
        }
        for (int i = 0 ; i < this.countSession ; i++) {
            if (this.session[i].getStartTime().equals(sn.getStartTime())) {
                throw new ConferenceException("It's not possible to set the session for this timer.");
            } else if (this.session[i].getStartTime().plusMinutes(this.session[i].getDuration()).isAfter(sn.getStartTime()) && this.session[i].getStartTime().isBefore(sn.getStartTime())) {
                throw new ConferenceException("It's not possible to set the session for this timer.");
            }
        } 
        
        for (int i = 0 ; i < sn.getNumberOfPresentations() ; i++) {
            for (int j = 0 ; j < this.countSession ; j++) {
                for (int k = 0 ; k < this.session[j].getNumberOfPresentations() ; k++) {
                    if (sn.getPresentations()[i].getPresenter() == this.session[j].getPresentations()[k].getPresenter()) {
                        if (sn.getStartTime().isBefore(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isBefore(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration())) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isAfter(this.session[j].getStartTime())) {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts and end before an existent one.");
                        } else if (sn.getStartTime().isAfter(this.session[j].getStartTime()) 
                                && sn.getStartTime().isBefore(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration())) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isAfter(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration())))  {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts and end after an existent one.");
                        } else if (sn.getStartTime().isBefore(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isAfter(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()))){
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts before and end after an existent one.");
                        } else if (sn.getStartTime().isAfter(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isBefore(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()))){
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts after and end before an existent one.");
                        } else if (sn.getStartTime().isEqual(this.session[j].getStartTime()) 
                                && (sn.getStartTime().plusMinutes(sn.getDuration()).isBefore(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration())))) {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session ends before another session but starts at the same time.");
                        } else if (sn.getStartTime().isEqual(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isAfter(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()))) {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session ends after another session but starts at the same time.");
                        } else if (sn.getStartTime().isBefore(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isEqual(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()))) {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts before another session but ends at the same time.");
                        } else if (sn.getStartTime().isAfter(this.session[j].getStartTime()) 
                                && sn.getStartTime().plusMinutes(sn.getDuration()).isEqual(this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()))) {
                            throw new ConferenceException("It is not possible to add this session because the presenter is the same and the session starts after another session but ends at the same time.");
                        }
                    }
                }
            }
        }
        
        for (int j = 0 ; j < sn.getPresentations().length && sn.getPresentations()[j] != null; j++) {
            for (int k = 0 ; k < this.countSession ; k++) {
                for (int m = 0 ; m < this.session[k].getPresentations().length && this.session[k].getPresentations()[m] != null; m++) {
                    if (sn.getPresentations()[j].equals(this.session[k].getPresentations()[m])) {
                        throw new ConferenceException("The presentation already exists in another session.");
                    }
                }
            }
        }
        
        
        if (pos != -1) {
            return false;
        }
        
        if (this.session.length == this.countSession) {
            this.session = expandSession(this.session);
        }
        this.session[this.countSession] = sn;
        this.countSession++;
        
        return true;
    }

    /**
     * Método usado para remover uma sessão do array de sessões
     * @param i id da sessão a se retirar
     * @throws ConferenceException 1. Se o id passado não pertence a nenhuma sessão,
     * 2. Se a conferência não tiver com o estado ON_EDITING
     */
    @Override
    public void removeSession(int i) throws ConferenceException {
        int pos = findSessionId(i);
        
        if (pos == -1) {
            throw new ConferenceException("This id don't belong to any session.");
        } else if (this.State != State.ON_EDITING) {
            throw new ConferenceException("Can not remove. The conference is not on editing mode.");
        }
        
        for (int j = pos ; j < this.countSession ; j++) {
            this.session[j] = this.session[j+1];
        }
        this.session[this.countSession] = null;
        this.countSession--;
    }

    /**
     * Método com a finalidade de procurar uma sessão através de um id
     * @param i id da sessão a ser procurada
     * @return o objeto sessão com o id passado como argumento
     * @throws ConferenceException Se o id não pertencer a nenhuma sessão
     */
    @Override
    public Session getSession(int i) throws ConferenceException {
        int pos = findSessionId(i);
        
        if (pos == -1) {
            throw new ConferenceException("This id don't belong to any session.");
        }
        return session[pos];
    }

    /**
     * getter para o array de sessões
     * @return um array de sessões
     */
    @Override
    public Session[] getSessions() {
        return this.session;
    }

    /**
     * Método para adicionar os participantes no array de participantes
     * @param p participante a adicionar no array
     * @throws ConferenceException 1. Se o participante é null, 2. Se o participante
     * já está adicionado no array, 3. Se o estado da conferência não está IN_PROGRESS
     */
    @Override
    public void checkIn(Participant p) throws ConferenceException {
        int pos = findParticipant(p);
        
        if (p == null) {
            throw new ConferenceException("Can not complete the check-in due to null participant.");
        } else if (pos != -1) {
            throw new ConferenceException("The participant already checked-in.");
        } else if (this.State != this.State.IN_PROGRESS) {
            throw new ConferenceException("Can not check-in. The conference is not in progress.");
        }
        
        if (this.participant.length == this.countParticipant) {
            this.participant = expandParticipant(this.participant);
        }
        this.participant[this.countParticipant] = p;
        this.countParticipant++;
    }

    /**
     * Método com a finalidade de procurar um participante no array de participantes
     * @param i id do participante a ser procurado
     * @return o objeto participante com o id passado como argumento
     * @throws ConferenceException se o participante não existir
     */
    @Override
    public Participant getParticipant(int i) throws ConferenceException {
        int pos = findParticipantId(i);
        
        if (pos == -1) {
            throw new ConferenceException("This id don't belong to any participant.");
        } 
        return participant[pos];
    }

    /**
     * getter para o array de participantes
     * @return um array dos participantes
     */
    @Override
    public Participant[] getParticipants() {
        return this.participant;
    }

    /**
     * Método com a finalidade de obter todos os apresentadores (sem duplicados) 
     * de todas as sessões
     * @return um array com os apresentadores
     */
    @Override
    public Participant[] getSpeakerParticipants() {
        Participant[] speaker_temp = new Participant[5];
        int speaker_count = 0, numberOfPresenter = 0;;
        
        for (int m = 0 ; m < this.countSession ; m++) {
            numberOfPresenter += this.session[m].getAllPresenters().length;
        }
        
        if (numberOfPresenter > 0) {
            speaker_temp[speaker_count] = this.session[0].getPresentations()[0].getPresenter();
            speaker_count++;

            for (int i = 0 ; i < this.countSession ; i++) {
                for (int j = 0 ; j < this.session[i].getNumberOfPresentations() ; j++) {
                    if (speaker_temp.length == speaker_count) {
                        speaker_temp = expandSpeakerTemp(speaker_temp);
                    }
                    for (int k = 0 ; k < speaker_count ; k++) {
                        if (this.session[i].getPresentations()[j].getPresenter() != speaker_temp[k]) {
                            speaker_temp[speaker_count] = this.session[i].getPresentations()[j].getPresenter();
                            speaker_count++;
                        }
                    }
                }
            }
        } else {
            System.out.println("Não existem apresentadores.");
        }
        return speaker_temp;
    }

    /**
     * Método usado para prourar uma sessão de uma determinada sala num determinado
     * período de tempo
     * @param i id da sala 
     * @param ldt hora de começo da sessão
     * @param ldt1 hora de término da sessão
     * @return um array com as sessões dentro dos argumentos passados
     * @throws ConferenceException 1. se o id da sala existe, 2. se o estado da 
     * conferência não é IN_PROGRESS, 3. se o intervalo de tempo passado é válido
     */
    @Override
    public Session[] getRoomSessions(int i, LocalDateTime ldt, LocalDateTime ldt1) throws ConferenceException {
        Session[] session_temp = new Session[5];
        int pos = findRoomId(i), session_count = 0;
        
        if (pos == -1) {
            throw new ConferenceException("This id don't belong to any room.");
        } else if (this.State != State.IN_PROGRESS) {
            throw new ConferenceException("The conference state is not in progress.");
        } else if (ldt1.isBefore(ldt)) {
            throw new ConferenceException("The time interval is invalid.");
        }
        if (session_temp.length == session_count) {
            session_temp = expandSession(session_temp);
        }
        
        for (int j = 0 ; j < this.countSession ; j++) {
            if (this.session[j].getRoom().getId() == i && this.session[j].getStartTime().isAfter(ldt) 
                    && this.session[j].getStartTime().plusMinutes(this.session[j].getDuration()).isBefore(ldt1)) {
                session_temp[session_count] = this.session[j];
                session_count++;
            }
        }
        return session_temp;
    }

    /**
     * Método usado para obter todas as salas (sem duplicadas) que irão suportar as sessões
     * @return um array com todas as salas que suportam sessões
     */
    @Override
    public Room[] getRooms() {
        Room[] room_temp = new Room [5];
        int room_count = 0;
        
        if (this.countSession > 0) {
            room_temp[room_count] = this.session[0].getRoom();
            room_count++;
            
            for (int i = 0 ; i < this.countSession ; i++) {
                if (room_temp.length == room_count) {
                    room_temp = expandRoomTemp(room_temp);
                }
                for (int j = 0 ; j < room_count ; j++) {
                    if (this.session[i].getRoom() != room_temp[j]) {
                        room_temp[room_count] = this.session[i].getRoom();
                        room_count++;
                    }
                }
            }
        }
        return room_temp;
    }

    /**
     * Método usado para gerar certificados para os apresentadores presentes na conferência
     * @param string caminho para a pasta onde é guardado o certificado dos apresentadores
     * @throws ConferenceException Se o estado da conferência não for "FINISHED"
     */
    @Override
    public void generateSpeakerCertificates(String string) throws ConferenceException {
        
        if (this.State != State.FINISHED) {
            throw new ConferenceException("The conference is not finished.");
        }
        for (int i = 0 ; i < this.countSession ; i++) {
            for (int j = 0 ; j < this.session[i].getNumberOfPresentations() ; j++) {
                try {
                    JSONObject jsonSpeaker = new JSONObject();
                    jsonSpeaker.put("Participant name: ", this.session[i].getPresentations()[j].getPresenter().getName());
                    jsonSpeaker.put("Conference name: ", this.name);
                    jsonSpeaker.put("Conference year: ", this.year);
                    jsonSpeaker.put("Presentation title: ", this.session[i].getPresentations()[j].getTitle());
                    jsonSpeaker.put("Presentation date: ", this.session[i].getStartTime());
                    FileWriter arq = new FileWriter(string + "Participant " + this.session[i].getPresentations()[j].getPresenter().getId() + ", Presentation " + this.session[i].getPresentations()[j].getId() + ".json");
                    arq.write(jsonSpeaker.toJSONString());
                    arq.close();
                } catch (IOException ex) {
                    Logger.getLogger(ConcretoConference.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Método usado para gerar certificados para todos os participantes presentes na conferência
     * @param string caminho para a pasta onde é guardado o certificado dos participantes
     * @throws ConferenceException Se o estado da conferência não for "FINISHED"
     */
    @Override
    public void generateParticipantCertificates(String string) throws ConferenceException {
        
        if (this.State != State.FINISHED) {
            throw new ConferenceException("The conference is not finished");
        }
        for (int i = 0 ; i < this.countParticipant ; i++) {
            try {
                JSONObject jsonParticipant = new JSONObject();
                jsonParticipant.put("Participant name: ", this.participant[i].getName());
                jsonParticipant.put("Conference name: ", this.name);
                jsonParticipant.put("Conference year: ", this.year);
                FileWriter arq;
                arq = new FileWriter(string + "Participante name: " + this.participant[i].getName() + ".json");
                arq.write(jsonParticipant.toJSONString());
                arq.close();
            } catch (IOException ex) {
                Logger.getLogger(ConcretoConference.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Método para obter o calendário da conferência com as sessões e apresentações
     * a aparecer ordenadamente
     * @return uma string do calendário
     */
    @Override
    public String getSchedule() {
        String schedule = "";
        
        for (int i = 0; i < this.countSession; i++) {
            for (int j = 0; j < this.countSession ; j++) {
                if (this.session[i].getStartTime().isBefore(this.session[j].getStartTime())) {
                    Session temp = this.session[i];
                    this.session[i] = this.session[j];
                    this.session[j] = temp;
                }
            }
        }
        
        for (int i = 0 ; i < this.countSession ; i++) {
            schedule += "Sessão " + this.session[i].getId() + "\nStart time: " + this.session[i].getStartTime() + ": ";
            for (int j = 0 ; j < this.session[i].getNumberOfPresentations() ; j++) {
                schedule +="\n    Apresentação " + this.session[i].getPresentations()[j].getId() + "\n    Duração: " + this.session[i].getDuration() + "\n------------------------";
            }
            schedule += "\n";
        }
        return schedule;
    }

    /**
     * Método usado para obter o número de participantes por sessão
     * @return array de estatísticas com o número de participantes(value) e o id
     * de cada sessão (description)
     */
    @Override
    public Statistics[] getNumberOfParticipantsBySession() {
        Statistics[] statistics_temp = new Statistics[5];
        int statistics_count = 0, n_participants = 0;
        
        for (int i = 0 ; i < this.countSession ; i++) {
            for (int j = 0 ; j < this.session[i].getNumberOfPresentations() ; j++) {
                if (this.session[i].getPresentations()[j].getPresenter() != null) {
                    n_participants += 1;
                }
            }
            if (statistics_temp.length == statistics_count) {
                statistics_temp = expandStatistics(statistics_temp);
            }
            ConcretoStatistics s1 = new ConcretoStatistics("Sessão " + this.session[i].getId(), n_participants);
            statistics_temp[statistics_count] = s1;
            statistics_count++;
            n_participants = 0;
        }
        
        JSONObject jsonPartBySes = new JSONObject();
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonDataSets = new JSONObject();
        JSONArray jsonSessions = new JSONArray();
        JSONArray jsonParticipants = new JSONArray();
        for (int k = 0 ; k < statistics_count ; k++) {
            jsonSessions.add(statistics_temp[k].getDescription());
            jsonParticipants.add(statistics_temp[k].getValue());
        }
        jsonPartBySes.put("type", "bar");
        jsonLabels.put("labels", jsonSessions);
        jsonPartBySes.put("data", jsonLabels);
        jsonDataSets.put("label", "Participants");
        jsonDataSets.put("data", jsonParticipants);
        jsonPartBySes.put("datasets", jsonDataSets);
        
        
        try {
        FileWriter arq;
        arq = new FileWriter("ParticipantsBySession.json");
        arq.write(jsonPartBySes.toJSONString());
        arq.close();
        } catch (IOException ex) {
            Logger.getLogger(ConcretoConference.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return statistics_temp;
    }

    
    /**
     * Método usado para obter o número de sessões que cada sala tem
     * @return array de estatísticas com o número de sessões por sala(value) e 
     * o id da sala(description)
     */
    @Override
    public Statistics[] getNumberOfSessionsByRoom() {
        Statistics[] statistics_temp = new Statistics[5];
        int n_sessions = 0, statistics_count = 0;
        
        for (int i = 0 ; i < this.getRooms().length && this.getRooms()[i] != null ; i++) {
            for (int j = 0 ; j < this.countSession ; j++) {
                if (this.getRooms()[i].getId() == this.session[j].getRoom().getId()) {
                    n_sessions++;
                }
            }
            if (statistics_temp.length == statistics_count) {
                statistics_temp = expandStatistics(statistics_temp);
            }
            ConcretoStatistics s1 = new ConcretoStatistics("Quarto " + this.getRooms()[i].getId(), n_sessions);
            statistics_temp[statistics_count] = s1;
            statistics_count++;
            n_sessions = 0;
        }
        
        JSONObject jsonSesByRoom = new JSONObject();
        JSONObject jsonLabels = new JSONObject();
        JSONObject jsonDataSets = new JSONObject();
        JSONArray jsonSessions = new JSONArray();
        JSONArray jsonRoom = new JSONArray();
        for (int k = 0 ; k < statistics_count ; k++) {
            jsonSessions.add(statistics_temp[k].getDescription());
            jsonRoom.add(statistics_temp[k].getValue());
        }
        jsonSesByRoom.put("type", "bar");
        jsonSesByRoom.put("data", jsonLabels);
        jsonLabels.put("labels", jsonSessions);
        jsonSesByRoom.put("datasets", jsonDataSets);
        jsonDataSets.put("label", "Participants");
        jsonDataSets.put("data", jsonRoom);
        
        try {
        FileWriter arq;
        arq = new FileWriter("SessionsByRoom.json");
        arq.write(jsonSesByRoom.toJSONString());
        arq.close();
        } catch (IOException ex) {
            Logger.getLogger(ConcretoConference.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return statistics_temp;
    }
    
    /**
     * Método usado para expandir o array de sessões de modo a este se tornar infinito
     * @param session array antigo que se quer expandir
     * @return 
     */
    private Session[] expandSession(Session[] session) {
        Session[] session_temp = new Session[this.session.length * 2];
        
        for (int i = 0 ; i < this.session.length ; i++) {
            session_temp[i] = session[i];
        }
        
        return session_temp;
    }
    
    /**
     * Método usado para expandir o array de participantes de modo a este se tornar infinito
     * @param participant array antigo que se quer expandir
     * @return 
     */
    private Participant[] expandParticipant(Participant[] participant) {
        Participant[] participant_temp = new Participant[this.participant.length * 2];
        
        for (int i = 0 ; i < this.participant.length ; i++) {
            participant_temp[i] = participant[i];
        }
        
        return participant_temp;
    }
    
    /**
     * Método usado para expandir o array de apresentadores de modo a este se tornar infinito
     * @param speaker_temp array antigo que se quer expandir
     * @return 
     */
    private Participant[] expandSpeakerTemp(Participant[] speaker_temp) {
        Participant[] speaker_temp_2 = new Participant[speaker_temp.length * 2];
        
        for (int i = 0 ; i < speaker_temp.length ; i++) {
            speaker_temp_2[i] = speaker_temp[i];
        }
        
        return speaker_temp_2;
    }
    
    /**
     * Método usado para expandir o array de salas de modo a este se tornar infinito
     * @param room_temp array antigo que se quer expandir
     * @return 
     */
    private Room[] expandRoomTemp(Room[] room_temp) {
        Room[] room_temp_2 = new Room[room_temp.length * 2];
        
        for (int i = 0 ; i < room_temp.length ; i++) {
            room_temp_2[i] = room_temp[i];
        }
        
        return room_temp_2;
    }
    
    /**
     * Método usado para expandir o array de estatisticas de modo a este se tornar infinito
     * @param statistics array antigo que se quer expandir
     * @return 
     */
    private Statistics[] expandStatistics(Statistics[] statistics) {
        Statistics[] statistics_temp = new Statistics[statistics.length * 2];
        
        for (int i = 0 ; i < statistics.length ; i++) {
            statistics_temp[i] = statistics[i];
        }
        
        return statistics_temp;
    }
    
    /**
     * Metodo usado para retornar a posição da sessão no array
     * @param sn sessão a procurar no array
     * @return i (posição no array) ou -1 caso não exista no array
     */
    private int findSession(Session sn){
        for(int i = 0; i < this.countSession;i++){
            if(this.session[i].equals(sn)){
                return i;
            }
        }
        return -1;
    }
    
    
     /**
     * Método usado para verificar se existe algum participante com o id passado
     * como argumento 
     * @param p participante a ser procurado
     * @return i (posição no array) se existe algum participante com o id passado 
     * como argumento ou -1 se não existe nenhum participante com esse id
     */
    private int findParticipant(Participant p) {
        for (int i = 0 ; i < this.countParticipant ; i++) {
            if (this.participant[i].equals(p)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Método usado para verificar se existe alguma sessão com o id passado
     * como argumento 
     * @param id id da sessão a ser procurada
     * @return i (posição no array) se existe alguma sessão com o id passado 
     * como argumento ou -1 se não existe nenhuma sessão com esse id
     */
    private int findSessionId(int id) {
        for (int i = 0 ; i < this.countSession ; i++) {
            if (this.session[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Método usado para verificar se existe algum participante com o id passado
     * como argumento
     * @param id id do participante a ser procurado
     * @return i (posição no array) se existe algum participante com o id passado
     * como argumento ou -1 se não existe nenhuma sessão com esse id
     */
    private int findParticipantId(int id) {
        for (int i = 0 ; i < this.countParticipant ; i++) {
            if (this.participant[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     *  Método usado para verificar se existe alguma sala com o id passado
     * como argumento
     * @param id id do participante a ser procurado
     * @return i (posição no array) se existe algum participante com o id passado
     * como argumento ou -1 se não existe nenhuma sessão com esse id
     */
    private int findRoomId(int id) {
        for (int i = 0 ; i < this.countSession ; i++) {
            if (this.session[i].getRoom().getId() == id) {
                return i;
            }
        }
        return -1;
    }

}

