package Sac;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SacADos {
	private float poidsMax;
	private List<Element> objets;
	private List<Element> contenu;

	/**
	 * Initialise les attributs du sac pour pouvoir réaliser sa constrction.
	 */
	private SacADos() {
		objets = new ArrayList<Element>();
		contenu = new ArrayList<Element>();
	}

	/**
		Construit un objet.
		@param name : le chemin d'accèes au contenu du sac.
		@param pds : le poids maximum du sac.
	 */
	public SacADos(String chemin, float poids_max) throws IOException {
		this();

		poidsMax = poids_max;
		File f = new File (chemin);
		FileReader fr = new FileReader (f);
		BufferedReader br = new BufferedReader (fr);

		String line = br.readLine();

		while (line != null) {
			String[] s = line.split(";");
			objets.add(new Element(s[0], Float.parseFloat(s[1]), Float.parseFloat(s[2])));
			line = br.readLine();
		}

		br.close();
		fr.close();

	}

	/**
	 * vide le contenu du sac pour éviter les erreurs
	 */
	private void CleanUp() {
		contenu.clear();
	}
	
	/**
		Résolution du contenu du sac à l'aide de la méthode gloutonne.
	 */
	public void resoudreGlouton() {
		CleanUp(); // permet de vider le contenu du sac
		
		float poidsTotal = 0;

		Collections.sort(objets, new ratioComp()); // tri de la liste d'objets

		for (int i = 0; i < objets.size(); ++i) {
			poidsTotal += objets.get(i).getPoids();
			contenu.add(objets.get(i));
			if (poidsTotal > poidsMax) {
				poidsTotal -= objets.get(i).getPoids();
				contenu.remove(objets.get(i));
			}
		}
		
		System.out.println("\n-------------------------- METHODE GLOUTONNE --------------------------\n");
		System.out.println("POIDS TOTAL : " + poidsTotal + "\n" 
							+ "VALEUR : " + getValeurLst(contenu) + "\n");

	}

	
	/**
	Résolution du contenu du sac à l'aide de la méthode dynamique.
	 */
	public void resoudreDynamic() {
		CleanUp(); // permet de vider le contenu du sac
		
		float[][] matrice = new float[objets.size()][(int)poidsMax+1];
		int i = 0, j = 0;

		while (j <= poidsMax) {
			if (objets.get(0).getPoids() > j)
				matrice[0][j] = 0;
			else matrice[0][j] = objets.get(0).getValeur();
			++j;
		}

		while (i+1 < objets.size()) {
			j = 0; ++i;
			while (j < poidsMax) {
				if (objets.get(i).getPoids() > j) 
					matrice[i][j] = matrice[i-1][j]; 
				
				else matrice[i][j] = maxVal(matrice[i-1][j], 
							matrice[i-1][(int)(j-objets.get(i).getPoids())] 
									+ objets.get(i).getValeur());
				++j;
			} 
		}
		
		do {
			--j;
		} while (matrice[i][j] == matrice[i][j-1]);
		
		while (j > 0) {
			while (i > 0 && matrice[i][j] == matrice[i-1][j])
				--i;
			j = j - (int) objets.get(i).getPoids();
			if (j >= 0)
				contenu.add(objets.get(i));
			if (i > 0)
				--i;
		}

		System.out.println("\n-------------------------- METHODE DYNAMIQUE --------------------------\n");
		System.out.println("POIDS TOTAL : " + getPoidsLst(contenu) + "\n" 
							+ "VALEUR : " + getValeurLst(contenu) + "\n");
		
	}

	/**
	Résolution du contenu du sac à l'aide de la méthode PSE.
	 */
	public void resoudrePse() {
		CleanUp(); // permet de vider le contenu du sac
		
		Arbre tree = new Arbre(getObjets(), new ArrayList<Element>(), false, true, poidsMax) ;
		
		tree.getSolutions(new ArrayList<ArrayList<Element>>());
		
		contenu = tree.getBestSolution();
		
		System.out.println("\n-------------------------- METHODE PSE --------------------------\n");
		System.out.println("POIDS TOTAL : " + getPoidsLst(contenu) + "\n" 
							+ "VALEUR : " + getValeurLst(contenu) + "\n");
		
	}
	
	
	/**
		Comparaison entre deux valeurs flottantes.
		@param f : premier flottant à comparer
		@param g : deuxième flottant à comparer
		@return : la plus grand des deux nombres
	 */
	private static float maxVal(float f, float g) {
		// TODO Auto-generated method stub
		if (f - g > 0) return f;
		return g;
	}

	/**
		Getter de la liste des objets du sac.
		@return : la liste d'objets.
	 */
	public ArrayList<Element> getObjets() {
		return (ArrayList<Element>) objets;
	}
	
	/**
		Getter de la valeur de la liste des objets du sac.
		@return : la valeur de la liste d'objets.
	 */
	private float getValeurLst(List<Element> lst) {
		float poids = 0;
		for (Element o : lst)
			poids += o.getValeur();
		return poids;
	}
	
	
	/**
		Getter du poids total de la liste des objets du sac.
		@return : le poids de la liste d'objets.
	 */
	private float getPoidsLst(List<Element> lst) {
		float poids = 0;
		for (Element o : lst)
			poids += o.getPoids();
		return poids;
	}
	
	/**
		Affichage de la liste des objets disponibles et du contenu du sac.
		@return : la chaine de caractère correspondante.
	 */
	@Override
	public String toString() {
		String s = "";
		if (objets.isEmpty())
			return "Pas d'objets disponibles !";

		else {
			s += "Voici la liste des objets disponibles : \n";
			for (Element o : objets)
				s += o.toStringFull();

			if (contenu.isEmpty())
				s += "\nLe sac n'a pour l'instant pas de contenu !\n";

			else {
				s += "\nVoici le contenu du sac : \n";
				for (Element o : contenu)
					s+= o.toString();
			}
		}

		return s;
	}

}
