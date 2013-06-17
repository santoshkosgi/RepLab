/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import org.xeustechnologies.googleapi.spelling.*;


public class SpellCheck {

    public static String googleCheckAdvanced() {
        /**
         * I don't think I'll have to play around with this one
         * We can give options for other language support etc.
         */

        
        SpellChecker checker = new SpellChecker();
        checker.setOverHttps(true); // Use https (default true from v1.1)
        checker.setLanguage(Language.SPANISH); // Use English (default)

        SpellRequest request = new SpellRequest();
        request.setText("holo como ettas");
        request.setIgnoreDuplicates(true); // Ignore duplicates

        SpellResponse spellResponse = checker.check(request);

        for (SpellCorrection sc : spellResponse.getCorrections()) {
            System.out.println(sc.getValue());
        }

        return null;
    }

    public static String googleCheck(String inString) {
        /**
         * This method takes a String (consider SINGLE WORD) and returns the closest
         * possible correct word for it
         * 
         * Because Google Spell Check API doesn't provide support for small words 
         * as we can't map them effectively, instead of returning no response we will
         * simply return as they are
         * 
         */
        if (inString.length() < 1) {
            System.out.println(inString);
        }
        try {
            SpellChecker checker = new SpellChecker();
            SpellResponse spellResponse = checker.check(inString);
            for (SpellCorrection sc : spellResponse.getCorrections()) {
                return(sc.getValue().split("\\t")[0]);
            }
        }
        catch(NullPointerException e){
            return (inString);
        }
        return null;
    }

    public String spellChecker(String allWords) {
        /**
         * This method takes a complete string from a tweet and try to map each
         * word to it's best possible string.
         * Finally returns the complete spell-corrected string
         */
        String[] words = allWords.split(" ");
        String tempWord;
        String outWords = "";
        for (int i = 0; i < words.length; i++) {
            if (words[i].length() < 2) {
                outWords = outWords + " " + words[i];
            } else {
                tempWord = googleCheck(words[i]);
                outWords = outWords + " " + tempWord;
            }
        }
        return outWords;
    }

    public static void main(String[] args) {
        SpellCheck sp = new SpellCheck(); 
    	System.out.println(sp.spellChecker("appe iphne"));
    	System.out.println(sp.spellChecker("appe iphne"));

    }
}
