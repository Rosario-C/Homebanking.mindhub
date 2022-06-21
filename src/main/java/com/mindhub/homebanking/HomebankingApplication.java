package com.mindhub.homebanking;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CRÉDITO;
import static com.mindhub.homebanking.models.TransactionType.DÉBITO;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			// save a couple of Clients



			Client client1 = new Client("Melba","Morel","melba@mindhub.com", passwordEncoder.encode("erica1234"));
			client1.setFirstName("Erica");
			String name = client1.getFirstName();
			System.out.println(name);

			clientRepository.save(client1);


			Client client2 = new Client("Melba", "Carrizo", "carri@mindhub.com", passwordEncoder.encode("carri1234"));
			clientRepository.save(client2);

			Client client3 = new Client("Rosario", "Ceballos", "rosarioceballoscarril@hotmail.com", passwordEncoder.encode("keti1234"));
			clientRepository.save(client3);

			Client client4 = new Client("Melba", "Lorenzo", "melbaL@mindhub.com", passwordEncoder.encode("melba1234"));
			clientRepository.save(client4);



			Account account1 = new Account(client4,"VIN001", LocalDateTime.now(), 5000.05,true);
			accountRepository.save(account1);

			Account account2 = new Account(client4,"VIN002", LocalDateTime.now().plusDays(1),7500.67,true);
			accountRepository.save(account2);

			Account account3 = new Account(client3, "VIN003", LocalDateTime.now().minusDays(1),15000,true);
			accountRepository.save(account3);


			Transaction transaction1 = new Transaction(DÉBITO,-500.56, "Birrita", LocalDateTime.now(),account1,10);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(CRÉDITO,5100.56, "El mes que viene te paso el resto", LocalDateTime.now(),account1,500);
			transactionRepository.save(transaction2);

			Transaction transaction3 = new Transaction(DÉBITO, -1500.00,"La Criolla", LocalDateTime.now(), account2,200);
			transactionRepository.save(transaction3);

			Transaction transaction4 = new Transaction(CRÉDITO, 3000.43, "alpogo", LocalDateTime.now(), account2,20);
			transactionRepository.save(transaction4);



			Loan hipotecario = new Loan("Hipotecario", 500000, List.of(12, 24, 36,48,60));
			loanRepository.save(hipotecario);

			Loan personal = new Loan("Personal", 100000, List.of(6,12,24) );
			loanRepository.save(personal);

			Loan automotriz = new Loan("Automotriz", 300000, List.of(6,12,24,36) );
			loanRepository.save(automotriz);



			ClientLoan clientLoan1= new ClientLoan(400000, 60,client4,hipotecario);
			clientLoanRepository.save(clientLoan1);

			ClientLoan clientLoan2 = new ClientLoan(50000, 12, client4, personal);
			clientLoanRepository.save(clientLoan2);


			Card card1 = new Card(client4.getFirstName() + " " + client4.getLastName(), CardType.DÉBITO, CardColor.GOLDEN, "3352-6735-7478-4445", 990, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client4, false );
			cardRepository.save(card1);

			Card card2 = new Card(client4.getFirstName() + " " + client4.getLastName(), CardType.CRÉDITO, CardColor.SILVER, "2234-6745-5521-7888", 750, LocalDateTime.now(), LocalDateTime.now().plusYears(5),client4, false);
			cardRepository.save(card2);

			Card card3 = new Card(client3.getFirstName() +" "+ client3.getLastName(), CardType.CRÉDITO, CardColor.TITANIUM, "4533-8689-7639-2244", 454, LocalDateTime.now(), LocalDateTime.now().plusYears(6), client3, false );
			cardRepository.save(card3);

		};
	}
}
