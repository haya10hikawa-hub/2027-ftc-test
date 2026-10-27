package teamcode.subsystems;


import ftclib.motor.FtcDcMotor;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;

public class Motor extends TrcSubsystem<Motor.Action> {
    public enum Action
    {
        Rotate,
        Stop,
        Brake,
    }

    public static final String SUBSYSTEM_NAME = "Motor";
    private static final String MOTOR_NAME = "motor";
    private static final double ROTATE_POWER_SCALE = 0.5;
    private final FtcDcMotor motor;

    public Motor()
    {
        super(SUBSYSTEM_NAME, false);
        motor = new FtcDcMotor(MOTOR_NAME);
        motor.setMotorInverted(false);
    }

    @Override
    public void cancel()
    {
        motor.cancel();
    }

    @Override
    public void zeroCalibrate(String owner, TrcEvent completionEvent)
    {
    }

    @Override
    public void resetState()
    {
        motor.setPower(0.0);
    }

    @Override
    public void subsystemControl(boolean altFunc, double... inputs)
    {
        motor.setPower(inputs[0]);
    }

    @Override
    public void subsystemAction(Action action, Object context)
    {
        switch (action)
        {
            case Rotate:
                motor.setPower(1 * ROTATE_POWER_SCALE);
                break;
            case Stop:
                motor.setBrakeModeEnabled(false);
                motor.setPower(0.0);
                break;
            case Brake:
                motor.setBrakeModeEnabled(true);
                motor.setPower(0.0);
                break;
            default:
                break;
        }
    }

    @Override
    public void tuneSubsystem(TuneAction action, String tuneSubsystemName)
    {
    }

    @Override
    public void publishToDashboard()
    {
    }

    @Override
    public int updateStatus(int lineNum, boolean slowLoop)
    {
        return lineNum;
    }

    @Override
    public void updateParamsToDashboard(String subsystemName)
    {
    }

    @Override
    public void updateParamsFromDashboard(String subsystemName)
    {
    }
}
