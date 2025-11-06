// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
//import com.ctre.phoenix.motorcontrol.can.TalonSRX;
// If TalonFX isn't working change for SRX
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {

  public double laggingMotorVelocity = 0;
  final VoltageOut m_request = new VoltageOut(0);
  final VelocityVoltage m_velocity = new VelocityVoltage(0);


  TalonFX motorLeft = new TalonFX(2);
  TalonFX motorRight = new TalonFX(1);

  public DriveTrain() {
    TalonFXConfiguration talonConfig = new TalonFXConfiguration();
    talonConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;
    talonConfig.ClosedLoopRamps.DutyCycleClosedLoopRampPeriod = 2;

    talonConfig.Slot0.kP = .5;
    talonConfig.Slot1.kS = 1;

    talonConfig.Feedback.SensorToMechanismRatio = 0;

    motorLeft.getConfigurator().apply(talonConfig);
    motorRight.getConfigurator().apply(talonConfig);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void set(double leftJoy, double rightJoy) {
    double leftSpeed = leftJoy;
    double rightSpeed = rightJoy;

    motorLeft.setControl(m_request.withOutput(12*leftSpeed));
    motorRight.setControl(m_velocity.withVelocity(laggingMotorVelocity*rightSpeed));
  }

  public void stop() {
    motorLeft.setControl(m_request.withOutput(0));
    motorRight.setControl(m_velocity.withVelocity(0));
  }
}
