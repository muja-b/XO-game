import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class XO extends JFrame {
    private JButton button01;
    private JButton button11;
    private JComboBox comboBox1;
    private JLabel diff;
    private JRadioButton a1PlayerRadioButton;
    private JRadioButton a2PlayerRadioButton;
    private JButton startButton;
    private JPanel home;
    private JButton button00;
    private JButton button02;
    private JButton button12;
    private JButton button10;
    private JButton button22;
    private JButton button21;
    private JButton button20;
    private JButton pressed;
    private JTextField whosturn;
    boolean turn=true;
    private int selectedDepth;


    public JPanel getMainPanel() {
        return home;
    }
    int[][] arr ={{-1, -1, -1}, {-1, -1, -1}, {-1, -1, -1}};
    int[][] arr2=new int[2][2];
    int i1;
    int j1;
    public XO() {
        createUIComponents();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==button00||e.getSource()==button01||e.getSource()==button02||e.getSource()==button10||
                        e.getSource()==button11||e.getSource()==button12||e.getSource()==button20||e.getSource()==button21||e.getSource()==button22){
                    pressed= getbutton(e);

                    if(turn){
                        if(pressed.getText().equals("")){
                            pressed.setForeground(new Color(0,0,255));
                            pressed.setText("X");
                            j1= Integer.parseInt(pressed.getName().substring(pressed.getName().length() - 1));
                            i1= Integer.parseInt(String.valueOf(pressed.getName().charAt(pressed.getName().length() - 2)));
                            arr[i1][j1]=1;
                            whosturn.setText("O's turn");

                            if(a2PlayerRadioButton.isSelected())turn=false;
                            if(a1PlayerRadioButton.isSelected()){
                                int bestVal =Integer.MIN_VALUE;
                                int bestrow = -1;
                                int bestcol = -1;
                                for(int i=0;i<Moves(arr);i++){
                                    int x=makeMove(arr,i,0);
                                    int moveVal=minmax(arr,0,false,Integer.MIN_VALUE,Integer.MAX_VALUE);
                                    undoMove(x);
                                    if(moveVal >bestVal){
                                        bestrow=x/10;
                                        bestcol=x%10;
                                        bestVal=moveVal;
                                    }
                                }
                                if(bestcol!=-1||bestrow!=-1)arr[bestrow][bestcol]=0;
                                setboard();
                            }
                            check();
                        }
                    }else{
                        if(pressed.getText().equals("")){
                            pressed.setForeground(new Color(255,0,0));
                            pressed.setText("O");
                            j1= Integer.parseInt(pressed.getName().substring(pressed.getName().length() - 1));
                            i1= Integer.parseInt(String.valueOf(pressed.getName().charAt(pressed.getName().length() - 2)));
                            arr[i1][j1]=0;
                            whosturn.setText("X's turn");
                            turn=true;
                            check();
                        }

                    }

                }

            }
        };
        button01.addActionListener(listener);
        button11.addActionListener(listener);
        button12.addActionListener(listener);
        button02.addActionListener(listener);
        button10.addActionListener(listener);
        button00.addActionListener(listener);
        button21.addActionListener(listener);
        button22.addActionListener(listener);
        button20.addActionListener(listener);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            for(int i=0;i<3;i++)
                for(int j=0;j<3;j++){
                    arr[i][j]=-1;
                }
                setbuttonsEnabled(true);
                setboard();
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox1.getSelectedIndex()==0){
                    selectedDepth =1;
                }
                if(comboBox1.getSelectedIndex()==1){
                    selectedDepth =4;
                }
                if(comboBox1.getSelectedIndex()==2){
                    selectedDepth =12;
                }
            }
        });
    }

    private void setboard() {
        if(arr[0][0]==1)button00.setText("X");
        if(arr[0][1]==1)button01.setText("X");
        if(arr[0][2]==1)button02.setText("X");
        if(arr[1][0]==1)button10.setText("X");
        if(arr[1][1]==1)button11.setText("X");
        if(arr[1][2]==1)button12.setText("X");
        if(arr[2][0]==1)button20.setText("X");
        if(arr[2][1]==1)button21.setText("X");
        if(arr[2][2]==1)button22.setText("X");

        if(arr[0][0]==0)button00.setText("O");
        if(arr[0][1]==0)button01.setText("O");
        if(arr[0][2]==0)button02.setText("O");
        if(arr[1][0]==0)button10.setText("O");
        if(arr[1][1]==0)button11.setText("O");
        if(arr[1][2]==0)button12.setText("O");
        if(arr[2][0]==0)button20.setText("O");
        if(arr[2][1]==0)button21.setText("O");
        if(arr[2][2]==0)button22.setText("O");

        if(arr[0][0]==0)button00.setForeground(new Color(255,0,0));
        if(arr[0][1]==0)button01.setForeground(new Color(255,0,0));
        if(arr[0][2]==0)button02.setForeground(new Color(255,0,0));
        if(arr[1][0]==0)button10.setForeground(new Color(255,0,0));
        if(arr[1][1]==0)button11.setForeground(new Color(255,0,0));
        if(arr[1][2]==0)button12.setForeground(new Color(255,0,0));
        if(arr[2][0]==0)button20.setForeground(new Color(255,0,0));
        if(arr[2][1]==0)button21.setForeground(new Color(255,0,0));
        if(arr[2][2]==0)button22.setForeground(new Color(255,0,0));

        if(arr[0][0]==-1)button00.setText("");
        if(arr[0][1]==-1)button01.setText("");
        if(arr[0][2]==-1)button02.setText("");
        if(arr[1][0]==-1)button10.setText("");
        if(arr[1][1]==-1)button11.setText("");
        if(arr[1][2]==-1)button12.setText("");
        if(arr[2][0]==-1)button20.setText("");
        if(arr[2][1]==-1)button21.setText("");
        if(arr[2][2]==-1)button22.setText("");


    }

    private int minmax(int [][]arr1,int depth,boolean isMax,int alpha,int beta) {
            if(Moves(arr1)==0||wins(arr1)||selectedDepth==depth)return eval();
            int x;
            if(isMax){
                int best=Integer.MIN_VALUE;
                for(int i=0;i<Moves(arr1);i++){
                    x =makeMove(arr1,i,0);
                    int value=minmax(arr1,depth+1,false,alpha,beta);
                    best=Integer.max(value,best);
                    undoMove(x);
                    alpha=Integer.max(alpha,best);
                    if (beta <= alpha)break;
                }
                return best;
            }else{
                int best=Integer.MAX_VALUE;
                for(int i=0;i<Moves(arr1);i++){
                    x =makeMove(arr1,i,1);
                    int value=minmax(arr1,depth+1,true,alpha,beta);
                    best=Integer.min(value,best);
                    undoMove(x);
                    alpha=Integer.min(alpha,best);
                    if (beta <= alpha)break;
                }
                return best;
            }

    }

    private void undoMove(int x) {
        if(x==44)return;
        int j=x%10;
        int i=x/10;
        arr[i][j]=-1;
    }

    private boolean wins(int[][] arr1) {
        if((arr[0][0]==1)&&(arr[0][1]==1)&&(arr[0][2]==1))return true;
        if((arr[1][0]==1)&&(arr[1][1]==1)&&(arr[1][2]==1))return true;
        if((arr[2][0]==1)&&(arr[2][1]==1)&&(arr[2][2]==1))return true;
        if((arr[0][0]==1)&&(arr[1][0]==1)&&(arr[2][0]==1))return true;
        if((arr[0][1]==1)&&(arr[1][1]==1)&&(arr[2][1]==1))return true;
        if((arr[0][2]==1)&&(arr[1][2]==1)&&(arr[2][2]==1))return true;
        if((arr[0][0]==1)&&(arr[1][1]==1)&&(arr[2][2]==1))return true;
        if((arr[0][2]==1)&&(arr[1][1]==1)&&(arr[2][0]==1))return true;

        if((arr[0][0]==0)&&(arr[0][1]==0)&&(arr[0][2]==0))return true;
        if((arr[1][0]==0)&&(arr[1][1]==0)&&(arr[1][2]==0))return true;
        if((arr[2][0]==0)&&(arr[2][1]==0)&&(arr[2][2]==0))return true;
        if((arr[0][0]==0)&&(arr[1][0]==0)&&(arr[2][0]==0))return true;
        if((arr[0][1]==0)&&(arr[1][1]==0)&&(arr[2][1]==0))return true;
        if((arr[0][2]==0)&&(arr[1][2]==0)&&(arr[2][2]==0))return true;
        if((arr[0][0]==0)&&(arr[1][1]==0)&&(arr[2][2]==0))return true;
        if((arr[0][2]==0)&&(arr[1][1]==0)&&(arr[2][0]==0))return true;
        else return false;
    }

    private  int makeMove(int [][] arr1,int i,int turn){
        int moveCounter=0;
        for(int k=0;k<3;k++)
            for(int j=0;j<3;j++){
                if(arr1[k][j]==-1){
                    if(i==moveCounter){
                        arr1[k][j]=turn;
                        return 10*k+j;
                    }else moveCounter++;
                }
            }
        return 44;
    }

    private int Moves(int [][]arr1) {
        int anyMove=0;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++){
                if(arr1[i][j]==-1)anyMove++;
            }
        return anyMove;
    }

    private JButton getbutton(ActionEvent e) {
        if(e.getSource()==button00)return button00;
        if(e.getSource()==button01)return button01;
        if(e.getSource()==button02)return button02;
        if(e.getSource()==button10)return button10;
        if(e.getSource()==button11)return button11;
        if(e.getSource()==button12)return button12;
        if(e.getSource()==button20)return button20;
        if(e.getSource()==button21)return button21;
        if(e.getSource()==button22)return button22;
        else return startButton;
    }

    public void check(){
       if((arr[0][0]==1)&&(arr[0][1]==1)&&(arr[0][2]==1))xWins(button00,button01,button02);
       if((arr[1][0]==1)&&(arr[1][1]==1)&&(arr[1][2]==1))xWins(button10,button11,button12);
       if((arr[2][0]==1)&&(arr[2][1]==1)&&(arr[2][2]==1))xWins(button20,button21,button22);
       if((arr[0][0]==1)&&(arr[1][0]==1)&&(arr[2][0]==1))xWins(button00,button10,button20);
       if((arr[0][1]==1)&&(arr[1][1]==1)&&(arr[2][1]==1))xWins(button01,button11,button21);
       if((arr[0][2]==1)&&(arr[1][2]==1)&&(arr[2][2]==1))xWins(button02,button12,button22);
       if((arr[0][2]==1)&&(arr[1][2]==1)&&(arr[2][2]==1))xWins(button02,button12,button22);
       if((arr[0][0]==1)&&(arr[1][1]==1)&&(arr[2][2]==1))xWins(button00,button11,button22);
       if((arr[0][2]==1)&&(arr[1][1]==1)&&(arr[2][0]==1))xWins(button02,button11,button20);

       if((arr[0][0]==0)&&(arr[0][1]==0)&&(arr[0][2]==0))oWins(button00,button01,button02);
       if((arr[1][0]==0)&&(arr[1][1]==0)&&(arr[1][2]==0))oWins(button10,button11,button12);
       if((arr[2][0]==0)&&(arr[2][1]==0)&&(arr[2][2]==0))oWins(button20,button21,button22);
       if((arr[0][0]==0)&&(arr[1][0]==0)&&(arr[2][0]==0))oWins(button00,button10,button20);
       if((arr[0][1]==0)&&(arr[1][1]==0)&&(arr[2][1]==0))oWins(button01,button11,button21);
       if((arr[0][2]==0)&&(arr[1][2]==0)&&(arr[2][2]==0))oWins(button02,button12,button22);
       if((arr[0][2]==0)&&(arr[1][2]==0)&&(arr[2][2]==0))oWins(button02,button12,button22);
       if((arr[0][0]==0)&&(arr[1][1]==0)&&(arr[2][2]==0))oWins(button00,button11,button22);
       if((arr[0][2]==0)&&(arr[1][1]==0)&&(arr[2][0]==0))oWins(button02,button11,button20);


    }
    public void xWins(JButton b0,JButton b1,JButton b2){
        b0.setBackground(Color.green);
        b1.setBackground(Color.green);
        b2.setBackground(Color.green);
        setbuttonsEnabled(false);
        whosturn.setText("X wins");
    }
    public void oWins(JButton b0,JButton b1,JButton b2){
        b0.setBackground(Color.green);
        b1.setBackground(Color.green);
        b2.setBackground(Color.green);
        setbuttonsEnabled(false);
        whosturn.setText("O wins");
    }
    public  int eval(){
        int score=0;
        score += evaluateLine(0, 0, 0, 1, 0, 2);  // row 0
        score += evaluateLine(1, 0, 1, 1, 1, 2);  // row 1
        score += evaluateLine(2, 0, 2, 1, 2, 2);  // row 2
        score += evaluateLine(0, 0, 1, 0, 2, 0);  // col 0
        score += evaluateLine(0, 1, 1, 1, 2, 1);  // col 1
        score += evaluateLine(0, 2, 1, 2, 2, 2);  // col 2
        score += evaluateLine(0, 0, 1, 1, 2, 2);  // diagonal
        score += evaluateLine(0, 2, 1, 1, 2, 0);  // alternate diagonal
        return score;
    }

    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
    int score=0;
    if(arr[row1][col1]==0&&arr[row2][col2]==0&&arr[row3][col3]==0)return 1000;
    if(arr[row1][col1]==0){
        score=1;
    }
    else if(arr[row1][col1]==1){
        score=-1;
    }
    if(arr[row2][col2]==0){
        if(score==0){
            score=10;
        }
        else if(score==1){
            return 0;
        }
        else score=1;
    }
    else if(arr[row2][col2]==1)
        if(score==-1)score=-10;
        else if(score==1)return 0;
        else score=-1;

    if(arr[row3][col3]==0)
        if(score>0)score*=10;
        else if(score<0)return 0;
        else score=1;

    else if(arr[row3][col3]==1)
        if(score<0)score*=10;
        else if(score>1)return 0;
        else score=-1;

        return score;
    }

    private void setbuttonsEnabled(boolean f) {
        button00.setEnabled(f);
        button01.setEnabled(f);
        button11.setEnabled(f);
        button12.setEnabled(f);
        button02.setEnabled(f);
        button10.setEnabled(f);
        button21.setEnabled(f);
        button22.setEnabled(f);
        button20.setEnabled(f);

    }


    private void createUIComponents() {
            a2PlayerRadioButton.setSelected(true);
            comboBox1.addItem("Easy");
            comboBox1.addItem("Medium");
            comboBox1.addItem("Hard");
            button00.setName("00");
            button01.setName("01");
            button11.setName("11");
            button12.setName("12");
            button02.setName("02");
            button10.setName("10");
            button21.setName("21");
            button22.setName("22");
            button20.setName("20");




    }
}
