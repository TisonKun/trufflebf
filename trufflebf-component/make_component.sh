#!/usr/bin/env bash

COMPONENT_DIR="component_temp_dir"
LANGUAGE_PATH="$COMPONENT_DIR/jre/languages/bf"

rm -rf COMPONENT_DIR

mkdir -p "$LANGUAGE_PATH"
cp ../trufflebf-language/target/trufflebf-language.jar "$LANGUAGE_PATH"

mkdir -p "$LANGUAGE_PATH/launcher"
cp ../trufflebf-launcher/target/trufflebf-launcher.jar "$LANGUAGE_PATH/launcher/"

mkdir -p "$LANGUAGE_PATH/bin"
cp ../bf $LANGUAGE_PATH/bin/

mkdir -p "$COMPONENT_DIR/META-INF"
MANIFEST="$COMPONENT_DIR/META-INF/MANIFEST.MF"
touch "$MANIFEST"
echo "Bundle-Name: TruffleBF" >> "$MANIFEST"
echo "Bundle-Symbolic-Name: io.tison.trufflebf" >> "$MANIFEST"
echo "Bundle-Version: 19.0.0" >> "$MANIFEST"
echo 'Bundle-RequireCapability: org.graalvm; filter:="(&(graalvm_version=19.0.0)(os_arch=amd64))"' >> "$MANIFEST"
echo "x-GraalVM-Polyglot-Part: True" >> "$MANIFEST"

cd $COMPONENT_DIR
jar cfm ../bf-component.jar META-INF/MANIFEST.MF .

echo "bin/bf = ../jre/languages/bf/bin/bf" > META-INF/symlinks
jar uf ../bf-component.jar META-INF/symlinks

echo "jre/languages/bf/bin/bf = rwxrwxr-x" > META-INF/permissions
jar uf ../bf-component.jar META-INF/permissions
cd ..
rm -rf $COMPONENT_DIR
