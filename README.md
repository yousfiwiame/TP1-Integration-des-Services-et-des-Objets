# TP1 - Application CORBA Hello World

## Description
Ce projet implémente une application CORBA simple qui permet à un client de communiquer avec un serveur pour afficher un message "Hello World".

## Structure du projet
```
TP1/
├── hello.idl                    # Interface IDL
├── ior.txt                      # Fichier IOR généré par le serveur
├── hello/                       # Package contenant les classes Java
│   ├── Client.java             # Client CORBA
│   ├── Server.java             # Serveur CORBA
│   ├── HelloImpl.java          # Implémentation de l'objet Hello
│   └── [Classes générées par idlj]
└── README.md                   # Ce fichier
```

## Prérequis
- JDK 1.8 ou supérieur
- Compilateur IDL (idlj) inclus dans le JDK

## Instructions d'installation et d'exécution

### 1. Compilation du fichier IDL
```bash
idlj -fall hello.idl
```
**Vérification :** Un répertoire `hello` doit être créé avec les classes générées.

### 2. Compilation des classes Java
```bash
javac hello/*.java
```

### 3. Exécution du serveur
```bash
java hello.Server
```
**Résultat attendu :**
- Le serveur démarre et affiche "hello.Server ready. IOR saved to ior.txt"
- Un fichier `ior.txt` est créé contenant la référence d'objet IOR
- Le serveur reste en attente de connexions

### 4. Exécution du client
```bash
java hello.Client
```
**Résultat attendu :**
- Le client se connecte au serveur
- Affiche "Response from hello.Server: Hello, World!"

## Exécution sur machines différentes

### Pour lancer le client depuis une autre machine :

1. **Copier les fichiers nécessaires :**
   - `hello/Client.class`
   - `hello/HelloHelper.class`
   - `hello/HelloHolder.class`
   - `hello/HelloOperations.class`
   - `hello/HelloPOA.class`
   - `hello/HelloStub.class`
   - `ior.txt`

2. **Modifier l'adresse IP dans l'IOR :**
   - Ouvrir `ior.txt`
   - Remplacer l'adresse IP locale par l'adresse IP du serveur
   - Sauvegarder le fichier

3. **Exécuter le client :**
   ```bash
   java hello.Client
   ```

## Commandes utiles

### Compilation complète
```bash
# 1. Générer les classes IDL
idlj -fall hello.idl

# 2. Compiler toutes les classes
javac hello/*.java

# 3. Nettoyer les fichiers .class
rm hello/*.class
```

### Débogage
```bash
# Activer les logs CORBA
java -Dorg.omg.CORBA.ORBInitialHost=localhost hello.Server
java -Dorg.omg.CORBA.ORBInitialPort=1050 hello.Client
```

## Architecture CORBA

### Composants principaux :
1. **Interface IDL** (`hello.idl`) : Définit le contrat de service
2. **Serveur** (`Server.java`) : Héberge l'objet CORBA
3. **Client** (`Client.java`) : Consomme le service
4. **Implémentation** (`HelloImpl.java`) : Logique métier

### Flux de communication :
1. Le serveur crée un objet CORBA et génère un IOR
2. Le client lit l'IOR et se connecte au serveur
3. Le client appelle la méthode `sayHello()`
4. Le serveur retourne "Hello, World!"

## Dépannage

### Problèmes courants :
1. **Port déjà utilisé** : Changer le port dans les propriétés ORB
2. **Fichier IOR introuvable** : Vérifier que le serveur a bien démarré
3. **Erreur de connexion** : Vérifier l'adresse IP dans l'IOR

### Solutions :
```bash
# Vérifier les ports utilisés
netstat -an | grep 1050

# Tuer un processus sur un port
taskkill /F /PID <PID>
```

## Notes techniques

- **ORB (Object Request Broker)** : Gère la communication entre client et serveur
- **POA (Portable Object Adapter)** : Adaptateur pour les objets CORBA
- **IOR (Interoperable Object Reference)** : Référence unique de l'objet distant
- **IDL (Interface Definition Language)** : Langage de définition d'interface

## Auteur
[Votre nom] - TP1 Intégration des Services et des Objets
