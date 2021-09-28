package pro.jiefzz.demo.uow.demo1.aggrGenerate;

import java.io.IOException;

import com.github.kimffy24.uow.util.GenerateSqlMapperUtil;

import pro.jiefzz.demo.uowdemo.aggr.profile.PersonalProfile;

@Deprecated
public class PersonalProfileGeneration {

	public static void main(String[] args) {
		try {
			GenerateSqlMapperUtil.generateSqlMapper(
					PersonalProfile.class,
					"personal_profile", "id");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
