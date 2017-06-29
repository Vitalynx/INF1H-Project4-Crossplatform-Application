package com.paladinzzz.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.paladinzzz.game.database.Database;

import com.paladinzzz.game.database.JSONfunctions;
import com.paladinzzz.game.database.parseJSON;
import com.paladinzzz.game.util.Constants;


public class HighScoresScreen implements Screen{

    private com.paladinzzz.game.CrossplatformApp game;
    private Stage stage;
    private TextButton backButton;
    private OrthographicCamera camera;
    private Table table;
    BitmapFont font = new BitmapFont();
    private Skin skin;
    private Texture background;
    private String[] data;
    private String[] playerscores;
    String nm = "";
    String sc = "";
    private Sound click = Gdx.audio.newSound(Gdx.files.internal("Audio/click.wav"));

    public HighScoresScreen (com.paladinzzz.game.CrossplatformApp game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.stage = new Stage(new FillViewport(com.paladinzzz.game.util.Constants.WIDTH, com.paladinzzz.game.util.Constants.HEIGHT, camera));
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        this.background = new Texture("Screens/HighscoresScreen/highscores.png");
    }


    @Override
    public void show() {

        backButton = new TextButton("back", skin);
        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
                MenuScreen.musicHandler.stopMusic();
                click.play(2.0f);
            }
        });

        //Hiermee kunnen elementen nu aan de stage worden toegevoegd
        Gdx.input.setInputProcessor(stage);

        JSONfunctions json = new JSONfunctions();
        parseJSON parse = new parseJSON(json.doInBackground());

        for (String i : parse.getNames()){
            if(i != null) {
                nm += i;
                nm += "\n\n";
            } else {
                break;
            }
        }

        System.out.println(nm);
//
        for (String i : parse.getScores()){
            if(i != null) {
                sc += i;
                sc += "\n\n";
            } else {
                break;
            }
        }
        
        //Een table wordt aangemaakt om buttons aan toe te voegen.
        table = new Table();
        table.setFillParent(true);
        table.bottom();
        table.add(backButton).size(100, 100);
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        font.draw(game.batch, nm, 100, 300);
        font.draw(game.batch, sc, 480, 300);

        game.batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();

    }
}
