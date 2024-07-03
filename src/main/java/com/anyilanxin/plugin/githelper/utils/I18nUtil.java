/*
 * Copyright (c) 2018-present FuLin. All rights reserved.
 * Copyright © 2024 anyilanxin xuanhongzhou(anyilanxin@aliyun.com)
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
 */
package com.anyilanxin.plugin.githelper.utils;

import java.text.MessageFormat;
import java.util.ResourceBundle;


@Deprecated
public class I18nUtil {

    private final static String baseName = "i18n/info";
    private final static ResourceBundle rb1 = ResourceBundle.getBundle(baseName);

    public static String getInfo(String key, String... params) {
        String string = rb1.getString(key);
        string = string.replace("${", "$'{'");
        string = string.replace("}", "'}'");
        return new MessageFormat(string).format(params);
    }

}
