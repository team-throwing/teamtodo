package org.example.서비스_레이어_설계_테스트_패키지입니다;

import org.example.dao.UserDaoImpl;
import org.example.model.User;
import org.example.서비스_레이어_설계_테스트_패키지입니다.config.DataSourceConfig;
import org.example.서비스_레이어_설계_테스트_패키지입니다.config.ServiceConfig;
import org.example.서비스_레이어_설계_테스트_패키지입니다.dto.UserAssignmentForm;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.TestUserService;
import org.example.서비스_레이어_설계_테스트_패키지입니다.service.transaction.TransactionManager;
import org.example.서비스_레이어_설계_테스트_패키지입니다.util.BcryptPasswordCodec;

import javax.sql.DataSource;

public class TestApplication {

    public static void main(String[] args) {
        TestUserService testUserService = ServiceConfig.testUserService();

        UserAssignmentForm userAssignmentForm
                = new UserAssignmentForm("ammezkhan@gmail.com", "Q7W8E9a4s5d6!", "Seunyeop-Han");

        User user;
        try {
            user = testUserService.assignUser(userAssignmentForm);
        } catch (Exception e) {
            System.out.println("사용자 생성 실패");
            return;
        }
        System.out.println("사용자 생성 성공: " + user);
    }
}
