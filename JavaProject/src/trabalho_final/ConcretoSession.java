/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;
import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;
import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import java.time.LocalDateTime;

/*
 *
 * @author Angelo
*/




/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import estg.ipp.pt.tp02_conferencesystem.exceptions.SessionException;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Session;
import java.time.LocalDateTime;



/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConcretoSession implements Session {
    
    /**
     * id da sessao
     */
    private int id;
    
    /**
     * nome da sessao
     */
    private String name;
    
    /**
     * array de apresentaçoes da sessao
     */
    private ConcretoPresentation[] concretoPresentation;
    
    /**
     * data de inicio da sessao
     */
    private LocalDateTime startTime;
    
    /**
     * tema da sessao
     */
    private String theme;
    
    /**
     * sala da sessao
     */
    private ConcretoRoom concretoRoom;
    
    /**
     * duraçao da sessao
     */
    private int duration;
    
    /**
     * duraçao maxima da sessao
     */
    private int maxDuration;
    
    /**
     * contador para o array de apresentaçoes da sessao
     */
    private int count;

    /**
     * Método construtor para a classe ConcretoSession
     * @param id id da sessao
     * @param name nome da sessao
     * @param startTime data de inicio da sessao
     * @param theme tema sessão
     * @param concretoRoom sala da sessão
     * @param duration duração da sessão
     */
    public ConcretoSession(int id, String name, LocalDateTime startTime, String theme, ConcretoRoom concretoRoom, int duration) {
        this.id = id;
        this.name = name;
        this.concretoPresentation = new ConcretoPresentation[30];
        this.startTime = startTime;
        this.theme = theme;
        this.concretoRoom = concretoRoom;
        this.duration = duration;
        this.maxDuration = 30;
        this.count = 0;
    }

    /**
     * Getter para o id da sessão
     * @return o id da sessão
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Getter para o nome da sessao
     * @return o nome da sessao
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter para a duraçao da sessao
     * @return a duraçao da sessao
     */
    @Override
    public int getDuration() {
        return this.duration;
    }

    /**
     * Getter para a duraçao máxima por apresentaçao
     * @return a duração máxima por apresentaçao
     */
    @Override
    public int getMaxDurationPerPresentation() {
        return this.maxDuration;
        
    }

    /**
     * getter para a data de começo da sessao
     * @return a hora de começo da sessao
     */
    @Override
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * getter para o tema da sessao
     * @return o tema da sessao
     */
    @Override
    public String getSessionTheme() {
        return this.theme;
    }

    /**
     * getter para o quarto da sessao
     * @return o quarto da sessao
     */
    @Override
    public Room getRoom() {
        return this.concretoRoom;
    }

    /**
     * Método para adicionar apresentações no array de apresentaçoes
     * @param presentation apresentação a ser adicionada
     * @return true se adicionar ou falso se não existir ou não adicionar
     * @throws SessionException 1. Se o argumento passado for nulo
     * 2. Se a duração exceder a duração maxima da sessao
     * 3. Se a duração de todas as apresentações somando com a passada como argumento
     * for superior à duraçao maxima da sessao
     */
    @Override
    public boolean addPresentation(Presentation presentation) throws SessionException {
        int pos = find(presentation);
        ConcretoParticipant cp = (ConcretoParticipant) presentation.getPresenter();
        int duration_total = presentation.getDuration();
        
        for (int i = 0 ; i < this.count ; i++) {
            duration_total += this.concretoPresentation[i].getDuration();
        }
        if (presentation == null) {
            throw new SessionException("Session is null !");
        }
        if (pos != -1) {
            return false;
        }
        if (presentation.getDuration() > this.duration) {
            throw new SessionException("Duration maximum time execeeded!");
        } else if (duration_total > this.duration) {
            throw new SessionException("This presentation can not be add because it will exceed the maximum time.");
        }
        if (this.concretoPresentation.length == this.count) {
            this.concretoPresentation = expandPresentation(concretoPresentation);
        }
        
        if (cp.getTipoParticipante() == ParticipantType.SPEAKER) {
            this.concretoPresentation[this.count++] = (ConcretoPresentation) presentation;
            return true;
        }
        
        return false;
    }
          
        
    /**
     * Método para remover uma apresentação do array
     * @param i id da apresentação a ser removida
     * @throws SessionException Se a apresentação não existir
     */
    @Override
    public void removePresentation(int i) throws SessionException {
        int pos = findId(i);
        
        if (pos == -1) {
            throw new SessionException("This id don't belong to any presentation.");
        }
        
        for (int k = pos ; k < this.count; k++) {
            this.concretoPresentation[k] = this.concretoPresentation[k+1];
        }
        this.concretoPresentation[this.count] = null;
        this.count--;
    }

    /**
     * Método para encontrar uma apresentação dado um id passado
     * @param i id da apresentação a ser retornada
     * @return apresentação com o id passado como argumento
     * @throws SessionException Se a apresentação não existir
     */
    @Override
    public Presentation getPresentation(int i) throws SessionException {
        int pos_getP = findId(i);
        
        if (pos_getP == -1) {
            throw new SessionException("This id don't belong to any presentation.");
        } 
        return concretoPresentation[pos_getP];
        
    }

    /**
     * Getter para o array de apresentações
     * @return o array de apresentações
     */
    @Override
    public Presentation[] getPresentations() {
        return this.concretoPresentation;
    }

    /**
     * Método para identificar se a sessão já começou
     * @return true se a sessão já foi apresentada e false se ainda não
     */
    @Override
    public boolean isStarted() {
        for(int i = 0 ; i < count ; i++){
            if(this.concretoPresentation[i].getPresentationState().equals(PresentationState.PRESENTED)){
                return true;
            }
        }
        return false;
    }

    /**
     * Getter para todos os apresentadores da sessão (sem duplicados)
     * @return o array com todos os apresentadores da sessão
     */
    @Override
    public Participant[] getAllPresenters() {
        Participant[] presentation_presenters = new Participant[this.count];
        
        for (int i = 0 ; i < this.count ; i++) {
            presentation_presenters[i] = concretoPresentation[i].getPresenter();
        }
        
        return presentation_presenters;
    }

    /**
     * Getter para o numero de apresentações da sessao
     * @return o número de apresentações da sessao
     */
    @Override
    public int getNumberOfPresentations() {
        return this.count;
    }
    
    /**
     * Método usado para expandir o array de apresentações de modo a este se tornar infinito
     * @param session array antigo que se quer expandir
     * @return 
     */
    private ConcretoPresentation[] expandPresentation(ConcretoPresentation[] concretoPresentation) {
        ConcretoPresentation[] presentation_temp = new ConcretoPresentation[this.concretoPresentation.length * 2];
        
        for (int i = 0 ; i < this.concretoPresentation.length ; i++) {
            presentation_temp[i] = concretoPresentation[i];
        }
        
        return presentation_temp;
    }
    

    /**
     * Método usado para retornar a posição no array da apreesntação
     * @param prsntn apresentação a ser procurada no array
     * @return i (posição no array) ou -1 caso a apresentação naão exista no array
     */
    private int find(Presentation prsntn){
        for(int i = 0; i<this.count;i++){
            if(this.concretoPresentation[i].equals(prsntn)){
                return i;
            } 
        }
        return -1;
    }
    
    /**
     * Método usado para verificar se existe alguma apresentação com o id passado
     * como argumento 
     * @param id id a verificar se já existe em alguma apresentação
     * @return i (posição no array) se existe algum apresentação com o id passado 
     * como argumento ou -1 se não existe nenhuma apresentação com esse id
     */
    private int findId(int id) {
        for (int i = 0 ; i < this.count ; i++) {
            if (this.concretoPresentation[i].getId() == id) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Formação da string referente à sessão
     * @return a string com os dados da sessão
     */
    @Override
    public String toString() {
        return "ConcretoSession{" + "id=" + id + ", name=" + name + ", concretoPresentation=" + concretoPresentation + ", startTime=" + startTime + ", theme=" + theme + ", concretoRoom=" + concretoRoom + ", duration=" + duration + ", maxDuration=" + maxDuration + ", count=" + count + '}';
    }
    
    
    
    
}
