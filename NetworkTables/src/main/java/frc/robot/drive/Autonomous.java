package frc.robot.drive;

import frc.robot.Robot;
import frc.robot.RobotMap;

public class Autonomous
{

    private double x;
    private double y;
    private double r;
    private boolean doneTwice = false;

    public boolean disable = false;

    public Autonomous()
    {

    }

    public void setX(double x)
    {
        this.x = x;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public void setR(double r)
    {
        this.r = r;
    }

    public void chooser()
    {
        if(Robot.oi.buttonX.get())
        {
            driveX();
            driveY();
            Robot.teleopDrive.turnOnAxis.turnAngleController.enable();
        }
    }

    public void driveX()
    {
        Robot.xpid.xController.enable();
        Robot.xpid.set(0);
        Robot.autoOp = true;
    }

    public void driveY()
    {
        Robot.ypid.yController.enable();
        Robot.ypid.set(0);
        Robot.autoOp = true;
    }

    public void driveAuto()
    {
        if(Robot.autoOp)
        {
            autoEnd();
            RobotMap.drive.driveCartesian(-y,x,0.7*Robot.teleopDrive.turnOnAxis.pidAngleOutput.getSpeed()); //x=right -y=forward
        }
    }

    public void autoEnd()
    {
        if(Robot.ypid.yController.isEnabled()&&Robot.ypid.yController.onTarget())
        {
            if(doneTwice)
            {
                Robot.ypid.yController.disable();
                doneTwice = false;
            }
            else
            {
                Robot.ypid.yController.setSetpoint(0);
                doneTwice = true;
            }
        }
        else if(Robot.xpid.xController.isEnabled()&&Robot.xpid.xController.onTarget())
        {
            if(doneTwice)
            {
                Robot.xpid.xController.disable();
                doneTwice = false;
            }
            else
            {
                Robot.xpid.xController.setSetpoint(0);
                doneTwice = true;
            }
        }
        else if(Robot.teleopDrive.turnOnAxis.turnAngleController.onTarget())
        {
            Robot.teleopDrive.turnOnAxis.turnAngleController.disable();
        }
    }
}