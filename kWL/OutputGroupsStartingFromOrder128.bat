@Echo off

REM This file's main purpose is to run GAP with the correct working Directory,
REM and open OutputGroupsStartingFromOrder.g that's located in the working Directory.
REM All it does is copy your default gap.bat, modify lines
REM #6 and #7 in the copied version and then run it.

REM Enter your GAP path that contains the bin directory in the next variable,
REM if the variable is left blank, the batch file will proceed to read the GAP
REM path from the registry.
Set "GAPdir="

REM In the next line, set the working directory you'll be using to run the java classes (kWLClass and Checking), and GAP
REM will launch with the working directory specified here so that they're both able to communicate with each other.
Set "GAPandJavaWorkingDir=%HOMEDRIVE%%HOMEPATH%\eclipse-workspace\kWL"

Set "gap49026Temp=%TEMP%.\gap49026.tmp.bat"

if defined GAPdir (
	Set "gap49026Inst=%GAPdir%"
) else (
	for /F "usebackq skip=2 tokens=2* eol= " %%a in (`REG QUERY HKEY_CURRENT_USER\Software\GAP /ve 2^>nul`) do (
		Set "gap49026Inst=%%b"
	)
)

SetLocal EnableDelayedExpansion EnableExtensions
Set "TempFile=%gap49026Temp%"
Set "InstallDirectory=%gap49026Inst%"
Echo( 2>"%TempFile%"
REM Special for loop options parameter which is unquoted with every delimiter escaped, to allow for no delims and no eol
REM (i.e., Just read the whole line).
for /F usebackq^ tokens^=*^ delims^=^ eol^= %%a in ("%InstallDirectory%\bin\gap.bat") do (
	
	Set "Line=%%a"
	Set "Test=!Line:~0,9!"
	REM This next line replaces all double quotes from the string Test by 2 double quotes
	Set Test=!Test:"=""!
	
	if "!Test!"=="cd %%HOME%%" (
		>>"%TempFile%" (
			Echo cd %GAPandJavaWorkingDir%
		)
	) else if "!Test!" == "start ""GA" (
		>>"%TempFile%" (
			Echo !Line! OutputGroupsStartingFromOrder128.g
		)
	) else (
		>>"%TempFile%" (
			Echo !Line!
		)
	)
)
EndLocal

Start /D "%gap49026Inst%" /Wait %ComSpec% /c "%gap49026Temp%"
Del %gap49026Temp%
Set GAPandJavaWorkingDir=
Set gap49026Temp=
Set gap49026Inst=