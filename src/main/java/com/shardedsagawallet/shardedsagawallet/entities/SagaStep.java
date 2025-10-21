package com.shardedsagawallet.shardedsagawallet.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "saga_step")
public class SagaStep {
    //id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //saga instance id
    @Column(name = "saga_instance_id", nullable = false)
    private String sagaInstanceId;

    //stepName
    @Column(name = "step_name",nullable = false)
    private String stepName;

    //status
    @Column(name = "status",nullable = false)
    private StepStatus status;


    //error_message
    @Column(name = "error_message")
    private String errorMessage;

    //stepData
    @Column(name = "step_data",columnDefinition = "json")
    private String stepData;

    public void markAsCompensated() {
        this.status = StepStatus.COMPENSATED;
    }

    public void markAsFailed() {
        this.status = StepStatus.FAILED;
    }

    public void markAsPending() {
        this.status = StepStatus.PENDING;
    }

    public void markAsRunning() {
        this.status = StepStatus.RUNNING;
    }

    public void markAsCompleted() {
        this.status = StepStatus.COMPLETED;
    }

    public void markAsCompensating() {
        this.status = StepStatus.COMPENSATING;
    }

}
