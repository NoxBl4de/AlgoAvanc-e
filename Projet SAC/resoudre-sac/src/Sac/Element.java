package Sac;

public class Element {
	
	private float valeur, poids, ratio;
	private String nom;
	
	/**
		Construit un objet.
		@param name : le nom de l'objet.
		@param pds : le poids de l'objet.
		@param val : la valeur de l'objet.
	 */
	public Element(String name, float pds, float val) {
		valeur = val;
		poids = pds;
		nom = name;
		ratio = val/pds;
	}

	
	/**
		Getter de la valeur de l'objet.
		@return : la valeur de l'objet.
	 */
	public float getValeur() {
		return valeur;
	}

	
	/**
		Getter du poids de l'objet.
		@return : le poids de l'objet.
	 */
	public float getPoids() {
		return poids;
	}

	/**
		Getter du ratio de l'objet.
		@return : le ratio de l'objet.
	 */
	public float getRatio() {
		return ratio;
	}

	/**
		Getter du nom de l'objet.
		@return : le nom de l'objet.
	 */
	public String getNom() {
		return nom;
	}

	/**
		affichage du nom de l'objet.
		@return : le nom de l'objet.
	 */
	public String toString() {
		String s = "";
		
		s += nom + "\n";
		
		return s;
		
	}
	
	/**
		affichage complet des attributs de l'objet.
		@return : une chaine avec le nom, le poids et la valeur de l'objet.
	 */
	public String toStringFull() {
		String s = "";
		
		s += nom + poids + " " + valeur + "\n";
		
		return s;
		
	}

}
