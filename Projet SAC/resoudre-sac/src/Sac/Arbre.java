package Sac;

import java.util.List;
import java.util.ArrayList;

public class Arbre {
	private List<Element> listObjs;
	private List<Element> listTampon;
	private boolean racine;
	private Arbre noeudG,noeudD;

	/**
	Initialise les attribut  d'un arbre pour pouvoir réaliser sa construction.
 */
	private Arbre() {
		listObjs = new ArrayList<Element>();
		listTampon = new ArrayList<Element>();
		noeudG = null; noeudD = null;
	}

	/**
    	Construit un arbre de listes d'objets.
    	@param lstTmp : la liste tampon des objets disponibles.
    	@param listNoeudPrecedent : liste des objets du noeud precedent.
    	@param put : booléen permettant de savoir si on rajoute ou non l'objet.
    	@param estRacine : nous permet de savoir si le noeud est la racine de l'arbre.
    	@param poidsRestant : permet de savoir le poids du noeud par rapport au poids max de l'arbre
	 */
	public Arbre(List<Element> listTmp, List<Element> listNoeudPrecedent, boolean put, boolean estRacine, float poidsRestant) {
		this();
		listTampon = copyList(listTmp); //pour ne pas altérer les listes en paramètre
		racine = estRacine;
		if(!listTampon.isEmpty()) {
			listObjs = copyList(listNoeudPrecedent); //pour ne pas altérer les listes en paramètre
			float restePoids;
			if (put) {
				listObjs.add(listTampon.get(0));
				restePoids = poidsRestant - listTampon.get(0).getPoids();
			}


			else
				restePoids = poidsRestant;

			if (!racine) //pour pouvoir conserver le premier élément
				listTampon.remove(0);

			if (restePoids >= 0) {
				noeudD = new Arbre(listTampon, listObjs, true, false, restePoids);
				noeudG = new Arbre(listTampon, listObjs, false, false, restePoids);
			}

		}

	}

	/**
		Récupère toutes les solutions disponibles dans l'arbre créé.
		@param listSolutions : une liste de listes d'objets nous permettant de stocker toutes les listes de l'arbre.
		@return : la liste des Listes passées en paramètre.
	 */
	public List<ArrayList<Element>> getSolutions(List<ArrayList<Element>> listSolutions) {
		if (noeudD!=null) {
			if (noeudG!=null) {
				listSolutions.add((ArrayList<Element>) listObjs);
				noeudG.getSolutions(listSolutions);
			}
			else
				listSolutions.add((ArrayList<Element>) listObjs);
				noeudD.getSolutions(listSolutions);
		}

		return listSolutions;
	}
	
	
	/**
		Choisi la meilleure solution dans l'ensemble des solutions de l'arbre.
		@return : la solution ayant la plus grande valeur dans l'ensemble des listes.
	 */
	public List<Element> getBestSolution() {
		ArrayList<Element> lstSolut = new ArrayList<Element>();
		List<ArrayList<Element>> lst = getSolutions(new ArrayList<ArrayList<Element>>());
		float value = getValeurLst(lst.get(0));
		
		for (int i = 1; i < lst.size(); ++i) {
			if (getValeurLst(lst.get(i)) >= value) {
				value = getValeurLst(lst.get(i));
				lstSolut = lst.get(i);
			}
			else continue;
		}
		
		return lstSolut;
	}

	
	/**
		Copie une liste d'objets passée en paramètre.
		@param lst : la liste d'objets à copier
		@return : la copie de la liste passée en paramètre.
	 */
	private List<Element> copyList(List<Element> lst) {
		List<Element> tmp = new ArrayList<Element>();
		for (int i = 0; i < lst.size(); ++i)
			tmp.add(lst.get(i));
		return tmp;
	}

	
	/**
		Récupère la valeur totale d'une liste d'objets.
		@param lst : la liste d'objets à évaluer.
		@return : la valeur de la liste d'objets.
	 */
	private float getValeurLst(List<Element> lst) {
		float poids = 0;
		for (Element o : lst)
			poids += o.getValeur();
		return poids;
	}

	/**
		Affichage d'une liste d'objets dans l'arbre.
		@return : la liste d'objets en chaine de caractères.
	 */
	private String recupList() {
		if (noeudG == null && noeudD == null)
			return "";

		else {
			String s = "{";
			for (int i = 0; i < listObjs.size(); ++i)
				s+= listObjs.get(i).toString();
			s += "}";
			return s;
		}
	}

	@Override
	public String toString() {
		return dessin("\t");
	}

	/**
		Dessin de l'arbre.
		@return : représentation en chaine de caractères de l'arbre construit.
	 */
	public String dessin(String s) {
		if (noeudD!=null) {
			if (noeudG!=null) 
				return(s+recupList()+"\n"+noeudD.dessin(s+"\t")+noeudG.dessin(s+"\t"));
			else
				return(s+recupList()+"\n"+noeudD.dessin(s+"\t")+"\n");
		}
		else {
			if (noeudG!=null) 
				return(s+recupList()+"\n"+noeudG.dessin(s+"\t"));
			else
				return("");
		}
	}
}

