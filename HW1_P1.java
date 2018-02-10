// Xin Zhou

class HW1_P1 {
	private static final String cipherText = "GWN GANBDJAN HWNDG ZD EJAZNK WNAN:\n" +
	"DBZI QARL DFNINGRO ZDIBOK GR GWN NBDG-DRJGWNBDG.\n" +
	"BQGNA GPR KBVD, VRJ PZII QZOK B KNDNAGNK ZDIBOK.\n" +
	"HIZLE GR GWN WZTWNDG URZOG RQ GWN ZDIBOK BOK IRRF QRA B IBATN HIZQQ GR GWN ORAGW.\n" +
	"PBIF B WJOKANK VBAKD GRPBAKD GWN HIZQQ BOK DGRU BG GWN GBII GANN.\n" +
	"PBIF GNO QNNG GR GWN NBDG, BOK VRJ PZII QZOK B ARHF PZGW BO S UBZOGNK RO ZG.\n" +
	"GWN GANBDJAN ZD EJAZNK RON QRRG ENIRP GWN ARHF.\n";

	static final int alphaIndex = (int) 'A'; // alphaIndex = 65
	static final int alphaLength = (int) 'Z' + 1 - (int) 'A';   // 90 + 1 - 65 = 26
	// needs to be filled out automatically based on the ciphertext
	static int[] frequency = new int[alphaLength]; //Array size = 26
	static int[] k1 = new int[2], k2 = new int[2];
	static int key1 = 0, key2 = 0;
	static int[] highestFreqCipher = new int[26];
	static int highestFreqSize = 0;

	private static void getFrequency(){
		for(char cipherChar : cipherText.toCharArray())
		  if (Character.isLetter(cipherChar)){
		    int cipher = (int) cipherChar - alphaIndex; // convert letter to number 0 - 25
		    frequency[cipher]++;
		}
		int highest = 0;
		int secondHighest = 0;
		int previous_i = 0;
		char[] letters = new char[26];

		for(int i = 0; i < 26; i++) {
			int temp = i + alphaIndex;
			letters[i] = (char) temp;
		}

		for(int i = 0; i < frequency.length; i ++){
		    System.out.print(letters[i] + ": ");
		    System.out.print(frequency[i] + " - ");
			int temp = highest;

		    if(frequency[i] > highest  ) {
		    	highest = frequency[i];
		    	secondHighest = previous_i;
		    	previous_i = i;
		    }

		} 
		System.out.println();

		// store highest and second highest frequency letters
		// use these two for loop to avoid if there has same frequency letters.
		for(int i = 0; i < frequency.length; i ++){

		    if(frequency[i] >= highest  ) {
		    	highest = frequency[i];
		    	highestFreqCipher[highestFreqSize] = i;
		    	highestFreqSize++;
		    }
		}

		highestFreqCipher[highestFreqSize] = secondHighest;
		highestFreqSize++;

		// for(int i = 0; i < highestFreqSize; i++){
		// 	System.out.println(highestFreqCipher[i]);
		// }
	}

	private static int[] gcd(int p, int q) {
		if( q == 0)
			return new int[] {p, 1, 0};
		int[] values = gcd(q, p % q);
		int d = values[0];
		int a = values[2];
		int b = values[1] - (p / q) * values[2];
		return new int[] {d, a, b};
	}

	private static int inverse(int k, int n) {
		int[] values = gcd(k, n);
		int d = values[0];
		int a = values[1];
		int b = values[2];
		if (d > 1) {return 0;}
		if(a > 0) return a;
		return n + a;
	}

	private static void findKeys(){
		int p1, p2, c1, c2;

		c1 = highestFreqCipher[0];
		c2 = highestFreqCipher[1];
		p1 = 4;
		p2 = 19;

		int dif_p = p1 - p2;
		if(dif_p < 0){
			dif_p += 26;
		}
		int inverseNum = inverse(dif_p,26);
		if( inverseNum != 0){
			k1[0] = ((c1 - c2) * inverseNum ) % 26;
		
		  	if(k1[0] < 0){
	  			k1[0] += 26;
			}
			k2[0] =  (c2 - p2 * k1[0]) % 26;

			if(k2[0] < 0){
	  			k2[0] += 26;
			}
		}
		System.out.println("\nTwo pairs for #1: p1: " +
			(char) (p1 + alphaIndex) + ", " + "c1: " + (char) (c1 + alphaIndex) +
			" & p2: " + (char) (p2 + alphaIndex) + ", " + "c2: " + (char) (c2 + alphaIndex)
			);

		if(frequency[c1] == frequency[c2]){
			System.out.println("\nThere are 2 highest frequency letters : " +
			 (char) (c1 + alphaIndex) + " & " + (char) (c2 + alphaIndex) + " --- " + frequency[c1] + " times.");
			c1 = highestFreqCipher[1];
			c2 = highestFreqCipher[0];
			p1 = 4;
			p2 = 19;

			dif_p = p1 - p2;
			if(dif_p < 0){
				dif_p += 26;
			}
			inverseNum = inverse(dif_p,26);
			if( inverseNum != 0){
				k1[1] = ((c1 - c2) * inverseNum ) % 26;
			
			  	if(k1[1] < 0){
		  			k1[1] += 26;
				}
				k2[1] =  (c2 - p2 * k1[1]) % 26;

				if(k2[1] < 0){
		  			k2[1] += 26;
				}
			}
			System.out.println("Two pairs for #2: p1: " +
				(char) (p1 + alphaIndex) + ", " + "c1: " + (char) (c1 + alphaIndex) +
				" & p2: " + (char) (p2 + alphaIndex) + ", " + "c2: " + (char) (c2 + alphaIndex) + "\n"
				);
		}
	}

	private static int decrypt(int index) {
		index =  ( (index - key2) * inverse(key1, 26)) % 26;
		if(index < 0) index += 26;
		return index;
	}

	public static void main(String [] args) {

		getFrequency();

		findKeys();

		for(int i = 0; i < 2; i++){
			if(k1[i] == 0) break;
			key1 = k1[i];
			key2 = k2[i];
			System.out.println("#" + (i + 1) + ": Key 1: " + key1 +  " --- Key2: " + key2);	
		    for (char cipherChar : cipherText.toCharArray()) {
		      	if (Character.isLetter(cipherChar)) { // only letters are encrypted, punctuation marks and whitespace are not
			        // following line converts letters to numbers between 0 and 25

			        int cipher = (int) cipherChar - alphaIndex; // convert letter to number 0 - 25
			        int plain = decrypt(cipher);
			        // following line coverts numbers between 0 and 25 to letters

			        char plainChar = (char) (plain + alphaIndex);
			   
			        System.out.print(plainChar);
		      	}
		      	else{
			        System.out.print(cipherChar);
		      	}
		  	}
		  	System.out.println("\n");
		}
	}  
}
