LoadPackage("sonata");

f := function()
	local s, a, b, x, o, G, L, fileName, order, numberHavingOrder, testInt;
	
	PrintTo(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-4] }, "Stream.txt"));
	PrintTo(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-4] }, "Stream2.txt"));
	
	fileName := Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-4] }, "Stream2.txt");
	
	s := InputTextFile(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-4] }, "Stream.txt"));
	#o := OutputTextFile(Concatenation(GAPInfo.UserGapRoot{ [1..Length(GAPInfo.UserGapRoot)-4] }, "Stream2.txt"), true);
	order := 256;
	numberHavingOrder := 56092;
	Print("Order: ", 256, "\n");
	while true do
		if s <> fail then
			a := ReadAll(s);
			if a <> fail then
				b := SplitString(a, "\n");
				for x in b do
					if x <> "" and Int(x) <= numberHavingOrder then
						Print(x, "\n");
						#if o <> fail then
							G := AsPermGroup(SmallGroup(order, Int(x)));
							L := MinimalGeneratingSet(G);
							PrintTo(fileName, L{[1..Length(L)]}, "\n");
							#Print(L{[1..Length(L)]}, "\nDone\n");
						#fi;
					else
						order := order + 1;
						Print("\nOrder: ", order, "\n");
						numberHavingOrder := NumberSmallGroups(order);
						PrintTo(fileName, numberHavingOrder, "\n");
						break;
					fi;
				od;
			fi;
		fi;
	od;
end;

f();