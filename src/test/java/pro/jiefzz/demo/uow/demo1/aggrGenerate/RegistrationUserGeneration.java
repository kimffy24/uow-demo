package pro.jiefzz.demo.uow.demo1.aggrGenerate;

import java.io.IOException;

import com.github.kimffy24.uow.util.GenerateSqlMapperUtil;

import pro.jiefzz.demo.uowdemo.aggr.user.RegistrationUser;

@Deprecated
public class RegistrationUserGeneration {

	public static void main(String[] args) {
		try {
			GenerateSqlMapperUtil.generateSqlMapper(
					RegistrationUser.class,
					"registration_user", "id");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
