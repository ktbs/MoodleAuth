package com.algo;

public class DiagnosticService {
	
	
    // -------------------------------------------------------
    //  Normal trust ActionApp
    // -------------------------------------------------------

    static double NormalTrustActionApp(double trustAP)
    {

        // ------------------------------------------
        //   La formule :
        // ------------------------------------------
        // /                                        /
        // /                  1                     /
        // /       -------------------------        /
        // /          1+Exp(-k*TrustKDMD)           /
        // /                                        /
        // ------------------------------------------


        double normal=0.0 ;
        double k=1 ;

        
            double denominateur=Math.exp(-0.1*trustAP)+1;
            normal=Math.pow(denominateur,-1);
            return normal ;

    }



    // -------------------------------------------------------
    //  Normal Trust Environnement Apprentissage
    // -------------------------------------------------------
    static double NormalTrustCompoAppr (double difftrust)
    {



        // ------------------------------------------
        //   La formule :
        // ------------------------------------------
        // /                                        /
        // /        Exp[ k*Delta(Trust)-1 ]         /
        // /                                        /
        // ------------------------------------------


        double normal=0.0 ;
        double k=1;
        normal=Math.exp(0.1*difftrust-1);
        return normal;

    }



    

    // ------------------------------------------------------
    //  degré Confiance Totale
    // ------------------------------------------------------

    static double DegreConfTotal(double normatTrustsApp, double normalDiffTrust)
    {
        // ------------------------------------------------------
        //   La formule :
        // ------------------------------------------------------
        // /                                                    /
        // /                                                    /
        // /     100 * Somme[ Wi * NormalTrust(trust) ]         /
        // /                                                    /
        // /                                                    /
        // ------------------------------------------------------

        double degre=(0.5*normatTrustsApp + 0.5*normalDiffTrust)*100 ;
      
        return degre;

    }




}
