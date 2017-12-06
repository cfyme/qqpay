import com.fshows.proxy.util.DateUtil;

public class Test {

    public static void main(String[] args){

        String createTime = DateUtil.getStringByMillis(System.currentTimeMillis(), "yyyyMMddHHmmss");

        System.out.println(createTime);
    }
}
