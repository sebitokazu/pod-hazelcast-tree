# tpe2-g3 - Procesamiento de datos de arbolado publico

Trabajo práctico para la materia Programación de Objetos Distribuidos, utilizando el modelo
de programación MapReduce y el framework Hazelcast


## Instrucciones de instalacion
Tener instalado Maven y correr parado en la carpeta raíz:
```sh
$> mvn clean install
```
### Server
Para descomprimir el script del server, ejecutar en la carpeta raíz:
```sh
$> ./server-untar.sh
```
En caso de conflicto con los permisos en el bash script, asignarle permisos de ejecución con:
```sh
$> chmod +x server-untar.sh
```
Esto disponibiliza el script para correr el server en server/target/tpe2-g3-server-1.0-SNAPSHOT

Cambiar a dicho directorio y levantar el server con:
```sh
$> ./run-server.sh
```

### Clientes
Para descomprimir los scripts de los clientes, ejecutar en la carpeta raíz:
```sh
$> mvn clean install
```
En caso de conflicto con los permisos en el bash script, asignarle permisos de ejecución con:
```sh
$> chmod +x client-untar.sh
```
Esto disponibilizará los scripts para correr las queries en client/target/tpe2-g3-client-1.0-SNAPSHOT

Cambiar a dicho directorio y correr en distintas terminales las queries.

### Ejemplos de invocación
```sh
$> ./queryX.sh -city=C -addresses="xx.xx.xx.xx:XXXX;yy.yy.yy.yy:YYYY" -inPath=XX -outPath=YY [params]
```
donde
 - queryX es el script que corre la query X.
 - -city indica con qué dataset de ciudad se desea trabajar. Los únicos valores
posibles son BUE y VAN.
 - -addresses refiere a las direcciones IP de los nodos con sus puertos (una o más,
separadas por punto y coma)
 - -inPath indica el path donde están los archivos de entrada de barrios y de
árboles.
 - -outPath indica el path donde estarán ambos archivos de salida queryX.csv y timeX.txt.
 - [params]: los parámetros extras que corresponden para algunas queries.

#### Query 1: Total de árboles por barrio
```sh
$> ./query1.sh -city=BUE -addresses=127.0.0.1:5702 -inPath=. -outPath=.
```
#### Query 2: Para cada barrio, la especie con mayor cantidad de arboles por habitante
```sh
$> ./query2.sh -city=VAN -addresses=127.0.0.1:5702 -inPath=. -outPath=.
```
#### Query 3:  Top N barrios con mayor cantidad de especies distintas
```sh
$> ./query1.sh -city=BUE -addresses=127.0.0.1:5702 -inPath=. -outPath=. -n=5
```
 - -n indica el top N que se retorna en la consulta
#### Query 4: Pares de barrios que registran la misma cantidad de cientos de especies distintas
```sh
$> ./query4.sh -city=VAN -addresses=127.0.0.1:5702 -inPath=. -outPath=.
```
#### Query 5: Pares de calles de un barrio X que registran la misma cantidad de decenas de árboles de una especie Y
```sh
$> ./query5.sh -city=BUE -addresses=127.0.0.1:5702 -inPath=. -outPath=. -neighbourhood="3" -commonName="Ficus benjamina"
```
 - -neighbourhood indica el barrio de filtro de la consulta
 - -commonName indica la especie de arbol de filtro de la consulta

### Observación
En caso de que alguno de los scripts falle proceder de la siguiente manera:
- Dentro de los proyectos server y client entrar en la carpeta target.
- Descomprimir los *-bin.tar.gz (usando tar -xzf <path>).
- Entrar en la carpeta que aparece y  dar permisos de ejecución a los scripts run-*.sh (chmod u+x <path>).

## Autores

- **Lautaro Galende** <<lgalende@itba.edu.ar>>
- **Sebastián Itokazu** <<sitokazu@itba.edu.ar>>
- **Valentín Ratti** <<vratti@itba.edu.ar>>
- **Tommy Rosenblatt** <<trosenblatt@itba.edu.ar>>
