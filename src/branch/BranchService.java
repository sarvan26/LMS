package branch;

import book.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BranchService {
    private List<Branch> branches;
    private static final Logger logger = Logger.getLogger(BranchService.class.getName());

    public BranchService() {
        this.branches = new ArrayList<>();
    }

    public boolean addBranch(Branch branch) {
        if (branches.contains(branch)) {
            logger.warning("Branch with ID " + branch.getId() + " already exists.");
            return false;
        }
        branches.add(branch);
        logger.info("Added branch: " + branch);
        return true;
    }

    public boolean updateBranch(Branch updatedBranch) {
        for (int i = 0; i < branches.size(); i++) {
            if (branches.get(i).getId().equals(updatedBranch.getId())) {
                branches.set(i, updatedBranch);
                logger.info("Updated branch: " + updatedBranch);
                return true;
            }
        }
        logger.warning("Branch with ID " + updatedBranch.getId() + " not found.");
        return false;
    }

    public Branch findBranchById(String id) {
        return branches.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Branch findBranchByName(String name) {
        return branches.stream()
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    public List<Branch> getAllBranches() {
        return new ArrayList<>(branches);
    }

    public boolean transferBook(String isbn, String sourceBranchId, String targetBranchId) {
        Branch sourceBranch = findBranchById(sourceBranchId);
        Branch targetBranch = findBranchById(targetBranchId);

        if (sourceBranch == null || targetBranch == null) {
            logger.warning("Source or target branch not found.");
            return false;
        }

        Book bookToTransfer = sourceBranch.getBooks().stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        if (bookToTransfer == null) {
            logger.warning("Book with ISBN " + isbn + " not found in branch " + sourceBranch.getName());
            return false;
        }

        if (!bookToTransfer.isAvailable()) {
            logger.warning("Book with ISBN " + isbn + " is currently checked out and cannot be transferred.");
            return false;
        }

        sourceBranch.removeBook(bookToTransfer);
        targetBranch.addBook(bookToTransfer);

        logger.info("Transferred book " + bookToTransfer.getTitle() + " from " +
                sourceBranch.getName() + " to " + targetBranch.getName());
        return true;
    }
}