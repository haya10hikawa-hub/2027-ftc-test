# STYLEGUIDE

TeamCode の Java コードを書くときのルールです。新しく書くコードはこのルールに従ってください。

## 空行

- クラスやメソッドの間は必ず **3 行以上** 空ける。
  - どのコメントがどのメソッドのものか、ひと目で分かるようにするため。

```java
    public void cancel()
    {
        motor.cancel();
    }



    /**
     * 初期状態に戻す。
     */
    public void resetState()
    {
        motor.setPower(0.0);
    }
```

## コメント

- 変数・定数への説明コメントは、その行の **下** に `// ↑ 説明` の形で書く。
  - 上に書くと、エディタの自動整列で前の行のコメントに見えてしまうため。

```java
    private static final double POWER = 0.5;
    // ↑ 出力の大きさ（0.0〜1.0）。
```

- クラス・メソッドの説明は、その直前に `/** ... */` で書く。

## 新しいサブシステムの追加

- `TeamCode/src/main/java/teamcode/subsystems/Template.java` をコピーして作る。
  手順は Template.java → Robot.java（【手順4】）→ FtcTeleOp.java（【手順5】【手順6】）の順に、
  各ファイル内のコメントで案内している。「【手順」で検索すると見つけやすい。
