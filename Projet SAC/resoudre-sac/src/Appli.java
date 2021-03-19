import java.io.IOException;
import Sac.SacADos;

public class Appli {

	public static void main(String[] args) throws IOException {
		
//		SacADos sac = new SacADos("C:\\IUT 2A\\AAV\\Projet SAC\\objects.txt", (float) 9.0);
		SacADos sac = new SacADos(args[0], Float.parseFloat(args[1]));
		String method = args[2];
		
//		System.out.println(sac.toStrinC	g());
		
//		sac.resoudreGlouton();
//		System.out.println("***************************\n");
//		System.out.println(sac.toString());
//		
//		sac.resoudreDynamic();
//		System.out.println("***************************\n");
//		System.out.println(sac.toString());
//		
//		sac.resoudrePse();
//		System.out.println("***************************\n");
//		System.out.println(sac.toString());
		
		if (method.equals("gloutonne"))
			sac.resoudreGlouton();
		else if (method.equals("prog.dynamique"))
			sac.resoudreDynamic();
		else if (method.equals("pse"))
			sac.resoudrePse();
		else
			System.out.println("Méthode inexistante !");
		
		System.out.println(sac.toString());
		
	}

}
