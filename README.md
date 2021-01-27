# Jacoco for Android

1. AndroidManifest.xml权限配置
```
   <!-- jacoco 需要读写权限-->
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

```

2. 将jacoco.gradle植入待测试项目下，与build.gradle同级
3. 项目build.gradle添加对应设置
```
    apply from: './jacoco.gradle'
    
    buildTypes {
        release {
            testCoverageEnabled true
            ...
            }
        debug {
            testCoverageEnabled true
            ...
            }
    } 
    
    dependencies {
    ...
    implementation 'com.mfw.plugin.jacoco:jacoco-helper:1.0.0'
    }
```

4.  插桩，项目onCreate方法内
```
    protected void onCreate(Bundle savedInstanceState) {
    
        JacocoHelper.Builder builder = new JacocoHelper.Builder();
        builder.setApplication(getApplication()).setDebuggable(true);
        JacocoHelper.initialize(builder.build());
        
        super.onCreate(savedInstanceState);
        
```
5. jacoco增量覆盖率依赖
```
pip install diff_cover

diff-cover ./module-poi/poi-implement/build/reports/jacoco/jacocoBuild/jacocoBuild.xml --html-report ./diff_cov.html --src-roots ./module-poi/poi-implement/src/main/java/ ./module-poi/poi-implement/src/main/kotlin/
```

