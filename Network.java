/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                return users[i]; 
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
        if (userCount == users.length) return false;

        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                return false;
            }
        }
     users[userCount++] = new User(name);
    return true;
    }

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        if (name1 == null || name2 == null || name1.equals(name2)) {
        return false;
    }
        boolean name2Exists = false;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name2)) {
                name2Exists = true;
                break;
            }
        }
        if (!name2Exists) return false;

        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name1)) {
                return users[i].addFollowee(name2);
                
            }
        }
        return false;
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User mostRecommendedUser = null;
        int maxMutuals = -1; 
    
        User subject = null;
        for (int i = 0; i < userCount; i++) {
            if (users[i].getName().equals(name)) {
                subject = users[i];
                break;
            }
        }
    
        if (subject == null) {
            return null;
        }

        for (int i = 0; i < userCount; i++) {
            User candidate = users[i];
            if (candidate.getName().equals(name)) {
                continue;
            }
            int currentMutualCount = 0;
             for (int j = 0; j < userCount; j++) {
                String otherName = users[j].getName();
                if (subject.follows(otherName) && candidate.follows(otherName)) {
                    currentMutualCount++;
                }
            }


            if (currentMutualCount > maxMutuals) {
                maxMutuals = currentMutualCount;
                mostRecommendedUser = candidate;
            }
      }

        if (mostRecommendedUser != null) {
            return mostRecommendedUser.getName();
        }
        return null;
}

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        String popularName = null;
        int maxCount = -1;

        for (int i = 0; i < userCount; i++) {
            String candidateName = users[i].getName();
            int currentCount = followeeCount(candidateName);
        
        if (currentCount > maxCount) {
            maxCount = currentCount;
            popularName = candidateName;
        }
    }
        return popularName;
}

    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int currentCount = 0;
        for (int j = 0; j < userCount; j++) {
            if (users[j].follows(name)) {
                currentCount++;
            }
        }
        return currentCount;
    }

    // Returns a textual description of all the users in this network, and who they follow.
    public String toString() {
        String ans = "Network:";
        for (int i = 0; i < userCount; i++) {
         ans += "\n" + users[i]; }
        return ans;
    }
}
