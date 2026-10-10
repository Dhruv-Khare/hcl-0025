package CollectionFramework;

import java.util.*;

public class Maps {
    public static void main(String[] args) {
        Map<Integer,String> mp=new HashMap<>();
        mp.put(1,"one");
        mp.put(2,"two");
        mp.put(3,"three");
        mp.put(4,"four");
        mp.put(5,"five");
        mp.put(6,"six");

        if(mp.containsKey(1))
        {
            System.out.print(mp.get(1)+" ");
        }

    }
}
