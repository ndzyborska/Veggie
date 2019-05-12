package com.gluon.Veg.views;

import com.gluon.Veg.Veggie;
import com.gluonhq.charm.glisten.application.GlassPane;
import com.gluonhq.charm.glisten.application.MobileApplication;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.layout.Layer;
import com.gluonhq.charm.glisten.layout.layer.SidePopupView;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static com.gluon.Veg.Veggie.*;

public class PrimaryPresenter {

    @FXML
    private View primary;

    @FXML
    Button go;

    @FXML
    VBox myView;

    @FXML
    HBox cheap, showstopper, quick, breakfast, deserts, salads;

    @FXML
    HBox recipeArray;

    @FXML
    Button imageDrop;

    @FXML
    ScrollPane recipeScroll;

    @Inject
    ArrayList<recipeButtons> myRecipes;

    @Inject
    recipeButtons buttons;

    private ArrayList<HBox> categories = new ArrayList<>();
    private final int buttonHeight = 96;
    private final int buttonWidth = 106;
    public String buttonId = null;


    public void initialize() {

        primary.showingProperty().addListener((obs, oldValue, newValue) -> {

            if (newValue) {
                AppBar appBar = MobileApplication.getInstance().getAppBar();
                appBar.setStyle("-fx-background-color: black;");
                appBar.setNavIcon(MaterialDesignIcon.MENU.button(e ->
                        MobileApplication.getInstance().getDrawer().open()));
                appBar.setTitleText("Recipes");
                appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e ->
                        System.out.println(myRecipes.get(0).getImage() + myRecipes.get(0).getText())));
                if (buttons.isTrue()) {
                    addButton();
                    buttons.setFalse();
                }
            }
        });



        try {
            initRecipes();
        } catch (IOException | InvalidFormatException e) {
                        e.printStackTrace();
        }




    }

    @FXML
    void addButton() {
        imageDrop.setVisible(true);

       Polyline polyline = new Polyline();
        polyline.getPoints().addAll(new Double[]{
                0.0,0.0,
                -70.1, 30.0,
                -125.0, 400.0
        });

        imageDrop.setStyle("-fx-background-image: url('" + buttons.getImage() + "')");
        imageDrop.setPrefWidth(65);
        imageDrop.setPrefHeight(65);

        PathTransition pt = new PathTransition();
        pt.setOnFinished(e -> putButton());
        pt.setNode(imageDrop);
        pt.setInterpolator(Interpolator.EASE_IN);
        pt.setDuration(Duration.seconds(1));
        pt.setPath(polyline);
        pt.setCycleCount(1);

        pt.play();

        recipeScroll.setVisible(true);


    }


    void putButton() {
          imageDrop.setVisible(false);
          imageDrop.setTranslateX(125);
          imageDrop.setTranslateX(-400);
        Button v = new Button();
        v.setStyle("-fx-background-image: url('" +buttons.getImage() + "')");
        v.setPrefWidth(65);
        v.setPrefHeight(65);


        recipeArray.getChildren().add(v);

    }



    @FXML
    void initRecipes() throws IOException, InvalidFormatException {

        File file = new File("src/main/resources/RecipeList/Recipes.xlsx");
        String path = file.getAbsolutePath();
        ExcelReader list = new ExcelReader(path);

        int i = 10;
        int sheetNo = 0;

        categories.add(cheap);
        categories.add(showstopper);
        categories.add(quick);
        categories.add(breakfast);
        categories.add(deserts);
        categories.add(salads);

        for (HBox h : categories) {
            for (int k = 0; k < list.getAmount(sheetNo); k++) {  // Can be presented better....
                String buttonId = Integer.toString(i) + Integer.toString(k + 1);
                Button button = new Button(list.getName(buttonId, sheetNo));
                button.setId(buttonId);
                button.setStyle("-fx-background-image: url('" + list.getImage(buttonId, sheetNo)+ "')");
                button.setPrefHeight(buttonHeight);
                button.setPrefWidth(buttonWidth);

                button.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event){

                        try {
                            buttons.setImage( list.getImage(buttonId, (Character.getNumericValue(buttonId.charAt(0)))-1));
                            buttons.setName(list.getName(buttonId, (Character.getNumericValue(buttonId.charAt(0)))-1));


                        } catch (IOException | InvalidFormatException ex) {
                            ex.printStackTrace();
                        }

                        buttons.setText(((Button)event.getSource()).getId());
                        System.out.println("GET TEXT" +buttons.getText());
                        MobileApplication.getInstance().addViewFactory(buttonId, () -> (View)(new RecipeView().getView()));
                        MobileApplication.getInstance().switchView(buttonId);

                    }
                });

                h.getChildren().add(button);
                h.getParent().prefWidth(h.getPrefWidth());


            }



            sheetNo++;
            i += 10;

        }

        list.close();
    }


    @FXML
    void myRecipes(ActionEvent event) {

      /*  if (buttons.getButtonArray().isEmpty()) {
            System.out.println("You haven't picked any recipes yet!");
            return;
        }*/

        MobileApplication.getInstance().switchView(SECONDARY_VIEW);

    }

    @FXML
    void changeScene(String buttonId) {

        MobileApplication.getInstance().addViewFactory(buttonId, () -> (View)(new RecipeView().getView()));

        MobileApplication.getInstance().switchView(buttonId);



     /*   MobileApplication.getInstance().addViewFactory();
        MobileApplication.getInstance().getView().
        MobileApplication.getInstance().switchView()*/



     /*   return new Layer() {
            private final Node root;
            private final double size = 150;
            final GlassPane glassPane = MobileApplication.getInstance().getGlassPane();

            {
                setBackgroundFade(0.5);

                VBox i = new VBox();
                i.setMinSize(350,364);

                i.getChildren().addAll(makeArray(buttonId, 0));
                i.setStyle("-fx-background-color: PINK;");

                VBox m = new VBox();
                m.setMinSize(350,364);
                i.getChildren().addAll(makeArray(buttonId, 1));
                i.setStyle("-fx-background-color: TEAL;");

                VBox n = new VBox();
                n.setMinSize(350,364);
                n.getChildren().addAll(makeArray(buttonId, 2));

                ScrollPane ingredients = new ScrollPane(i);
                ingredients.setMinSize(950,364);
                ScrollPane method = new ScrollPane(m);
                method.setMinSize(950,364);
                ScrollPane nutrients = new ScrollPane(n);
                nutrients.setMinSize(950,364);


                root = new HBox();
                ((HBox) root).setMinSize(950, 364);
                ((HBox) root).getChildren().add(ingredients);
                ((HBox) root).getChildren().add(method);
                ((HBox) root).getChildren().add(nutrients);

                root.setStyle("-fx-background-color: PINK;");
                getChildren().add(root);
            }

            @Override
            public void layoutChildren() {
                super.layoutChildren();
                root.setVisible(isShowing());
                if (!isShowing()) {
                    return;
                }
                root.resize(size, size);
                resizeRelocate((glassPane.getWidth() - size) / 2, (glassPane.getHeight() - size) / 2, size, size);
            }
        };*/
    }



       /* System.out.println("HERE2: "+buttonId);
        setButtonId(buttonId);

        RecipeView r = new RecipeView(buttonId);

        View t = (View) r.getView();
        t.getChildren().add(new Label("Porddige"));

        MobileApplication.getInstance().addViewFactory(buttonId, () -> t);
            MobileApplication.getInstance().addLayerFactory(buttonId, );
        MobileApplication.getInstance().switchView(buttonId);
*/


}
