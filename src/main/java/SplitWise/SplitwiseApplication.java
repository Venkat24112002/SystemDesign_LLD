package SplitWise;

//enums

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum ExpenseSplitType {
    EQUAL,
    UNEQUAL,
    PERCENTAGE
}

//models

class User {
    public String userId;
    public String userName;
    private UserExpenseBalanceSheet userExpenseBalanceSheet;

    public User(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        userExpenseBalanceSheet = new UserExpenseBalanceSheet();
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public UserExpenseBalanceSheet getUserExpenseBalanceSheet() { return userExpenseBalanceSheet; }
}

class Balance{
    private double amountOwe;
    private double amountGetBack;

    public Balance(){
        amountOwe = 0;
        amountGetBack = 0;
    }

    public double getAmountOwe() { return amountOwe; }
    public void setAmountOwe(double amountOwe) { this.amountOwe = amountOwe; }
    public double getAmountGetBack() { return amountGetBack; }
    public void setAmountGetBack(double amountGetBack) { this.amountGetBack = amountGetBack; }
}

class UserExpenseBalanceSheet {
    private Map<String,Balance> userVsBalance;
    private double totalYourExpense;
    private double totalPayment;
    private double totalYouOwe;
    private double totalYouGetBack;

    public UserExpenseBalanceSheet(){
        userVsBalance = new HashMap<>();
        totalPayment = 0;
        totalYouGetBack = 0;
        totalYouOwe = 0;
        totalYourExpense =0;
    }

    public Map<String, Balance> getUserVsBalance() { return userVsBalance; }
    public double getTotalYourExpense() { return totalYourExpense; }
    public void setTotalYourExpense(double totalYourExpense) { this.totalYourExpense = totalYourExpense; }
    public double getTotalYouOwe() { return totalYouOwe; }
    public void setTotalYouOwe(double totalYouOwe) { this.totalYouOwe = totalYouOwe; }
    public double getTotalYouGetBack() { return totalYouGetBack; }
    public void setTotalYouGetBack(double totalYouGetBack) { this.totalYouGetBack = totalYouGetBack; }
    public double getTotalPayment() { return totalPayment; }
    public void setTotalPayment(double totalPayment) { this.totalPayment = totalPayment; }

}

class Split {
    private User user;
    private double amountOwe;

    public Split(User user, double amountOwe) {
        this.user = user;
        this.amountOwe = amountOwe;
    }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public double getAmountOwe() { return amountOwe; }
    public void setAmountOwe(double amountOwe) { this.amountOwe = amountOwe; }
}

class Expense {
    private String expenseId;
    private String description;
    private double expenseAmount;
    private User paidByUser;
    private ExpenseSplitType splitType;
    private List<Split> splitDetails;

    public Expense(String expenseId, double expenseAmount, String description,
                   User paidByUser, ExpenseSplitType splitType, List<Split> splitDetails) {
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.description = description;
        this.paidByUser = paidByUser;
        this.splitType = splitType;
        this.splitDetails = new ArrayList<>(splitDetails);
    }

    // Getters
    public String getExpenseId() { return expenseId; }
    public String getDescription() { return description; }
    public double getExpenseAmount() { return expenseAmount; }
    public User getPaidByUser() { return paidByUser; }
    public ExpenseSplitType getSplitType() { return splitType; }
    public List<Split> getSplitDetails() { return splitDetails; }
}

class Group {
    private String groupId;
    private String groupName;
    private List<User> groupMembers;
    private List<Expense> expenseList;
    private ExpenseController expenseController;

    public Group() {
        groupMembers = new ArrayList<>();
        expenseList = new ArrayList<>();
        expenseController = new ExpenseController();
    }

    public void addMember(User member) {
        groupMembers.add(member);
    }

    public String getGroupId() { return groupId; }
    public void setGroupId(String groupId) { this.groupId = groupId; }
    public String getGroupName() { return groupName; }
    public void setGroupName(String groupName) { this.groupName = groupName; }
    public List<User> getGroupMembers() { return groupMembers; }
    public List<Expense> getExpenseList() { return expenseList; }

    public Expense createExpense(String expenseId, String description, double expenseAmount, List<Split> splitDetails, ExpenseSplitType splitType,
                                 User paidByUser) {
        Expense expense = expenseController.createExpense(expenseId, description, expenseAmount, splitDetails, splitType, paidByUser);
        expenseList.add(expense);
        return expense;
    }
}

//Split strategies

interface ExpenseSplit {
    void validateSplitRequest(List<Split> splitList, double totalAmount);
}

class EqualExpenseSplit implements ExpenseSplit {
    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {
        double amountShouldBePresent = totalAmount / splitList.size();
        for (Split split : splitList) {
            if (Math.abs(split.getAmountOwe() - amountShouldBePresent) > 0.01) {
                throw new RuntimeException("Invalid equal split amounts");
            }
        }
    }
}

class UnequalExpenseSplit implements ExpenseSplit {
    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {
        double totalSplitAmount = 0;
        for (Split split : splitList) {
            totalSplitAmount += split.getAmountOwe();
        }
        if (Math.abs(totalSplitAmount - totalAmount) > 0.01) {
            throw new RuntimeException("Total split amount doesn't match expense amount");
        }
    }
}

class PercentageExpenseSplit implements ExpenseSplit {
    @Override
    public void validateSplitRequest(List<Split> splitList, double totalAmount) {
        double totalPercentage = 0;
        for (Split split : splitList) {
            totalPercentage += split.getAmountOwe(); // In percentage split, amountOwe represents percentage
        }
        if (Math.abs(totalPercentage - 100.0) > 0.01) {
            throw new RuntimeException("Total percentage should be 100%");
        }
    }
}

// split factory
class SplitFactory {
    public static ExpenseSplit getSplitObject(ExpenseSplitType splitType) {
        switch (splitType) {
            case EQUAL:
                return new EqualExpenseSplit();
            case UNEQUAL:
                return new UnequalExpenseSplit();
            case PERCENTAGE:
                return new PercentageExpenseSplit();
            default:
                return null;
        }
    }
}


//Controllers
class UserController {
    private List<User> userList;
    public UserController() {
        userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User getUser(String userId) {
        for (User user : userList) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userList;
    }
}

class GroupController {
    private List<Group> groupList;

    public GroupController() {
        groupList = new ArrayList<>();
    }

    public void createNewGroup(String groupId, String groupName, User createdByUser) {
        Group group = new Group();
        group.setGroupId(groupId);
        group.setGroupName(groupName);
        group.addMember(createdByUser);
        groupList.add(group);
    }

    public Group getGroup(String groupId) {
        for (Group group : groupList) {
            if (group.getGroupId().equals(groupId)) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getAllGroups() {
        return groupList;
    }
}

class ExpenseController {
    private BalanceSheetController balanceSheetController;

    public ExpenseController() {
        balanceSheetController = new BalanceSheetController();
    }

    public Expense createExpense(String expenseId, String description, double expenseAmount,
                                 List<Split> splitDetails, ExpenseSplitType splitType, User paidByUser) {

        ExpenseSplit expenseSplit = SplitFactory.getSplitObject(splitType);
        expenseSplit.validateSplitRequest(splitDetails, expenseAmount);
        Expense expense = new Expense(expenseId, expenseAmount, description, paidByUser, splitType, splitDetails);
        balanceSheetController.updateExpenseBalanceSheet(paidByUser, splitDetails, expenseAmount);
        return expense;
    }
}

class BalanceSheetController {

    public void updateExpenseBalanceSheet(User expensePaidBy, List<Split> splitList, double expenseAmount) {
        UserExpenseBalanceSheet paidUserExpenseBalanceSheet = expensePaidBy.getUserExpenseBalanceSheet();
        paidUserExpenseBalanceSheet.setTotalPayment(paidUserExpenseBalanceSheet.getTotalPayment() + expenseAmount);

        for(Split split : splitList) {
            User user = split.getUser();
            double amount = split.getAmountOwe();
            UserExpenseBalanceSheet userExpenseBalanceSheet = user.getUserExpenseBalanceSheet();

            if(user.getUserId().equals(expensePaidBy.getUserId())){
                userExpenseBalanceSheet.setTotalYourExpense(userExpenseBalanceSheet.getTotalPayment()+amount);
            }
            else {
                //lets first update the paid one

                paidUserExpenseBalanceSheet.setTotalYouGetBack(paidUserExpenseBalanceSheet.getTotalYouGetBack() + amount);

                Balance balance;
                if(!paidUserExpenseBalanceSheet.getUserVsBalance().containsKey(user.getUserId())){
                    balance = new Balance();
                    paidUserExpenseBalanceSheet.getUserVsBalance().put(user.getUserId(),balance);
                    balance.setAmountGetBack(amount);
                }
                else {
                    balance = paidUserExpenseBalanceSheet.getUserVsBalance().get(user.getUserId());
                    balance.setAmountGetBack(balance.getAmountGetBack() + amount);
                }

                //now the current one which owes;
                userExpenseBalanceSheet.setTotalYourExpense(userExpenseBalanceSheet.getTotalYourExpense() + amount);
                userExpenseBalanceSheet.setTotalYouOwe(userExpenseBalanceSheet.getTotalYouOwe() + amount);

                if(!userExpenseBalanceSheet.getUserVsBalance().containsKey(expensePaidBy.getUserId())){
                    balance = new Balance();
                    balance.setAmountOwe(amount);
                    paidUserExpenseBalanceSheet.getUserVsBalance().put(expensePaidBy.getUserId(), balance);
                }
                else {
                    balance = userExpenseBalanceSheet.getUserVsBalance().get(expensePaidBy.getUserId());
                    balance.setAmountOwe(balance.getAmountOwe() + amount);
                }
            }

        }
    }

    public void showBalanceSheetOfUser(User user) {
        System.out.println("---------------------------------------");
        System.out.println("Balance sheet of user: " + user.getUserId());

        UserExpenseBalanceSheet userExpenseBalanceSheet = user.getUserExpenseBalanceSheet();

        System.out.println("TotalYourExpense: " + userExpenseBalanceSheet.getTotalYourExpense());
        System.out.println("TotalGetBack: " + userExpenseBalanceSheet.getTotalYouGetBack());
        System.out.println("TotalYouOwe: " + userExpenseBalanceSheet.getTotalYouOwe());
        System.out.println("TotalPaymentMade: " + userExpenseBalanceSheet.getTotalPayment());

        for (Map.Entry<String, Balance> entry : userExpenseBalanceSheet.getUserVsBalance().entrySet()) {
            String userId = entry.getKey();
            Balance balance = entry.getValue();
            System.out.println("UserID: " + userId + " YouGetBack: " + balance.getAmountGetBack() + " YouOwe: " + balance.getAmountOwe());
        }

        System.out.println("---------------------------------------");
    }

}

class Splitwise {
    private UserController userController;
    private GroupController groupController;
    private BalanceSheetController balanceSheetController;

    public Splitwise() {
        userController = new UserController();
        groupController = new GroupController();
        balanceSheetController = new BalanceSheetController();
    }

    public void demo() {
        setupUserAndGroup();

        // Step 1: Add members to the group
        Group group = groupController.getGroup("G1001");
        group.addMember(userController.getUser("U2001"));
        group.addMember(userController.getUser("U3001"));

        // Step 2: Create an equal expense
        List<Split> splits = new ArrayList<>();
        splits.add(new Split(userController.getUser("U1001"), 300));
        splits.add(new Split(userController.getUser("U2001"), 300));
        splits.add(new Split(userController.getUser("U3001"), 300));

        group.createExpense("Exp1001", "Breakfast", 900, splits, ExpenseSplitType.EQUAL, userController.getUser("U1001"));

        // Step 3: Create an unequal expense
        List<Split> splits2 = new ArrayList<>();
        splits2.add(new Split(userController.getUser("U1001"), 400));
        splits2.add(new Split(userController.getUser("U2001"), 100));

        group.createExpense("Exp1002", "Lunch", 500, splits2, ExpenseSplitType.UNEQUAL, userController.getUser("U2001"));

        // Step 4: Show balance sheets for all users
        for (User user : userController.getAllUsers()) {
            balanceSheetController.showBalanceSheetOfUser(user);
        }
    }

    public void setupUserAndGroup() {
        // Onboard users to Splitwise app
        addUsersToSplitwiseApp();

        // Create a group by user1
        User user1 = userController.getUser("U1001");
        groupController.createNewGroup("G1001", "Outing with Friends", user1);
    }

    private void addUsersToSplitwiseApp() {
        User user1 = new User("U1001", "User1");
        User user2 = new User("U2001", "User2");
        User user3 = new User("U3001", "User3");

        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);
    }

    // Public getters for external access
    public UserController getUserController() { return userController; }
    public GroupController getGroupController() { return groupController; }
    public BalanceSheetController getBalanceSheetController() { return balanceSheetController; }


}

public class SplitwiseApplication {
    public static void main(String[] args) {
        Splitwise splitwise = new Splitwise();
        splitwise.demo();
    }
}
