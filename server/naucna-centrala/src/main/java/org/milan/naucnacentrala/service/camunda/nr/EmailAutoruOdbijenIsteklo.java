package org.milan.naucnacentrala.service.camunda.nr;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.milan.naucnacentrala.model.NaucniRad;
import org.milan.naucnacentrala.model.User;
import org.milan.naucnacentrala.model.enums.Enums;
import org.milan.naucnacentrala.repository.INaucniRadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailAutoruOdbijenIsteklo implements JavaDelegate {

    @Autowired
    private Environment environment;

    @Autowired
    private INaucniRadRepository _nrRepo;


    @Autowired
    private JavaMailSender jmSender;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        System.out.println("[EmailAutoruOdbijenIsteklo]: start");

        int nrId = (int) delegateExecution.getVariable("nrId");

        NaucniRad nr = _nrRepo.findById(nrId).get();

        nr.setStatus(Enums.NaucniRadStatus.ODBIJEN);
        _nrRepo.save(nr);

        String messageBody =
                "Sa dubokim žaljenjem Vas obaveštavamo da je vaš naučni rad pod nazivom \"" + nr.getNaslov()
                        + "\" odbijen zbog isteka vremenskog roka za ispravku. Pokušajte ponovo.";
        sendEmail(nr.getAutor(), messageBody);

    }

    private void sendEmail(User u, String messageBody) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(u.getEmail());

        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("NC Obaveštenje: Vaš naučni rad je odbijen");


        mail.setText("Poštovani,"+
                "\n" + messageBody);

        jmSender.send(mail);
    }
}
