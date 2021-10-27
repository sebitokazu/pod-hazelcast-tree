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
chmod +x quey1.sh
chmod +x quey2.sh
chmod +x quey3.sh
chmod +x quey4.sh
chmod +x quey5.sh

#go back to parent's project dir
cd ..
cd ..
cd ..

echo "Client scripts available at client/target/tpe2-g3-client-1.0-SNAPSHOT"