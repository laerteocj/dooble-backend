
### Data object

I have the habit of calling them Value Objects (add an explanation here), so the data objects are 
inside of the vo folder.  
 
Enumeration created based on how it looks like inside of the data structure, values that are a finit
set where definied as enumerations as well as the one that had only one value (state) but it would
potentially have more than one, also if I assume that the data structure was extracted from an
existing system which was developed in java enumeration/constants have the convention of being defined as
upper case text.
  
Files defined as enumerations:
* type
* preferencesType
* state

Explain why poll options, state and type have poll in the name and other data objects not.

Why optionsHash isn't extracted automatically?

**Why invitees is a list of string?**
From the data sample provided it isn't possible to extrapolate the structure, so I kept it simple and defined as a string. I could have defined that as an object that isn't sound when you want to deserialize the string into an object (it would make the code more complex without a point).

**Why Poll class has two sub classes?**
_Need to add the answer here...._  