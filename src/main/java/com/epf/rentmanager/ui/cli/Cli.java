package com.epf.rentmanager.ui.cli;

import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import static com.epf.rentmanager.utils.IOUtils.*;
import static com.epf.rentmanager.utils.IOUtils.print;

public class Cli {
    private ClientService clientService;
    private VehicleService vehicleService;

    public static void main(String[] args) {
        print("Bonjour bienvenue sur le site de reservation ");

        int rep = readInt("Création d'un client tapez 1 / Création d'un vehicule tapez 2 :");
        switch (rep){
            case 1:
                CliCreationClient();
                break;
            case 2:
                CliCreationVehicule();
                break;
            default:
                print("Erreur dans l'entrée");
        }
    }
    public static void CliCreationClient(){
        print("Gestion de la Creation des Client");

    }

    private static void CliCreationVehicule(){
        print("CreationVehicule");

    }


}
