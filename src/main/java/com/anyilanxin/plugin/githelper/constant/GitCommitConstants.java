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
package com.anyilanxin.plugin.githelper.constant;

/**
 * @program: git-commit-message-helper
 * @author: fulin
 * @create: 2019-12-08 11:37
 **/
public class GitCommitConstants {

    public static final String ACTION_PREFIX = "ay_git_commit_message_helper_settings";

    public static final String DEFAULT_TEMPLATE = "#if($gitemoji)${gitemoji} #end#if($type)${type}#end#if($scope)(${scope})#end: #if($subject)${subject}#end\n" +
            "#if($body)${newline}${newline}${body}#end\n" +
            "#if($changes)${newline}${newline}BREAKING CHANGE: ${changes}#end\n" +
            "#if($closes)${newline}${newline}Closes ${closes}#end\n" +
            "#if($skipCi)${newline}${newline}${skipCi}#end";
}
