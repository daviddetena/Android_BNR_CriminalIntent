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


####Chapter 9: Crime List
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

####Chapter 10: Crime List with Crime detail view
Changelog:

* Updated CrimeHolder to start CrimeActivity with the proper Crime object data that was tapped in the list. 
* CrimeListFragment => [crimeId as *EXTRA*] => CrimeActivity.newIntent() => [crimeId as *ARG*] => CrimeFragment.newInstance().
* Adapter now refresh data in CrimeListFragment's **UpdateUI()** method. It's also called now in **onResume()** method to show changes in model when back from the Crime detail view.

####Chapter 11: ViewPager
Changelog:

* Crime detail view is now displayed with a **CrimePagerActivity** object. This allows us to easily move from the current detail view to the previous or the next one by swiping left or right.
* Replaced intent from CrimeActivity In CrimeHolder's onClick() in CrimeListFragment with intent from CrimePagerActivity to start the Crime detail view from the **ViewPager**.
* CrimeActivity class deleted.

####Chapter 12: Dialogs
Changelog:

* Crime detail view presents a DatePicker dialog to change the Crime's date when pressing the button.
* Communication between two fragments: CrimeFragment, whose date is passed in as arg, and DatePickerFragment, which receives this arg, allows changing the date and send the selected date back to the CrimeFragment. The updated date is now displayed in the Crime detail view and the CrimeList.

####Chapter 13: Toolbar
Changelog:

* Added Toolbar for the app thanks to AppCompat.
* Toolbar includes two menu actions:
	*  Add Crime: action to add a new crime object to the CrimeLab.
	*  Show subtitle: action to show or display the current number of crime as the toolbar's subtitle
* List of dummy crimes deleted, since we can now add new crimes by ourselves.  