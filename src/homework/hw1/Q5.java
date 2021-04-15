package homework.hw1;

/* I got help from "Ali Razghandi" for (?<= |^) and (?: |$) regex patterns */

import java.util.Hashtable;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Q5 {
    public static void main(String[] args) {
        Pattern addDoc = Pattern.compile("^\\s*ADD DOC ([^ ]+)\\s*$");
        Pattern removeDoc = Pattern.compile("^\\s*RMV DOC ([^ ]+)\\s*$");
        Pattern replaceWord = Pattern.compile("^\\s*RPLC ([^ ]+) ([^ ]+) ([^ ]+)\\s*$");
        Pattern removeWord = Pattern.compile("^\\s*RMV WORD ([^ ]+) ([^ ]+)\\s*$");
        Pattern addWord = Pattern.compile("^\\s*ADD WORD ([^ ]+) ([^ ]+)\\s*$");
        Pattern findRepeat = Pattern.compile("^\\s*FIND REP ([^ ]+) ([^ ]+)\\s*$");
        Pattern gcd = Pattern.compile("^\\s*GCD ([^ ]+)\\s*$");
        Pattern findMirror = Pattern.compile("^\\s*FIND MIRROR ([^ ]+) ([a-zA-Z])\\s*$");
        Pattern findAlphabet = Pattern.compile("^\\s*FIND ALPHABET WORDS ([^ ]+)\\s*$");
        Pattern print = Pattern.compile("^\\s*PRINT ([^ ]+)\\s*$");
        Pattern end = Pattern.compile("^\\s*END\\s*$");

        Scanner input = new Scanner(System.in);

        boolean quit = false;
        while (!quit) {
            String command = input.nextLine();
            Hashtable<String, Doc> foundDocs;

            Matcher matchAddDoc = addDoc.matcher(command);
            Matcher matchRemoveDoc = removeDoc.matcher(command);
            Matcher matchReplaceWord = replaceWord.matcher(command);
            Matcher matchRemoveWord = removeWord.matcher(command);
            Matcher matchAddWord = addWord.matcher(command);
            Matcher matchFindRepeat = findRepeat.matcher(command);
            Matcher matchGCD = gcd.matcher(command);
            Matcher matchFindMirror = findMirror.matcher(command);
            Matcher matchFindAlphabet = findAlphabet.matcher(command);
            Matcher matchPrint = print.matcher(command);
            Matcher matchEnd = end.matcher(command);


            if (matchAddDoc.find()) {
                new Doc(matchAddDoc.group(1), input.nextLine());

            } else if (matchRemoveDoc.find()) {
                foundDocs = Doc.finder(matchRemoveDoc.group(1));

                for (String docName : foundDocs.keySet()) {
                    Doc.allDocs.remove(docName);
                }

            } else if (matchReplaceWord.find()) {
                foundDocs = Doc.finder(matchReplaceWord.group(1));

                for (Doc doc : foundDocs.values()) {
                    doc.replaceWord(matchReplaceWord.group(2), matchReplaceWord.group(3));
                }

            } else if (matchRemoveWord.find()) {
                foundDocs = Doc.finder(matchRemoveWord.group(1));

                for (Doc doc : foundDocs.values()) {
                    doc.removeWord(matchRemoveWord.group(2));
                }

            } else if (matchAddWord.find()) {
                foundDocs = Doc.finder(matchAddWord.group(1));

                for (Doc doc : foundDocs.values()) {
                    doc.addWord(matchAddWord.group(2));
                }

            } else if (matchFindRepeat.find()) {
                foundDocs = Doc.finder(matchFindRepeat.group(1));
                String word = matchFindRepeat.group(2);

                for (String docName : foundDocs.keySet()) {
                    String output = word + " is repeated ";
                    output += foundDocs.get(docName).findRepeat(word) + " times in " + docName;
                    System.out.println(output);
                }

            } else if (matchGCD.find()) {
                foundDocs = Doc.finder(matchGCD.group(1));

                for (Doc doc : foundDocs.values()) {
                    doc.appendGCD();
                }

            } else if (matchFindMirror.find()) {
                foundDocs = Doc.finder(matchFindMirror.group(1));

                for (Doc doc : foundDocs.values()) {
                    System.out.println(doc.countMirrors(matchFindMirror.group(2)) + " mirror words!");
                }

            } else if (matchFindAlphabet.find()) {
                foundDocs = Doc.finder(matchFindAlphabet.group(1));

                for (Doc doc : foundDocs.values()) {
                    System.out.println(doc.countAlphabetical() + " alphabetical words!");
                }

            } else if (matchPrint.find()) {
                foundDocs = Doc.finder(matchPrint.group(1));

                for (Doc doc : foundDocs.values()) {
                    System.out.println(doc);
                }

            } else if (matchEnd.find()) {
                quit = true;

            } else {
                throwCommandError();
            }
        }
    }

    private static void throwFileError() {
        System.out.println("invalid file name!");
    }

    private static void throwCommandError() {
        System.out.println("invalid command!");
    }

    public static int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }

    private static class Doc {
        public static Hashtable<String, Doc> allDocs = new Hashtable<>();
        private String text;

        private Doc(String name, String text) {
            setText(text);
            allDocs.put(name, this);
        }

        public static Hashtable<String, Doc> finder(String docName) {
            Hashtable<String, Doc> toReplace;

            if (docName.equals("-ALL")) {
                toReplace = Doc.allDocs;
            } else if (Doc.allDocs.containsKey(docName)) {
                toReplace = new Hashtable<>();
                toReplace.put(docName, Doc.allDocs.get(docName));
            } else {
                toReplace = new Hashtable<>();
                throwFileError();
            }
            return toReplace;
        }

        private void setText(String text) {
            StringBuilder stringBuilder = new StringBuilder(text);
            Pattern pictureOrLink = Pattern.compile("(?<= |^)(!?\\[([^ ]*)]\\([^)]*\\))(?: |$)");
            Matcher matcher = pictureOrLink.matcher(text);
            while (matcher.find()) {
                stringBuilder.replace(matcher.start(1), matcher.end(1), matcher.group(2));
                matcher = pictureOrLink.matcher(stringBuilder.toString());
            }

            Pattern bold = Pattern.compile("(?<= |^)(\\*{2,}([A-Za-z0-9 ]+)\\*{2,})(?: |$)");
            matcher = bold.matcher(stringBuilder.toString());
            while (matcher.find()) {
                stringBuilder.replace(matcher.start(1), matcher.end(1), matcher.group(2));
                matcher = bold.matcher(stringBuilder.toString());
            }

            Pattern noise = Pattern.compile("(?<= |^)([a-zA-Z0-9*]+[^a-zA-Z0-9 ][^ ]+)(?: |$)");
            matcher = noise.matcher(stringBuilder.toString());
            while (matcher.find()) {
                stringBuilder.delete(matcher.start(1), matcher.end(1));
                matcher = noise.matcher(stringBuilder.toString());
            }

            this.text = stringBuilder.toString();
        }

        public void addWord(String word) {
            text += word;
        }

        public int findRepeat(String word) {
            Pattern wordPattern = Pattern.compile(word);
            Matcher matcher = wordPattern.matcher(text);
            String remainingText = text;

            int count = 0;
            while (matcher.find()) {
                count++;
                remainingText = remainingText.substring(matcher.start() + 1);
                matcher = wordPattern.matcher(remainingText);
            }
            return count;
        }

        public void replaceWord(String initialWords, String finalWord) {
            Pattern words = Pattern.compile("(?<=,|^)([a-zA-Z0-9]+)(?:,|$)");
            Matcher matcher = words.matcher(initialWords);

            while (matcher.find()) {
                String word = matcher.group(1);
                Pattern initialWord = Pattern.compile(".*(?<= |^)(" + word + ")(?: |$)");
                Matcher wordMatcher = initialWord.matcher(text);

                if (wordMatcher.find()) {
                    int lastIndex = wordMatcher.start(1);
                    text = text.substring(0, lastIndex) + finalWord + text.substring(lastIndex + word.length());
                }
            }
        }

        public int countMirrors(String character) {
            Pattern word = Pattern.compile("(?<= |^)([0-9]+)" + character + "([0-9]+)(?: |$)");
            Matcher matcher = word.matcher(text);

            int count = 0;
            while (matcher.find()) {
                if (matcher.group(1).equals(matcher.group(2))) count++;
            }
            return count;
        }

        public int countAlphabetical() {
            Pattern word = Pattern.compile("(?<= |^)[a-zA-Z]+(?: |$)");
            Matcher matcher = word.matcher(text);

            int count = 0;
            while (matcher.find()) count++;
            return count;
        }

        public void appendGCD() {
            Pattern number = Pattern.compile("([0-9]+)");
            Matcher matcher = number.matcher(text);

            if (matcher.find()) {
                int gcd = Integer.parseInt(matcher.group(1));
                while (matcher.find()) {
                    gcd = GCD(gcd, Integer.parseInt(matcher.group(1)));
                }
                this.addWord(Integer.toString(gcd));
            }
        }

        public void removeWord(String word) {
            StringBuilder stringBuilder = new StringBuilder(text);
            Pattern wordPattern = Pattern.compile("(?<= |^)(" + word + ")(?: |$)");
            Matcher matcher = wordPattern.matcher(text);

            while (matcher.find()) {
                stringBuilder.delete(matcher.start(1), matcher.end(1));
                matcher = wordPattern.matcher(stringBuilder.toString());
            }
            text = stringBuilder.toString();
        }

        public String toString() {
            return text;
        }
    }
}
