class Pool{
    
    static void poolAlloc(int pools[], int m, int processes[], int n) {
        
        
        int alloc[] = new int[n];
        
        for (int i = 0; i < alloc.length; i++)
            alloc[i] = -1;
        
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (pools[j] >= processes[i]) {
                    
                    alloc[i] = j;
                    
                    pools[j] -= processes[i];
                    break;
                }
            }
        }
        System.out.println("\nProcess No.\tProcess Size\tBlock no.");
        for (int i = 0; i < n; i++) {
            System.out.print(" " + (i + 1) + "\t\t" + processes[i] + "\t\t");
            if (alloc[i] != -1)
                System.out.print(alloc[i] + 1);
            else
                System.out.print("Not Allocated");
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        int pools[] = new int[1000];
        int k=1;
        for(int i=0;i<1000;i++)
        {
            pools[i]=k;
            k=(k*2)%65536;
        }
        int processes[] = {212,417,112,426,100,600,1232,34324,4533,45321,34123,1234,32123,1927387,213321,342,12,0,321};
        int m = 1000;
        int n = processes.length;
        poolAlloc(pools, m, processes, n);
    }
}
