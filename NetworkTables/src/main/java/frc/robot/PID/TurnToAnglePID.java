/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.PID;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.PID.PIDAngleWrite;
import frc.robot.Constants;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

/**
 * Add your docs here.
 */
public class TurnToAnglePID {
    
    public PIDController turnAngleController;
    public AHRS aHRS;
    public PIDAngleWrite pidAngleOutput;
    public double rotationSpeed, setpoint;
    public boolean autoTurn =false;
    public boolean doneTwice = false;
    public boolean toRocket = false;
    public boolean pressAgain = false;
    public boolean first = true;
    public double angle = 0;
    public XboxController _xBox = new XboxController(0);
    Timer buttonTime = new Timer();


    public TurnToAnglePID () {
        pidAngleOutput = new PIDAngleWrite();
        aHRS = new AHRS(SPI.Port.kMXP);
        aHRS.setPIDSourceType(PIDSourceType.kDisplacement);
        turnAngleController = new PIDController(Constants.kPr, Constants.kIr, Constants.kDr, aHRS, pidAngleOutput);
        turnAngleController.setInputRange(-180, 180);
        turnAngleController.setAbsoluteTolerance(Constants.turnLimit);
        turnAngleController.setContinuous(true);
        turnAngleController.setToleranceBuffer(5); //if we don't want to use this, we can easily create it...
        LiveWindow.addActuator("DriveSystem", "turnController", turnAngleController);
    }

    public double TurnToAbsolute () {
        if(autoTurn)
        {
            return pidAngleOutput.getSpeed();
        }
        else
        {
            return Robot.oi.getRightX();
        }
    }

    public void checkTurn()
    {
        
        if(turnAngleController.onTarget())
        {

        }
    }

    public void rocket()
    {
        if(Robot.oi.startB.get()&&(first||buttonTime.get()>=0.25))
        {
            buttonTime.reset();
            buttonTime.start();
            toRocket=!toRocket;
            first = false;
        }
    }

    public void automatedTurnToAngle (){
        if(!autoTurn)
        {
            rocket();
            if(!toRocket){
                if((_xBox.getPOV()) == 0) { 
                    setpoint = 0;
                    turnAngleController.setSetpoint(0);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if((_xBox.getPOV()) == 90) { 
                    setpoint = 90;
                    turnAngleController.setSetpoint(90);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if ((_xBox.getPOV()) == 180) { 
                    setpoint = 180;
                    turnAngleController.setSetpoint(180);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if((_xBox.getPOV()) == 270) { 
                    setpoint = -90;
                    turnAngleController.setSetpoint(-90);
                    turnAngleController.enable();
                    autoTurn = true;
                }
            }
            else if(toRocket){
                if(_xBox.getPOV() == 45) { 
                    setpoint = 29;
                    turnAngleController.setSetpoint(61);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if(_xBox.getPOV() == 315) { 
                    setpoint = -29;
                    turnAngleController.setSetpoint(-61);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if(_xBox.getPOV() == 135){
                    setpoint = 151;
                    turnAngleController.setSetpoint(119);
                    turnAngleController.enable();
                    autoTurn = true;
                }
                else if(_xBox.getPOV() == 225) {
                    setpoint = -151;
                    turnAngleController.setSetpoint(-119);
                    turnAngleController.enable();
                    autoTurn = true;
                }
            }
        }
        else
        {
            checkTurn();
        }
    }
}
