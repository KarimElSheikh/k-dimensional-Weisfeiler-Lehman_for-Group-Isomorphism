# k-dimensional Weisfeiler-Lehman for Group Isomorphism

A Java implementation of the k-dimensional Weisfeiler-Lehman method to test Group Isomorphism between 2 groups given by a list of generators with various tests to help analyze the method.
It is a 2-dimensional Weisfeiler-Lehman that considers every pair of elements. It is possible to extend it to any k and not very hard even, but it is not on our plans now.

## Information
* Eclipse project folder is "kWL".  
* Test examples are available in the main method of the class "kWLClass". Others are available in the "Checking" class.
* This project is developed and tested using the Eclipse IDE for Java EE Developers, and is ran under Java SE Development Kit 8 for testing.
* Of the information gained from this project so far: 10 pairs of groups of the order 128 are undifferentiated by the 2-dimensional kWL method, and for order 256, for the first 8000 groups (Going by GAP numbering), there are over 150 pairs. The IDs of the pairs for order 128 are outlined in the report.

## k-dimensional Weisfeiler-Lehman
The k-dimensional Weisfeiler-Lehman method is a combinatorial method that is used to test graph isomorphism and has been widely studied in the literature for graph isomorphism. In most cases (informally speaking) it will detect a difference between 2 non-isomorphic graphs, this applies to even the 1-dimensional one. For group isomorphism, the same idea was applied with a sensible notion of an initial colour for every pair of the group (hence 2-dimensional).

## To do
* It is possible to drastically reduce the memory usage if we index the the colors while we haven't still finalized the color set. It is on plan soon.
* Also on plan is to change the serialization of the information gathered by the algorithm to be more portable and future-proof for any class name changes, etc... . Probably, we will seek some text or binary format.
* Rest of the documentation in the classes: "Checking", "kWLclass", and "GroupInvariant".

## Notes about the usage of the batch files
About what the files "OutputGroupsStartingFromOrder*.bat" do:
* They detect the installation directory of GAP through registry<font><sup>[1](#footnote1)</sup></font>, and copy the original "gap.bat" (from gap-4.11.0\bin\ under Windows) to a temporary file in the working directory, then change 2 specific lines in it then run the resulting batch file.

* The changes done to "gap.bat" (effective in temporary file only): Now it changes the working directory to "C:\Users\<User name>\eclipse-workspace\kWL" where we assumed the directory of the Java project will be. And additionally modifies the line that starts GAP ("mintty.exe") to load the respective "OutputGroupsStartingFromOrder*.g" file from the working directory.

* Now it runs the modified and temporary batch file to launch GAP then deletes it immediately.

* You may want do set the working directory and run the files "OutputGroupsStartingFromOrder*.g" in your own way.

<font size="-1"><a name="footnote1">1</a>: Hence, these files only work if you use Windows and installed GAP (not merely extracted the archive or built it from the source).</font>

# License
This project is licensed under [GNU GPL](https://www.gnu.org/licenses/gpl-3.0.html). The Indexed TreeSet and TreeMap [data structures](https://code.google.com/archive/p/indexed-tree-map/) by "Vitaly Sazanovich" are under the [GNU Lesser GPL license](https://www.gnu.org/licenses/lgpl-3.0.html).