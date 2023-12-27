/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import estg.ipp.pt.tp02_conferencesystem.enumerations.PresentationState;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Presentation;

/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConcretoPresentation implements Presentation{

    /**
     * id de cada apresentaçao
     */
    private int id;
    
    
    /**
     * titulo de cada apresentação
     */
    private String title;
    
    
    /**
     * duração de cada apresentação
     */
    private int duration;
    
    
    /**
     * Estado de cada apresentação
     */ 
    private PresentationState presentationState;
    
    /**
     * apresentador de cada apresentação
     */
    private ConcretoParticipant presenter;
    
    /**
     * Método construtor da classe ConcretoPresentation
     * @param id id identifica cada apresentação
     * @param title titulo de cada apresentação
     * @param duration duração de cada apresentação
     * @param presentationState  Estado de cada apresentação
     * @param presenter apresentador de cada apreentação
     */
    public ConcretoPresentation(int id, String title, int duration, PresentationState presentationState, ConcretoParticipant presenter ) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.presentationState = presentationState;
        this.presenter = presenter;
    }

    
    /**
     * Método que serve para caso existam duas instâncias iguais, eles consigam ser distinguidos
     * umas vez que o hashcode é diferente
     * @return um hash code
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.id;
        return hash;
    }

    
    /**
     * Método usado para comparar dois objetos
     * @param obj objeto a ser comparado
     * @return true se for igual, false se for nulo ou diferente
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConcretoPresentation other = (ConcretoPresentation) obj;
        return this.id == other.id;
    }


    /**
     * Getter para o id da apresentação
     * @return id da apresentação
     */
    @Override
    public int getId() {
       return this.id;
    }
    
    
    /** 
     * Getter para o título da apresentação
     * @return o titulo da apresentação 
     */
    @Override
    public String getTitle() {
       return this.title;
    }
    
    /**
     * verifica a duração da apresentação
     * @return a duração de uma apresentação
     */

    @Override
    public int getDuration() {
        if(this.duration > 15 && this.duration <= 30){
            return this.duration;
        }
        return 30;
    }

    /**
     * Getter para o estado da apresentação
     * @return o estado de apresentação  
     */
    @Override
    public PresentationState getPresentationState() {
        return this.presentationState;
    }
    
    /**
     * Verifica se a apresentação esta NOT_PRESENTED, caso esteja ele muda para PRESENTED
     */
    @Override
    public void setPresented() {
        if(getPresentationState()== PresentationState.NOT_PRESENTED){
            this.presentationState = PresentationState.PRESENTED;
        }
    }

    
    /**
     * Getter para o apresentador da apresentação
     * @return o apresentador da apresentação 
     */
    @Override
    public Participant getPresenter() {
       return this.presenter;
    }

    
}
