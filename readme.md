#  Exercice 4 (Spring Batch) :

Objectifs :

* Compréhension des notions de _job_, _step_, _chunk_, _tasklet_
* Implémentation d'une _tasklet step_
* Implémentation d'un _chunk oriented step_
* Démarrage et tests d'un `Job`
* Utilisation des _job parameters_
* Accès aux statistiques
*****

1. Créer un `Job` `ReservationJob` qui déclare comme seul _step_ (pour l'instant) une petite tâche qui affiche dans la console "hello".

2. Créer une classe de test `ReservationJobTest`. Celle-ci doit:

    * intégrer le fichier `classpath:application-test.properties` .
    * vérifier la bonne exécution du _job_

3. Reprendre la définition du _job_. Celui-ci doit désormais:

    1. Obtenir en base de données les réservations du jour.

        Datasource : `new DriverManagerDataSource("jdbc:postgresql:formation_spring", "postgres", null)`

        Requête SQL :
        ```sql
        select b.id, b.title, m.username from Reservation r 
        join Member m on r.member_id = m.id 
        join Book b on r.book_id = b.id
        ```
    2. Fabriquer pour chaque ligne une instance de `ReservationRow` (à créer)

    3. Ecrit les instances de `ReservationRow` dans un fichier csv (c:\formation_spring-plus\files\reservations.csv)

    4. Envoie le fichier reservations.csv par mail (ne pas écrire le code d'implémentation de l'envoi du mail,seulement `System.out.println("send mail");`).
