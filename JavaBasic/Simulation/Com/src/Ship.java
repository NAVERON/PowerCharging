
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ship extends Navigator {

    private List<History> historys = new LinkedList<>();
    private double rudder = 0;
    
    public Point2D destination;  //目的地
    public double direction;  //目标方向
    public double finalVelocity;  //目标速度
    private double K = 0.0785, T = 3.12;

    public Ship(int id) {
        super(new Circle(10, Color.RED));
        super.setId(id);
        super.setVelocity(Point2D.ZERO);
        super.getView().setRotate(0);
    }

    public Ship(int id, Point2D velocity) {
        super(new Circle(10, Color.RED));
        super.setId(id);
        super.setVelocity(velocity);
    }

    public int isWhat() {  //对象类型，1代表普通船舶
        return 1;
    }

    public void diffRudder(double diff) {  //传入的是舵角变化值
        this.rudder += diff;
    }

    public double getRudder() {
        return this.rudder;
    }

    public List<History> getHistorys() {
        return historys;
    }

    public boolean addHistory() {
        return this.historys.add(new History(super.getView().getRotate(), super.getVelocity(), super.getPosition().getX(), super.getPosition().getY(), getRudder()));
    }

    public void clearHistory() {
        historys.clear();
    }

    public void setDestination(Point2D destination) {
        this.destination = destination;
    }

    public Point2D getDestination() {
        return this.destination;
    }

    @Override
    public void upDate() {
        
        if (isDead()) {  //如果已经碰撞，则不进行所有的计算
            this.setVelocity(Point2D.ZERO);  //设置为静止
            return;
        }
        if (super.getView().getTranslateX() > 800) {
            super.getView().setTranslateX(0);
        } else if (super.getView().getTranslateX() < 0) {
            super.getView().setTranslateX(800);
        } else if (super.getView().getTranslateY() > 600) {
            super.getView().setTranslateY(0);
        } else if (super.getView().getTranslateY() < 0) {
            super.getView().setTranslateY(600);
        }
        //下面把速度变化了
        //System.out.println(super.getVelocity().getX() + "before===" + super.getVelocity().getY() + "rotate :" + super.getView().getRotate());
        double dr = K * rudder * (1 - T + T * Math.pow(Math.E, -1 / T));
        super.getView().setRotate( (super.getAngle() + dr)%360 );
        setVelocity(new Point2D(Math.cos(Math.toRadians(super.getView().getRotate())) * getSpeed(),
                Math.sin(Math.toRadians(super.getView().getRotate())) * getSpeed()
        ));
        //System.out.println(super.getVelocity().getX() + "after===" + super.getVelocity().getY() + "rotate : " + super.getView().getRotate());
        super.getView().setTranslateX(super.getView().getTranslateX() + super.getVelocity().getX());
        super.getView().setTranslateY(super.getView().getTranslateY() + super.getVelocity().getY());
        
        addHistory();
        
        thinkAndDoing();
    }

    @Override
    public void turnLeft() {  //转向影响的是速度分量的变化
        if(rudder < -35){
            return;
        }
        rudder -= 4;
    }
    @Override
    public void turnRight() {
        if(rudder >= 35){
            return;
        }
        rudder += 3.5;
    }
    public void turnPositiveOn(){  //摆正舵角  //这种写法模拟舵角逐渐变化的过程
        //摆正舵
        double difrudder = 0 - rudder;
        if(Math.abs(difrudder) < 0.5){  //如果舵角很小，则直接认为已经摆正舵角
            rudder = 0;
            return;
        }
        rudder += difrudder/4;
    }
    public void speedDown(){
        if(this.getSpeed() < 0.5){
            return;
        }
        this.setVelocity(this.getVelocity().subtract(
                new Point2D(Math.cos(Math.toRadians(this.getAngle())), Math.sin(Math.toRadians(this.getAngle())))
        ));
    }
    public void speedUp(){
        if(this.getSpeed() > 10){
            return;
        }
        this.setVelocity(this.getVelocity().add(
                new Point2D(Math.cos(Math.toRadians(this.getAngle())), Math.sin(Math.toRadians(this.getAngle())))
        ));
    }

    public void coursePID(double direction) {
        //
    }
    public void autoSpeed(double finalVelocity) {
        //
    }
    private List<LocalShip> local;  //转换成本地坐标系中无人艇的对象
    private double[][] SA_matrix;  //代价矩阵
    public void thinkAndDoing() {  //覆盖方法
        //周围船舶数据的传入
        
        List<Ship> temp = getNear(300);   //获取附近对象的引用
        SA_matrix = this.get_sa_matrix(temp);
//        System.out.println("计算后的SA_matrix数组：" );
//        for(int i = 0; i < SA_matrix.length; i++){
//            for(int j = 0; j < SA_matrix[i].length; j++){
//                System.out.print("    "+SA_matrix[i][j]);
//            }
//            System.out.println();
//        }
        Message message = this.getMessage();
        LocalShip com = null;
        if (message == null) {    //如果没有协商内容，则继续前进，不做动作
            //判断情况适当的时候发送  协商消息
            double min = 0;
            int index = 0;
            //Random random = new Random();
            //index = random.nextInt(3);   //这里使用随机动作代替计算危险度最小值
            for(int j = 0; j < SA_matrix[0].length; j++){
                if(SA_matrix[0][j] <= min){
                    min = SA_matrix[0][j];
                    index = j;
                }
            }
            //这里使用传统方法，主要体现通信协商过程，不要把主要放在这里
            if(SA_matrix[0][0] > 0){
                if(SA_matrix[0][1] > 0){
                    if(SA_matrix[0][2] > 100){
                        //前面有，右边有，左边也有
                        speedDown();
                    }else{
                        //前面有，右边有，左边没有
                        turnLeft();
                        //这里发消息
                        for (int i = 0; i < local.size(); i++) {
                            LocalShip ls = local.get(i);
                            if (ls.ratio > 90 && ls.ratio < 330) {  //左舷交叉
                                com = ls;
                                break;
                            }
                        }
                        if(com != null){
                            sendMessage(this.getId(), new Message(this.getId(), com.getId() , min, new int[]{0, index}));
                        }
                    }
                }else{
                    //前面有，右边没有
                    turnRight();
                    //发消息，通知右边的
                    for (int i = 0; i < local.size(); i++) {
                        LocalShip ls = local.get(i);
                        if (ls.ratio > 30 && ls.ratio <= 112.5) {  //右舷交叉
                            com = ls;
                            break;
                        }
                    }
                    if(com != null){
                        sendMessage(com.getId(), new Message( this.getId(), com.getId(), min, new int[]{0, index} ));
                    }
                }
            }else{
                //正常前进============11.14修改为  朝向目标前近
                //turnPositiveOn();
                
                if( !getNear(100).isEmpty() ){
                    turnPositiveOn();
                    return;
                }
                
                Point2D delta_D = destination.subtract(getPosition());
                double delta_angle = calAngle(delta_D.getX(), delta_D.getY());
                
                if( delta_D.crossProduct(this.getVelocity()).getZ() > 5 ){
                    if(this.getId()==0){
                    System.out.println("当前航向：" + this.getAngle()+", 目标航向：" + delta_angle + "，决策：左转");
                    }
                    turnLeft();
                }else if ( delta_D.crossProduct(this.getVelocity()).getZ() < 5 ){
                    if(this.getId()==0){
                    System.out.println("当前航向：" + this.getAngle()+", 目标航向：" + delta_angle + "，决策：右转");
                    }
                    turnRight();
                }else{
                    turnPositiveOn();
                }
            }
        } else {  //如果收到信表示别人看到你了，所以不用发信给别人，直接进入协商阶段
            for(int i = 0; i < local.size(); i++){
                if(local.get(i).getId() == message.getSender()){
                    com = local.get(i);
                    break;
                }
            }
            if(com == null || message.getState().equals("AGREE")){
                return;
            }
            //判断会遇局面
            int row_index = message.getIndex()[0], col_index = message.getIndex()[1];
            double r = message.getR();
            //从表中减少对应态势的r
            //求取最小代价值
            int col = 0;
            if (com.ratio >= 355 || com.ratio <= 30) {  //对遇局面
                row_index = 0;
                SA_matrix[row_index][col_index] -= r;
                //col = hook_min_index(row_index);
            } else if (com.ratio > 30 && com.ratio <= 112.5) {  //右舷交叉
                row_index = 1;
                SA_matrix[row_index][col_index] -= r;
            } else if (com.ratio > 210 && com.ratio < 355) {  //左舷交叉
                row_index = 2;
                SA_matrix[row_index][col_index] -= r;
            } else if (com.ratio > 112.5 && com.ratio <= 210) {  //追越局面，在我后面
                row_index = 3;
                SA_matrix[row_index][col_index] -= r;
            }
            col = hook_min_index(row_index);
            switch(col){
                case 0:
                    speedDown();
                    break;
                case 1:
                    turnRight();
                    //转向之后需要向这边的发消息
                    break;
                case 2:
                    turnLeft();
                    //转向后需要进一步判断
                    break;
                case 3:
                    turnPositiveOn();
                    break;
            }
        }
        
    }
    
    public double[][] get_sa_matrix(List<Ship> temp) {     //输入临近的无人艇引用 =========输出代价矩阵array
        
        local = warpAxis(temp);  //获取转换坐标后的对象
        double[][] dcpa_tcpa = new double[local.size()][2];
        for (int i = 0; i < local.size(); i++) {
            dcpa_tcpa[i] = this.cal_Dcpa_Tcpa(local.get(i));
        }
        for (int i = 0; i < dcpa_tcpa.length; i++) {  //增加权值
            dcpa_tcpa[i][0] = 2 * dcpa_tcpa[i][0];
            dcpa_tcpa[i][1] = 0.5 * dcpa_tcpa[i][1];
        }
        double r_usv[] = new double[local.size()];
        double region[] = new double[]{0, 0, 0, 0};  //每个领域的危险度值
        for (int i = 0; i < local.size(); i++) {
            r_usv[i] = dcpa_tcpa[i][0]*dcpa_tcpa[i][0] + dcpa_tcpa[i][1]*dcpa_tcpa[i][1];    //这里平方之后不能看到负数，负数表示没有危险
            
            LocalShip ls = local.get(i);
            if (ls.ratio >= 330 || ls.ratio <= 30) {  //对遇局面
                region[0] += r_usv[i];
            } else if (ls.ratio > 30 && ls.ratio <= 112.5) {  //右舷交叉
                region[1] += r_usv[i];
            } else if (ls.ratio > 210 && ls.ratio < 355) {  //左舷交叉
                region[3] += r_usv[i];
            } else if (ls.ratio > 112.5 && ls.ratio <= 210) {  //追越局面
                region[2] += r_usv[i];
            }
        }
        double[][] sa_matrix = new double[4][4];
        for (int i = 0; i < 4; i++) {
            sa_matrix[i][0] = region[0];
            sa_matrix[i][1] = region[1];
            sa_matrix[i][2] = region[2];
            sa_matrix[i][3] = region[3];
        }
        
        return sa_matrix;
    }
    public int hook_min(double[][] sa_matrix, double min){   //取最小值
        int col_index = -1;   //如果传进去的就是最小值，则表示同意现在的情况，否则本无人艇选择另一种方案
        for(int i = 0; i < sa_matrix[0].length; i++){
            if(sa_matrix[0][i] < min){
                min = sa_matrix[0][i];
                col_index = i;
            }
        }
        return col_index;
    }
    public int hook_min_index(int row_index){
        int col_index = -1;
        double min = SA_matrix[row_index][0];
        for(int i = 0; i < SA_matrix[row_index].length; i++){
            if(SA_matrix[row_index][i] < min){
                min = SA_matrix[row_index][i];
                col_index = i;
            }
        }
        return col_index;
    }
    public List<LocalShip> warpAxis(List<Ship> temp) {  //将附近的无人艇转换坐标系，并返回转换坐标系后的对象存储
        List<LocalShip> local = new LinkedList<>();
        for (int i = 0; i < temp.size(); i++) {
            Ship i_ship = temp.get(i);
            Point2D dp = i_ship.getPosition().subtract(this.getPosition());
            double radian = -Math.toRadians(this.getAngle());  //当前航向，转换成   弧度
            double x = dp.getX() * Math.cos(radian) - dp.getY() * Math.sin(radian);
            double y = dp.getX() * Math.sin(radian) + dp.getY() * Math.cos(radian);
            
            double dh = i_ship.getAngle() - this.getAngle();
            while (dh >= 360 || dh < 0) {                   ///////////////////////////////需要验证
                if (dh >= 360) {
                    dh -= 360;
                }
                if (dh < 0) {
                    dh += 360;
                }
            }
            //y = -y;  //这里转换成左下角坐标系
            
            LocalShip s = new LocalShip(i_ship.getId());
            s.setPosition(new Point2D(x, y));
            s.getView().setRotate(dh);
            s.ratio = s.calAngle(x, y);
            //所有的坐标系都是正向右  是0度航向角，左上角为坐标原点
            //System.out.println(this.getId() + "转换后状态：" + "x " + x + ", y " + y + ", ratio :" + s.ratio);
            local.add(s);
        }
        return local;
    }
    
    public double[] cal_Dcpa_Tcpa(Navigator local_ship) {  //输入一个转换后对象，得出它的DCPA和TCPA
        double Dcpa = 0, Tcpa = 0;
        double dis = Point2D.ZERO.distance(local_ship.getPosition());
        Point2D rxy = local_ship.getVelocity().subtract(this.getVelocity());
        double rh = this.calAngle(rxy.getX(), rxy.getY());
        double th = (180 + local_ship.calAngle(local_ship.getPosition().getX(), local_ship.getPosition().getY())) % 360;
        double alpha = rh - th;
        if (th < 180) {
            alpha = -alpha;
        }
        alpha = Math.toRadians(alpha);
        Dcpa = Math.sin(alpha) * dis;
        Tcpa = Math.cos(alpha) * dis / Math.sqrt(rxy.getX() * rxy.getX() + rxy.getY() + rxy.getY());
        //System.out.println(this.getId() + "==DCPA " + Dcpa + "TCPA " + Tcpa);
        return new double[]{Dcpa, Tcpa};
    }
    
    //  把环境船舶数据  传入
    List<Ship> ships = null;
    public void inShips(List<Ship> ships) {
        this.ships = ships;
    }
    
    public List<Ship> getNear(int dis) {  // dis代表多远的距离  附近值
        List<Ship> near = new LinkedList<>();
        for (Ship s : this.ships) {
            if (s.getId() == this.getId()) {
                continue;
            }
            if (this.getPosition().distance(s.getPosition()) < dis) {
                near.add(s);
            }
        }
        return near;
    }

    public Queue<Message> messages = new LinkedList<>();     // offer插入     poll  提出

    public Message getMessage() {
        return messages.poll();
    }
    
    public void sendMessage(int id, Message message) {
        for (int i = 0; i < ships.size(); i++) {
            if (ships.get(i).getId() == id) {
                if(ships.get(i).isAlive()){
                    ships.get(i).messages.offer(message);
                }
                break;  //不管发没发，只要找到了就完成发送信息任务
            }
        }
    }
}
