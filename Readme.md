Journal is a native Android app for recording and reading simple text entries.

Current status:
* All data stored in the device data directory in a SQLite database.
* User can add an entry via (+) button. Upon clicking "Save" the entry is added to the database.
* User can read their old entries by clicking the history button. Data from the database is loaded into a recyclerview in descending order by timestamp.

Next steps:
* Implement Google authentication (currently, anyone using the device accesses the same journal).
* Add interaction where user taps a card in the recyclerview and can view a single entry.
* Add ability to edit and delete entries from the recyclerview.