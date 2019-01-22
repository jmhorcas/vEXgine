# vEXgine

vEXgine is a customizable execution engine of CVL (Common Variability Language) that fully supports the materialization process, including the delegation engine.

## Requirements

### To execute vEXgine GUI application
- Java JDK 8 or later

### To create, edit and manage CVL models and metamodels
- Obeo Designer Community 8.1.2 or later (with the UML Designer plugin)

### To use or extend the vEXgine API
- Maven 3.5.0 or later
- Eclipse JEE
- Libraries included in lib folder


### To create, edit or modify ATL transformations
- Eclipse Modeling Tools (with the ATL plugin).


## Usage

In order to use the vEXgine GUI application:

- Launch the demo/vEXgine.jar file as a Java application.
- In the application:
    - Load a variability model
	- Load a resolution model
	- Load a base model
	- (Optional) Load the metamodel of the base model (if not UML)
	- (Mandatory in current version) Fill the base model name in the transformations (e.g., IN)
	- (Mandatory in current version) Fill the metamodel name in the transformations (e.g., UML)
		
	
## License

vEXgine is free software and is distributed under the GPLv3 license.
See LICENSE for more information.

## External code

This project uses code from third-parties, licensed under their own terms:
- [JDOM](http://www.jdom.org/) Complete Java-based solution for accessing, manipulating, and outputting XML data from Java code.