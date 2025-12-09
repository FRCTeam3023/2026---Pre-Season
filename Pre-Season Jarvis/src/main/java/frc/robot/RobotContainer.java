package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.events.EventTrigger;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.DeferredCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Commands.Notifications;
import frc.robot.Subsystems.ChassisVisionLocalizer;
import frc.robot.Subsystems.Drivetrain;
import frc.robot.Subsystems.LED;
import frc.robot.Util.PIDDisplay;

public class RobotContainer {
  private static final Drivetrain drivetrain = new Drivetrain();
  private static final LED led = new LED();
  SendableChooser<Command> autoChooser;

  private static boolean isBlue = false;

  public RobotContainer() {
    new ChassisVisionLocalizer();

    ControlPanel.configureBinding(drivetrain);
    configureAuto();
    
  }

  public static boolean isBlue() {
    return isBlue;
  }

  public void onTeleopEnabled() {
    isBlue = DriverStation.getAlliance().get().equals(Alliance.Blue);
  }

  private void configureAuto() {
    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
      drivetrain.homeCommand(),
      AutoBuilder.buildAuto(autoChooser.getSelected().getName())
    );
  }
}
