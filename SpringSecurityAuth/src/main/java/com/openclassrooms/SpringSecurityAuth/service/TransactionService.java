package com.openclassrooms.SpringSecurityAuth.service;

import com.openclassrooms.SpringSecurityAuth.model.Account;
import com.openclassrooms.SpringSecurityAuth.model.Transaction;
import com.openclassrooms.SpringSecurityAuth.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public List<Transaction> getSentTransactions(Integer id){
        Optional<List<Transaction>> optTransactions = transactionRepository.findBySenderAccountId(id);

        optTransactions.orElseThrow(() -> new UsernameNotFoundException("No sent transactions for account id: " + id));

        List<Transaction> transactions = optTransactions.get();

        return transactions;

    }

    @Transactional
    public List<Transaction> getReceivedTransactions(Integer id){
        Optional<List<Transaction>> optTransactions = transactionRepository.findByReceiverAccountId(id);

        optTransactions.orElseThrow(() -> new UsernameNotFoundException("No received transactions for account id: " + id));

        List<Transaction> transactions = optTransactions.get();

        return transactions;

    }

    @Transactional
    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }

    public Float getBalance(Account account){
        Float balance = new Float(0);
        List<Transaction> sentTransactions = getSentTransactions(account.getId());
        List<Transaction> receivedTransactions = getReceivedTransactions(account.getId());

        Float sumSent = sentTransactions.stream().map(transaction -> transaction.getAmount()).reduce((float) 0, (a, b) -> a + b);
        Float sumReceived = receivedTransactions.stream().map(transaction -> transaction.getAmount()).reduce((float) 0, (a, b) -> a + b);

        balance = sumReceived - sumSent ;
        return balance;
    }

}
