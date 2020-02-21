@echo off

setlocal DisableDelayedExpansion enableextensions

set KeyName=HKEY_CURRENT_USER\Software\GAP
for /F "usebackq skip=2 tokens=3" %%A IN (`REG QUERY %KeyName% /ve 2^>nul`) DO (
    set InstallDirectory=%%A
)

copy %InstallDirectory%\bin\gap.bat %cd%\gap29548.tmp /Y >nul
echo. 2>"%cd%\gap29548.tmp.bat"
for /F "tokens=*" %%a in (gap29548.tmp) do (
	set "myVar=%%a"
	
	setlocal enabledelayedexpansion
	set test=!myVar:~0,8!
	Rem // This line removes all Double Quotes from the String Test
	Set test=!test:"=!
	if "!test!"=="set HOME" (
		>>"%cd%\gap29548.tmp.bat" (
			echo set HOME=%HOMEDRIVE%%HOMEPATH%\eclipse-workspace\kWL
		)
	) else if "!test!"=="start G" (
		>>"%cd%\gap29548.tmp.bat" (
			echo !myVar! "%%HOME%%\1.g"
		)
	) else (
		>>"%cd%\gap29548.tmp.bat" (
			echo !myVar!
		)
	)
	endlocal
)
goto :writtenTheBatchFileNowRunIt REM (to run GAP with the correct working Directory)

:writtenTheBatchFileNowRunIt
call gap29548.tmp.bat
