# k-dimensional Weisfeiler-Lehman for Group Isomorphism

An implementation of the k-dimensional Weisfeiler-Lehman method to test Group Isomorphism between 2 groups given by a list of generators.
The implementation is programmed in the Java language.
It is a 2-dimensional Weisfeiler-Lehman that considers every pair of elements. It is possible to extend it to any k and not very hard even, but it is not on my plans now.
Hopefully soon, I will try to package this project in a more proper manner while documenting the main class better...

## Information
* Eclipse project folder is "kWL".  
* Test examples are available in the main method of the class "kWLClass" in the file "kWLClass.java". Others are in a main method that is commented out.
* This project is developed and tested using the Eclipse IDE for Java EE Developers.

## k-dimensional Weisfeiler-Lehman
The k-dimensional Weisfeiler-Lehman method is a combinatorial method that is used to test graph isomorphism and has been widely studied in the literature for graph isomorphism. In most cases (informally speaking) it will detect a difference between 2 non-isomorphic graphs, this applies to even the 1-dimensional one. For group isomorphism, the same idea was applied with a sensible notion of an initial colour for every pair of the group (hence 2-dimensional).

## Technical...
Note that the file run.bat basically detects the installation directory of GAP, and copies the original "gap.bat" to the current directory, changes a few lines in it then it runs it.
The changes in "gap.bat" are as follows:

It changes the line that sets the home directory to be instead the directory of the project "kWL" in the workspace "eclipse-workspace",
and then runs the file "1.g". If you want you can instead do the same (setting the home directory and run the file "1.g" in your own way).

# License
This project is licensed under [GNU Lesser GPL](https://www.gnu.org/licenses/lgpl-3.0.html). The Indexed TreeSet and TreeMap data structures by "Vitaly Sazanovich" are under the same license, the code
for these data structures is exactly the same except for the package name changes as well as the access modifier changes outlined in the beginning of the file "IndexedTreeMap.java".