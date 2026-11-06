package teamcode.subsystems;

import ftclib.motor.FtcServo;
import trclib.motor.TrcServo;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;

/**
 * Standard (positional) servo subsystem. The servo moves gradually while a button is held and stops where it is
 * when the button is released.
 */
public class Servo extends TrcSubsystem<Servo.Action>
{
    public enum Action
    {
        MoveUp,
        MoveDown,
        Stop,
    }

    public static final String SUBSYSTEM_NAME = "Servo";
    private static final String SERVO_NAME = "servo";
    // Logical position range the servo is allowed to move in (0.0 to 1.0 is the full range).
    private static final double MIN_POS = 0.0;
    private static final double MAX_POS = 1.0;
    // Position used when the servo has not been commanded yet.
    private static final double INIT_POS = 0.5;
    // How fast the servo moves while a button is held (position units per second, full range = 1.0).
    private static final double MAX_STEP_RATE = 0.5;
    private static final double MOVE_POWER = 1.0;
    private final FtcServo servo;

    public Servo()
    {
        super(SUBSYSTEM_NAME, false);
        TrcServo.Params params = new TrcServo.Params().setMaxStepRate(MAX_STEP_RATE);
        servo = new FtcServo(SERVO_NAME, params);
        servo.setInverted(false);
    }

    @Override
    public void cancel()
    {
        servo.cancel();
    }

    @Override
    public void zeroCalibrate(String owner, TrcEvent completionEvent)
    {
    }

    @Override
    public void resetState()
    {
        servo.setPower(0.0);
    }

    @Override
    public void subsystemControl(boolean altFunc, double... inputs)
    {
        ensureInitialPosition();
        servo.setPower(inputs[0], MIN_POS, MAX_POS);
    }

    @Override
    public void subsystemAction(Action action, Object context)
    {
        switch (action)
        {
            case MoveUp:
                ensureInitialPosition();
                servo.setPower(MOVE_POWER, MIN_POS, MAX_POS);
                break;
            case MoveDown:
                ensureInitialPosition();
                servo.setPower(-MOVE_POWER, MIN_POS, MAX_POS);
                break;
            case Stop:
                servo.setPower(0.0);
                break;
            default:
                break;
        }
    }

    /**
     * A servo has no position until it is commanded once, so stepping would start from NaN. Command a known
     * position first in that case.
     */
    private void ensureInitialPosition()
    {
        if (Double.isNaN(servo.getPosition()))
        {
            servo.setPosition(INIT_POS);
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
