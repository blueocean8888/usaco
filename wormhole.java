/*
ID: frankmo2
LANG: JAVA
TASK: wormhole
 */
import java.io.*;
import java.util.*;

class wormhole {
    
    public static void main (String [] args) throws Exception {
        //testOutputFile();
        //testAllConfigs();

        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));
        StringTokenizer st = new StringTokenizer(f.readLine().trim());
        int num = Integer.parseInt(st.nextToken());
        List<Wormhole> wormy = new ArrayList<Wormhole>();
        String check = f.readLine();
        while(check!= null)
        {
            StringTokenizer st2 = new StringTokenizer(check.trim());
            int start = Integer.parseInt(st2.nextToken());
            int end = Integer.parseInt(st2.nextToken());
            Wormhole worm = new Wormhole();
            worm.start = start;
            worm.end = end;
            wormy.add(worm);
            check = f.readLine();
        }

        Collections.sort(wormy);
        //jfgjfjdfk;FranklinsdfdefewfefFedfewr ffgdfnf nfdrf  jk;ooiujvnzcjFraqThe lazy dog jumped over the soething log something log and the quick brown fox jumped over the lazy dog 
        int count = 0;

        List<Config> configs = allConfigs(wormy);
        for(Config conf: configs)
        {
            boolean hasLoop = false;
            for(Wormhole worm: wormy)
            {
                boolean enterExit = true;
                Wormhole next = nextWormhole(worm, enterExit, conf, wormy);
                while(true)
                {
                    if(worm.equals(next) && enterExit == false)
                    {
                        //count = count+1;
                        hasLoop = true;
                        break;
                    }

                    if(next == null)
                    {
                        break;
                    }
                    else
                    {
                        enterExit = !enterExit;
                        next = nextWormhole(next, enterExit, conf, wormy);
                    }

                }
                if(hasLoop)
                {
                    break;
                }
            }
            
            if (hasLoop)
            {
                count = count + 1;
                continue;
            }
        }

        out.println(count);
        f.close();
        out.close();                                  
    }

    public static class Wormhole implements Comparable<Wormhole>
    {
        int start;
        int end; 

        @Override
        public int compareTo(Wormhole other)
        {
            if (other == null)
            {
                return 1;
            }
            else
            {
                if (this.start < other.start)
                {
                    return -1;
                }
                else if (this.start == other.start)
                {
                    return (this.end - other.end);
                }
                else
                {
                    return 1;
                }
            }
        }

        @Override
        public boolean equals(Object random)
        {
            if(random == null)
            {
                return false;
            }
            if(!(random instanceof Wormhole))
            {
                return false;
            }
            Wormhole other = (Wormhole) random;
            if(this.start == other.start && this.end == other.end )
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
    
    public static class Pair 
    {                                                       
        Wormhole w1;
        Wormhole w2; 
    }
    
    public static class Config
    {
        List<Pair> pairs = new ArrayList<Pair>();

    }
    public static Wormhole nextWormhole(Wormhole w, boolean isEnter, Config con, List<Wormhole> holes)
    {
        if(isEnter)
        {
            for(Pair pair: con.pairs)
            {
                if(pair.w1.equals(w))
                {
                    return pair.w2;
                }
                if(pair.w2.equals(w))
                {
                    return pair.w1;
                }
            }
            return null;
        }
        else
        {
            // holes needs to be already sorted
            for(Wormhole wh: holes)
            {
                if(w.start < wh.start && w.end == wh.end)
                {
                    return wh;
                }
            }
            return null;
        }   
    }

    /**
     * Find out all the possible configurations, and return them as a list of configurations.
     */
    public static List<Config> allConfigs(List<Wormhole> holes)
    {
        List<Config> configs = new ArrayList<Config>();

        if(holes == null || holes.size() == 0)
        {
            return configs;
        }

        if(holes.size()%2 == 1)
        {
            return configs;
        }

        if(holes.size() == 2)
        {
            Pair pair = new Pair();
            pair.w1 = holes.get(0);
            pair.w2 = holes.get(1);
            Config c = new Config();
            c.pairs.add(pair);

            configs.add(c);
            return configs;
        }   
        else
        {
            // If the number of holes is 4 or above
            for(int i = 1; i < holes.size(); i++)
            {
                Pair p1  = new Pair();
                p1.w1 = holes.get(0);
                p1.w2 = holes.get(i);

                List<Wormhole> subHoles = new ArrayList<Wormhole>();
                for(int j = 1 ; j < holes.size(); j++)
                {
                    if(j != i)
                    {
                        subHoles.add(holes.get(j));
                    }
                }
                List<Config> subConfigs = allConfigs(subHoles);

                for (Config sConfig : subConfigs)
                {
                    sConfig.pairs.add(p1);
                }

                configs.addAll(subConfigs);
            }

            return configs;
        }
    }

    /*
    public static void testAllConfigs()
    {
        Wormhole w1 = new Wormhole();
        Wormhole w2 = new Wormhole();
        Wormhole w3 = new Wormhole();
        Wormhole w4 = new Wormhole();
        Wormhole w5 = new Wormhole();
        Wormhole w6 = new Wormhole();
        Wormhole w7 = new Wormhole();
        Wormhole w8 = new Wormhole();
        Wormhole w9 = new Wormhole();
        Wormhole w10 = new Wormhole();

        List<Wormhole> holes = new ArrayList<Wormhole>();

        holes.add(w1);
        holes.add(w2);
        List<Config> configs1 = allConfigs(holes);
        System.out.println("Number of pairs: " + configs1.get(0).pairs.size() + "; configs1.size(): " + configs1.size());

        holes.add(w3);
        holes.add(w4);
        List<Config> configs3 = allConfigs(holes);
        System.out.println("Number of pairs: " + configs3.get(0).pairs.size() + "; configs3.size(): " + configs3.size());

        holes.add(w5);
        holes.add(w6);
        List<Config> configs15 = allConfigs(holes);
        System.out.println("Number of pairs: " + configs15.get(0).pairs.size() + "; configs15.size(): " + configs15.size());

        holes.add(w7);
        holes.add(w8);
        List<Config> configs105 = allConfigs(holes);
        System.out.println("Number of pairs: " + configs105.get(0).pairs.size() + "; configs105.size(): " + configs105.size());

        holes.add(w9);
        holes.add(w10);
        List<Config> configs945 = allConfigs(holes);
        System.out.println("Number of pairs: " + configs945.get(0).pairs.size() + "; configs945.size(): " + configs945.size());
    }
    
    public static void testOutputFile()
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(new BufferedWriter(new FileWriter("ttttt.out")));
            out.println("ttttt");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    */
}