# Project description

Use this to search for construction authorizations or town planning certificates.

# usage

java -jar autorizatii-de-construire-certificate-de-urbanism/target/autorizatii-de-construire-certificate-de-urbanism-1.0-SNAPSHOT.jar --spring.config.additional-location=$HOME/autorizatii-de-construire-certificate-de-urbanism/config/'

# git setup

git rm -r --cached .idea
git branch --set-upstream-to=origin/master master

# maven project creation

mvn archetype:generate -DgroupId=ro.go.adrhc -DartifactId=autorizatii-de-construire-certificate-de-urbanism
-DarchetypeArtifactId=maven-archetype-quickstart
