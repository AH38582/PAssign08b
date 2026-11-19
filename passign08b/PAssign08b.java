package passign08b;
 
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

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
			TextField tfPlayerName = new TextField(character.getPlayerName());
			Label playerError = new Label();
			VBox playerVbox = new VBox(tfPlayerName, playerError);
			HBox playerHbox = new HBox(lblPlayerName, playerVbox);
			
			// ---------- set input and UI for Character Name ------
			Label lblCharacterName = new Label("Character Name: ");
			TextField tfCharacterName = new TextField(character.getCharacterName());
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
	class CharacterClassScene extends VBox {
		public CharacterClassScene(Stage stage, Character character) {
			setSpacing(15);
			setPadding(new Insets(20));
			
			// ------------  Generate class instances
			Fighter fighter = new Fighter();
			Mage mage = new Mage();
			Cleric cleric = new Cleric();
			
			Label lblTitle = new Label("Choose Your Class");
			
			// ------- radio buttons
			ToggleGroup classGroup = new ToggleGroup();
			RadioButton rbFighter = new RadioButton(fighter.getClassName());
			RadioButton rbMage = new RadioButton(mage.getClassName());
			RadioButton rbCleric = new RadioButton(cleric.getClassName());

			rbFighter.setToggleGroup(classGroup);
			rbMage.setToggleGroup(classGroup);
			rbCleric.setToggleGroup(classGroup);
			
			HBox classHbox = new HBox();
			classHbox.setSpacing(15);
			classHbox.getChildren().addAll(rbFighter, rbMage, rbCleric);
			
			// -------------  Description Label that will be the block of text in open space
			Label lblDescription = new Label("Select a class above to read its desctription");
			lblDescription.setWrapText(true);
			lblDescription.setStyle("-fx-font-style: italic; -fx-padding: 10px");
			
			rbFighter.setOnAction(e -> lblDescription.setText(fighter.getClassDescription()));
			rbMage.setOnAction(e -> lblDescription.setText(mage.getClassDescription()));
			rbCleric.setOnAction(e -> lblDescription.setText(cleric.getClassDescription()));
			
			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");

			// getChildren()
			getChildren().addAll(lblTitle, classHbox, lblDescription, btnConfirm, btnBack);
			
			btnConfirm.setOnMouseClicked(e -> { /// -> CharacterRaceScene
			// checks and saves the selected button with the selected class
				if (rbFighter.isSelected()) {
					character.setCharClass(fighter);
					rbFighter.setSelected(true);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				} else if (rbMage.isSelected()) {
					rbMage.setSelected(true);
					character.setCharClass(mage);
					stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
				} else if (rbCleric.isSelected()) {
					rbCleric.setSelected(true);
					character.setCharClass(cleric);
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
	class CharacterRaceScene extends VBox {
		public CharacterRaceScene(Stage stage, Character character) {
			setSpacing(15);
			setPadding(new Insets(20));
			
			Label lblTitle = new Label("Choose your Race:");
			
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
			Label lblDescription = new Label("Select race above to read its description");
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

			Button btnConfirm = new Button("Confirm");
			Button btnBack = new Button("Back");
			HBox buttons = new HBox(10, btnConfirm, btnBack);

			// getChildren()
			getChildren().addAll(lblTitle, cbRace, lblRaceName, lblDescription, buttons);

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
		private int availablePoints;
		private Label lblPool = new Label("Available Attribute Points: " + availablePoints);
				
		// Private data fields that set initial text fields to "0" and arrow buttons
		private TextField tfStrength = new TextField();
		private TextField tfDexterity = new TextField();
		private TextField tfConstitution = new TextField();
		private TextField tfIntelligence = new TextField();
		private TextField tfWisdom = new TextField();
		private TextField tfCharisma = new TextField();
		
		
		public CharacterAbilityScene(Stage stage, Character character) {
			availablePoints = (80 - character.getStrength() - character.getDexterity() - character.getConstitution() - character.getIntelligence() - character.getWisdom() - character.getCharisma());
			lblPool.setText("Available Attibute Points: " + availablePoints);
			setSpacing(10);
			setPadding(new Insets(20));
			
			// --------- Hbox Confirm and back buttons
			Button btnBack = new Button("Back");
			Button btnConfirm = new Button("Confirm");
			HBox buttons = new HBox(10, btnConfirm, btnBack);
			
			// -------- Set attribute fields based on character sheet ----
			tfStrength.setText(String.valueOf(character.getStrength()));
			tfDexterity.setText(String.valueOf(character.getDexterity()));
			tfConstitution.setText(String.valueOf(character.getConstitution()));
			tfIntelligence.setText(String.valueOf(character.getIntelligence()));
			tfWisdom.setText(String.valueOf(character.getWisdom()));
			tfCharisma.setText(String.valueOf(character.getCharisma()));
			
			// ----- Making a gridPane to line up all labels buttons and text fields to be lined up in columns ------
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			
			
			createStatRow(grid, 0, "STR: ", "STRENGTH - Physical power, imacts melee attacks, and carrying capacity.", tfStrength);
			createStatRow(grid, 1, "DEX: ", "DEXTERITY - Agility, reflexes, balance, and ranged accuracy", tfDexterity);
			createStatRow(grid, 2, "CON: ", "CONSTITUTION - Health, stamina, and resistance to damage or poison", tfConstitution);
			createStatRow(grid, 3, "INT: ", "INTELLIGENCE - Reasoning, memory, and arcane power", tfIntelligence);
			createStatRow(grid, 4, "WIS: ", "WISDOM - Perception, intuition, willpower, and divine magic", tfWisdom);
			createStatRow(grid, 5, "CHA: ", "CHARISMA - Force of personality, persuasion, and leadership", tfCharisma);
			
			// ---------- add items to VBox using new method
			getChildren().addAll(lblPool, grid, buttons	);
			
			btnConfirm.setOnMouseClicked(e -> { // -> CharacterSheet
				saveAbilities(character);
				stage.setScene(new Scene(new CharacterSheet(stage, character), 600, 600));
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				stage.setScene(new Scene(new CharacterRaceScene(stage, character), 600, 600));
			});
						
		}
		
		private void createStatRow(GridPane grid, int rowIndex, String labelText, String hoverText, TextField tf) {
			Label lblStat = new Label(labelText);
			lblStat.setPickOnBounds(true);
			
			// ------- hover popup when mousing over stat label
			Tooltip tooltip = new Tooltip(hoverText);
			tooltip.setShowDelay(Duration.millis(50));
			tooltip.install(lblStat, tooltip);;
			
			tf.setEditable(false);
			tf.setPrefWidth(50);
			
			Button btnLeft = new Button("◄");
			Button btnRight = new Button("►");
			
			btnLeft.setOnMouseClicked(e -> modifyStat(tf, -1));
			btnRight.setOnMouseClicked(e -> modifyStat(tf, 1));
			
			grid.add(lblStat, 0, rowIndex);
			grid.add(btnLeft, 1, rowIndex);
			grid.add(tf, 2, rowIndex);
			grid.add(btnRight, 3, rowIndex);
		}
		
		public void modifyStat(TextField textField, int delta) {
			int currentVal = Integer.parseInt(textField.getText());
			int newVal = currentVal + delta;
			
			if (newVal >= 6 && newVal <= 18) {
				if (delta > 0 && availablePoints > 0) {
					textField.setText(String.valueOf(newVal));
					availablePoints--;
					lblPool.setText("Available Attribute Points: " + availablePoints);	
				} else if (delta < 0) {
					textField.setText(String.valueOf(newVal));
					availablePoints++;
					lblPool.setText("Available Attribute Points: " + availablePoints);
				}
			}
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
		
		Character() {
			setPlayerName("");
			setCharacterName("");
			setStrength(10);
			setDexterity(10);
			setConstitution(10);
			setIntelligence(10);
			setWisdom(10);
			setCharisma(10);
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
			setRaceDescription("Half-orcs combine human ingenuity with the towering stature and raw physical strength of their orcish ancestry. Often viewed with suspicion by civilized societies, they possess fierce endurance and an unstoppable drive, making them intimidating, relentless warriors who rely on sheer grit and power to survive.");
		}

		@Override
		public void applyStats(Character character) {
			character.setCharisma(character.getCharisma() -1);
			character.setStrength(character.getStrength() + 2);
			character.setConstitution(character.getConstitution() + 1);
		}
	}

}