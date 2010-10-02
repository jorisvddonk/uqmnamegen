/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uqmnamegen.generators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class NameGenerator {
    /*
     * This class has some.. instanely ugly code.
     * DO NOT HOLD THIS AGAINST ME!
     * What can I say... I was somewhat lazy when I coded this... :)
     */

    public ArrayList<String> vowels;
    public ArrayList<String> consonants;
    public ArrayList<String> stops;
    public ArrayList<String> affricates;
    public ArrayList<String> fricatives;
    public ArrayList<String> nasals;
    public ArrayList<String> liquids;
    public ArrayList<String> glides;
    public ArrayList<String> remainingConsonants;
    public ArrayList<String> namepatterns;
    public int maxcombolength = -1;

    public HashMap<ArrayList<String>,String> patchars = new HashMap<ArrayList<String>, String>();
    public HashMap<ArrayList<String>,String> patnames = new HashMap<ArrayList<String>, String>();

    static Random RANDOM = new Random();


    public NameGenerator() {
        vowels = new ArrayList<String>();
        vowels.add("a");
        vowels.add("e");
        vowels.add("i");
        vowels.add("o");
        vowels.add("u");
        vowels.add("y");

        consonants = new ArrayList<String>();
        consonants.add("b");
        consonants.add("c");
        consonants.add("d");
        consonants.add("f");
        consonants.add("g");
        consonants.add("h");
        consonants.add("j");
        consonants.add("k");
        consonants.add("l");
        consonants.add("m");
        consonants.add("n");
        consonants.add("p");
        consonants.add("q");
        consonants.add("r");
        consonants.add("s");
        consonants.add("t");
        consonants.add("v");
        consonants.add("x");
        consonants.add("z");
        consonants.add("w");







        stops = new ArrayList<String>();
        stops.add("p");
        stops.add("t");
        stops.add("k");
        stops.add("b");
        stops.add("d");
        stops.add("c");
        stops.add("ck");
        //stops.add("pp");
        //stops.add("g");

        affricates = new ArrayList<String>();
        affricates.add("j");
        affricates.add("g");
        affricates.add("ch");
        //affricates.add("zz");
        affricates.add("dz");
        affricates.add("ds");
        affricates.add("ts");
        affricates.add("tch");
        affricates.add("tu");
        affricates.add("dg");

        fricatives = new ArrayList<String>();
        fricatives.add("f");
        fricatives.add("gh");
        fricatives.add("v");
        fricatives.add("f");
        fricatives.add("th");
        fricatives.add("s");
        //fricatives.add("ss");
        fricatives.add("sh");
        fricatives.add("ch");
        fricatives.add("g");
        fricatives.add("h");

        nasals = new ArrayList<String>();
        nasals.add("m");
        //nasals.add("mm");
        nasals.add("n");
        nasals.add("kn");
        nasals.add("gn");
        nasals.add("ng");

        liquids = new ArrayList<String>();
        liquids.add("l");
        //liquids.add("ll");
        liquids.add("le");
        liquids.add("r");
        //liquids.add("rr");
        liquids.add("er");
        liquids.add("ur");

        glides = new ArrayList<String>();
        glides.add("w");
        glides.add("wh");

        remainingConsonants = new ArrayList<String>();
        remainingConsonants.addAll(consonants);
        remainingConsonants.removeAll(stops);
        remainingConsonants.removeAll(affricates);
        remainingConsonants.removeAll(fricatives);
        remainingConsonants.removeAll(nasals);
        remainingConsonants.removeAll(liquids);
        remainingConsonants.removeAll(glides);

        for (String s : vowels) {
            if (s.length() > maxcombolength) {
                maxcombolength = s.length();
            }
        }
        for (String s : consonants) {
            if (s.length() > maxcombolength) {
                maxcombolength = s.length();
            }
        }
        for (String s : stops) {
            if (s.length() > maxcombolength) {
                maxcombolength = s.length();
            }
        }
        for (String s : affricates) {
            if (s.length() > maxcombolength) {
                maxcombolength = s.length();
            }
        }
        for (String s : fricatives) {
            if (s.length() > maxcombolength) {
                maxcombolength = s.length();
            }
        }
        //System.err.println("maxcombolength: " + maxcombolength);
        try {
            File f = new File("patterns.txt");
            namepatterns = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(f));
            while (br.ready()) {
                namepatterns.add(br.readLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        ///////////
        addpat("vowels", "v", vowels);
        addpat("consonants", "c", consonants);
        addpat("stops", "s", stops);
        addpat("affricates", "a", affricates);
        addpat("fricatives", "f", fricatives);
        addpat("nasals", "n", nasals);
        addpat("liquids", "l", liquids);
        addpat("glides", "g", glides);
        addpat("remaining consonants", "r", remainingConsonants);
    }

    public String genRandName() {
        return genName(getRandom(namepatterns));
    }

    public void addpat(String name, String character, ArrayList<String> list) {
        patchars.put(list, character);
        patnames.put(list, name);
    }

    public String genName(String namePattern) {
        int hash = namePattern.indexOf("#");
        if (hash != -1) {
            namePattern = namePattern.substring(0,hash).trim();
        }

        String retstring = "";
        String tempstring = "";
        boolean literal = false;
        for (int i = 0; i < namePattern.length(); i++) {
            char in = namePattern.charAt(i);
            if (in == '\\' && !literal) {
                literal = true;
            } else {
                if (literal) {
                    retstring += in;
                    literal = false;
                } else {
                    switch (in) {
                        case 'C': //uppercase consonant
                            retstring += getRandomU(consonants);
                            break;
                        case 'c': //lowercase consonant
                            retstring += getRandom(consonants);
                            break;
                        case 'V': //uppercase vowel
                            retstring += getRandomU(vowels);
                            break;
                        case 'v': //lowercase vowel
                            retstring += getRandom(vowels);
                            break;
                        case 'S': //uppercase stop
                            retstring += getRandomU(stops);
                            break;
                        case 's': //lowercase stop
                            retstring += getRandom(stops);
                            break;
                        case 'R': //uppercase remaining consonant
                            retstring += getRandomU(remainingConsonants);
                            break;
                        case 'r': //lowercase remaining consonant
                            retstring += getRandom(remainingConsonants);
                            break;
                        case 'A': //uppercase affricate
                            retstring += getRandomU(affricates);
                            break;
                        case 'a': //lowercase affricate
                            retstring += getRandom(affricates);
                            break;
                        case 'F': //uppercase fricative
                            retstring += getRandomU(fricatives);
                            break;
                        case 'f': //lowercase fricative
                            retstring += getRandom(fricatives);
                            break;
                        case 'N': //uppercase nasal
                            retstring += getRandomU(nasals);
                            break;
                        case 'n': //lowercase nasal
                            retstring += getRandom(nasals);
                            break;
                        case 'L': //uppercase liquid
                            retstring += getRandomU(liquids);
                            break;
                        case 'l': //lowercase liquid
                            retstring += getRandom(liquids);
                            break;
                        case 'G': //uppercase glide
                            retstring += getRandomU(glides);
                            break;
                        case 'g': //lowercase glide
                            retstring += getRandom(glides);
                            break;
                        case 'U': //non-zero number
                            retstring += RANDOM.nextInt(9) + 1;
                            break;
                        case 'u': //number
                            retstring += RANDOM.nextInt(10);
                            break;
                        case 'p': //previous character; lowercase
                            retstring += retstring.substring(retstring.length() - 1, retstring.length()).toLowerCase();
                    }
                }
            }
        }
        return retstring;
    }

    private String getRandom(ArrayList<String> fromList) {
        return fromList.get(RANDOM.nextInt(fromList.size()));
    }

    private String getRandomU(ArrayList<String> fromList) {
        String tempstring = getRandom(fromList);
        return tempstring.substring(0, 1).toUpperCase() + tempstring.substring(1, tempstring.length());
    }

    public void analyze(String filein) {
        HashMap<String, Integer> hashmap = new HashMap();
        HashMap<String, ArrayList<String>> hashmap2 = new HashMap<String, ArrayList<String>>();

        try {
            File f = new File(filein);
            ArrayList<String> retlist = new ArrayList<String>();
            BufferedReader br = new BufferedReader(new FileReader(f));
            while (br.ready()) {
                String process = br.readLine();
                String out = "";
                for (int i = 0; i < process.length(); i++) {
                    for (int j = maxcombolength; j > 0; j--) {
                        if (i + j <= process.length()) {
                            String chk = process.substring(i, i + j).toLowerCase();
                            if (i > 0) {
                                if (j == 1 && chk.equals(process.substring(i - 1, i).toLowerCase())) {
                                    out += "p";
                                    i += j - 1;
                                    break;
                                }
                            }
                            if (stops.contains(chk)) {
                                out += "s";
                                i += j - 1;
                                break;
                            }
                            if (affricates.contains(chk)) {
                                out += "a";
                                i += j - 1;
                                break;
                            }
                            if (fricatives.contains(chk)) {
                                out += "f";
                                i += j - 1;
                                break;
                            }
                            if (nasals.contains(chk)) {
                                out += "n";
                                i += j - 1;
                                break;
                            }
                            if (liquids.contains(chk)) {
                                out += "l";
                                i += j - 1;
                                break;
                            }
                            if (glides.contains(chk)) {
                                out += "g";
                                i += j - 1;
                                break;
                            }
                            if (vowels.contains(chk)) {
                                out += "v";
                                i += j - 1;
                                break;
                            }
                            if (consonants.contains(chk)) {
                                out += "r";
                                i += j - 1;
                                break;
                            }
                        }
                    }
                }
                Integer i = hashmap.get(out);
                ArrayList<String> i2 = hashmap2.get(out);
                if (i == null) {
                    i = 0;
                }
                if (i2 == null) {
                    i2 = new ArrayList<String>();
                }
                i2.add(process);
                hashmap.put(out, i + 1);
                hashmap2.put(out, i2);


            }
            for (Iterator it = sortByValue(hashmap).iterator(); it.hasNext();) {
                String key = (String) it.next();
                String list = "";
                for (String s : hashmap2.get(key)) {
                    list += "`" + s + "` ";
                }
                System.out.println("" + key + " #:" + hashmap.get(key) + " ---- " + list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List sortByValue(final Map m) {
        List keys = new ArrayList();
        keys.addAll(m.keySet());
        Collections.sort(keys, new Comparator() {

            public int compare(Object o1, Object o2) {
                Object v1 = m.get(o1);
                Object v2 = m.get(o2);
                if (v1 == null) {
                    return (v2 == null) ? 0 : 1;
                } else if (v1 instanceof Comparable) {
                    return ((Comparable) v1).compareTo(v2);
                } else {
                    return 0;
                }
            }
        });
        return keys;
    }
}
