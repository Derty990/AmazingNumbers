package numbers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class AmazingNumbers{

    private boolean isEven;
    private boolean isOdd;
    private boolean isBuzz;
    private boolean isDuck;
    private boolean isPalindromic;
    private boolean isGapful;
    private boolean isSpy;
    private boolean isSquare;
    private boolean isSunny;
    private boolean isJumping;
    private boolean isHappy;
    private boolean isSad;
    private String[] split;
    private long n;
    private final Map<String, Command> commands = new HashMap<>();
    private final List<String> possibleOperations = new ArrayList<>(List.of("BUZZ", "DUCK",
            "PALINDROMIC", "GAPFUL", "SPY", "EVEN", "ODD", "SQUARE", "SUNNY","JUMPING", "HAPPY", "SAD",

            "-BUZZ", "-DUCK",
            "-PALINDROMIC", "-GAPFUL", "-SPY", "-EVEN", "-ODD", "-SQUARE", "-SUNNY","-JUMPING", "-HAPPY", "-SAD"));

    interface Command {
        boolean runCommand();
    }

    {
        commands.put("BUZZ", this::buzzNumber);
        commands.put("DUCK", this::duckNumber);
        commands.put("PALINDROMIC", this::palindrom);
        commands.put("GAPFUL", this::gapful);
        commands.put("SPY", this::spy);
        commands.put("EVEN", this::evenNumber);
        commands.put("ODD", this::oddNumber);
        commands.put("SQUARE", this::square);
        commands.put("SUNNY", this::sunny);
        commands.put("JUMPING", this::jumping);
        commands.put("HAPPY", this::happy);
        commands.put("SAD", this::sad);
    }

    {
        System.out.println("Welcome to Amazing Numbers!\n");
        printInstruction();
    }

    private  void printInstruction(){
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
                - a property preceded by minus must not be present in numbers;
                - separate the parameters with one space;
                - enter 0 to exit.""");
        System.out.println("\nEnter a request: ");
    }

    private void availableProperties(String property){
        System.out.printf("The property [%s] is wrong.\n" +
                "Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SQUARE, SUNNY, JUMPING, HAPPY, SAD]", property);
    }

    private void properties(){
        evenNumber();
        oddNumber();
        buzzNumber();
        duckNumber();
        palindrom();
        gapful();
        spy();
        square();
        sunny();
        jumping();
        happy();
        sad();
    }
    private boolean jumping(){
        String[] numbers = String.valueOf(n).split("");

        if(numbers.length == 1){
            isJumping = true;
            return isJumping;
        }
        for (int i = 1; i < numbers.length; i++) {
            if(Math.abs(Integer.parseInt(numbers[i]) - Integer.parseInt(numbers[i-1])) != 1){
                isJumping = false;
                return isJumping;
            }
        }
        isJumping = true;
        return isJumping;
    }

    private boolean spy(){
        int sum=0;
        int multiplication=1;

        for (int i = 0; i < split.length; i++) {
            sum+=Integer.parseInt(split[i]);
            multiplication*=Integer.parseInt(split[i]);
        }

        isSpy = sum == multiplication;
        return isSpy;
    }

    private boolean gapful(){
        if(split.length<3){
            isGapful = false;
            return isGapful;
        }
        String number = split[0]+split[split.length-1];
        int connected = Integer.parseInt(number);
        isGapful = n % connected == 0;
        return isGapful;
    }

    private boolean palindrom(){
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        StringBuilder rb = new StringBuilder();
        rb.append(n).reverse();
        isPalindromic = sb.compareTo(rb) == 0;
        return isPalindromic;
    }

    private boolean happy(){
        int[] parsedNumbers = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            parsedNumbers[i] = Integer.parseInt(split[i]);
        }

        List<Long> results = new ArrayList<>();
        long result;

        do{
            result = 0;
            for(Integer i : parsedNumbers){
                result += (long) i*i;
            }
            if(results.contains(result)){
                isHappy = false;
                return isHappy;
            }else{
                results.add(result);
            }
            String[] newSplit= String.valueOf(result).split("");
            parsedNumbers = new int[newSplit.length];
            for (int i = 0; i < newSplit.length; i++) {
                parsedNumbers[i] = Integer.parseInt(newSplit[i]);
            }
            //System.out.println(result);

        }while(result!=1);

        results.clear();

        isHappy = true;
        return isHappy;
    }

    private boolean sad(){
        isSad = !happy();
        return isSad;
    }

    private boolean evenNumber(){
        isEven = n % 2 == 0;
        return isEven;
    }
    private boolean oddNumber(){
        isOdd = n%2==1;
        return isOdd;
    }

    private boolean buzzNumber() {
        isBuzz = n % 7 == 0 || Long.parseLong(split[split.length - 1]) == 7;
        return isBuzz;
    }

    private boolean duckNumber(){
        for(String s : split){
            if(s.equals("0")) {
                isDuck = true;
                return isDuck;
            }
        }
        isDuck = false;
        return isDuck;
    }

    private boolean square(){
        isSquare = Math.sqrt(n) == Math.floor(Math.sqrt(n));
        return isSquare;
    }

    private boolean sunny(){
        isSunny = Math.sqrt(n+1) == Math.floor(Math.sqrt(n+1));
        return isSunny;
    }
    private boolean isOneNumberNotValid(String s) {
        return !s.matches("[1-9][0-9]*|(0)");
    }
    private boolean areTwoNumbersNotValid(String s, String s1) {
        if(isOneNumberNotValid(s)){
            System.out.println("The first parameter should be a natural number or zero.");
            System.out.println("\nEnter a request: ");
            return true;
        }
        if(isOneNumberNotValid(s1)){
            System.out.println("The second parameter should be a natural number or zero.");
            System.out.println("\nEnter a request: ");
            return true;
        }
        return false;
    }

    public boolean evaluate(String input) {
        String[] splitInput = input.split(" ");
        if(splitInput.length==1){
            if(isOneNumberNotValid(splitInput[0])){
                System.out.println("The first parameter should be a natural number or zero.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(splitInput[0].isEmpty()){
                printInstruction();
                return false;
            }
            n=Long.parseLong(splitInput[0]);
            if(n == 0){
                System.out.println("\nGoodbye!");
                return true;
            }
            else {
                split = String.valueOf(n).split("");
                properties();
                print();
                System.out.println("\nEnter a request: ");
            }
        }
        else if (splitInput.length==2) {
            if(areTwoNumbersNotValid(splitInput[0], splitInput[1])){
                return false;
            }
            long start=Long.parseLong(splitInput[0]);
            long end = Long.parseLong(splitInput[1]);
            for (long i = start; i <start+end; i++) {
                n=i;
                split = String.valueOf(n).split("");
                properties();
                printInLine();
            }
            System.out.println("\nEnter a request: ");
        }
        else if (splitInput.length > 2){
            if(areTwoNumbersNotValid(splitInput[0], splitInput[1])){
                return false;
            }
            long start=Long.parseLong(splitInput[0]);
            long end = Long.parseLong(splitInput[1]);
            List<String> allCommands = new ArrayList<>();
            int k=0;
            for (int i = 2; i < splitInput.length; i++) {
                if(!possibleOperations.contains(splitInput[i].toUpperCase())){
                    availableProperties(splitInput[i].toUpperCase());
                    System.out.println("\nEnter a request: ");
                    return false;
                }
                if(allCommands.contains("-"+splitInput[i].toUpperCase())||allCommands.contains(splitInput[i].substring(1).toUpperCase())){
                    System.out.println("The request contains mutually exclusive properties:\n" +
                            "There are no numbers with these properties.");
                    System.out.println("\nEnter a request: ");
                    return false;
                }
                else{
                    allCommands.add(splitInput[i].toUpperCase());
                }

            }
            if(allCommands.contains("EVEN")&&allCommands.contains("ODD")){
                System.out.println("The request contains mutually exclusive properties: [EVEN, ODD]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("SQUARE")&&allCommands.contains("SUNNY")){
                System.out.println("The request contains mutually exclusive properties: [SQUARE, SUNNY]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("DUCK")&&allCommands.contains("SPY")){
                System.out.println("The request contains mutually exclusive properties: [DUCK, SPY]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("SAD")&&allCommands.contains("HAPPY")){
                System.out.println("The request contains mutually exclusive properties: [HAPPY, SAD]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("-EVEN")&&allCommands.contains("-ODD")){
                System.out.println("The request contains mutually exclusive properties: [-EVEN, -ODD]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("-SQUARE")&&allCommands.contains("-SUNNY")){
                System.out.println("The request contains mutually exclusive properties: [-SQUARE, -SUNNY]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("-DUCK")&&allCommands.contains("-SPY")){
                System.out.println("The request contains mutually exclusive properties: [-DUCK, -SPY]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            if(allCommands.contains("-SAD")&&allCommands.contains("-HAPPY")){
                System.out.println("The request contains mutually exclusive properties: [-HAPPY, -SAD]\n" +
                        "There are no numbers with these properties.");
                System.out.println("\nEnter a request: ");
                return false;
            }
            int counter = 0;
            boolean isOk;
            while(counter<end){
                isOk=true;
                n=start++;
                split = String.valueOf(n).split("");

                for (int i = 2; i < splitInput.length; i++) {
                    String command = splitInput[i].toUpperCase();
                    if(command.startsWith("-")){
                        command=command.substring(1);
                        if(commands.get(command).runCommand()){
                            isOk=false;
                            break;
                        }
                    }
                    else{
                        if(!commands.get(command).runCommand()){
                            isOk=false;
                            break;
                        }
                    }
                }
                if(isOk){
                    properties();
                    printInLine();
                    counter++;
                }
            }
            System.out.println("\nEnter a request: ");
        }
        return false;
    }
    D:\Materiały na studia\Materiały na studia\Semestr IV\ProgramowanieJava\Amazing Numbers\Amazing Numbers\task\src\numbers
    private void printInLine() {
        StringBuilder sb = new StringBuilder();
        sb.append("\t\t\t");
        sb.append(n);
        sb.append(" is ");
        if(isBuzz)sb.append("buzz, ");
        if(isDuck)sb.append("duck, ");
        if(isPalindromic)sb.append("palindromic, ");
        if(isGapful)sb.append("gapful, ");
        if(isSpy)sb.append("spy, ");
        if(isSquare)sb.append("square, ");
        if(isSunny)sb.append("sunny, ");
        if(isJumping)sb.append("jumping, ");
        if(isHappy)sb.append("happy, ");
        if(isSad)sb.append("sad, ");
        sb.append(isOdd?"odd":"even");
        System.out.println(sb);
    }

    private void print(){
        System.out.println("Properties of "+n);
        System.out.println("\t\teven: "+isEven);
        System.out.println("\t\t odd: "+isOdd);
        System.out.println("\t\tbuzz: "+isBuzz);
        System.out.println("\t\tduck: "+isDuck);
        System.out.println(" palindromic: "+isPalindromic);
        System.out.println("\t  gapful: "+isGapful);
        System.out.println("\t\t spy: "+isSpy);
        System.out.println("\t  square: "+isSquare);
        System.out.println("\t   sunny: "+isSunny);
        System.out.println("\t jumping: "+isJumping);
        System.out.println("\t   happy: "+isHappy);
        System.out.println("\t\t sad: "+isSad);
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        AmazingNumbers amazingNumbers = new AmazingNumbers();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String n;
        do {
            n = br.readLine();
        }while(!amazingNumbers.evaluate(n));

    }
}