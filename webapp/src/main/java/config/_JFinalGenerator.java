package config;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.c3p0.C3p0Plugin;

import javax.sql.DataSource;


/**
 * Created by hzqiuxm on 2016/3/3 0003.
 *
 * 在数据库表有任何变动时，运行一下 main 方法,重新生成model和表映射关系
 */
public class _JFinalGenerator {

    public static DataSource getDataSource() {
        PropKit.use("properties/config.properties");

        C3p0Plugin c3p0Plugin = CommonConfig.createC3p0Plugin();
        c3p0Plugin.start();
        return c3p0Plugin.getDataSource();
    }

    public static void main(String[] args) {

        //base model 所使用的包名
        String baseModelPackageName = "com.znblog.model.base";
        // base model 文件保存路径
//        System.out.println(PathKit.getWebRootPath());
        String baseModelOutputDir = PathKit.getWebRootPath() + "/../../model\\src\\main\\java\\com\\znblog\\model\\base";
        System.out.println(baseModelOutputDir);
        // model 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.znblog.model";
        // model 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = baseModelOutputDir + "/..";

        // 创建生成器
        Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 添加不需要生成的表名
        gernerator.addExcludedTable("");
        // 设置是否在 Model 中生成 dao 对象
        gernerator.setGenerateDaoInModel(true);
        // 设置是否生成字典文件
        gernerator.setGenerateDataDictionary(true);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
        gernerator.setRemovedTableNamePrefixes("t_");
        // 生成
        gernerator.generate();
    }
}
