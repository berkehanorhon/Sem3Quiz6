import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int p_length = Integer.parseInt(scan.nextLine());
        String[] words = scan.nextLine().split(" ");
        scan.close();
        String password = find_password(words, p_length);
        System.out.println(password);
        clear_words(words, password);
        decrypt_strings(words,password);
    }

    public static String find_password(String[] array, int length){
        HashMap<String, Integer> counter_map = new HashMap<>();
        counter_map.put("iamlazy", 0);
        for(String i : array) {
            String[] iterate_array = give_all_substrings(i, length);
            if (iterate_array == null)
                continue;
            for (String j : iterate_array) {
                Integer cur_count = counter_map.putIfAbsent(j, 0);
                counter_map.put(j,(cur_count != null)?cur_count + 1:1);
            }
        }
        String password = "iamlazy";
        for(String i : counter_map.keySet())
            password = counter_map.get(password) < counter_map.get(i)?i:password;
        return password;
    }

    public static String[] give_all_substrings(String str, int length){
        if (str.length() < length)
            return null;
        String[] return_array = new String[str.length()-length+1];
        for (int i = 0;i <= str.length()-length;i++)
            return_array[i] = str.substring(i,i+length);
        return return_array;
    }

    public static void clear_words(String[] array, String password){
        for(int i = 0; i < array.length; i++)
            while(array[i].contains(password))
                array[i] = array[i].replace(password,"");
    }

    public static void decrypt_strings(String[] array, String password){
        int counter = 0;
        for(int i = 0; i < array.length; i++) {
            char[] char_arr = array[i].toCharArray();
            for(int z = 0; z < char_arr.length; z++){
                char_arr[z] = decrypt_char(char_arr[z],password.charAt(counter%password.length()));
                counter++;
            }
            array[i] = new String(char_arr);
        }
        for(String i : array)
            System.out.print(i+" ");
    }

    public static char decrypt_char(char chr, char key){
        int i_key = (int)key;
        int i_chr = (int)chr;
        if(i_key<=122 && i_key>=97)
            i_key = i_key-96;
        else if(i_key<=90 && i_key>=65)
            i_key = i_key-64;
        if(i_chr<=122 && i_chr>=97) {
            i_chr = i_chr + i_key > 122?((i_chr + i_key) % 123)+97:i_chr + i_key;
        }
        else if(i_chr<=90 && i_chr>=65)
            i_chr = i_chr + i_key > 90?((i_chr + i_key) % 91)+65:i_chr + i_key;
        return (char)i_chr;
    }
}