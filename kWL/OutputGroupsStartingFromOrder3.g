LoadPackage("sonata");

f := function()
	local a, b, s, G, L, streamFile1, streamFile2, integerFromStream1, curOrder, numberOfGroupsHavingOrder;
	
	# Under Windows by default GAPInfo.UserGapRoot is the directory C:/Users/<username>/_gap
	# Our launcher batch file, changes the home directory
	PrintTo(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-5] }, "/eclipse-workspace/kWL/StreamJavaToGAP.txt"));
	PrintTo(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-5] }, "/eclipse-workspace/kWL/StreamGAPToJava.txt"));
	streamFile1 := InputTextFile(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-5] }, "/eclipse-workspace/kWL/StreamJavaToGAP.txt"));
	streamFile2 := Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-5] }, "/eclipse-workspace/kWL/StreamGAPToJava.txt");
	
	curOrder := 3;  # The starting oder of Groups, change it to match the java call of generateInvariants() in the class "kWLClass"
	numberOfGroupsHavingOrder := NumberSmallGroups(curOrder);
	# Print("Order: ", curOrder, "\n");
	Print("Order: ", curOrder, " (", numberOfGroupsHavingOrder, " groups)\n");
	while true do
		if streamFile1 <> fail then
			a := ReadAll(streamFile1);
			if a <> fail then
				b := SplitString(a, "\n");
				for integerFromStream1 in b do
					if integerFromStream1 <> "" and Int(integerFromStream1) <= numberOfGroupsHavingOrder then
						Print(integerFromStream1, "\n");
						#if o <> fail then
							G := AsPermGroup(SmallGroup(curOrder, Int(integerFromStream1)));
							L := MinimalGeneratingSet(G);
							PrintTo(streamFile2, L{[1..Length(L)]}, "\n");
							#Print(L{[1..Length(L)]}, "\nDone\n");
						#fi;
					else
						curOrder := curOrder + 1;
						Print("\nOrder: ", curOrder, "\n");
						numberOfGroupsHavingOrder := NumberSmallGroups(curOrder);
						PrintTo(streamFile2, numberOfGroupsHavingOrder, "\n");
						break;
					fi;
				od;
			fi;
		fi;
	od;
end;

Print(IO_getcwd(), "\n");
f();