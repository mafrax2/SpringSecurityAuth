package com.openclassrooms.SpringSecurityAuth.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@DiscriminatorColumn(name="type",
        discriminatorType = DiscriminatorType.STRING)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "amount")
    private float amount;
    @Column(name="description")
    private String description;
    @Column(name = "emission_date")
    private Date emissionDate;

    @ManyToOne
    @JoinColumn(name="sender_id")
    private Account senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Account receiverAccount;
}


