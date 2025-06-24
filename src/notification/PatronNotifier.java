package notification;

import java.util.logging.Logger;

public class PatronNotifier implements Observer {
    private static final Logger logger = Logger.getLogger(PatronNotifier.class.getName());

    @Override
    public void update(String message) {
        // In a real system, this would send an email or SMS
        logger.info("NOTIFICATION: " + message);
    }
}
