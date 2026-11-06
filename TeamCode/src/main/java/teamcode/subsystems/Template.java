package teamcode.subsystems;

import ftclib.motor.FtcDcMotor;
import trclib.robotcore.TrcEvent;
import trclib.subsystem.TrcSubsystem;



/**
 * 新しいサブシステム（機構）を追加するためのテンプレート。
 * 新しい機能を追加するときの手順:
 *
 * 1. このファイルをコピーして名前を変える（例: Intake.java）。
 *    クラス名・コンストラクタ名・Action の型名「Template」をすべて新しい名前に置き換える。
 *
 * 4.〜6. このファイルの一番最後のコメントを参照（Robot.java → FtcTeleOp.java の順に進む）。
 *
 * モーターではなくサーボを使う場合は FtcDcMotor の代わりに ftclib.motor.FtcServo を使う。
 * コードの書き方はリポジトリ直下の STYLEGUIDE.md に従うこと。
 */
public class Template extends TrcSubsystem<Template.Action>
{
    /**
     * このサブシステムが行える動作の一覧。TeleOp からはこの値を subsystemAction() に渡して動かす。
     * 【手順3】ボタンで呼び出したい動作をここに追加し、subsystemAction() の switch に処理を書く。
     */
    public enum Action
    {
        Run,        // 正転
        Reverse,    // 逆転
        Stop,       // 停止（惰性で止まる）
    }



    public static final String SUBSYSTEM_NAME = "Template";
    // ↑【手順2】サブシステムの名前。クラス名と同じにする。

    private static final String HARDWARE_NAME = "templateMotor";
    // ↑【手順2】Driver Station の Configure Robot で付けた名前と完全に一致させる（大文字小文字も区別）。
    //   ポート番号と名前は ROBOINFO.md にも追記する。

    private static final double POWER = 0.5;
    // ↑ 出力の大きさ（0.0〜1.0）。速すぎる場合はここを下げる。

    private static final boolean INVERTED = false;
    // ↑ 回転方向が逆なら true にする。

    private final FtcDcMotor motor;



    /**
     * コンストラクタ。ハードウェアの初期化を行う。
     * HARDWARE_NAME がロボットの設定に存在しないと、ここで例外が発生する。
     */
    public Template()
    {
        super(SUBSYSTEM_NAME, false);
        motor = new FtcDcMotor(HARDWARE_NAME);
        motor.setMotorInverted(INVERTED);
    }



    /**
     * 実行中の動作を中止する。OpMode 終了時などに呼ばれる。
     */
    @Override
    public void cancel()
    {
        motor.cancel();
    }



    /**
     * 原点合わせ（リミットスイッチ等で位置を初期化する）。不要なら空のままでよい。
     */
    @Override
    public void zeroCalibrate(String owner, TrcEvent completionEvent)
    {
    }



    /**
     * 初期状態に戻す。
     */
    @Override
    public void resetState()
    {
        motor.setPower(0.0);
    }



    /**
     * スティックなどのアナログ入力で直接動かすときに使う。inputs[0] は -1.0〜1.0。
     */
    @Override
    public void subsystemControl(boolean altFunc, double... inputs)
    {
        motor.setPower(inputs[0] * POWER);
    }



    /**
     * ボタンなどから呼ばれる動作。
     * 【手順3】Action に追加した動作の処理を case として書く。
     */
    @Override
    public void subsystemAction(Action action, Object context)
    {
        switch (action)
        {
            case Run:
                motor.setPower(POWER);
                break;

            case Reverse:
                motor.setPower(-POWER);
                break;

            case Stop:
                motor.setPower(0.0);
                break;

            default:
                break;
        }
    }



    /**
     * Driver Station の画面に状態を表示する。lineNum 行目から書き、使った行数分だけ進めて返す。
     */
    @Override
    public int updateStatus(int lineNum, boolean slowLoop)
    {
        return lineNum;
    }



    /**
     * チューニング用。必要になるまで空のままでよい。
     */
    @Override
    public void tuneSubsystem(TuneAction action, String tuneSubsystemName)
    {
    }



    /**
     * ダッシュボード表示用。必要になるまで空のままでよい。
     */
    @Override
    public void publishToDashboard()
    {
    }



    /**
     * ダッシュボードへパラメータを送る。必要になるまで空のままでよい。
     */
    @Override
    public void updateParamsToDashboard(String subsystemName)
    {
    }



    /**
     * ダッシュボードからパラメータを読み込む。必要になるまで空のままでよい。
     */
    @Override
    public void updateParamsFromDashboard(String subsystemName)
    {
    }
}



/*
 * このファイルの書き換えが終わったら、次は Robot.java へ。
 * Robot.java 内の【手順4】のコメントに従ってサブシステムを登録する。
 */
