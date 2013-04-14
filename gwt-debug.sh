#!/bin/bash
export gwt_extraJvmArgs="-javaagent:'/home/rzymek/opt/eclipse/plugins/org.zeroturnaround.eclipse.embedder_5.2.0.SR1-201303112135/jrebel/jrebel.jar' -Drebel.workspace.path='/home/rzymek/workspace' -Drebel.log.file='/home/rzymek/.jrebel/jrebel.log' -Drebel.properties='/home/rzymek/.jrebel/jrebel.properties' -Drebel.notification.url='http://127.0.0.1:44708/jrebel/notifications'"
echo $gwt_extraJvmArgs
mvn -f engine/pom.xml war:exploded gwt:debug -Pdev  -Dgwt.extraJvmArgs="$gwt_extraJvmArgs"
