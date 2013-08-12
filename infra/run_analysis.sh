#!/bin/bash

JAVA_EXEC="/home/brind/jdk1.7.0_21/bin/java"
ECLIPSE_HOME="/home/brind/eclipse"
PROJECT_HOME="/home/brind/gitsvn"
PROJECT_NAME="SvnToGit"
PORT_NO=1999
COLLECTOR_CLASSPATH="$ECLIPSE_HOME/plugins/org.eclipse.jdt.junit_3.7.0.v20110928-1453.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.jdt.junit.core_3.7.0.v20110928-1453.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-antlr.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-bcel.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-bsf.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-log4j.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-oro.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-regexp.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-resolver.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-apache-xalan2.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-commons-logging.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-commons-net.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-jai.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-javamail.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-jdepend.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-jmf.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-jsch.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-junit.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-junit4.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-launcher.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-netrexx.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-swing.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant-testutil.jar\
:$ECLIPSE_HOME/plugins/org.apache.ant_1.8.2.v20120109-1030/lib/ant.jar\
:$ECLIPSE_HOME/plugins/org.junit_4.8.2.v4_8_2_v20110321-1705/junit.jar\
:$ECLIPSE_HOME/plugins/org.hamcrest.core_1.3.0.v201303031735.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.core.runtime_3.7.0.v20110110.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.osgi_3.7.2.v20120110-1415.jar\
:$ECLIPSE_HOME/plugins/javax.transaction_1.1.1.v201105210645.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.equinox.common_3.6.0.v20110523.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.core.jobs_3.5.101.v20120113-1953.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.core.runtime.compatibility.registry_3.5.0.v20110505/runtime_registry_compatibility.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.equinox.registry_3.5.101.R37x_v20110810-1611.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.equinox.preferences_3.4.2.v20120111-2020.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.core.contenttype_3.4.100.v20110423-0524.jar\
:$ECLIPSE_HOME/plugins/org.eclipse.equinox.app_1.3.100.v20110321.jar\
:$PROJECT_HOME/workspace/PDETestUtils/bin"

ECLIPSE_TEST_CLASSPATH="$ECLIPSE_HOME/plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar \
org.eclipse.equinox.launcher.Main"

CSV_FILES="../../results"
RECOVERY_FILE="$CSV_FILES/restore_point.txt"
REPO_LOC="../../svnToGitRepos"

JUNIT_LISTENER="$JAVA_EXEC -Dfile.encoding=UTF-8 -classpath $COLLECTOR_CLASSPATH \
pde.test.utils.PDETestResultsCollector $PROJECT_NAME $PORT_NO &"

while read init_line
do
	echo "Running for $init_line"

	$JAVA_EXEC -Dfile.encoding=UTF-8 -classpath $COLLECTOR_CLASSPATH \
pde.test.utils.PDETestResultsCollector $PROJECT_NAME $PORT_NO &

	$JAVA_EXEC -Xms128m -Xmx12g -XX:MaxPermSize=512M \
	-Declipse.pde.launch=true -Declipse.p2.data.area=@config.dir/p2 -Dfile.encoding=UTF-8 \
	-Dedu.illinois.gitsvn.initline=$init_line \
	-classpath $ECLIPSE_TEST_CLASSPATH \
	-os linux -ws gtk -arch x86_64 -nl en_US -consoleLog -version 3 \
	-port $PORT_NO \
	-testLoaderClass org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader \
	-loaderpluginname org.eclipse.jdt.junit4.runtime \
	-classNames edu.illinois.gitsvn.analysis.launchers.StartProjectLauncher \
	-application org.eclipse.pde.junit.runtime.uitestapplication -product org.eclipse.sdk.ide \
	-data $PROJECT_HOME/workspace/../junit-workspace \
	-configuration file:$PROJECT_HOME/workspace/.metadata/.plugins/org.eclipse.pde.core/pde-junit/ \
	-dev file:$PROJECT_HOME/workspace/.metadata/.plugins/org.eclipse.pde.core/pde-junit/dev.properties \
	-os linux -ws gtk -arch x86_64 -nl en_US -consoleLog \
	-testpluginname infra

	while [ -e $RECOVERY_FILE ]
	do
		rec_line=$(head -n 1 $RECOVERY_FILE)

		rm $RECOVERY_FILE

		$JAVA_EXEC -Dfile.encoding=UTF-8 -classpath $COLLECTOR_CLASSPATH \
pde.test.utils.PDETestResultsCollector $PROJECT_NAME $PORT_NO &

		$JAVA_EXEC -Xms128m -Xmx12g -XX:MaxPermSize=512M \
		-Declipse.pde.launch=true -Declipse.p2.data.area=@config.dir/p2 -Dfile.encoding=UTF-8 \
		-Dedu.illinois.gitsvn.recoveryLine=$rec_line \
		-classpath $ECLIPSE_TEST_CLASSPATH \
		-os linux -ws gtk -arch x86_64 -nl en_US -consoleLog -version 3 \
		-port $PORT_NO \
		-testLoaderClass org.eclipse.jdt.internal.junit4.runner.JUnit4TestLoader \
		-loaderpluginname org.eclipse.jdt.junit4.runtime \
		-classNames edu.illinois.gitsvn.analysis.launchers.RecoverableLauncher \
		-application org.eclipse.pde.junit.runtime.uitestapplication -product org.eclipse.sdk.ide \
		-data $PROJECT_HOME/workspace/../junit-workspace \
		-configuration file:$PROJECT_HOMEworkspace/.metadata/.plugins/org.eclipse.pde.core/pde-junit/ \
		-dev file:$PROJECT_HOME/workspace/.metadata/.plugins/org.eclipse.pde.core/pde-junit/dev.properties \
		-os linux -ws gtk -arch x86_64 -nl en_US -consoleLog \
		-testpluginname infra
	done

done
