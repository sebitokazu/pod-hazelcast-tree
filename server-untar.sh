#!/bin/bash
#arguments: [$1: project.name, $2: project.version]

#Aborts on error
set -e
#enter server/target folder
cd server/target

# untar
gzip="tpe2-g3-server-1.0-SNAPSHOT-bin.tar.gz"
tar -xzf $gzip

#cd into the folder name
cd "tpe2-g3-server-1.0-SNAPSHOT"

#give execution permission to scripts
chmod +x run-registry.sh #TODO: Creo que ese archivo hay que eliminarlo
chmod +x run-server.sh

#go back to parent's project dir
cd ..
cd ..
cd ..

echo "Server scripts available at server/target/tpe2-g3-server-1.0-SNAPSHOT"