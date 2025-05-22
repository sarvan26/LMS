package patron;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PatronService {
    private List<Patron> patrons;
    private static final Logger logger = Logger.getLogger(PatronService.class.getName());

    public PatronService() {
        this.patrons = new ArrayList<>();
    }

    public boolean addPatron(Patron patron) {
        if (patrons.contains(patron)) {
            logger.warning("Patron with ID " + patron.getId() + " already exists.");
            return false;
        }
        patrons.add(patron);
        logger.info("Added patron: " + patron);
        return true;
    }

    public boolean updatePatron(Patron updatedPatron) {
        for (int i = 0; i < patrons.size(); i++) {
            if (patrons.get(i).getId().equals(updatedPatron.getId())) {
                patrons.set(i, updatedPatron);
                logger.info("Updated patron: " + updatedPatron);
                return true;
            }
        }
        logger.warning("Patron with ID " + updatedPatron.getId() + " not found.");
        return false;
    }

    public Patron findPatronById(String id) {
        return patrons.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Patron> getAllPatrons() {
        return new ArrayList<>(patrons);
    }
}
