import java.io.*;
import java.util.*;
class Buddy {
    
    class tupled{
        int left,right;
        tupled(int x, int y) {
            left = x;
            right = y;
        }
    }
    
    int size;
    ArrayList<tupled> memBlocks[];
    
    HashMap<Integer, Integer> hm;
    
    
    @SuppressWarnings("unchecked")
    Buddy(int s) {
        size = s;
        hm = new HashMap<>();
        
        int x = (int) Math.ceil(Math.log(s) / Math.log(2));
        
        
        memBlocks = new ArrayList[x + 1];
        for (int i = 0; i <= x; i++)
            memBlocks[i] = new ArrayList<>();
        
        
        memBlocks[x].add(new tupled(0, size - 1));
    }
    void alloc(int s) {
        
        
        int x = (int) Math.ceil(Math.log(s) / Math.log(2));
        int i;
        tupled temp = null;
        
        if (memBlocks[x].size() > 0) {
            
            
            temp = (tupled) memBlocks[x].remove(0);
            System.out.println("Memory from " + temp.left + " to " + temp.right + " allocated");
            
            hm.put(temp.left, temp.right - temp.left + 1);
            return;
        }
        
        for (i = x + 1; i < memBlocks.length; i++) {
            if (memBlocks[i].size() == 0)
                continue;
            
            break;
        }
        
        
        if (i == memBlocks.length) {
            System.out.println("Sorry, failed to alloc memory");
            return;
        }
        
        temp = (tupled) memBlocks[i].remove(0);
        i--;
        
        for (; i >= x; i--) {
            
            
            tupled newtupled = new tupled(temp.left, temp.left + (temp.right - temp.left) / 2);
            
            tupled newtupled2 = new tupled(temp.left + (temp.right - temp.left + 1) / 2, temp.right);
            
            
            memBlocks[i].add(newtupled);
            memBlocks[i].add(newtupled2);
            
            temp = (tupled) memBlocks[i].remove(0);
        }
        
        
        System.out.println("Memory from " + temp.left + " to " + temp.right + " allocated");
        
        hm.put(temp.left, temp.right - temp.left + 1);
    }
    void dealloc(int s) {
        
        if (!hm.containsKey(s)) {
            System.out.println("Sorry, invalid free request");
            return;
        }
        
        
        int x = (int) Math.ceil(Math.log(hm.get(s)) / Math.log(2));
        int i, buddyNumber, buddyAddress;
        
        memBlocks[x].add(new tupled(s, s + (int) Math.pow(2, x) - 1));
        System.out.println("Memory block from " + s + " to " + (s + (int) Math.pow(2, x) - 1) + " freed");
        
        
        
        buddyNumber = s / hm.get(s);
        if (buddyNumber % 2 != 0) {
            buddyAddress = s - (int) Math.pow(2, x);
        }
        else {
            buddyAddress = s + (int) Math.pow(2, x);
        }
        
        for (i = 0; i < memBlocks[x].size(); i++) {
            
            if (memBlocks[x].get(i).left == buddyAddress) {
                
                
                if (buddyNumber % 2 == 0) {
                    
                    memBlocks[x + 1].add(new tupled(s, s + 2 * ((int) Math.pow(2, x)) - 1));
                    System.out.println("Coalescing of blocks starting at " + s + " and " + buddyAddress + " was done");
                }
                
                
                else {
                    
                    memBlocks[x + 1].add(new tupled(buddyAddress, buddyAddress + 2 * ((int) Math.pow(2, x)) - 1));
                    System.out.println("Coalescing of blocks starting at " + buddyAddress + " and " + s + " was done");
                }
                
                
                memBlocks[x].remove(i);
                memBlocks[x].remove(memBlocks[x].size() - 1);
                break;
            }
        }
        
        hm.remove(s);
    }
    public static void main(String args[]) throws IOException {
        int memSize = 0, type = -1, val = 0;

        memSize = 65536;
        Buddy obj = new Buddy(memSize);
        obj.alloc(32);
        obj.alloc(32);
        obj.alloc(112);
        obj.alloc(32);
        obj.dealloc(18);
        obj.dealloc(64);
        obj.dealloc(0);
        obj.dealloc(32);
    }
}
