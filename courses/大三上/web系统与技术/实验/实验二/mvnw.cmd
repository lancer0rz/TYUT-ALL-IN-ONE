@echo off
setlocal
set "SCRIPT_DIR=%~dp0"
set "MAVEN_HOME=%SCRIPT_DIR%..\tools\apache-maven-3.9.6"
set "MAVEN_OPTS=%MAVEN_OPTS% -Dmaven.repo.local=%SCRIPT_DIR%\.m2"
"%MAVEN_HOME%\bin\mvn.cmd" %*
endlocal
