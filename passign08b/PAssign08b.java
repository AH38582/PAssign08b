package passign08b;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.WritableImage;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
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
		Scene scene = new Scene(root, 780, 380);
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
			heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
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
			heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
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
			
			// saves user's last selected radio button
			CharacterClass saved = character.getCharClass();
			if (character.getCharClass() != null) {
				String name = saved.getClassName();
				if (name.equals(fighter.getClassName())) {
					rbFighter.setSelected(true);
					lblDescription.setText(fighter.getClassDescription());
				} else if (name.equals(mage.getClassName())) {
					rbMage.setSelected(true);
					lblDescription.setText(mage.getClassDescription());
				} else if (name.equals(cleric.getClassName())) {
					rbCleric.setSelected(true);
					lblDescription.setText(cleric.getClassDescription());
				}
			}
			
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
			heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
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
		
		private int availablePoints;
		private Label lblPool = new Label("Available Attribute Points");
		private Label lblPoints = new Label(String.valueOf(availablePoints));
		
		// Private data fields that set initial text fields to "0" and arrow buttons
		private TextField tfStrength = new TextField();
		private TextField tfDexterity = new TextField();
		private TextField tfConstitution = new TextField();
		private TextField tfIntelligence = new TextField();
		private TextField tfWisdom = new TextField();
		private TextField tfCharisma = new TextField();
		
		public CharacterAbilityScene(Stage stage, Character character) {
			
			// ---- All containers in order to go in VBox
			VBox headerContainer = new VBox();
			Separator seperatorTop = new Separator(); // Divider line
			HBox bodyContainer = new HBox();

			setMargin(seperatorTop, new Insets(-5, 0, 0, 0));
			getChildren().addAll(headerContainer, seperatorTop, bodyContainer);
			setSpacing(15);
			setPadding(new Insets(20));

			// ----- Header Container Setup
			Label heading = new Label("Character Attributes");
			heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
			Label subtitle = new Label("Distribute your attribute points; Hover over attribute for more details");

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
			
			availablePoints = (80 - character.getStrength() - character.getDexterity() - character.getConstitution() - character.getIntelligence() - character.getWisdom() - character.getCharisma());
			lblPoints.setText((String.valueOf(availablePoints)));
			setSpacing(10);
			setPadding(new Insets(20));
			
			// -------- Set attribute fields based on character sheet ----
			tfStrength.setText(String.valueOf(character.getStrength()));
			tfDexterity.setText(String.valueOf(character.getDexterity()));
			tfConstitution.setText(String.valueOf(character.getConstitution()));
			tfIntelligence.setText(String.valueOf(character.getIntelligence()));
			tfWisdom.setText(String.valueOf(character.getWisdom()));
			tfCharisma.setText(String.valueOf(character.getCharisma()));
			
			// ----- Making left and right gridpanes to line up all labels buttons and text fields to be lined up in columns ------
			GridPane leftGrid = new GridPane();
			GridPane rightGrid = new GridPane();
			
			leftGrid.setHgap(10);
			leftGrid.setVgap(15);
			
			rightGrid.setHgap(10);
			rightGrid.setVgap(15);
			
			// ----- Split grid into two columns
			createStatRow(leftGrid, 0, "STR: ", "STRENGTH - Physical power, imacts melee attacks, and carrying capacity.", tfStrength);
			createStatRow(leftGrid, 1, "DEX: ", "DEXTERITY - Agility, reflexes, balance, and ranged accuracy", tfDexterity);
			createStatRow(leftGrid, 2, "CON: ", "CONSTITUTION - Health, stamina, and resistance to damage or poison", tfConstitution);
			
			createStatRow(rightGrid, 0, "INT: ", "INTELLIGENCE - Reasoning, memory, and arcane power", tfIntelligence);
			createStatRow(rightGrid, 1, "WIS: ", "WISDOM - Perception, intuition, willpower, and divine magic", tfWisdom);
			createStatRow(rightGrid, 2, "CHA: ", "CHARISMA - Force of personality, persuasion, and leadership", tfCharisma);
			
			// ---------- add items to bodyContainer
			
			// HBox for gridpanes
			HBox statContainer = new HBox(30);
			statContainer.setAlignment(Pos.CENTER);
			statContainer.setPadding(new Insets(50, 0, 0, 0));
			statContainer.getChildren().addAll(leftGrid, rightGrid);
			
			//VBox for lblPool and lblPoints
			VBox pointsContainer = new VBox(lblPool, lblPoints);
			pointsContainer.setAlignment(Pos.CENTER);
			
			lblPool.setStyle("-fx-font-size: 14px;");
			lblPoints.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");
			pointsContainer.setPadding(new Insets(15, 25, 15, 25));
			pointsContainer.setMinWidth(160);
			pointsContainer.setStyle("-fx-border-color: #c8c8c8; -fx-border-radius: 8; -fx-background-color: white; -fx-background-radius: 8;");
			
			bodyContainer.setSpacing(40);
			bodyContainer.setAlignment(Pos.CENTER);
			bodyContainer.getChildren().addAll(pointsContainer, statContainer);
			
			VBox.setVgrow(statContainer, Priority.ALWAYS);
			VBox.setVgrow(bodyContainer, Priority.ALWAYS);
			
			btnConfirm.setOnMouseClicked(e -> { // -> CharacterSheet
				saveAbilities(character);
				showScene(new CharacterSheet(stage, character), 5);
			});
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				showScene(new CharacterRaceScene(stage, character), 3);
			});
			
		}
		
		private void createStatRow(GridPane grid, int rowIndex, String labelText, String hoverText, TextField tf) {
			Label lblStat = new Label(labelText);
			lblStat.setPickOnBounds(true);

			// ------- hover popup when mousing over stat label
			Tooltip tooltip = new Tooltip(hoverText);
			tooltip.setShowDelay(Duration.millis(50));
			tooltip.install(lblStat, tooltip);

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
					lblPoints.setText((String.valueOf(availablePoints)));
				} else if (delta < 0) {
					textField.setText(String.valueOf(newVal));
					availablePoints++;
					lblPoints.setText((String.valueOf(availablePoints)));
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
			// ---- All containers in order to go in VBox
			VBox headerContainer = new VBox();
			Separator seperatorTop = new Separator(); // Divider line
			HBox bodyContainer = new HBox();

			setMargin(seperatorTop, new Insets(-5, 0, 0, 0));
			getChildren().addAll(headerContainer, seperatorTop, bodyContainer);
			setSpacing(10);
			setPadding(new Insets(15));

			// ----- Header Container Setup
			Label heading = new Label("Character Review");
			heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
			Label subtitle = new Label("Review your character before saving.");

			VBox headerColumn = new VBox(5, heading, subtitle);
			headerColumn.setAlignment(Pos.CENTER_LEFT);

			Button btnSave = new Button("Save");
			Button btnBack = new Button("Back");

			HBox headerRow = new HBox(20);
			headerRow.setAlignment(Pos.CENTER_LEFT);
			HBox.setMargin(btnSave, new Insets(35, 0, 0, 0));
			HBox.setMargin(btnBack, new Insets(35, 0, 0, -10));

			Region spacer = new Region(); // Makes space between labels and button
			HBox.setHgrow(spacer, Priority.ALWAYS);

			headerRow.getChildren().addAll(headerColumn, spacer, btnSave, btnBack);
			headerContainer.getChildren().add(headerRow);
			headerContainer.setAlignment(Pos.CENTER_LEFT);
						
			// Labels for all updated information goes on sheet, and back and save buttons
			Label playerName = new Label("Player Name");
			Label playerInput = new Label(String.valueOf(character.getPlayerName()));

			Label characterName = new Label("Character Name");
			Label characterInput = new Label(String.valueOf(character.getCharacterName()));

			Label classType = new Label("Class");
			Label classInput = new Label(String.valueOf(character.getCharClass()));

			Label raceType = new Label("Race");
			Label raceInput = new Label(String.valueOf(character.getCharRace()));

			Label strength = new Label("Strength");
			Label strengthInput = new Label(String.valueOf(character.getStrength()));

			Label dexterity = new Label("Dexterity");
			Label dexterityInput = new Label(String.valueOf(character.getDexterity()));

			Label constitution = new Label("Constitution");
			Label constitutionInput = new Label(String.valueOf(character.getConstitution()));

			Label intelligence = new Label("Intelligence");
			Label intelligenceInput = new Label(String.valueOf(character.getIntelligence()));

			Label wisdom = new Label("Wisdom");
			Label wisdomInput = new Label(String.valueOf(character.getWisdom()));

			Label charisma = new Label("Charisma");
			Label charismaInput = new Label(String.valueOf(character.getCharisma()));
			
			Label charInfoHeader = new Label("Character Info");
			Label attributesHeader = new Label("Attributes");
			
			Label[] labels = {
				    playerName,
				    characterName,
				    classType,
				    raceType,
				    strength,
				    dexterity,
				    constitution,
				    intelligence,
				    wisdom,
				    charisma, 
				    charInfoHeader,
				    attributesHeader
				};

			bodyContainer.setStyle("-fx-font-size: 14px;");
			
			for (Label label : labels) {
			    label.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
			}
			
			charInfoHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
			attributesHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
			
			// GridPane for attributes
			GridPane grid = new GridPane();
			grid.setHgap(20);
			grid.setVgap(2);
			grid.add(strength, 0, 0);
			grid.add(strengthInput, 1, 0);
			grid.add(dexterity, 0, 1);
			grid.add(dexterityInput, 1, 1);
			grid.add(constitution, 0, 2);
			grid.add(constitutionInput, 1, 2);
			grid.add(intelligence, 0, 3);
			grid.add(intelligenceInput, 1, 3);
			grid.add(wisdom, 0, 4);
			grid.add(wisdomInput, 1, 4);
			grid.add(charisma, 0, 5);
			grid.add(charismaInput, 1, 5);
			
			// Gridpane for char info
			GridPane infoGrid = new GridPane();
			infoGrid.setHgap(20);
			infoGrid.setVgap(6);
			infoGrid.add(playerName, 0, 0);
			infoGrid.add(playerInput, 1, 0);
			infoGrid.add(characterName, 0, 1);
			infoGrid.add(characterInput, 1, 1);
			infoGrid.add(classType, 0, 2);
			infoGrid.add(classInput, 1, 2);
			infoGrid.add(raceType, 0, 3);
			infoGrid.add(raceInput, 1, 3);

			// Divider for left VBox
			Separator divideChar = new Separator();
			
			// Divider for right VBox
			Separator divideAttrib = new Separator();
			
			// VBox for character info
			VBox charInfoContainer = new VBox(6);
			charInfoContainer.setAlignment(Pos.TOP_CENTER);
			charInfoContainer.setPrefWidth(300);
			charInfoContainer.getChildren().addAll(charInfoHeader, divideChar, infoGrid);
			
			// VBox for attributes
			VBox attributesContainer = new VBox(6);
			attributesContainer.setAlignment(Pos.TOP_CENTER);
			attributesContainer.setPrefWidth(200);
			attributesContainer.getChildren().addAll(attributesHeader, divideAttrib, grid);
			
			bodyContainer.setSpacing(60);
			bodyContainer.setAlignment(Pos.TOP_CENTER);
			bodyContainer.setPadding(new Insets(2, 0, 0, 0));
			VBox.setVgrow(bodyContainer, Priority.ALWAYS);
			bodyContainer.getChildren().addAll(charInfoContainer, attributesContainer);
			
			// see print() method
			btnSave.setOnMouseClicked(e -> {
				save(this, stage);
			});
			
			
			// back button that goes back to previous scene
			btnBack.setOnMouseClicked(e -> {
				showScene(new CharacterAbilityScene(stage, character), 4);
			});
			
		}
		
		// method opens file explorer and allows user to save their character sheet
		
		public void save(Node node, Window ownerWindow) {
			// simulates a interface for user to save their character sheet into file explorer
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save Character Sheet");
			chooser.setInitialFileName("character_sheet.png");
			chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG Image", "*.png"));
			File file = chooser.showSaveDialog(ownerWindow);
			
			if (file != null) {
				try {
					// creates snapshot image of the node
					WritableImage image = node.snapshot(new SnapshotParameters(), null);
					
					// converts image to buffered image and writes as a PNG
					ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
				} catch (IOException e) {
					e.printStackTrace();
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
