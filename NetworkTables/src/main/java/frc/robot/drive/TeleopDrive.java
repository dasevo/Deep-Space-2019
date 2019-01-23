package frc.robot.drive;

import frc.robot.RobotMap;
import frc.robot.PID.TurnToAnglePID;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Robot;
import frc.robot.Constants;

public class TeleopDrive
{
    public TurnToAnglePID turnOnAxis;

    public TeleopDrive()
    {
        turnOnAxis = new TurnToAnglePID();
    }

    public void execute()
    {
        if(!Robot.autoOp)
        {
            //CONSTANTS ARE HERE
            RobotMap.drive.driveCartesian(0.7*Robot.oi.getLeftY(), 0.7*Robot.oi.getLeftX(), 0.7*turnOnAxis.TurnToAbsolute()); 
            //first parameter is forward/backwards second is left/right
        }
    }

    public void getDiagnostics()
    {
        SmartDashboard.putBoolean("DPad turn", turnOnAxis.turnAngleController.isEnabled());
        SmartDashboard.putBoolean("autoTurn", turnOnAxis.autoTurn);
        SmartDashboard.putBoolean("exitPID", Robot.teleopDrive.turnOnAxis.turnAngleController.onTarget());
        SmartDashboard.putNumber("turError", turnOnAxis.turnAngleController.getError());
        SmartDashboard.putNumber("turnPID", Robot.teleopDrive.turnOnAxis.rotationSpeed);
        SmartDashboard.putNumber("Dpad", Robot.teleopDrive.turnOnAxis._xBox.getPOV());
        SmartDashboard.putNumber("setpoint", Robot.teleopDrive.turnOnAxis.turnAngleController.getSetpoint());
    }


}