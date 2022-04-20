public class CutRodModified {
    private static Soln MemoizedCutRod(int[] p, int n ){
        int[] r = new int[n+1];
        int[] s = new int[n+1];
        for (int i = 0; i < n+1; i++) {
            r[i] = Integer.MIN_VALUE;
        }
        return new Soln(MemoizedCutRodAux(p, n, r, s), s);
    }
    private static int MemoizedCutRodAux(int[] p, int n, int[] r, int[] s){
        int q;
        if (r[n] >= 0) {
            return r[n];
        }
        if (n == 0) {
            q = 0;
        } 
        else {
            q = Integer.MIN_VALUE;
            for (int i = 1; i <= n; i++) {
                int newRevenue = p[i-1] + MemoizedCutRodAux(p, n-i, r, s);
                if (q < newRevenue) {
                    q = newRevenue;
                    s[n] = i;
                }
            }        
        }
        r[n] = q;
        return q;
    }
    public static void main(String[] args) {
        int[] p = {1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
        int n = 9;
        Soln soln = MemoizedCutRod(p, n);
        System.out.println("Maximum revenue for r" + n + " = " + soln.rev);
        String soluString = "from solution: " + n + " = ";
        while (n > 0) {
            soluString = soluString + (soln.s[n] + " + ");
            n = n - soln.s[n];
        }
        soluString = soluString.substring(0, soluString.length()-2);
        System.out.println(soluString);
    }
}
class Soln {
    public int rev;
    public int[] s;
    public Soln(int rev, int[] s) {
        this.rev = rev;
        this.s = s;
    }
}