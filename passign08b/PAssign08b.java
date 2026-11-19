package passign08b;

import javafx.application.Application;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

/**
 * - Running window opens with the first scene, which is a class. Each ___Scene class has a confirm button that projects the next scene 
 * - "Character" class holds all the information the user will input into each scene 
 * - "CharacterRace" and "CharacterClass" classes are abstract for its subclasses and serves as setting a subclass for the "Character" class 
 * - subclasses that extend "CharacterRace" and "CharacterClass" are place-holders visually for now and can be modified
 */

public class PAssign08b extends Application {
	private BorderPane root = new BorderPane();
	
	// ----- Method that sets nodes in center and the tracker at the bottom
	private void showScene(Node content, int page) {
		root.setCenter(content);
		HBox trackerBox = new HBox(new CharacterCreationTracker(page));
		trackerBox.setAlignment(Pos.CENTER);
		trackerBox.setPadding(new Insets(0, 0, 10, 0));
		root.setBottom(new VBox(10, new Separator(), trackerBox));
	}
	
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
		Scene scene = new Scene(root, 800, 350);
		showScene(new CharacterNameScene(stage, character), 1);
		
		// prioritizes focus to be on entire node (to prevent cursor from automatically setting into text field)
		characterScene.setFocusTraversable(true);
		characterScene.requestFocus();
		stage.setScene(scene);
		stage.show();

	}

	// CharacterNameScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterNameScene extends VBox {

		public CharacterNameScene(Stage stage, Character character) {
			// ---- All containers in order to go in VBox
			VBox headingBox = new VBox();
			Separator seperatorTop = new Separator(); // Divider line
			GridPane gridBox = new GridPane();
			
			setMargin(seperatorTop, new Insets(-10, 0, 0, 0));
			
			getChildren().addAll(headingBox, seperatorTop, gridBox);
			
			// ----- Header Container Setup
			Label heading = new Label("Create Your Character");
			heading.setStyle("-fx-font-size: 24px; -fx-fontweight: bold;");
			Label subtitle = new Label("Enter your player and character name below.");
			
			VBox headerColumn = new VBox(5, heading, subtitle);
			headerColumn.setAlignment(Pos.CENTER_LEFT);
			
			Button btnConfirm = new Button("Confirm");
			
			HBox headerRow = new HBox(20);
			headerRow.setAlignment(Pos.CENTER_LEFT);
			HBox.setMargin(btnConfirm, new Insets(35, 0, 0, 0));
			
			Region spacer = new Region(); // Makes space between labels and button
			HBox.setHgrow(spacer, Priority.ALWAYS);
			
			headerRow.getChildren().addAll(headerColumn, spacer, btnConfirm);
			headingBox.getChildren().add(headerRow);
			headingBox.setAlignment(Pos.CENTER_LEFT);
			
			// ----- GridPane Container Setup
			
			// ---------- Set input and UI for Player Name------
			Label lblPlayerName = new Label("Player Name: ");
			TextField tfPlayerName = new TextField(character.getPlayerName());
			tfPlayerName.setPromptText("Enter Player Name...");
			tfPlayerName.setPrefWidth(630);
			Label playerError = new Label();
			playerError.setStyle("-fx-text-fill: #eb4034;");
			VBox playerVbox = new VBox(tfPlayerName, playerError);
			
			// ---------- set input and UI for Character Name ------
			Label lblCharacterName = new Label("Character Name: ");
			TextField tfCharacterName = new TextField(character.getCharacterName());
			tfCharacterName.setPromptText("Enter Character Name...");
			Label characterError = new Label();
			characterError.setStyle("-fx-text-fill: #eb4034;");
			VBox characterVbox = new VBox(tfCharacterName, characterError);
			
			gridBox.setHgap(15);
			gridBox.setVgap(10);
			gridBox.add(lblPlayerName, 0, 0);
			gridBox.add(playerVbox, 1, 0);
			gridBox.add(lblCharacterName, 0, 1);
			gridBox.add(characterVbox, 1, 1);
			GridPane.setMargin(playerVbox, new Insets(22, 0, 0, 0));
			GridPane.setMargin(characterVbox, new Insets(22, 0, 0, 0));
			
			//------ put spacing between items
			setSpacing(15);
			setPadding(new Insets(30));
			setAlignment(Pos.CENTER);
			
			btnConfirm.setOnAction(e -> { // -> CharacterClassScene
				// -------- clear the errors before checking
				characterError.setText("");
				playerError.setText("");

				// takes trimmed input and store in variable
				String pName = tfPlayerName.getText().trim();
				String charName = tfCharacterName.getText().trim();

				// -------- error checks for if the fields have text -----
				if (pName.isEmpty() && charName.isEmpty()) { // error checks for empty textfield
					playerError.setText("*Player Name cannot be blank.");
					characterError.setText("*Character Name cannot be blank.");
				} else if (pName.isEmpty()) {
					playerError.setText("*Player cannot be blank.");
				} else if (charName.isEmpty()) {
					characterError.setText("*Character cannot be blank.");
				} else {
					character.setCharacterName(charName); // stores the character name in character class data field
					character.setPlayerName(pName);
					showScene(new CharacterClassScene(stage, character), 2); // sets scene so window presents CharacterClssScene
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
	class CharacterClassScene extends VBox {
		public CharacterClassScene(Stage stage, Character character) {
			// ---- All containers in order to go in VBox
			VBox headerContainer = new VBox();
			Separator seperatorTop = new Separator(); // Divider line
			VBox bodyContainer = new VBox();
			
			setMargin(seperatorTop, new Insets(-10, 0, 0, 0));
			getChildren().addAll(headerContainer, seperatorTop, bodyContainer);
			setSpacing(15);
			setPadding(new Insets(20));
			
			// ----- Header Container Setup
			Label heading = new Label("Choose Your Class");
			heading.setStyle("-fx-font-size: 24px; -fx-fontweight: bold;");
			Label subtitle = new Label("Select a class below to read its description.");

			VBox headerColumn = new VBox(5, heading, subtitle);
			headerColumn.setAlignment(Pos.CENTER_LEFT);

			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");

			HBox headerRow = new HBox(20);
			headerRow.setAlignment(Pos.CENTER_LEFT);
			HBox.setMargin(btnConfirm, new Insets(35, 0, 0, 0));
			HBox.setMargin(btnBack, new Insets(35, 0, 0, -10));

			Region spacer = new Region(); // Makes space between labels and button
			HBox.setHgrow(spacer, Priority.ALWAYS);

			headerRow.getChildren().addAll(headerColumn, spacer, btnConfirm, btnBack);
			headerContainer.getChildren().add(headerRow);
			headerContainer.setAlignment(Pos.CENTER_LEFT);
			
			// ------------  Generate class instances
			Fighter fighter = new Fighter();
			Mage mage = new Mage();
			Cleric cleric = new Cleric();
			
			// ------- radio buttons
			ToggleGroup classGroup = new ToggleGroup();
			RadioButton rbFighter = new RadioButton(fighter.getClassName());
			RadioButton rbMage = new RadioButton(mage.getClassName());
			RadioButton rbCleric = new RadioButton(cleric.getClassName());

			rbFighter.setToggleGroup(classGroup);
			rbMage.setToggleGroup(classGroup);
			rbCleric.setToggleGroup(classGroup);
			
			// // ----- Body Container Setup
			HBox classHbox = new HBox();
			classHbox.setSpacing(25);
			classHbox.setAlignment(Pos.CENTER_LEFT);
			classHbox.getChildren().addAll(rbFighter, rbMage, rbCleric);
			
			// -------------  Description Label that will be the block of text in open space
			Label lblDescription = new Label("");
			lblDescription.setWrapText(true);
			lblDescription.setStyle("-fx-font-style: italic; -fx-padding: 10px");
			
			bodyContainer.getChildren().addAll(classHbox, lblDescription);
			
			rbFighter.setOnAction(e -> lblDescription.setText(fighter.getClassDescription()));
			rbMage.setOnAction(e -> lblDescription.setText(mage.getClassDescription()));
			rbCleric.setOnAction(e -> lblDescription.setText(cleric.getClassDescription()));
			
			btnConfirm.setOnMouseClicked(e -> { // -> CharacterRaceScene
				// checks and saves the selected button with the selected class
				if (rbFighter.isSelected()) {
					character.setCharClass(fighter);
					rbFighter.setSelected(true);
					showScene(new CharacterRaceScene(stage, character), 3);
				} else if (rbMage.isSelected()) {
					rbMage.setSelected(true);
					character.setCharClass(mage);
					showScene(new CharacterRaceScene(stage, character), 3);
				} else if (rbCleric.isSelected()) {
					rbCleric.setSelected(true);
					character.setCharClass(cleric);
					showScene(new CharacterRaceScene(stage, character), 3);
				}
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				showScene(new CharacterNameScene(stage, character), 1);
			});
			
		}
	}

	// CharacterRaceScene class that customizes scene and will project next scene when user presses "Confirm" button
	class CharacterRaceScene extends VBox {
		public CharacterRaceScene(Stage stage, Character character) {
			// ---- All containers in order to go in VBox
			VBox headerContainer = new VBox();
			Separator seperatorTop = new Separator(); // Divider line
			HBox bodyContainer = new HBox();
			
			setMargin(seperatorTop, new Insets(-10, 0, 0, 0));
			getChildren().addAll(headerContainer, seperatorTop, bodyContainer);
			setSpacing(15);
			setPadding(new Insets(20));

			// ----- Header Container Setup
			Label heading = new Label("Choose Your Race");
			heading.setStyle("-fx-font-size: 24px; -fx-fontweight: bold;");
			Label subtitle = new Label("Select race below to read its description");

			VBox headerColumn = new VBox(5, heading, subtitle);
			headerColumn.setAlignment(Pos.CENTER_LEFT);

			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");

			HBox headerRow = new HBox(20);
			headerRow.setAlignment(Pos.CENTER_LEFT);
			HBox.setMargin(btnConfirm, new Insets(35, 0, 0, 0));
			HBox.setMargin(btnBack, new Insets(35, 0, 0, -10));

			Region spacer = new Region(); // Makes space between labels and button
			HBox.setHgrow(spacer, Priority.ALWAYS);

			headerRow.getChildren().addAll(headerColumn, spacer, btnConfirm, btnBack);
			headerContainer.getChildren().add(headerRow);
			headerContainer.setAlignment(Pos.CENTER_LEFT);
			
			// All available CharacterRace subclasses
			RaceHuman human = new RaceHuman();
			RaceElf elf = new RaceElf();
			RaceDwarf dwarf = new RaceDwarf();
			RaceHalfElf halfElf = new RaceHalfElf();
			RaceHalfOrc halfOrc = new RaceHalfOrc();

			// combo-box
			ComboBox<CharacterRace> cbRace = new ComboBox<CharacterRace>();
			cbRace.getItems().addAll(human, elf, dwarf, halfElf, halfOrc);
			cbRace.setPromptText("Choose a race: ");
			
			// --------- Race Description Label to describe selection
			Label lblRaceName = new Label("");
			lblRaceName.setStyle("-fx-font-style: bold; -fx-padding: 5px; -fx-font-size: 30px");			
			Label lblDescription = new Label("");
			lblDescription.setWrapText(true);
			lblDescription.setStyle("-fx-font-style: italic; -fx-padding: 5px");
			
			// ------ update the race description with what is selected from comboBox
			
			cbRace.setOnAction(e -> {
				CharacterRace selected = cbRace.getValue();
				if (selected != null) {
					lblRaceName.setText(selected.getRaceName());
					lblDescription.setText(selected.getRaceDescription());
				}
			});

			// ----- Body Container Setup
			VBox leftBodyContainer = new VBox(10, lblRaceName, lblDescription);
			leftBodyContainer.setPrefWidth(600);
			leftBodyContainer.setAlignment(Pos.CENTER_LEFT);
			
			bodyContainer.getChildren().addAll(cbRace, leftBodyContainer);
			bodyContainer.setSpacing(30);
			
			cbRace.setPrefWidth(100);
			VBox.setMargin(lblRaceName, new Insets(0, 0, 0, 12));
			VBox.setMargin(lblDescription, new Insets(0, 0, 0, 12));

			btnConfirm.setOnMouseClicked(e -> { // -> CharacterAbilityScene
				CharacterRace selectedRace = cbRace.getValue();
				// checks if box is selected and saves selected box along with selected race
				if (selectedRace != null) {
					character.setCharRace(selectedRace);
					showScene(new CharacterAbilityScene(stage, character), 4);
				}
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				showScene(new CharacterClassScene(stage, character), 2);
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
			// Confirm and back buttons
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
				showScene(new CharacterSheet(stage, character), 5);
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				showScene(new CharacterRaceScene(stage, character), 3);
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
				showScene(new CharacterAbilityScene(stage, character), 4);
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
		
		Character() {
			setPlayerName("");
			setCharacterName("");
		}
		
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
	
	class CharacterCreationTracker extends HBox {
		private String[] pages = { "Name", "Class", "Race", "Abilities", "Review" };
		
		public CharacterCreationTracker (int currentPage) {
			setAlignment(Pos.CENTER);
			
		    HBox pageRow = new HBox();
		    pageRow.setAlignment(Pos.TOP_CENTER);
		    
		    Line line = new Line(0, 0, 100 * (pages.length - 1), 0);
		    line.setStroke(Color.web("#1365d1"));
		    line.setStrokeWidth(2);
		    line.setTranslateY(5);
		    
			for (int i = 0; i < pages.length; i++) {
				int pageNumber = i + 1;
				Circle circle = new Circle(5);
				
				Label name = new Label(pages[i]);
				name.setAlignment(Pos.CENTER);
				
				if (pageNumber <= currentPage) {
					circle.setFill(Color.web("#1365d1"));
					circle.setStroke(Color.web("#1365d1"));
				} else {
					circle.setFill(Color.WHITE);
					circle.setStroke(Color.web("#1365d1"));
				}
				VBox pageColumn = new VBox(5, circle, name);
				pageColumn.setAlignment(Pos.TOP_CENTER);
				pageColumn.setPrefWidth(100);
				pageColumn.setMinWidth(100);
				pageRow.getChildren().add(pageColumn);
			}
			
			StackPane container = new StackPane(line, pageRow);
			StackPane.setAlignment(line, Pos.TOP_CENTER);
			getChildren().add(container);
		}
	}

	// CharacterClass class that the Character class can use to set a class object
	abstract class CharacterClass {
		private String className;

		public CharacterClass(String className) {
			setClassName(className);
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}

		@Override
		public String toString() {
			return className;
		}

	}

	// placeholder subclasses of CharacterClass that will specify particular stats
	class Fighter extends CharacterClass {
		private String classDescription = "Fighters are trained warriors who rely on skill, strength, and weapons rather than magic to win battles. They can use almost any weapon or armor, making them versatile frontline soldiers who can both take heavy hits and dish out fast, deadly attacks.";
		
		Fighter() {
			super("Fighter");
		}
		
		public String getClassDescription() {
			return classDescription;
		}
	}
	
	class Mage extends CharacterClass {
		private String classDescription = "Mages are scholars and spellcasters who study the arcane arts to bend reality to their will. Though physically fragile and unable to wear heavy armor, they stay in the backlines to unleash devastating elemental magic, summon creatures, and control the battlefield with powerful spells.";
		
		Mage() {
			super("Mage");
		}
		
		public String getClassDescription() {
			return classDescription;
		}
	}
	
	class Cleric extends CharacterClass{
		
		private String classDescription =  "Clerics are devout warriors who channel divine magic granted by the gods they serve. Wearing medium or heavy armor, they stand near the frontlines to heal wounded allies, banish undead monsters, and smite enemies with sacred power.";
		
		Cleric() {
			super("Cleric");
		}
		
		public String getClassDescription() {
			return classDescription;
		}
	}
	
	
	// CharacterRace class that the Character class can use to set a race object
	abstract class CharacterRace {
		private String raceName;
		private String raceDescription;

		public CharacterRace(String raceName) {
			setRaceName(raceName);
		}

		public String getRaceName() {
			return raceName;
		}

		public void setRaceName(String raceName) {
			this.raceName = raceName;
		}
		
		public String getRaceDescription() {
			return raceDescription;
		}
		
		public void setRaceDescription(String raceDescription) {
			this.raceDescription = raceDescription;
		}

		abstract void applyStats(Character character);

		@Override
		public String toString() {
			return raceName;
		}
	}

	// placeholder subclasses of CharacterRace that will specify particular stats
	class RaceHuman extends CharacterRace {
		RaceHuman() {
			super("Human");
			setRaceDescription("Humans are the most adaptable and ambitious of the common races. Though their lifespans are relatively short, they make up for it with endless drive, curiosity, and versatility, allowing them to excel in almost any role, build sprawling empires, and quickly adjust to changing times.");
		}

		@Override
		public void applyStats(Character character) {
			character.setCharisma(character.getCharisma() + 1);
			character.setConstitution(character.getConstitution() + 1);

		}
	}

	class RaceElf extends CharacterRace { // placeholder subclass with fixed ability score increase
		RaceElf() {
			super("Elf");
			setRaceDescription("Elves are graceful, long-lived beings with a deep connection to nature and the arcane arts. Known for their keen senses, slender builds, and unmatched agility, they spend centuries perfecting their crafts, magic, and martial skills while maintaining an aloof, timeless perspective on the world.");
		}

		@Override
		public void applyStats(Character character) {
			character.setDexterity(character.getDexterity() + 1);
			character.setIntelligence(character.getIntelligence() + 1);
		}
	}
	
	class RaceDwarf extends CharacterRace {
		RaceDwarf() {
			super("Dwarf");
			setRaceDescription("Dwarves are stout, rugged folk known for their resilience, loyalty, and unmatched skill in mining, stonework, and metalcraft. Living deep within mountain strongholds, they are fierce, stubborn warriors who value honor, tradition, and the forging of legendary weapons and armor.");
		}

		@Override
		public void applyStats(Character character) {
			character.setWisdom(character.getWisdom() + 1);
			character.setConstitution(character.getConstitution() + 1);

		}
	}
	
	class RaceHalfElf extends CharacterRace {
		RaceHalfElf() {
			super("Half-Elf");
			setRaceDescription("Half-elves blend the best traits of their dual heritage, combining human ambition and adaptability with elven grace and keen senses. Often walking between two worlds without fully belonging to either, they tend to be charismatic, natural diplomats and travelers who easily charm others and thrive in diverse company.");
		}

		@Override
		public void applyStats(Character character) {
			character.setCharisma(character.getCharisma() + 1);
			character.setIntelligence(character.getIntelligence() + 1);
		}
	}
	
	class RaceHalfOrc extends CharacterRace {
		RaceHalfOrc() {
			super("Half-Orc");
			setRaceDescription(
					"Half-orcs combine human ingenuity with the towering stature and raw physical strength of their orcish ancestry. Often viewed with suspicion by civilized societies, they possess fierce endurance and an unstoppable drive, making them intimidating, relentless warriors who rely on sheer grit and power to survive.");
		}

		@Override
		public void applyStats(Character character) {
			character.setCharisma(character.getCharisma() - 1);
			character.setStrength(character.getStrength() + 2);
			character.setConstitution(character.getConstitution() + 1);
		}
	}

}
