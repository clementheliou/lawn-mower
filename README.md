# README #

## Préambule ##

Le projet LawnMower, contenu dans ce *repository*, constitue ma réponse au test technique que je me suis vu proposé par Xebia.

La version actuelle est la `1.0-SNAPSHOT`.

---

## Démarrage rapide ##
Deux possibilités s'offrent à vous:

1. Utiliser le JAR *standalone* proposé;
2. cloner ce *repository*, construire l'application et la lancer.

### Solution n°1: JAR *standalone* ###
#### Pré-requis ####
Vous devez disposer d'un JRE en version 6 ou supérieure.
#### Etape n°1: télécharger l'application ####
Téléchargez la [dernière version](https://bitbucket.org/mistertie/xebia-test/downloads/lawn-mower-cli-1.0-SNAPSHOT-jar-with-dependencies.jar) de l'application en date du **09/09/2014**.
#### Etape n°2: lancer l'application ####
Placez-vous dans le dossier où se trouve le JAR téléchargé (`lawn-mower-cli-1.0-SNAPSHOT-jar-with-dependencies.jar`) que vous pouvez lancer avec la commande suivante:

	java -jar lawn-mower-cli-1.0-SNAPSHOT-jar-with-dependencies.jar


### Solution n°2: construction de l'application ###
#### Pré-requis ####
Vous devez disposer:

* d'un JDK en version 6 ou supérieure;
* de Maven en version 3 ou supérieure;
* de Git. 

#### Etape n°1: cloner l'application ####
Clonez l'application à l'aide de la commande suivante:

	git clone https://bitbucket.org/mistertie/xebia-test.git
	
Un identifiant et un mot de passe vous sont demandés et vous ont été fournis pour accéder à ce *repository* privé.
#### Etape n°2: construire l'application ####
Entrez dans le répertoire de l'application:

	cd lawn-mower/
	
Puis, lancez la construction de l'application via:

	mvn clean install -U
	
#### Etape n°3: lancer l'application ####
Une fois la construction terminée, rendez-vous dans le dossier `target` du module `lawn-mower-cli`:

	cd lawn-mower-cli/target/
	
Vous pouvez y trouver le JAR `lawn-mower-cli-1.0-SNAPSHOT-jar-with-dependencies.jar`que vous pouvez lancer avec la commande suivante:

	java -jar lawn-mower-cli-1.0-SNAPSHOT-jar-with-dependencies.jar

---
## Guide d'utilisation ##
L'usage de l'application est le suivant:

	Usage: lawn-mower-cli <file>
	
  		<file>
        	source file path

Un seul argument est attendu en entrée; ce dernier doit être un fichier existant. Cet usage vous est affiché pour rappel en cas d'erreur. Le libellé `lawn-mower-cli`est bien évidemment à remplacer par le nom du JAR.
### Fichiers de test
Quelques exemples vous sont fournis pour vous familiariser avec l'application:

Fichier | Cas d'usage
--------|------------
[singleMower](https://bitbucket.org/mistertie/xebia-test/downloads/singleMower)|Une tondeuse seule.
[singleMowerAtLawnBounds](https://bitbucket.org/mistertie/xebia-test/downloads/singleMowerAtLawnBounds)|Une tondeuse seule qui tente de franchir les limites de la pelouse.
[multipleMowersWithoutCrash](https://bitbucket.org/mistertie/xebia-test/downloads/multipleMowersWithoutCrash)|Plusieures tondeuses qui n'entrent pas en collision.
[multipleMowersWithCrash](https://bitbucket.org/mistertie/xebia-test/downloads/multipleMowersWithCrash)|Plusieures tondeuses qui engendrent des collisions.

---
## Notes techniques ##
### Technologies utilisées ###

* Maven;
* Scala/ScalaTest;
* [Scopt](https://github.com/scopt/scopt) pour la CLI.

### Architecture ###
L'application est découpée en modules selon la correspondance suivante:

Module            | Description
------------------|------
lawn-mower-cli    | contient l'interface de ligne de commande permettant de faire usage de l'application.
lawn-mower-core   | contient la logique "pure" de l'application; fonctionnement des pelouses et des tondeuses.
lawn-mower-parsing| contient la logique de *parsing* des fichiers sources.
lawn-mower-test   | contient quelques utilitaires pour faciliter les tests (*tags*, classes ScalaTest de base).

Il est important de noter que les modules `lawn-mower-core`et `lawn-mower-parsing` n'ont pas de dépendance entre eux. C'est le module `lawn-mower-cli`qui fait le lien.
### Désactivation des tests d'intégration ###
Lors de la construction de l'application ou du lancement de tout autre *goal* Maven faisant appel à la phase *test*, vous disposez d'un profil `skipIntegrationTests` pour désactiver les tests d'intégration (qui ne sont cependant pas, rassurez-vous, très longs). 

Cela donne donc par exemple:

	mvn clean test -PskipIntegrationTests

---
## Rappel des spécifications ##
### Bases ###
La spécification de l'exercice était, mots pour mots, celle-ci:
> La société MowItNow a décidé de développer une tondeuse à gazon automatique, destinée aux surfaces rectangulaires.
> 
> La tondeuse peut être programmée pour parcourir l'intégralité de la surface.
>
> La position de la tondeuse est représentée par une combinaison de coordonnées `(x,y)`et d'une lettre indiquant l'orientation selon la notation cardinale anglaise (`N,E,W,S`). La pelouse est divisée en grille pour simplifier la navigation.
>
> Par exemple, la position de la tondeuse peut être `0,0,N`, ce qui signifie qu'elle se situe dans le coin inférieur gauche de la pelouse, et orientée vers le Nord.
>
> Pour contrôler la tondeuse, on lui envoie une séquence simple de lettres. Les lettres possibles sont `D`, `G`et `A`. `D`et `G`font pivoter la tondeuse de 90° à droite ou à gauche respectivement, sans la déplacer. `A` signifie que l'on avance la tondeuse d'une case dans la direction à laquelle elle fait face, et sans modifier son orientation.
> 
> Si la position après mouvement est en dehors de la pelouse, la tondeuse ne bouge pas, conserve son orientation et traite la commande suivante.
> 
> On assume que la case directement au Nord de la position `(x,y)` a pour coordonnées `(x,y+1)`.
> 
> Pour programmer la tondeuse, on lui fournit un fichier d'entrée construit comme suit:
> 
> * La première ligne correspond aux coordonnées du coin supérieur droit de la pelouse, celles du coin inférieur gauche sont supposées être `(0,0)`.
> * La suite du fichier permet de piloter toutes les tondeuses qui ont été déployées. Chaque tondeuse a deux lignes la concernant:
> 	* la première ligne donne la position initiale de la tondeuse, ainsi que son orientation. La position et l'orientation sont fournies sous la forme de 2 chiffres et une lettre, séparés par un espace.
> 	* la seconde ligne est une série d'instructions ordonnant à la tondeuse d'explorer la pelouse. Les instructions sont une suite de caractères sans espaces.
> 	
> Chaque tondeuse se déplace de façon séquentielle, ce qui signifie que la seconde tondeuse ne bouge que lorsque la premières a exécuté intégralement sa série d'instructions.
> 
> Lorsqu'une tondeuse achève une série d'instructions, elle communique sa position et son orientation.
> 
> OBJECTIF
> 
> Concevoir et écrire un programme s'executant sur une JVM >= 6, implémentant la spécification ci-dessus et passant le test ci-après:
> 
> TEST
> 
> Le fichier suivant est fourni en entrée:

	5 5
	1 2 N
	GAGAGAGAA
	3 3 E
	AADAADADDA
> 
> On attend le résultat suivant (position finale des tondeuses):

	1 3 N
	5 1 E

> NB: Les données en entrée peuvent être injectées sous une autre forme qu'un fichier (par exemple un test automatisé).


### Précisions ###
La base de spécification ayant donné lieu à des questionnements, voici les arbitrages portés.

> * Si la position après mouvement est déjà occupée, la tondeuse ne bouge pas, conserve son orientation et traite la commande suivante.
> * La création d'une tondeuse ne peut se faire:
> 	* sur une position hors de la pelouse;
> 	* sur une position déjà prise.
> * Une pelouse doit avoir à minima 2 points (i.e. ne peut avoir `(0,0)` comme coin supérieur droit).
> * Le fichier fournit en entrée doit strictement respecter le format décrit dans les spécifications. Seuls les espaces multiples sont acceptés:
> 	* en début et fin de lignes;
> 	* là où un seul espace est attendu.
> * Toutes les tondeuses sont initialiées dans leur ordre de déclaration PUIS, toutes sont exécutées séquentiellement.

---
## A propos de l'auteur ##
Vous pouvez retrouver ma présentation sur [mon blog](http://mister-tie.fr/blog/a-propos-de-lauteur/) et/ou me suivre sur [Twitter](https://twitter.com/c_heliou) ainsi que dans la [communauté Développez.com](http://cheliou.developpez.com).