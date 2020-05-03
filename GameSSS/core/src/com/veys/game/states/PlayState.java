package com.veys.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.veys.game.Game;
import com.veys.game.managers.GSM;

public class PlayState extends GameState{

	private World world;
	private Box2DDebugRenderer b2dr;
	private OrthographicCamera camb2dr;
	private Body platform;
	private Body player,circle1,player2,circle2;
	private TmxMapLoader tmx;
	private TiledMap map;
	private OrthogonalTiledMapRenderer tiledRenderer;
	
	private RevoluteJoint rightWhell,leftWhell;
	private float value = 3;
	
	public static final float PPM = 100;
	public static final short  BIT_GROUND = 2;
	public static final short  BIT_BOX = 4;
	public static final short BIT_PLAYER = 8;
	public static Viewport view;
	
	public PlayState(GSM gsm) {
		super(gsm);
		

		
		
		camb2dr = new OrthographicCamera();
		camb2dr.setToOrtho(false,(Game.WIDTH)/PPM,(Game.HEIGHT)/PPM);

		view = new FitViewport(Game.WIDTH/PPM, Game.HEIGHT/PPM);
		
		world = new World(new Vector2(0,-9.81f),true);

		b2dr = new Box2DDebugRenderer();
		
		DefinitionMyWorldMethod();
		
		

		/*
		tmx = new TmxMapLoader();
		map = tmx.load("level1.tmx");
		tiledRenderer = new OrthogonalTiledMapRenderer(map,1/PPM);
		*/
		
		/*
		for(MapObject object : map.getLayers().get(2)
				.getObjects()
				.getByType(RectangleMapObject.class)) {
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			bdef.type = BodyType.StaticBody;
			bdef.position.set((rect.getX()+rect.getWidth()/2)/PPM,
					(rect.getY()+rect.getHeight()/2)/PPM);
			
			body = world.createBody(bdef);
			
			shape.setAsBox((rect.getWidth()/2)/PPM, 
					(rect.getHeight()/2)/PPM);
			
			fdef.shape = shape;
			body.createFixture(fdef);
			
		}
		
		*/
	
	}

	public void HandleInput(float dt) {
		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			//world.step(1/60f, 6,2);
			rightWhell.setMotorSpeed(rightWhell.getJointSpeed());
			leftWhell.setMotorSpeed(leftWhell.getJointSpeed());
			
		}
		else if(Gdx.input.isKeyJustPressed(Input.Keys.X)) {
			player.setType(BodyDef.BodyType.DynamicBody);
		
		}
		*/
	

		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.applyLinearImpulse(new Vector2(0,4f), 
					player.getWorldCenter(), true);
			
		}
		
		
		if(Gdx.input.isKeyPressed(Input.Keys.D) && player.getLinearVelocity().x <=2) {
			player.applyLinearImpulse(new Vector2(0.1f,0),
					
					player.getWorldCenter(), true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A) && player.getLinearVelocity().x >=-2) {
			player.applyLinearImpulse(new Vector2(-0.1f,0),
					
					player.getWorldCenter(), true);
		}
	*/
		
		
	}
	
	public void DefinitionMyWorldMethod() {
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		CircleShape shapeCircle = new CircleShape();
		FixtureDef fdef = new FixtureDef();
		

		
		//create platform
		createBox(100, 100, 700, 10, true, false);

		
		////create object

		player = createBox(350, 400, 20, 20, false, false);
		//player2 = createBox(410, 400, 20, 20, false, false);
		circle1  = createCircle(350, 380, 20, false);
		circle2  = createCircle(410, 380, 20, false);
		
		RevoluteJointDef rdef = new RevoluteJointDef();
		rdef.enableMotor= true;
		rdef.motorSpeed = value;
		rdef.maxMotorTorque = 2;
		rdef.bodyA = player;
		rdef.bodyB = circle1;
		rdef.collideConnected = false;
		rdef.localAnchorA.set(-64/PPM, -64/PPM);
		//rdef.localAnchorB.set(64/PPM, 0);
		leftWhell = (RevoluteJoint) world.createJoint(rdef);
		
		RevoluteJointDef rdef2 = new RevoluteJointDef();
		rdef2.bodyA = player;
		rdef2.bodyB = circle2;
		rdef2.collideConnected = false;
		rdef2.localAnchorA.set(64/PPM,-64/PPM);
		//rdef2.localAnchorB.set(64/PPM,0);
		
		rightWhell = (RevoluteJoint) world.createJoint(rdef2);
		
	}

	public void tick(float dt) {
		
		HandleInput(dt);
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			//world.step(1/60f, 6,2);
			rightWhell.setMotorSpeed(-rightWhell.getJointSpeed());
			leftWhell.setMotorSpeed(-leftWhell.getJointSpeed());
			
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			if(rightWhell.getJointSpeed() <= 5){
				rightWhell.setMotorSpeed(rightWhell.getJointSpeed() +2);
				leftWhell.setMotorSpeed(leftWhell.getJointSpeed()  +2);
			}else {
				rightWhell.setMotorSpeed(rightWhell.getJointSpeed());
				leftWhell.setMotorSpeed(leftWhell.getJointSpeed());
			}
		
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.S)) {
			if(rightWhell.getJointSpeed() >= -5){
				rightWhell.setMotorSpeed(rightWhell.getJointSpeed() -2);
				leftWhell.setMotorSpeed(leftWhell.getJointSpeed()  -2);
			}
			else {
				rightWhell.setMotorSpeed(rightWhell.getJointSpeed());
				leftWhell.setMotorSpeed(leftWhell.getJointSpeed() );
			}
	
		}


		world.step(1/60f, 6,2);
		camb2dr.update();
		
	}

	public void render(SpriteBatch sb) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//tiledRenderer.render();
		//tiledRenderer.setView(camb2dr);
		
		
		b2dr.render(world, camb2dr.combined);
		
		
		
	}
	
	
	public Body createBox(int x , int y ,int width , int height,boolean isStatic,boolean fixedRotation) {
		Body pBody;
		BodyDef def = new BodyDef();
		
		if(isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;
		
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = fixedRotation;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/PPM, height/PPM);
		pBody.createFixture(shape,1.0f);
		shape.dispose();
		return pBody;
	}
	
	public Body createCircle(int x,int y,int radius,boolean fixedRotation) {
		Body pBody;
		BodyDef def = new BodyDef();

		def.type = BodyDef.BodyType.DynamicBody;
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = fixedRotation;
		pBody = world.createBody(def);

		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius/PPM);

		pBody.createFixture(shape,1.0f);
		shape.dispose();
		return pBody;
		
		
	}


	@Override
	public void dispose() {
		b2dr.dispose();
		world.dispose();
		map.dispose();
		
	}

	
	

}
