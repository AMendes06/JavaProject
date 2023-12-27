/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;


/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class Professor extends ConcretoParticipant{
    
    /**
    * grau de escolaridade que esta habilitado
    */
    private String grauEscolaridade;
    
    /**
     * Topico que é expert
     */
    private String topico;


    /**
     * Método construtor da classe Professor
     * @param grauEscolaridadeque esta habilitado
     * @param topico que é expert
     * @param id id do professor
     * @param name nome do professor
     * @param bio biografia do professor
     * @param tipoParticipante Tipo de participante
     */
    public Professor(String grauEscolaridade, String topico, int id, String name, String bio, ParticipantType tipoParticipante) {    
        super(id, name, bio, tipoParticipante);
        this.grauEscolaridade = grauEscolaridade;
        this.topico = topico;
    }

    /**
     * Getter para o grau de escolaridade do professor
     * @return grau de escolaridade do professor 
     */
    public String getGrauescolaridade() {
        return grauEscolaridade;
    }
    
    /**
     * Setter para o grau de escolaridade do professor
     * @param grauEscolaridade novo grau de escolaridade do professor
     */
    public void setGrauEscolaridade(String grauEscolaridade) {
        this.grauEscolaridade = grauEscolaridade;
    }

    /**
     * Getter para o tópico que o professor é expert
     * @return o tópico em que o professor é expert 
     */
    public String getTopico() {
        return topico;
    }
    
    /**
     * Setter para o tópico em que o professor é expert
     * @param topico novo tópico em que o professor é expert 
     */
    public void setTopico(String topico) {
        this.topico = topico;
    } 
    
}
