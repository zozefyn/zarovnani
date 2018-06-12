/*
 * MIT License
 * Copyright (c) 2017 Gymnazium Nad Aleji
 * Copyright (c) 2017 Vojtech Horky
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cz.alisma.alej.text.arellanesova.wrapping;

import java.util.Scanner;

public class WrapAndAlign {
    private static final int MAX_WIDTH = 50;

    public static void main(String[] args) throws FileNotFoundException {
        String align= "left";
        for (int i=0 ; i<args.length; i++) {
            if ("--center".equals (args [i]) || "--centre".equals (args [i])) {
                 align = "center";
            } else if ("--right".equals (args [i])) {
                align = "right";
            } else if ("--justify".equals (args [i])) {
                align = "justify";
            } else if (args [i].startsWith("--width=")) {
                try { //kdyz uzivatel nezada center, right nebo justify, program automaticky zarovnava doleva
                MAX_WIDTH = Integer.ParseInt (args[i].substring(8));
            } catch (Exception e) {
                    System.out.println ("Incorrect use of --width argument, expected integer, got: " + args [i]);
            }
            } else if ("-w".equals (args [i])) {
                if (i == args.length - 1)
                    System.out.println ("Incorrect use of --w argument, expected integer");
                } else {
                  try {
                       MAX_WIDTH = Integer.ParseInt (args[i+1]); 
                  } catch (Exception e) {
                    System.out.println ("Incorrect use of --w argument, expected integer, got: " + args [i+1]);
                
                  }
                    i++;
                }    
            } else { 
                        System.out.println "Unknown argument: " + args [i]);
            }

        }
        Scanner input = new Scanner(System.in);
        ParagraphDetector pd = new ParagraphDetector(input);

        Aligner aligner = null;
        switch (align) {

            case "right": aligner=new RightAligner (); break;
            case "center": aligner=new CenterAligner (); break;
            case "justify": aligner=new JustifyAligner (); break;
            default: aligner = new LeftAligner ();
        }

        while (pd.hasNextParagraph()) {
            Paragraph para = pd.nextParagraph();
            LinePrinter line = new LinePrinter(System.out, MAX_WIDTH, aligner);
            while (para.hasNextWord()) {
                String word = para.nextWord();
                line.addWord(word);
            }
            line.flush();
            System.out.println();
        }
    }
}
