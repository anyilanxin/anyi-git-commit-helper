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


import org.apache.commons.lang3.StringUtils;

/**
 * @author fulin
 */
public class VelocityTool extends StringUtils {

    private static final String[] numsAry = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

    public static String camelCaseName(String underscoreName) {

        if (isNotBlank(underscoreName)) {
            underscoreName = underscoreName.replace(" ", "");
            StringBuilder result = new StringBuilder();
            if (isNumeric(underscoreName.substring(0, 1))) {
                underscoreName = numsAry[Integer.parseInt(underscoreName.substring(0, 1))] + "-" + underscoreName.substring(1);
            }
            boolean first = true;
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if ('_' == ch || '-' == ch) {
                    flag = true;
                } else {
                    if (flag || first) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                        first = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
            return result.toString();
        } else {
            return underscoreName;
        }
    }
}
