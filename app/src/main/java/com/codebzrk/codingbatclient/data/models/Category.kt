/*
 *
 *  * Copyright (C) 2018 Arthur McCay.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.codebzrk.codingbatclient.data.models

/**
 * @author Arthur Alan McCay <arthur.alan.mccay@gmail.com
 * @version 1.0.0
 * @since 6/05/2018
 */

data class Category(val name: String,
                    val description: String,
                    val link: String,
                    val progress: IntArray?,
                    val type: String) {
}