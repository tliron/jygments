
Jygments
========

Jygments is a generic syntax highlighter for general use in all kinds of software such as
forum systems, wikis or other applications that need to prettify source code. 

See the [Jygments web site](https://github.com/tliron/jygments) for more information.


Building Jygments
----------------- 

All you need to build Sincerity is [Ant](http://ant.apache.org/).

Simply change to the "/build/" directory and run "ant".

The result of the build will go into the "/build/distribution/" directory. Temporary
files used during the build process will go into "/build/cache/", which you are free to
delete.

To avoid the "bootstrap class path not set" warning during compilation (harmless),
configure the "compile.boot" setting in "private.properties" to the location of an
"rt.jar" file belonging to JVM version 5.


Configuring the Build
---------------------

The "/build/custom.properties" file contains configurable settings, along with some
commentary on what they are used for. You are free to edit that file, however to avoid
git conflicts, it would be better to create your own "/build/private.properties"
instead, in which you can override any of the settings. That file will be ignored by
git.


Deploying to Maven
------------------

You do *not* need Maven to build Jygments, however you can deploy your build to a
Maven repository using the "deploy-maven" Ant target. To enable this, you must install
[Maven](http://maven.apache.org/) and configure its path in "private.properties".
