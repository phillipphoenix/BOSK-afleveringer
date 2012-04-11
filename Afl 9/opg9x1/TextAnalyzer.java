package opg9x1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class TextAnalyzer {

	public static void main(String[] args) {
		new TextAnalyzer();
	}
	
	ArrayList<String> arrayList;
	
	public TextAnalyzer() {
		arrayList = new ArrayList<String>();
		arrayList.addAll( Text.getTextAsLinkedList() );
		
		// Opgave a
		// Udskriv antallet af elementer, teksten består af
		System.out.println("a) Antallet af elementer i teksten: " + numberOfElements());
		System.out.println();
		
		// Opgave b
		// Udskriv ordet på plads 45
		System.out.println("b) Ordet/elementet på plads 45 er: " + getElementByIndex(45));
		System.out.println();
		
		// Opgave c
		// Udskriv antallet af forskellige elementer i listen
		System.out.println("c) Antallet af forskellige elementer: " + numberOfDifferentElements());
		System.out.println();
		
		// Opgave d
		// Udskriv alle forskellige elementer i alfabetisk rækkefølge
		System.out.println("d) ");
		for ( String s : sortCollection(getSetOfElements()) )
			System.out.println(s);
		System.out.println();
		
		// Opgave e
		// Udskriv alle elementer i alfabetisk rækkefølge
		System.out.println("e) ");
		for ( String s : sortCollection(arrayList) )
			System.out.println(s);
		System.out.println();
		
		// Opgave f
		// Udskriv antallet af hver forskelligt element
		HashMap<String, Integer> hm = getNumberOfEachDifferentElement();
		System.out.println("f) ");
		for ( String s : sortCollection(hm.keySet()) )
			System.out.println(s + " : " + hm.get(s));
		System.out.println();
	}
	
	// Antallet af elementer i teksten
	private int numberOfElements() {
		return arrayList.size();
	}
	
	// Returnerer elementet på den angivne plads
	private String getElementByIndex(int index) {
		return arrayList.get(index);
	}
	
	// Antallet af forskellige elementer i listen
	private int numberOfDifferentElements() {
		HashSet<String> hs = new HashSet<String>();
		hs.addAll( arrayList );
		return hs.size();
	}
	
	// Returnerer et set af elementer, så der kun eksisterer ét af hvert element
	private HashSet<String> getSetOfElements() {
		HashSet<String> hs = new HashSet<String>();
		hs.addAll( arrayList );
		return hs;
	}
	
	// Returnerer en sorteret array list af strings
	private ArrayList<String> sortCollection(Collection<String> c) {
		ArrayList<String> al = new ArrayList<String>();
		al.addAll(c);
		Collections.sort(al);
		return al;
	}
	
	// Returnerer et hash map af elementer med antallet som value og elementer som keys
	private HashMap<String, Integer> getNumberOfEachDifferentElement() {
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		for ( String s : arrayList ) {
			if ( hm.containsKey(s) )
				hm.put(s, hm.get(s) + 1);
			else
				hm.put(s, 1);
		}
		return hm;
	}
	
}
