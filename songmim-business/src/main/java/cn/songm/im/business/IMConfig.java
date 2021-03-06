/*
 * Copyright [2016] [zhangsong <songm.cn>].
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package cn.songm.im.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 属性管理器
 * 
 * @author zhangsong
 * @since 0.1, 2016-8-2
 * @version 0.1
 * 
 */
public class IMConfig {

    private static final Logger LOG = LoggerFactory.getLogger(IMConfig.class);

    /** 系统版本信息 */
    public static final String VERSION = "0.1";

    /** 属性文件全名 */
    private static final String PFILE = "config/songmim-config.properties";

    /** 本类存在的唯一实例 */
    private static IMConfig instance = new IMConfig();

    /** 属性文件的文件对象 */
    private File file;

    /** 属性文件的最后修改日期 */
    private long lastModified = 0;

    /** 属性文件对应的属性对象 */
    private Properties props;

    private IMConfig() {
        loadFile();
    }

    private void loadFile() {
        if (file == null) {
            file = new File(Thread.currentThread().getContextClassLoader()
                    .getResource(PFILE).getFile());
        }
        // 检查属性文件被修改，重新读取此文件
        long newTime = file.lastModified();
        if (newTime > lastModified) {
            lastModified = newTime;
            if (props != null) {
                props.clear();
            } else {
                props = new Properties();
            }
            try {
                props.load(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                LOG.error(e.getMessage(), e);
            } catch (IOException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 静态工厂方法
     * 
     * @return
     */
    public static IMConfig getInstance() {
        return instance;
    }

    /**
     * 读取一个特定的属性项
     * 
     * @param key
     * @return
     */
    private final String getItem(String key) {
        loadFile();
        return props.getProperty(key);
    }

    public String getServerKey() {
        return this.getItem("songmim.server.key");
    }

    public String getServerSecret() {
        return this.getItem("songmim.server.secret");
    }

    public String getServerName() {
        return getItem("songmim.server.name");
    }
    
    public String getServerIp() {
        return getItem("songmim.server.ip");
    }
    
    public int getServerTcpPort() {
        return Integer.parseInt(getItem("songmim.server.tcp.port"));
    }
    
    public int getServerWsPort() {
        return Integer.parseInt(getItem("songmim.server.ws.port"));
    }

    public Integer getHttpdPort() {
        return Integer.parseInt(getItem("songmim.server.httpd.port"));
    }
}
