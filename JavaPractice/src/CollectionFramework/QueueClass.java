package CollectionFramework;
import java.util.*;

public class QueueClass {
    public static void main(String[] args) {
//        Queue<Integer> q=new LinkedList<>();
//        q.offer(1);
//        q.offer(2);
//        q.offer(3);
//        q.offer(4);
//        q.offer(5);
//        System.out.println(q);

//        Queue<Integer> q=new ArrayDeque<>();
//        q.add(1);
//        q.add(2);
//        q.add(3);
//        q.add(4);
//        System.out.println(q);
//        System.out.println(q.poll());
//        System.out.println(q);
//        System.out.println(q.poll());
//        System.out.println(q);

//        Set<Integer> set = new HashSet<>();
//        set.add(1);
//        set.add(2);
//        set.add(3);
//        set.add(4);
//        System.out.println(set);

        PriorityQueue<Integer> pq=new PriorityQueue<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        pq.add(4);
        pq.add(5);

        while(!pq.isEmpty()){
            System.out.print(pq.poll()+" ");
        }





    }

}
