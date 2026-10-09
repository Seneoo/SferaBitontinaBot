package caldarola.giuseppe.telegram_bot;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class SferaBitontinaMain {

    public static void main(String[] args) {
        Properties configProperties = new Properties();
        
        try (InputStream inputStream = new FileInputStream("config.properties")) {
            configProperties.load(inputStream);
            
            String botUsername = configProperties.getProperty("bot_username");
            String botToken = configProperties.getProperty("bot_token");

            if (botUsername == null || botToken == null || botUsername.isEmpty() || botToken.isEmpty()) {
                System.err.println("Errore: bot_username o bot_token non configurati nel file config.properties");
                return;
            }

            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new SferaBitontinaBot());
            System.out.println("Bot avviato correttamente!");

        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file config.properties: " + e.getMessage());
        } catch (TelegramApiException e) {
            System.err.println("Errore durante l'avvio del bot: " + e.getMessage());
        }
    }
}
