import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printCron(args);

        System.out.println("Run tests? y/n: ");
        if(scanner.nextLine().equals("y")) {
            System.out.println("Tests:");
            printCron("5 0 * 8 * /var/lib/jenkins".split(" "));
            printCron("0 22 5-8 * 1-5 mkdir -p Music/2020/Songs".split(" "));
            printCron("23 0-20/2 7,20-30/3 * 2,4-6 touch /home/username/Documents/Web.html".split(" "));
            printCron("*/5 0,12 1 */2 * find -name filename.txt".split(" "));
        }
    }

    static void printCron(String[] args){
        System.out.println("input: " + Arrays.toString(args));
        System.out.print("minute        ");
        for(var i : parseCron(args[0], 60, 0)) System.out.print(i + " ");
        System.out.println();
        System.out.print("hour          ");
        for(var i : parseCron(args[1], 24, 0)) System.out.print(i + " ");
        System.out.println();
        System.out.print("day of month  ");
        for(var i : parseCron(args[2], 32, 1)) System.out.print(i + " ");
        System.out.println();
        System.out.print("month         ");
        for(var i : parseCron(args[3], 13, 1)) System.out.print(i + " ");
        System.out.println();
        System.out.print("day of week   ");
        for(var i : parseCron(args[4], 7, 0)) System.out.print(i + " ");
        System.out.println();
        System.out.print("command       ");
        for(int i = 5; i < args.length; i++) System.out.print(args[i] + " ");
        System.out.println("\n --------------------------------");
    }

    static Set<Integer> parseCron(String exp, int ceil, int ground){
        SortedSet<Integer> result = new TreeSet<>();
        for(String val : exp.split(",")){
            int stepIndx = val.indexOf('/');
            if(stepIndx != -1){
                int step = Integer.parseInt(val.substring(stepIndx+1));
                result.addAll(getCronWithStep(val.substring(0, stepIndx), step, ceil));
            }
            else{
                result.addAll(getCronNoStep(val, ceil));
            }
        }
        return result.tailSet(ground);
    }


    static List<Integer> getCronWithStep(String val, int step, int ceil){
        List<Integer> values = new ArrayList<>();
        int range = val.indexOf('-');
        if(range != -1){
            int from = Integer.parseInt(val.substring(0, range));
            int to = Integer.parseInt(val.substring(range + 1));
            for(int i = from; i <= to; i += step){
                values.add(i);
            }
        }
        else{
            int from;
            if(val.equals("*")) from = 0;
            else from = Integer.parseInt(val);
            for(int i = from; i < ceil; i += step){
                values.add(i);
            }
        }
        return values;
    }

    static List<Integer> getCronNoStep(String val, int ceil){
        List<Integer> values = new ArrayList<>();
        if(val.equals("*")){
            for(int i = 0; i < ceil; i++) values.add(i);
            return values;
        }
        int range = val.indexOf('-');
        if(range != -1){
            int from = Integer.parseInt(val.substring(0, range));
            int to = Integer.parseInt(val.substring(range + 1));
            for(int i = from; i <= to; i++){
                values.add(i);
            }
        }
        else values.add(Integer.parseInt(val));
        return values;
    }


}