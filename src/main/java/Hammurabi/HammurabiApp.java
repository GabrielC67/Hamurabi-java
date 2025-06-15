package Hammurabi;
import java.util.*;

public class HammurabiApp {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        new HammurabiApp().playGame();
    }

    void playGame(){
        int population = 100;
        int bushels = 2800;
        int acresOwned = 1000;
        int price = 19 ;
        int year = 1;
        int peopleStarved = 0;
        int immigration = 5;
        int plagueDeaths = 0;
        int bushelsHarvested = 3000;
        int bushelsDestroyedByRats = 200;

        while (year <= 10) {
            printSummary(year, peopleStarved, immigration, population, bushelsHarvested, bushelsDestroyedByRats, bushels, acresOwned, price);
            int acresBought = askHowManyAcresToBuy(price, bushels);
            int acresSold;

            //This will allow user to either input a purchase or a sale. But, not both. One or the other happens.
            if (acresBought > 0) {
                acresOwned += acresBought;
                bushels -= acresBought * price;
            } else {
                //Acres sold
                acresSold =  askHowManyAcresToSell(acresOwned);
                acresOwned -= acresSold;
                bushels += acresSold * price;
            }


//            askHowMuchGrainToFeedPeople(bushels);
//            askHowManyAcresToPlant(acresOwned, population, bushels);
            price = newCostOfLand();
            year++;
        }
    }


    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                int input = scanner.nextInt();
                 if (input < 0){
                     System.out.println("Invalid input! Please enter a non-negative number.");
                 } else {
                     return input;
                 }
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number! Please enter a valid number.");
            }
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        //User Input
        int buy;
        //Calculated Bushels after purchase

        while(true) {
            buy = getNumber("\nO great Hammurabi, how many acres shall you buy?\n");
            //Program checks if user inputted more than 0

            if (buy * price > bushels) {
                System.out.println("Not enough bushels! Please input a lower amount.\n");
            } else {
                return buy;
            }
        }
    }

    int askHowManyAcresToSell(int acresOwned){
        //User Input
        int sell;

        while (true) {
            sell = getNumber("O great Hammurabi, how many acres shall you sell?\n");
            if (sell > acresOwned) {
                System.out.println("\nYou don't own that many acres! Please enter another amount.\n");
            } else {
                //Return User Input assuming all conditions are true. This will add to bushels Hammurabi will have.
                return sell;
            }
        }
    }


    int askHowMuchGrainToFeedPeople(int bushels){
        int feedPeople = getNumber("O great Hammurabi, how many grains of bushels shall you feed the people?");

        //Difference between what user inputs and what's left as a result
        int bushelsRemaining = bushels - feedPeople;

            //Must make sure bushels remaining does not go below 0. Even though the Emperor will be obviously overthrown.
            if (bushelsRemaining < 0){
                throw new ArithmeticException("You cannot have less than 0 bushels remaining! Try again!");
            }
            //This will return what the user inputs, then will compute the initialized variable.
            return feedPeople;
        }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels) {
        //This loop is to allow the user to re-input another number should there be not enough bushels.
        while (true) {
            try {
                int acresToPlant = getNumber("O great Hammurabi, how many acres of land will you plant?");
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

                //This sums up the total acres produced from each person.
                int sumOfAcresProducedFromEachPerson = 0;
                for (int numberOfAcresProduced : individualPersonList.values()) {
                    sumOfAcresProducedFromEachPerson += numberOfAcresProduced;
                }

                /*If the sum of acres produced surpasses or is equal to what user inputs, AND there's the bushels to do the work
                then we can return what the user inputs without errors. Both conditions must be true*/
                if (acresToPlant <= sumOfAcresProducedFromEachPerson && acresToPlant <= acresOwned) {
                    if (bushels >= (sumOfAcresProducedFromEachPerson * 2)) {
                        //This would mean I need to subtract what is returned * 2 for the bushels AND, I need to add to acres.
                        return sumOfAcresProducedFromEachPerson;
                    }
                }
            } catch (ArithmeticException e) {
                System.out.println("There's not enough bushels to do this job! Please enter another number.");
            }
        }
    }


//    int plagueDeaths(int population)
//
//    int starvationDeaths(int population, int bushelsFedToPeople)
//
//    boolean uprising(int population, int howManyPeopleStarved)
//
//    int immigrants(int population, int acresOwned, int grainInStorage)

//    int harvest(int acres, int bushelsUsedAsSeed)

//    int grainEatenByRats(int bushels)

    public int newCostOfLand(){
        //Test passed.
        int min = 17, max = 23;
        int newPrice =  rand.nextInt(max - min + 1) + min;
        return newPrice;
    }

    void printSummary(int year, int peopleStarved, int immigration, int population, int bushelsHarvested, int bushelsDestroyedByRats, int bushels, int acresOwned, int price){
        System.out.println("O great Hammurabi!\n" +
                "You are in year " + year +  " of your ten year rule.\n" +
                "In the previous year " + peopleStarved + " people starved to death.\n" +
                "In the previous year " + immigration + " people entered the kingdom.\n" +
                "The population is now " + population + ".\n" +
                "We harvested " + bushelsHarvested + " bushels at 2 bushels per acre.\n" +
                "Rats destroyed " + bushelsDestroyedByRats + " bushels, leaving " + bushels + " bushels in storage.\n" +
                "The city owns " + acresOwned + " acres of land.\n" +
                "Land is currently worth " + price + " bushels per acre.");
    }

    void finalSummary(){}

}
