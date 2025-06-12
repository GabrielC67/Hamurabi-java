package hammurabi;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Hammurabi {
    Random rand = new Random();
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args){
        new Hammurabi().playGame();
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
        int buy = getNumber("O great Hammurabi, how many acres shall you buy?");
        int bushelsRemaining = bushels - (price * buy);//Calculated Bushels after purchase
        if (buy > 0) {
            if (bushelsRemaining <= 0) {
                throw new ArithmeticException("Remaining bushels cannot be zero or lower!!");
            } else {
                return buy;
            }
        }
        return 0;
    }

    int askHowManyAcresToSell(int acresOwned){
        int sell = getNumber("O great Hammurabi, how many acres shall you sell?");
        int acresRemaining = acresOwned - sell;
        if (sell > 0) {
            if (acresRemaining <= 0) {
                throw new ArithmeticException("We cannot sell all of our land O great one!");
            } else {
                return sell;
            }
        }
        return 0;
    }

    int askHowMuchGrainToFeedPeople(int bushels){
        int feedPeople = getNumber("O great Hammurabi, how many grains of bushels shall you feed the people?");
    }

    int askHowManyAcresToPlant(int acresOwned, int population, int bushels){
        int acresToPlant = getNumber("O great Hammurabi, how many acres of land will you plant?");
    }

//    int plagueDeaths(int population)
//
//    int starvationDeaths(int population, int bushelsFedToPeople)
//
//    boolean uprising(int population, int howManyPeopleStarved)
//
//    int immigrants(int population, int acresOwned, int grainInStorage)

    int harvest(int acres, int bushelsUsedAsSeed)

//    int grainEatenByRats(int bushels)

    int newCostOfLand(){
        int min = 17, max = 23;
        int newPrice =  rand.nextInt((max - min + 1) + min);
        return newPrice;
    }

    void printSummary(){}

    void finalSummary(){}

}
