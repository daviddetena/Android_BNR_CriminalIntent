#Criminal Intent
###Android Programming - Big Nerd Ranch, 2nd ed.

This file explains the changes introduced along the development of this app.

####Chapter 7:
Our *MVC* is composed by:

* Model: a class called **Crime**.
* Controller: one Activity, called **ActivityCrime**, and one fragment, **CrimeFragment**. 
* View: **activity_crime.xml** and **crime_fragment.xml** layouts.


####Chapter 8:
The following things were made along this chapter:

* Crime model updated with two new fields.
* New layout for landscape mode in fragment_crime.
* New widgets to hold the new fields in model.


####Chapter 9:
Changelog:

* Model now contains a **CrimeLab** class to hold a list of Crime objects.
* Controller has three new classes:
	* **SingleFragmentActivity**: allows inflating layouts from generic fragments. Each class that inherits from this class must implement its own createFragment() method to receive a proper Fragment object. 
	* **CrimeListFragment**: this class is responsible for managing RecyclerView, ViewHolder and Adapter.
		* **CrimeHolder**: holds the itemView, provided by *CrimeAdapter* inner class, for each Crime object, wires up the view widgets and is the listener for click events in the itemView.
		* **CrimeAdapter**: it's the link between the RecyclerView and the data model. It inflates the view for the *CrimeHolder* from the list_item_crime layout and is responsible for binding model data, too.
	* **CrimeListActivity**: class that extends SingleFragmentActivity and implements createFragment() by returning a new CrimeListFragment object.
* New widgets to hold the new fields in model.
* *CrimeListActivity* set as the default activity when launching the app.