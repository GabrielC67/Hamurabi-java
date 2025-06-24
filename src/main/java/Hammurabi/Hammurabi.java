package Hammurabi;
import java.util.*;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        new Hammurabi().playGame();
        Hammurabi ham = new Hammurabi();
    }

    void playGame() {

        int population = 100;
        int bushels = 2800;
        int acresOwned = 1000;
        int price = 19;
        int year = 1;
        int peopleStarved = 0;
        int immigration = 5;
        int plagueDeaths = 0;
        int bushelsHarvested = 3000;
        int bushelsDestroyedByRats = 200;
        int bushelsFedToPeople = 0;
        int totalPeopleStarved = 0;
        int acresPerPerson = (acresOwned / population);

        while (year <= 10) {
            printSummary(year, peopleStarved, immigration, population, bushelsHarvested, bushelsDestroyedByRats, bushels, acresOwned, price);

            //Purchase or Sale of Acres
            //This will allow user to either input a purchase or a sale. But, not both. One or the other happens.
            int acresBought = askHowManyAcresToBuy(price, bushels);
            int acresSold;
            int acresPlanted;
            if (acresBought > 0) {
                acresOwned += acresBought;
                bushels -= (acresBought * price);
                getSanityCheck(bushels);
            } else {
                //Acres sold
                acresSold = askHowManyAcresToSell(acresOwned);
                acresOwned -= acresSold;
                bushels += acresSold * price;
                getSanityCheck(bushels);
            }


            //Grain to feed people
            bushelsFedToPeople = askHowMuchGrainToFeedPeople(bushels);
            if(bushelsFedToPeople > 0) {
                bushels = bushels - bushelsFedToPeople;
                getSanityCheck(bushels);
            }

            //People starved
            peopleStarved = starvationDeaths(population, bushelsFedToPeople);//Result of bushels fed to people
            totalPeopleStarved += peopleStarved;
            //Will there be an Uprising???
            if (uprising(population, peopleStarved)) {
                System.out.println("\nMany people have starved O great Hammurabi and the people are having" +
                        " an uprising!! You have been removed from the office!");
                System.exit(0);
            } else if ((!uprising(population, peopleStarved)) && peopleStarved > Math.round((double) population * 0.15)) {
                System.out.println("\nO great Hammurabi, people are starving in the city. People are not " +
                        "going to want to come and live in our city!\n");
            }
            //Acres to plant
            acresPlanted = askHowManyAcresToPlant(acresOwned, population, bushels);
            if (acresPlanted > 0) {
                bushels = bushels - (acresPlanted * 2); // Cost of Planting Acres

                //Result of the Harvest
                bushelsHarvested = harvest(acresPlanted, (acresPlanted * 2));
                bushels = bushels + bushelsHarvested;
                getSanityCheck(bushels);
            }

            //Plague
            plagueDeaths = plagueDeaths(population);
            if (plagueDeaths > 0) {
                population -= plagueDeaths;
                System.out.println("Oh No!! A plague has happened and half of the population has died!! You now have " +
                        population + " people left as a result O Great One!!\n");
            }

            immigration = immigrants(population, acresOwned, bushels);
            if (peopleStarved > 0) {
                population -= peopleStarved;
                immigration = 0;
            }

            if (immigration > 0){
                population += immigration;
            }

            bushelsDestroyedByRats = grainEatenByRats(bushels);
            if(bushelsDestroyedByRats > 0){
                bushels -= bushelsDestroyedByRats;
                System.out.println("Oh no!! There is a rat infestation!! As a result, " + bushelsDestroyedByRats + " bushels were eaten!" +
                        " You now have " + bushels + " bushels remaining in storage at this time.\n");

            }
            year++;
            price = newCostOfLand();
        }
        finalSummary(year, peopleStarved, acresPerPerson);
    }


    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                int input = scanner.nextInt();
                if (input < 0) {
                    System.out.println("Invalid input! Please enter a non-negative number.");
                } else {
                    return input;
                }
            } catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number! Please enter a valid number.");
            }
        }
    }

    public int askHowManyAcresToBuy(int price, int bushels) {
        //User Input
        int buy = getNumber("\nO great Hammurabi, how many acres shall you buy?\n");
        //Calculated Bushels after purchase

        while (true) {
            //Program checks if user inputted more than 0
            if (buy * price > bushels) {
                System.out.println("\nO Great one, There's not enough bushels to purchase the amount of land you want!\n");
                buy = getNumber("Please input a lower amount of acres to purchase.\n");
                continue;
            } else {
                return buy;
            }
        }
    }

    public int askHowManyAcresToSell(int acresOwned) {
        //User Input
        int sell = getNumber("\nO great Hammurabi, how many acres shall you sell?\n");

        while (true) {
            if (sell > acresOwned) {
                System.out.println("\nO Great One, Surely you don't own that many acres to sell off!\n");
                sell = getNumber("Please enter another amount of acres to sell.\n");
                continue;
            } else {
                //Return User Input assuming all conditions are true. This will add to bushels Hammurabi will have.
                return sell;
            }
        }
    }


    public int askHowMuchGrainToFeedPeople(int bushels) {
        int feedPeople = getNumber("\nO great Hammurabi, how many grains of bushels shall you feed the people?\n");;

        //Difference between what user inputs and what's left as a result
        while (true) {
            //Must make sure bushels remaining does not go below 0. Even though the Emperor will be obviously overthrown.
            if (bushels < feedPeople) {
                System.out.println("\nYou don't have that many bushels to feed people.\n");
                feedPeople = getNumber("Please input a more reasonable number.\n");
                continue;
            } else {
                //This will return what the user inputs, then will compute the initialized variable.
                return feedPeople;
            }
        }
    }

    public int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        int acresToPlant = getNumber("\nO great Hammurabi, how many acres of land will you plant?\n");

        while (true) {
            if (acresToPlant > 0) {
                //This checks if there's enough acres to plant.
                if (acresToPlant <= acresOwned) {
                    //This checks if there's enough people to do the job
                    if ((population * 10) >= acresToPlant) {
                        //I think I need to make a Map of people that have a range of 0-10 to be able to plant acres based on what user can input.
                        Map<Integer, Integer> individualPersonList = new HashMap<>();

                        //This will Iterate through each person and finds out how many acres each person in the population is able to plant.
                        int personID = 1;
                        while (personID <= population) {
                            int acresThatPersonPlants = rand.nextInt(11);
                            individualPersonList.put(personID, acresThatPersonPlants);
                            //After one person is added to the list
                            personID++;
                        }

                        //This sums up the total acres farmed from each person.
                        int sumOfAcresFarmed = 0;
                        for (int numberOfAcresProduced : individualPersonList.values()) {
                            sumOfAcresFarmed += numberOfAcresProduced;
                        }

                            /*If the sum of acres produced surpasses or is equal to what user inputs, AND there's the bushels to do the work
            1                then we can return what the user inputs without errors. Both conditions must be true*/
                        int bushelsUsed = sumOfAcresFarmed * 2;
                        if (acresToPlant >= sumOfAcresFarmed) {
                            if (bushels >= bushelsUsed && bushels > 0) {
                                //This would mean I need to subtract what is returned * 2 for the bushels AND, I need to add to acres.
                                return sumOfAcresFarmed;
                            }
                            else {
                                System.out.println("O great one, there's not enough bushels to do this job.\n");
                                acresToPlant = getNumber("Please enter another amount.\n");
                                continue;
                            }
                        } else {
                            return acresToPlant;
                        }
                    } else {
                        System.out.println("O great Hammurabi, there's not enough people to harvest the acres you request.\n");
                        acresToPlant = getNumber("Please enter another amount.\n");
                        continue;
                    }
                } else {
                    System.out.println("There's not enough acres O great Hammurabi.\n");
                    acresToPlant = getNumber("Please enter another amount.\n");

                }
            } else {
                return 0;
            }
        }
    }


    public int plagueDeaths(int population) {
        boolean plague = (rand.nextInt(100) < 15);
//        System.out.println("\n Debugging plague: " + plague);
        if (plague) {
            return (population / 2);
        }
        return 0;
    }

    public int starvationDeaths(int population, int bushelsFedToPeople) {
        int bushelsNeeded = population * 20;
        if (bushelsFedToPeople >= bushelsNeeded) {
            return 0;
        }
        //In this case, for the test to pass, I need to change the equation to a double first, then (type) cast it back to an int.
        int peopleStarved = (int) Math.ceil((bushelsNeeded - bushelsFedToPeople) / 20.0);
        return peopleStarved;
    }

    public boolean uprising(int population, int howManyPeopleStarved) {
        if (howManyPeopleStarved > Math.round((double) population * 0.45)) {
            return true;
        }
        return false;
    }

    public int immigrants(int population, int acresOwned, int grainInStorage) {
        int immigration = ((20 * acresOwned) + grainInStorage) / (100 * population) + 1;
        return immigration;
    }

    public int harvest(int acres, int bushelsUsedAsSeed) {
        int min = 1, max = 6;
        bushelsUsedAsSeed = (rand.nextInt(max - min + 1) + min) * acres;
        return bushelsUsedAsSeed;
    }

    public int grainEatenByRats(int bushels) {
        boolean ratInfestation = false;
        ratInfestation = (rand.nextInt(100) < 40);
//        System.out.println("\n Debugging rat infestation: " + ratInfestation + "\n");
        if (ratInfestation) {
            int min = 10;
            int max = 30;
            double percentageBushelsEaten = (rand.nextInt(max - min + 1) + min) / 100.0;
            return (int) ((percentageBushelsEaten) * bushels);
        }
        return 0;
    }

    public int newCostOfLand() {
        //Test passed.
        int min = 17, max = 23;
        int newPrice = rand.nextInt(max - min + 1) + min;
        return newPrice;
    }

    void printSummary(int year, int peopleStarved, int immigration, int population, int bushelsHarvested, int bushelsDestroyedByRats, int bushels, int acresOwned, int price) {
        System.out.println("O great Hammurabi!\n" +
                "You are in year " + year + " of your ten year rule.\n" +
                "In the previous year " + peopleStarved + " people starved to death.\n" +
                "In the previous year " + immigration + " people entered the kingdom.\n" +
                "The population is now " + population + ".\n" +
                "We harvested " + bushelsHarvested + " bushels at 2 bushels per acre.\n" +
                "Rats destroyed " + bushelsDestroyedByRats + " bushels, leaving " + bushels + " bushels in storage.\n" +
                "The city owns " + acresOwned + " acres of land.\n" +
                "Land is currently worth " + price + " bushels per acre.");
    }

    void finalSummary(int year, int totalOfPeopleStarved, int acresPerPerson) {
        StringBuilder sb = new StringBuilder();
        sb.append("O Great Hammurabi, your 10 year rule is complete! As a result of your rule, " +
                totalOfPeopleStarved + " have starved. \n");
        if (totalOfPeopleStarved > 0){
            if (totalOfPeopleStarved <= 25){
                sb.append("Not bad O Great One! You have ruled with excellency! ");
            }
            else if (totalOfPeopleStarved <= 50){
                sb.append("You ruling was mediocre. Many people starved! ");
            } else {
                sb.append("Your rule was a disaster! You are exiled!");
                System.exit(0);
            }
        } else {
            sb.append("You have done excellent your majesty! ");
        }

        sb.append("\nAnd your acres of your population was ").append(acresPerPerson).append(". ");

        if (acresPerPerson <= 10){
            sb.append("Excellent work O great one! ");
        } else if (acresPerPerson <= 7) {
            sb.append("Good work. However, this could have been better O Great One! ");
        } else if (acresPerPerson <= 3) {
            sb.append("This was not good O Great One. Our city has declined! You are EXILED! ");
            System.exit(0);
        }

        sb.append("\nThank you for your rule O Great Hammurabi!");

        System.out.println(sb);
    }


    void getSanityCheck(int bushelsLeft) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nO Great Hammurabi, surely you jest! We have " + bushelsLeft + " bushels remaining!\n");
        if (bushelsLeft <= 0){
            sb.append("You have poorly managed our inventory Hammurabi! You are EXILED!");
            System.out.println(sb);
            System.exit(0);
        } else if (bushelsLeft <= 1350){
            sb.append("\nWe don't have many bushels remaining O Great One! Please be cautious of your decisions!\n");
        }
        System.out.println(sb);
    }
}