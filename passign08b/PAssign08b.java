package passign08b;

/**
* File: PAssign08b.java
* Class: CSCI 1302
* Author: Asia Hayden
* Created on: 11/13/26
* Last Modified: 11/14/26
* Description: D&D Character Design Sheet GUI
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * - Running window opens with the first scene, which is a class. Each ___Scene class has a confirm button that projects the next scene 
 * - "Character" class holds all the information the user will input into each scene 
 * - "CharacterRace" and "CharacterClass" classes are abstract for its subclasses and serves as setting a subclass for the "Character" class 
 * - subclasses that extend "CharacterRace" and "CharacterClass" are place-holders visually for now and can be modified
 */

public class PAssign08b extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Create Character
		Character character = new Character();

		// Create CharacterNameScene
		CharacterNameScene characterScene = new CharacterNameScene(stage, character);

		// Set first scene here and CharacterScene class will hold action event for next scene
		Scene scene = new Scene(characterScene, 600, 600);
		stage.setScene(scene);
		stage.show();

	}

	// CharacterNameScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterNameScene extends HBox {

		public CharacterNameScene(Stage stage, Character character) {
			// Label and textfield input
			Label lblName = new Label("Name: ");
			TextField tfName = new TextField();
			Button btnConfirm = new Button("Confirm");
			getChildren().addAll(lblName, tfName, btnConfirm);

			btnConfirm.setOnMouseClicked(e -> { // -> CharacterClassScene
				character.setName(tfName.getText().trim()); // stores the character name and trims blank space
				stage.setScene(new Scene(new CharacterClassScene(stage, character), 600, 600));
			});

		}

	}

	// CharacterClassScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterClassScene extends HBox {
		public CharacterClassScene(Stage stage, Character character) {
			ClassOne classOne = new ClassOne();
			ClassTwo classTwo = new ClassTwo();
			Button btnConfirm = new Button("Confirm");

			// radio button
			ToggleGroup classGroup = new ToggleGroup();
			RadioButton rbClassOne = new RadioButton(new ClassOne().getName());
			RadioButton rbClassTwo = new RadioButton(new ClassTwo().getName());

			rbClassOne.setToggleGroup(classGroup);
			rbClassTwo.setToggleGroup(classGroup);
			getChildren().addAll(rbClassOne, rbClassTwo, btnConfirm);

			btnConfirm.setOnMouseClicked(e -> {
				if (rbClassOne.isSelected()) {
					character.setCharClass(classOne);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				} else if (rbClassTwo.isSelected()) {
					character.setCharClass(classTwo);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				}
			});

		}
	}

	// CharacterRaceScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterRaceScene extends HBox {
		public CharacterRaceScene(Stage stage, Character character) {
			RaceOne raceOne = new RaceOne();
			RaceTwo raceTwo = new RaceTwo();
			Button btnConfirm = new Button("Confirm");

			// combo-box
			ComboBox<CharacterRace> cbRace = new ComboBox<CharacterRace>();
			cbRace.getItems().addAll(raceOne, raceTwo);
			cbRace.setPromptText("Choose a race: ");

			getChildren().addAll(cbRace, btnConfirm);

			btnConfirm.setOnMouseClicked(e -> {
				if (cbRace.getValue() instanceof RaceOne) {
					character.setCharRace(raceOne);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				} else if (cbRace.getValue() instanceof RaceTwo) {
					character.setCharRace(raceTwo);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				}
			});

		}

	}

	// CharacterAbilityScene class that customizes scene and will project next scene when user presses "Confirm" button
	// incomplete
	class CharacterAbilityScene extends GridPane {
		public CharacterAbilityScene(Stage stage, Character character) {
			Label lblStrength = new Label("Strength");
			Label lblDexterity = new Label("Dexterity");
			Label lblConstitution = new Label("Constitution");
			Label lblIntelligence = new Label("Intelligence");
			Label lblWisdom = new Label("Wisdom");
			Label lblCharisma = new Label("Charisma");

		}
	}

	// CharacterSheet class that will show all of the collected data for the user to view
	// incomplete
	class CharacterSheet {
		public CharacterSheet(Stage stage, Character character) {

		}
	}

	// Character class that stores character information
	class Character {
		private String name;
		private CharacterClass charClass;
		private CharacterRace charRace;
		private int strength;
		private int dexterity;
		private int constitution;
		private int intelligence;
		private int wisdom;
		private int charisma;

		public Character() {

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public CharacterClass getCharClass() {
			return charClass;
		}

		public void setCharClass(CharacterClass charClass) {
			this.charClass = charClass;
		}

		public CharacterRace getCharRace() {
			return charRace;
		}

		public void setCharRace(CharacterRace charRace) {
			this.charRace = charRace;
		}

		public int getStrength() {
			return strength;
		}

		public void setStrength(int strength) {
			this.strength = strength;
		}

		public int getDexterity() {
			return dexterity;
		}

		public void setDexterity(int dexterity) {
			this.dexterity = dexterity;
		}

		public int getConstitution() {
			return constitution;
		}

		public void setConstitution(int constitution) {
			this.constitution = constitution;
		}

		public int getIntelligence() {
			return intelligence;
		}

		public void setIntelligence(int intelligence) {
			this.intelligence = intelligence;
		}

		public int getWisdom() {
			return wisdom;
		}

		public void setWisdom(int wisdom) {
			this.wisdom = wisdom;
		}

		public int getCharisma() {
			return charisma;
		}

		public void setCharisma(int charisma) {
			this.charisma = charisma;
		}

	}

	// CharacterClass class that the Character class can use to set a class object
	abstract class CharacterClass {
		private String name;

		public CharacterClass(String name) {
			setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}

	}

	// CharacterRace class that the Character class can use to set a race object
	abstract class CharacterRace {
		private String name;

		public CharacterRace(String name) {
			setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void applyStats(Character character) {
			character.setDexterity(character.getDexterity());
			character.setCharisma(character.getCharisma());
			character.setIntelligence(character.getIntelligence());
			character.setStrength(character.getStrength());
			character.setWisdom(character.getWisdom());
			character.setConstitution(character.getConstitution());
		}

		@Override
		public String toString() {
			return name;
		}
	}

	// placeholder subclasses of CharacterRace that will specify particular stats
	class RaceOne extends CharacterRace {
		RaceOne() {
			super("Race #1");

		}

		@Override
		public void applyStats(Character character) {
			character.setDexterity(character.getDexterity());
			character.setCharisma(character.getCharisma());
			character.setIntelligence(character.getIntelligence());
			character.setStrength(character.getStrength());
			character.setWisdom(character.getWisdom());
			character.setConstitution(character.getConstitution());

		}
	}

	class RaceTwo extends CharacterRace { // placeholder subclass with fixed ability score increase
		RaceTwo() {
			super("Race #2");
		}

		@Override
		public void applyStats(Character character) {
			character.setDexterity(character.getDexterity() + 1);
			character.setCharisma(character.getCharisma() + 1);
			character.setIntelligence(character.getIntelligence() + 1);
			character.setStrength(character.getStrength() + 1);
			character.setWisdom(character.getWisdom() + 1);
			character.setConstitution(character.getConstitution() + 1);

		}
	}

	// placeholder subclasses of CharacterClass that will specify particular stats
	class ClassOne extends CharacterClass {

		ClassOne() {
			super("Class #1");
		}

	}

	class ClassTwo extends CharacterClass {

		ClassTwo() {
			super("Class #2");
		}

	}

}
