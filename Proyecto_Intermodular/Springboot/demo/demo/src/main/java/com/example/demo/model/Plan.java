package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PLAN")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer idPlan;

    @Column(name = "tipo_plan", nullable = false, length = 20)
    private String tipoPlan;

    // Getters y setters
    public Integer getIdPlan() { return idPlan; }
    public void setIdPlan(Integer idPlan) { this.idPlan = idPlan; }
    public String getTipoPlan() { return tipoPlan; }
    public void setTipoPlan(String tipoPlan) { this.tipoPlan = tipoPlan; }
}