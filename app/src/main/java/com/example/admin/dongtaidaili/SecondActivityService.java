package com.example.admin.dongtaidaili;

import java.util.Map;

/**
 * Created by admin on 2017/3/24.
 */

public interface SecondActivityService {
    @ActivityRouter("com.example.admin.dongtaidaili.SecondActivity")
    void startSecondActivity(@ParamsRouter Map<String,String> map);
}
