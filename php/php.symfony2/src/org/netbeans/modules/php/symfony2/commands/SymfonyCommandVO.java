/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.php.symfony2.commands;

/**
 * Value object for Symfony2 command.
 */
public final class SymfonyCommandVO {

    private final String command;
    private final String description;
    private final String help;


    public SymfonyCommandVO(String command, String description, String help) {
        this.command = command;
        this.description = description;
        this.help = help;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getHelp() {
        return help;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(200);
        sb.append(getClass().getName());
        sb.append(" [ command: "); // NOI18N
        sb.append(command);
        sb.append(", description: "); // NOI18N
        sb.append(description);
        sb.append(", help: "); // NOI18N
        sb.append(help);
        sb.append(" ]"); // NOI18N
        return sb.toString();
    }

}
