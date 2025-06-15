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
        int year = 0;

        while (year <= 10){

        }
    }


    int getNumber(String message) {
        while (true) {
            System.out.print(message);
            try {
                return scanner.nextInt();
            }
            catch (InputMismatchException e) {
                System.out.println("\"" + scanner.next() + "\" isn't a number!");
            }
        }
    }

    int askHowManyAcresToBuy(int price, int bushels) {
        //User Input
        int buy = getNumber("O great Hammurabi, how many acres shall you buy?");

        //Calculated Bushels after purchase
        int bushelsRemaining = bushels - (price * buy);

        //Program checks if user inputted more than 0
        if (buy > 0) {
            if (bushelsRemaining < 0) {
                throw new ArithmeticException("Remaining bushels cannot be lower than zero!!");
            } else {
                //Returns what the user inputted. Then will affect the bushels left and in
                return buy;
            }
        }
        //If user inputs 0, then return 0
        return 0;
    }

    int askHowManyAcresToSell(int acresOwned){
        //User Input
        int sell = getNumber("O great Hammurabi, how many acres shall you sell?");

        int acresRemaining = acresOwned - sell;
        if (sell > 0) {
            if (acresRemaining <= 0) {
                throw new ArithmeticException("We cannot sell all of our land O great one!");
            } else {
                //Return User Input assuming all conditions are true. This will add to bushels Hammurabi will have.
                return sell;
            }
        }
        return 0;
    }

    int askHowMuchGrainToFeedPeople(int bushels){
        int feedPeople = getNumber("O great Hammurabi, how many grains of bushels shall you feed the people?");

        //Difference between what user inputs and what's left as a result
        int bushelsRemaining = bushels - feedPeople;

        //Have to make sure user can't input less than 0 for bushels they want to feed the people
        if(feedPeople < 0){
            throw new ArithmeticException("You cannot input a negative number! Try again.");
        } else{
            //Must make sure bushels remaining does not go below 0. Even though the Emperor will be obviously overthrown.
            if (bushelsRemaining < 0){
                throw new ArithmeticException("You cannot have less than 0 bushels remaining! Try again!");
            }
            //This will return what the user inputs, then will compute the initialized variable.
            return feedPeople;
        }

    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        //User input
        int acresToPlant = getNumber("O great Hammurabi, how many acres of land will you plant?");

        //I think I need to make a Map of people that have a range of 0-10 to be able to plant acres based on what user can input.
        Map<Integer, Integer> individualPersonList = new HashMap<>();


        //This will Iterate through each person and finds out how many acres each person in the population is able to plant.
        int personID = 1;
        while(personID <= population){
            int acresThatPersonPlants = rand.nextInt(11);
            individualPersonList.put(personID, acresThatPersonPlants);
            //After one person is added to the list
            personID++;
        }

        //This sums up the total acres produced from each person.
        int sumOfAcresProducedFromEachPerson = 0;
        for(int numberOfAcresProduced : individualPersonList.values()) {
            sumOfAcresProducedFromEachPerson += numberOfAcresProduced;
        }

        /*If the sum of acres produced surpasses or is equal to what user inputs, AND there's the bushels to do the work
        then we can return what the user inputs without errors. Both conditions must be true*/
        if(acresToPlant <= sumOfAcresProducedFromEachPerson) {
            if (bushels >= (acresToPlant * 2)) {
                //This would mean I need to subract what is returned * 2 for the bushels AND, I need to add to acres.
                return acresToPlant;
            } else {
                //If there's not enough bushels for the job, then the job is not doable at all.
                return 0;
            }
        } else {
            //If the people cannot produce what Hammurabi demands, then return what the population could produce.
            return sumOfAcresProducedFromEachPerson;
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
        int min = 17, max = 23;
        int newPrice =  rand.nextInt(max - min + 1) + min;
        return newPrice;
    }

    void printSummary(){}

    void finalSummary(){}

}
