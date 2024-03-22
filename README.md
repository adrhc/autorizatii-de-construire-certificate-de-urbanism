# Project description

Use this to search for construction authorizations or town planning certificates.

# git setup

git rm -r --cached .idea
git branch --set-upstream-to=origin/master master

# maven project creation

mvn archetype:generate -DgroupId=ro.go.adrhc -DartifactId=autorizatii-de-construire-certificate-de-urbanism
-DarchetypeArtifactId=maven-archetype-quickstart
