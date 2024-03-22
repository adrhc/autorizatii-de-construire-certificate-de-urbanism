# git cleanup
git rm -r --cached .idea

# maven project creation

mvn archetype:generate -DgroupId=ro.go.adrhc -DartifactId=autorizatii-de-construire-certificate-de-urbanism
-DarchetypeArtifactId=maven-archetype-quickstart
