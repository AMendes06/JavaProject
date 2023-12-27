/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;
import estg.ipp.pt.tp02_conferencesystem.interfaces.Participant;



/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
abstract class ConcretoParticipant implements Participant{

    /**
     * id único de cada participante
     */
    private int id;
    
     /**
     * nome de cada participante
     */
    private String name;
    
    /**
     * pequena descrição sobre participant
     */
    private String bio;
    
    /**
     * tipo de participante
     */
    private ParticipantType tipoParticipante;
    

    /**
     * Método construtor da classe ConcretoParticipant
     * @param id id único de cada participante
     * @param name nome de cada participante
     * @param bio pequena descrição sobre participant
     * @param tipoParticipante Tipo do participante
     */
    public ConcretoParticipant(int id, String name, String bio, ParticipantType tipoParticipante) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.tipoParticipante = tipoParticipante;
    }

    /**
     * Getter para o tipo de participante
     * @return o tipo de participante
     */
    public ParticipantType getTipoParticipante() {
        return tipoParticipante;
    }

    /**
     * @return o id do partipante 
     */
    @Override
    public int getId() {
        return this.id;
    }

    
    /**
     * @return o nome do participante 
     */
    @Override
    public String getName() {
        return this.name;
    }

    
    /** 
     * @return Bio do participante 
     */
    @Override
    public String getBio() {
        return this.bio;
    }
    
    
    
}
