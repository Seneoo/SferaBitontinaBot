package caldarola.giuseppe.telegram_bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class SferaBitontinaBot extends TelegramLongPollingBot {

    private String botUsername;
    private String botToken;

    public SferaBitontinaBot() {
        Properties prop=new Properties();
        try(InputStream input=new FileInputStream("config.properties")) {
            prop.load(input);
            this.botUsername=prop.getProperty("bot_username");
            this.botToken=prop.getProperty("bot_token");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String msg=update.getMessage().getText();
            String chatId=update.getMessage().getChatId().toString();

            User user=update.getMessage().getFrom();
            String nome="";
            String cognome="";
            if(user.getFirstName() != null) {
                nome=user.getFirstName();
            }
            if(user.getLastName() != null) {
                cognome=user.getLastName();
            }
            String utente=(nome + " " + cognome).trim();
            if(utente.isEmpty()) {
                utente="Sconosciuto";
            }
            DateTimeFormatter dtf=DateTimeFormatter.ofPattern("HH:mm:ss");
            String ora=LocalDateTime.now().format(dtf);

            System.out.println("[" + ora + "] Utente: " + utente + " | ChatID: " + chatId + " | Comando: " + msg);

            if(msg.startsWith("/start")) {
                sendMessage(chatId, "Uè! Ciao! Sónne 'a Sfera Bitontina. Farme 'na domànne o usa i comandi!");
            }
            else if(msg.startsWith("/orario")) {
                LocalTime now=LocalTime.now();
                String reply="Sónne e " + now.getHour() + " e " + now.getMinute() + " minuét!";
                sendMessage(chatId, reply);
            }
            else if(msg.startsWith("/sfera")) {
                String[] risposte={"Sì, assè!", "Mànghe p' 'a càpe!", "Domànne a màmmte!", "Ma va cùrchete!", "A campà non è ffàcele!", "Àcque e fùche!", "Sì càpe tòste!", "Pàcc' d' séule!"}; 
                Random r=new Random();
                sendMessage(chatId, risposte[r.nextInt(risposte.length)]);
            }
            else if(msg.startsWith("/proverbio")) {
                String[] proverbi = {
                        "Se Vëtónde tenéve 'u pórte, Bàre ère mórte.",
                        "A lavà 'a càpe 'u ciúcce se pèrde l'àcqua e 'u sapóne.",
                        "Ccéste va facènne e ccéste va truènn.",
                        "Dìmme a ce sì fìggie, ca te dìgghe a ce ràssemigghe.",
                        "Ce téine pìcche s'àcchendènde, ce téine àsseie se làmende."
                    };
                Random r=new Random();
                sendMessage(chatId, proverbi[r.nextInt(proverbi.length)]);
            }
            else if(msg.startsWith("/crediti")) {
                String crediti = "Sfera Bitontina Bot\n\n"
                    + "Creato da: Giuseppe Caldarola (classe 2008)\n"
                    + "Scuola: IISS Volta - De Gemmis (Bitonto)\n"
                    + "Progetto scolastico del: 09/10/2026";
                sendMessage(chatId, crediti);
            }
            else {
                sendMessage(chatId, "Puoi utilizzare solo i seguenti comandi:\n /sfera, /orario, /proverbio o /crediti. \nPàcc' d' séule!");
            }
        }
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    private void sendMessage(final String chatId, final String replyMessage) {
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(replyMessage);
        try {
            execute(sendMessage);
        } catch(TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
