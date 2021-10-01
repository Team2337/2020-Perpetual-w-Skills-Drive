package frc.robot;

import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static final).  Do not put anything functional in this class.
 *
 * <p>It is advised to static finalally import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  // Path Max Velocities and Accelerations
  //BarrelRacing
  // Max Velocity - 4.145
  // Acceleration - 8.2

  //Slalom
  // Max Velocity - 4.145
  // Acceleration - 7.5

  //Bounce
  // Max Velocity - 4.145
  // Acceleration - 8.2




  /**
   * 17x17in robot - since the values are the same, we'll only define one value
   * as opposed to having a length and a width. Keep in mind - this will not work
   * in the future if the robot is not a perfect square.
   */
  private static final double DRIVETRAIN_WIDTH_INCHES = 23.5;
  public static final double DRIVETRAIN_LENGTH_INCHES = 22.5;

  // The module inset from the outside edges of the robot
  private static final double MODULE_INSET_WIDTH_INCHES = 3.25;
  private static final double MODULE_INSET_HEIGHT_INCHES = 3.25;

  private static final double TRACK_WIDTH = DRIVETRAIN_WIDTH_INCHES - (MODULE_INSET_WIDTH_INCHES * 2);
  private static final double WHEEL_BASE = DRIVETRAIN_LENGTH_INCHES - (MODULE_INSET_HEIGHT_INCHES * 2);

  //Swerve trajectory config
  public static final TrajectoryConfig SWERVE_TRAJECTORY_CONFIG = new TrajectoryConfig(
    Units.feetToMeters(Constants.Swerve.MAX_FEET_PER_SECOND),
    Units.feetToMeters(Constants.Swerve.MAX_FEET_PER_SECOND)
  );

  // Robot-specific configuration for our swerve drive algorithm
  public static final class Swerve {
    public final static double SLOWROTATESPEED = 0.05;


    public enum ModulePosition {
      FRONT_RIGHT(0),
      FRONT_LEFT(1),
      BACK_LEFT(2),
      BACK_RIGHT(3);

      public final int value;

      ModulePosition(int value) {
        this.value = value;
      }
    }

    public static final double TICKSPERINCH = 1550;


    // /2 since we're measuring from the center - halfway
    private static final double MODULE_DISTANCE_WIDTH_FROM_CENTER_INCHES = TRACK_WIDTH / 2;
    private static final double MODULE_DISTANCE_LENGTH_FROM_CENTER_INCHES = WHEEL_BASE / 2;

    // Radius to the wheel modules can be thought of as a triangle - width and length are the two sides
    public static final double DRIVETRAIN_RADIUS_INCHES = Math.hypot(MODULE_DISTANCE_WIDTH_FROM_CENTER_INCHES, MODULE_DISTANCE_LENGTH_FROM_CENTER_INCHES);

    /**
     * The max unadjusted speed in feet/sec for a Falcon 500 motor with the MK3
     * Swerve Drive Specialties module is 13.6 feet/second
     * https://www.swervedrivespecialties.com/products/mk3-swerve-module
     */
    public static final double MAX_FEET_PER_SECOND = 22;
    private static final double MAX_INCHES_PER_SECOND = MAX_FEET_PER_SECOND * 12;
    /**
     * To calculate max rotational speed:
     * Max speed in feet per second * 12 = inches per second
     * 2pi * radius of the chassis (3.71231 in) = inches in one revolution
     * inches per second / inches in one revolution =  revolutions per second
     * revolutions per second * 360 degrees = degrees per second
     */
    private static final double INCHES_PER_REVOLUTION = Math.PI * 2 * DRIVETRAIN_RADIUS_INCHES;
    private static final double REVOLUTION_PER_SECOND = MAX_INCHES_PER_SECOND / INCHES_PER_REVOLUTION;
    public static final double MAX_DEGREES_PER_SECOND = REVOLUTION_PER_SECOND * 360;
  }

  public static final class Vision {
    /******************/
    /* -------------- */
    /* --- VISION --- */
    /* -------------- */
    /******************/

    /** P value for vision rotation */
    public static double VISIONCLOSEROTATIONP = 2; //2.5
    public static double VISIONMIDDLEROTATIONP = 0.4; //0.9
    public static double VISIONOFFROTATIONP = 0.65; 
    public static double VISIONFARROTATIONP = 0.85; 

    public static double BALLTRACKINGP = 0.65;

    }

  public static final int PIXY_CHIP_SELECT = 0;
  public static final int PIXY_ANALOG = 4; //MXP pin 3
  public static final int PIXY_DIGITAL = 5;

  /*******************/
  /* --------------- */
  /* --- CAN IDs --- */
  /* --------------- */
  /*******************/

  public static final int MODULE0_DRIVE_MOTOR_ID = 0;
  public static final int MODULE0_ANGLE_MOTOR_ID = 4;
  public static final int MODULE0_ANGLE_CANCODER_ID = 1;
  public static final double MODULE0_ANGLE_OFFSET = -170.801;

  public static final int MODULE1_DRIVE_MOTOR_ID = 1;
  public static final int MODULE1_ANGLE_MOTOR_ID = 5;
  public static final int MODULE1_ANGLE_CANCODER_ID = 2;
  public static final double MODULE1_ANGLE_OFFSET = -126.826;

  public static final int MODULE2_DRIVE_MOTOR_ID = 14;
  public static final int MODULE2_ANGLE_MOTOR_ID = 10;
  public static final int MODULE2_ANGLE_CANCODER_ID = 3;
  public static final double MODULE2_ANGLE_OFFSET = -260.992;

  public static final int MODULE3_DRIVE_MOTOR_ID = 15;
  public static final int MODULE3_ANGLE_MOTOR_ID = 11;
  public static final int MODULE3_ANGLE_CANCODER_ID = 4;
  public static final double MODULE3_ANGLE_OFFSET = -8.557;

  //public static final int INTAKE = 6;

  public final class KickerWheel {
    public final static double SHORTVELOCITYP = 0.0000525; 
}

public static int CANID0;
public static int MODULE0DRIVEMOTORID = 0;
public static int MODULE1DRIVEMOTORID = 1;
public static int SHOOTERRIGHTMOTOR = 2;
public static int SHOOTERLEFTMOTOR = 3;
public static int MODULE0ANGLEMOTORID = 4;
public static int MODULE1ANGLEMOTORID = 5;
public static int KICKER = 6;
                  //Limelight = 7
public static int INTAKE = 8;
public static int AGITATOR = 9;
public static int MODULE2ANGLEMOTORID = 10;
public static int MODULE3ANGLEMOTORID = 11;
public static int SERIALIZER = 12;
public static int CLIMBER = 13;
public static int MODULE2DRIVEMOTORID = 14;
public static int MODULE3DRIVEMOTORID = 15;

public static final int PCM0 = 0;
public static final int PCM1 = 1;

 /*********************/
    /* ----------------- */
    /* --- PCM PORTS --- */
    /* ----------------- */
    /*********************/

    public static int PCM0PORT0 = 0;
    public static int PCM0PORT1 = 1;
    public static int PCM0PORT2 = 2;
    public static int PCM0PORT3 = 3;
    public static int PCM0PORT4 = 4;
    public static int PCMLEDSTRIP = 5;
    public static int PCM0PORT6 = 6;
    public static int PCM0PORT7 = 7;

    /*********************/
    /* ----------------- */
    /* --- DIO PORTS --- */
    /* ----------------- */
    /*********************/

    public static int DIOPORT0 = 0;
    public static int DIOPORT1 = 1;
    public static int DIOPORT2 = 2;
    public static int DIOPORT3 = 3;
    public static int DIOPORT4 = 4;
    public static int DIOPORT5 = 5;
    public static int DIOPORT6 = 6;
    public static int DIOPORT7 = 7;
    public static int DIOPORT8 = 8;
    public static int DIOPORT9 = 9;

    /************************/
    /* -------------------- */
    /* --- ANALOG PORTS --- */
    /* -------------------- */
    /************************/

    public static int ANALOGPORT0 = 0;
    public static int ANALOGPORT1 = 1;
    public static int ANALOGPORT2 = 2;
    public static int ANALOGPORT3 = 3;

    /***********************/
    /* ------------------- */
    /* --- RELAY PORTS --- */
    /* ------------------- */
    /***********************/

    public static int RELAYPORT0 = 0;
    public static int RELAYPORT1 = 1;
    public static int RELAYPORT2 = 2;
    public static int RELAYPORT3 = 3;

    /*********************/
    /* ----------------- */
    /* --- PWM PORTS --- */
    /* ----------------- */
    /*********************/

    public static int PWMPORT0 = 0;
    public static int PWMPORT1 = 1;
    public static int PWMPORT2 = 2;
    public static int PWMPORT3 = 3;
    public static int PWMBLINKIN = 4;
    public static int PWMPORT5 = 5;
    public static int PWMPORT6 = 6;
    public static int PWMPORT7 = 7;
    public static int PWMPORT8 = 8;
    public static int SERVOPORT = 9;
   /********************/
    /* ---------------- */
    /* --- AGITATOR --- */
    /* ---------------- */
    /********************/

    /** Percent speed of the agitator */
    public static double AGITATORSPEED = 0.4;

    public static double AGITATORSHOOTSPEED = 0.5;

    /** Percent speed of the agitator */
    public static double AGITATORREVERSESPEED = -0.2;

    /******************/
    /* -------------- */
    /* --- INTAKE --- */
    /* -------------- */
    /******************/
    
   /* --- Intake --- */
   public static double INTAKEFORWARDSPEED = 0.5;
   public static double INTAKEREVERSESPEED = -0.4;

   //Whether or not to detect jams for the agitator
   public static boolean DETECTINTAKEJAMS = true;

   //The current to trigger motor reversal at
   public static int INTAKECURRENTTOLERENCE = 40;

   //The amount of time (in seconds) to reverse the Serializer when a jam is detected
   public static double INTAKEREVERSALDURATION = 0.4;


    /*******************/
    /* --------------- */
    /* --- CLIMBER --- */
    /* --------------- */
    /*******************/

    /** Percent speed on the climber */
    public static double CLIMBERSPEED = 0.7;

    
    /*******************/
    /* --------------- */
    /* --- SHOOTER --- */
    /* --------------- */
    /*******************/

    /**
     * This value is the number at which the closed loop ramp rate of the shooter
     * goes from 0.5 to 0 to increase speed
     */
    public static int SHOOTERRAMPSWITCHVALUE = 5000;
    
    /** Speed to shoot at from ~16 feet away */
    public static int SHOOTSPEEDCLOSE = 13025; 
    /** Speed to shoot at from ~34 feet away */
    public static int SHOOTSPEEDFAR = 15400; 

    public static int SHOOTFRONTTRENCHSPEED = 13750;

    public static int SHOOTFRONTTRENCHAUTO = 13300;

    /**********************/
    /* ------------------ */
    /* --- SERIALIZER --- */
    /* ------------------ */
    /**********************/

    /** Maximum speed of the serializer */
    public static double SERIALIZERPEAKSPEED = 0.4; // .4

    /** Percent speed on the serializer when moving to positions */
    public static double SERIALIZERPOSITIONSPEED = 0.2;

    /** Percent forward speed when serializing or shooting */
    public static double SERIALIZERDRIVERFORWARDSPEED = 0.4; // 0.25
    
    public static double SERIALIZEROPERATORFORWARDSPEED = 0.4;  //0.3
    /** Percent reverse speed when serializing or shooting */
    public static double SERIALIZERREVERSESPEED = -0.3;
    
    //Amount of ticks to reverse the serializer by when readying the kicker wheel
    public static double SERIALIZERREGRESSIONDISTANCE = -4096;

    /******************/
    /* -------------- */
    /* --- KICKER --- */
    /* -------------- */
    /******************/
    
    /** Kicker wheel velocity for the far shot */
    public static int KICKERSPEEDFAR = 5000;

    /** Kicker wheel velocity for the near shot */
    public static int KICKERSPEEDCLOSE = 7000;

    public static int KICKERSPEEDFRONTTRENCH = 4000;

    public static double KICKERCONTROLPANELSPEED = 12000;

    /******************/
    /* -------------- */
    /* --- VISION --- */
    /* -------------- */
    /******************/

    /** P value for vision rotation */

    /*
    public static double VISIONCLOSEROTATIONP = 2.5; 
    public static double VISIONMIDDLEROTATIONP = 0.9; 
    public static double VISIONOFFROTATIONP = 0.65; 
    public static double VISIONFARROTATIONP = 0.85; 
*/
    public static double BALLTRACKINGP = 0.80; //0.65

    public Constants() {
      //KICKERSPEEDCLOSE = 7000; 
      //KICKERSPEEDFAR = 5000; 
      //KICKERSPEEDFRONTTRENCH = 4000;

    }
    public static final class Auton {
      public static final double INCHESTOJOYSTICKVALUE = 1;
      public static final double AUTOSTRAFEP = 1.25;
  }
}
