# jvm2llvm

Programme pour convertir des classes java compilés (*.class) en code executable. La compilation est faites avec LLVM.
La version initiale a été faites avec LLVM 3.6

Il y a 3 main :
- src/main/java/org/backend/ssa/MainSSA.java : Pour tester la conversion en SSA
- src/main/java/org/jvm2llvm/Tools.java : Pour générer le programme de conversion du CSV doc/tools/opcodes3.csv en  une classe Enum Java
- src/main/java/org/jvm2llvm/App.java : Le programme principal

