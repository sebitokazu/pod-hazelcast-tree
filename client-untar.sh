#!/bin/bash
#Improvement: arguments: [$1: project.name, $2: project.version]

#Aborts on error
set -e
#enter server/target folder
cd client/target

# untar
gzip="tpe2-g3-client-1.0-SNAPSHOT-bin.tar.gz"
tar -xzf $gzip

#cd into the folder name
cd "tpe2-g3-client-1.0-SNAPSHOT"

#give execution permission to scripts
chmod +x query1.sh
chmod +x query2.sh
chmod +x query3.sh
chmod +x query4.sh
chmod +x query5.sh

#go back to parent's project dir
cd ..
cd ..
cd ..

echo "Client scripts available at client/target/tpe2-g3-client-1.0-SNAPSHOT"