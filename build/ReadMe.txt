Für die Ausführung des .jar wird Java 3D benötigt. Dies muss vom System vorhanden sein.

ParticleSimulation - v2.jar ist die aktuelle Version.


Run with:
java -Djava.library.path="<PATH-TO-3D-DDL>" -jar „ParticleSimulation - v2.jar“ 201

Beispiel
java -Djava.library.path="C:\Program Files\Java\Java3D\1.5.1\bin" -jar „ParticleSimulation - v2.jar“ 201
java -jar "ParticleSimulation - v2.jar" 4

There algorithm with number:

Gravity Algorithm
1-3:		Random, Slice 2d, Slice 3d
4-5:		Slice 2d, Slice 3d

nBody
101-103:	Random, Slice 2d, Slice 3d

Barnes Hut
201-203		Random, Slice 2d, Slice 3d