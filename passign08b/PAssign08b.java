package passign08b;
 
import javafx.application.Application;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.geometry.Insets;

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

		// Set first scene here and CharacterScene class will hold action event for next
		// scene
		Scene scene = new Scene(characterScene, 600, 600);
		stage.setScene(scene);
		stage.show();

	}

	// CharacterNameScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterNameScene extends HBox {

		public CharacterNameScene(Stage stage, Character character) {
			// ---------- Set input and UI for Player Name------
			Label lblPlayerName = new Label("Player Name: ");
			TextField tfPlayerName = new TextField();
			Label playerError = new Label();
			VBox playerVbox = new VBox(tfPlayerName, playerError);
			HBox playerHbox = new HBox(lblPlayerName, playerVbox);
			
			// ---------- set input and UI for Character Name ------
			Label lblCharacterName = new Label("Character Name: ");
			TextField tfCharacterName = new TextField();
			Label characterError = new Label();
			VBox characterVbox = new VBox(tfCharacterName, characterError);
			HBox characterHbox = new HBox(lblCharacterName, characterVbox);

			Button btnConfirm = new Button("Confirm");
			
			//------ put spacing between items
			setSpacing(10);
			setPadding(new Insets(20));
			
			// getChildren();
			getChildren().addAll(playerHbox, characterHbox, btnConfirm);

			btnConfirm.setOnAction(e -> { // -> CharacterClassScene
				// -------- clear the errors before checking
				characterError.setText("");
				playerError.setText("");
				
				// takes trimmed input and store in variable
				String pName = tfPlayerName.getText().trim();
				String charName = tfCharacterName.getText().trim();
				
				// -------- error checks for if the fields have text -----
				if (pName.isEmpty() && charName.isEmpty()) { // error checks for empty textfield
					playerError.setText("*Player Name cannot be blank");
					characterError.setText("*Character Name cannot be blank");
				} else if (pName.isEmpty()){
					playerError.setText("*Name cannot be blank");
				} else if (charName.isEmpty()) {
					characterError.setText("*Name cannot be blank");
				} else {
				character.setCharacterName(charName); // stores the character name in character class data field
				character.setPlayerName(pName);
				stage.setScene(new Scene(new CharacterClassScene(stage, character), 600, 600)); // sets scene so window presents CharacterClssScene
				}
				});

			// Allows Enter key to also interact the same way as btnConfirm.setOneAction
			tfPlayerName.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER) {
					btnConfirm.fire();
				}
			});
			
			tfCharacterName.setOnKeyPressed(e -> {
				if (e.getCode() == KeyCode.ENTER) {
					btnConfirm.fire();
				}
			});

		}

	}

	// CharacterClassScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterClassScene extends HBox {
		public CharacterClassScene(Stage stage, Character character) {
			// All available CharacterClass subclasses, and confirm and back buttons
			ClassOne classOne = new ClassOne();
			ClassTwo classTwo = new ClassTwo();
			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");

			// radio button
			ToggleGroup classGroup = new ToggleGroup();
			RadioButton rbClassOne = new RadioButton(new ClassOne().getName());
			RadioButton rbClassTwo = new RadioButton(new ClassTwo().getName());

			// toggle group
			rbClassOne.setToggleGroup(classGroup);
			rbClassTwo.setToggleGroup(classGroup);
			
			// getChildren()
			getChildren().addAll(rbClassOne, rbClassTwo, btnConfirm, btnBack);
			
			btnConfirm.setOnMouseClicked(e -> { /// -> CharacterRaceScene
			// checks and saves the selected button with the selected class
				if (rbClassOne.isSelected()) {
					character.setCharClass(classOne);
					rbClassOne.setSelected(true);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				} else if (rbClassTwo.isSelected()) {
					rbClassTwo.setSelected(true);
					character.setCharClass(classTwo);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				}
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				stage.setScene(new Scene(new CharacterNameScene(stage, character), 600, 600));
			});
			
		}
	}

	// CharacterRaceScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterRaceScene extends HBox {
		public CharacterRaceScene(Stage stage, Character character) {
			// All available CharacterRace subclasses, and confirm and back buttons
			RaceOne raceOne = new RaceOne();
			RaceTwo raceTwo = new RaceTwo();
			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");

			// combo-box
			ComboBox<CharacterRace> cbRace = new ComboBox<CharacterRace>();
			cbRace.getItems().addAll(raceOne, raceTwo);
			cbRace.setPromptText("Choose a race: ");

			// getChildren()
			getChildren().addAll(cbRace, btnConfirm, btnBack);

			btnConfirm.setOnMouseClicked(e -> { // -> CharacterAbilityScene
				CharacterRace selectedRace = cbRace.getValue();
				// checks if box is selected and saves selected box along with selected race
				if (selectedRace != null) {
					character.setCharRace(selectedRace);
					stage.setScene(new Scene(new CharacterAbilityScene(stage, character), 600, 600));
				} 
				
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				stage.setScene(new Scene(new CharacterClassScene(stage, character), 600, 600));
			});

		}

	}

	// CharacterAbilityScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterAbilityScene extends VBox {
		// Private data fields that set initial text fields to "0" and arrow buttons
		private TextField tfStrength = new TextField("0");
		private TextField tfDexterity = new TextField("0");
		private TextField tfConstitution = new TextField("0");
		private TextField tfIntelligence = new TextField("0");
		private TextField tfWisdom = new TextField("0");
		private TextField tfCharisma = new TextField("0");
		
		private Label strength = new Label("Strength: ");
		private Label dexterity = new Label("Dexterity: ");
		private Label constitution = new Label("Constitution: ");
		private Label intelligence = new Label("Intelligence: ");
		private Label wisdom = new Label("Wisdom: ");
		private Label charisma = new Label("Charisma:");
		
		private Button leftStrength = new Button("◄");
		private Button rightStrength = new Button("►");
		private Button leftDexterity = new Button("◄");
		private Button rightDexterity = new Button("►");
		private Button leftConstitution = new Button("◄");
		private Button rightConstitution = new Button("►");
		private Button leftIntelligence = new Button("◄");
		private Button rightIntelligence = new Button("►");
		private Button leftWisdom = new Button("◄");
		private Button rightWisdom = new Button("►");
		private Button leftCharisma = new Button("◄");
		private Button rightCharisma = new Button("►");
		
		public CharacterAbilityScene(Stage stage, Character character) {
			// c=Confirm and back buttons
			Button btnBack = new Button("Back");
			Button btnConfirm = new Button("Confirm");
			
			// HBoxes for individual abilities and their buttons
			HBox hStrength = new HBox(leftStrength, tfStrength, rightStrength);
			HBox hDexterity = new HBox(leftDexterity, tfDexterity, rightDexterity);
			HBox hConstitution = new HBox(leftConstitution, tfConstitution, rightConstitution);
			HBox hIntelligence = new HBox(leftIntelligence, tfIntelligence, rightIntelligence);
			HBox hWisdom = new HBox(leftWisdom, tfWisdom, rightWisdom);
			HBox hCharisma = new HBox(leftCharisma, tfCharisma, rightCharisma);
			HBox buttons = new HBox(btnConfirm, btnBack);
			
			// getChildren()
			getChildren().addAll(strength, hStrength, dexterity, hDexterity, constitution, hConstitution, 
					intelligence, hIntelligence, wisdom, hWisdom, charisma, hCharisma, buttons);
			
			// Adjusts left and right buttons to any desired number within range(assuming there is a range)
			leftStrength.setOnMouseClicked(e -> leftButtonCondition(tfStrength));
			leftDexterity.setOnMouseClicked(e -> leftButtonCondition(tfDexterity));
			leftConstitution.setOnMouseClicked(e -> leftButtonCondition(tfConstitution));
			leftIntelligence.setOnMouseClicked(e -> leftButtonCondition(tfIntelligence));
			leftWisdom.setOnMouseClicked(e -> leftButtonCondition(tfWisdom));
			leftCharisma.setOnMouseClicked(e -> leftButtonCondition(tfCharisma));
			
			rightStrength.setOnMouseClicked(e -> rightButtonCondition(tfStrength));
			rightDexterity.setOnMouseClicked(e -> rightButtonCondition(tfDexterity));
			rightConstitution.setOnMouseClicked(e -> rightButtonCondition(tfConstitution));
			rightIntelligence.setOnMouseClicked(e -> rightButtonCondition(tfIntelligence));
			rightWisdom.setOnMouseClicked(e -> rightButtonCondition(tfWisdom));
			rightCharisma.setOnMouseClicked(e -> rightButtonCondition(tfCharisma));
			
			btnConfirm.setOnMouseClicked(e -> { // -> CharacterSheet
				saveAbilities(character);
				stage.setScene(new Scene(new CharacterSheet(stage, character), 600, 600));
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
			});
			
			// sets text to previous inputs when user returns to this scene
			tfStrength.setText(String.valueOf(character.getStrength()));
			tfDexterity.setText(String.valueOf(character.getDexterity()));
			tfConstitution.setText(String.valueOf(character.getConstitution()));
			tfIntelligence.setText(String.valueOf(character.getIntelligence()));
			tfWisdom.setText(String.valueOf(character.getWisdom()));
			tfCharisma.setText(String.valueOf(character.getCharisma()));
			
		}
		
		// method that saves the ability inputs and stores them into character data fields
		public void saveAbilities(Character character) {
			character.setStrength(Integer.parseInt(tfStrength.getText()));
			character.setDexterity(Integer.parseInt(tfDexterity.getText()));
			character.setConstitution(Integer.parseInt(tfConstitution.getText()));
			character.setIntelligence(Integer.parseInt(tfIntelligence.getText()));
			character.setWisdom(Integer.parseInt(tfWisdom.getText()));
			character.setCharisma(Integer.parseInt(tfCharisma.getText()));
			character.getCharRace().applyStats(character);
		}
		
		// method that conditions the floor for left button (assuming there is a floor)
		public void leftButtonCondition(TextField textField) {
			int num = Integer.parseInt(textField.getText());
			
			if (num > 0) {
				textField.setText(String.format("%d", num - 1));
			} 
		}
		
		// method that conditions the ceiling for right button (assuming there is a ceiling)
		public void rightButtonCondition(TextField textField) {
			int num = Integer.parseInt(textField.getText());

			if (num < 20) {
				textField.setText(String.format("%d", num + 1));
			}
		}
	}

	// CharacterSheet class that will show all of the collected data for the user to view
	class CharacterSheet extends VBox {
		public CharacterSheet(Stage stage, Character character) {
			// Labels for all updated information goes on sheet, and back and save buttons
			Label playerName = new Label("Player Name: " + character.getPlayerName());
			Label characterName = new Label("CharacterName: " + character.getCharacterName());
			Label classType = new Label("Class: " + character.getCharClass());
			Label raceType = new Label("Race: " + character.getCharRace());
			Label strength = new Label("Strength: " + character.getStrength());
			Label dexterity = new Label("Dexterity: " + character.getDexterity());
			Label constitution = new Label("Constitution: " + character.getConstitution());
			Label intelligence = new Label("Intelligence: " + character.getIntelligence());
			Label wisdom = new Label("Wisdom: " + character.getWisdom());
			Label charisma = new Label("Charisma: " + character.getCharisma());
			
			Button btnSave = new Button("Save");
			Button btnBack = new Button("Back");
			
			// HBox for save and confirm button
			HBox hbox = new HBox(btnBack, btnSave);
			
			// getChildren
			getChildren().addAll(playerName, characterName, classType, raceType, strength, dexterity, constitution, intelligence, wisdom, charisma, hbox);
			
			// see print() method
			btnSave.setOnMouseClicked(e -> {
				print(this, stage);
			});
			
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				stage.setScene(new Scene(new CharacterAbilityScene(stage, character), 600, 600));
			});
			
		}
		
		// method opens file explorer and allows user to save their character sheet
		public void print(Node node, Window ownerWindow) {
			PrinterJob job = PrinterJob.createPrinterJob();
			if (job != null) {
				boolean printed = job.printPage(node);
				
				if (printed) {
					job.endJob();
				}
			}
		}
	}
	
	/**
	 * lines 328 and down are to be modified later
	 */

	// Character class that stores character information
	class Character {
		private String playerName;
		private String characterName;
		private CharacterClass charClass;
		private CharacterRace charRace;
		private int strength;
		private int dexterity;
		private int constitution;
		private int intelligence;
		private int wisdom;
		private int charisma;
		
		public String getPlayerName() {
			return playerName;
		}
		
		public void setPlayerName(String playerName) {
			this.playerName = playerName;			
		}

		public String getCharacterName() {
			return characterName;
		}

		public void setCharacterName(String name) {
			this.characterName = name;
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

