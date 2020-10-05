@ECHO OFF

IF [%1]==[] (
    echo "Usage: %0 {full_build_start|backend_build|frontend_build|backend_start|frontend_start}"
    GOTO END
)

IF %1==full_build_start (
    CALL :backend_build
    CALL :frontend_build
    CALL :backend_start
    CALL :frontend_start
    CALL :open_browser
    GOTO END
)
IF %1==backend_build (
    CALL :backend_build
    GOTO END
)
IF %1==frontend_build (
    CALL :frontend_build
    GOTO END
)
IF %1==backend_start (
    CALL :backend_start
    GOTO END
)
IF %1==frontend_start (
    CALL :frontend_start
    GOTO END
)
echo "Usage: %0 {full_build_start|backend_build|frontend_build|backend_start|frontend_start}"
:END
EXIT /B %ERRORLEVEL%

:backend_build
    call mvn -v
    call mvn install
EXIT /B 0

:frontend_build
    cd %CD%\gre-todo-frontend
    call node -v
    call npm install
    cd ..
EXIT /B 0

:backend_start
    cd %CD%\gre-todo-server
    start cmd /k call mvn spring-boot:run
    cd ..
EXIT /B 0

:frontend_start
    cd %CD%\gre-todo-frontend
    start cmd /k call npm run serve --open
    cd ..
EXIT /B 0