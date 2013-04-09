#!/bin/bash
export gwt_extraJvmArgs=
echo $gwt_extraJvmArgs
mvn -f engine/pom.xml war:exploded gwt:debug -Pdev  -Dgwt.extraJvmArgs="$gwt_extraJvmArgs"
