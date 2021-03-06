/*
 * Copyright (C) 2015 47 Degrees, LLC http://47deg.com hello@47deg.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fortysevendeg.translatebubble.ui.wizard

import android.support.v4.view.ViewPager
import android.widget._
import com.fortysevendeg.translatebubble.modules.persistent.PersistentServicesComponent
import com.fortysevendeg.translatebubble.ui.preferences.MainActivity
import macroid.FullDsl._
import macroid.{IdGeneration, ActivityContext, AppContext}
import Styles._
import com.fortysevendeg.macroid.extras.ViewTweaks._
import com.fortysevendeg.macroid.extras.UIActionsExtras._

trait Layout extends IdGeneration {

  self: PersistentServicesComponent =>

  var viewPager = slot[ViewPager]

  var paginationContent = slot[LinearLayout]

  var gotIt = slot[Button]

  def layout(implicit appContext: AppContext, context: ActivityContext) = getUi(
    l[LinearLayout](
      l[ViewPager]() <~ wire(viewPager) <~ pagerStyle <~ id(Id.pager), // ViewPager need set resource id
      l[FrameLayout](
        l[LinearLayout]() <~ wire(paginationContent) <~ paginationContentStyle,
        w[Button] <~ wire(gotIt) <~ gotItStyle <~ On.click {
          persistentServices.wizardWasSeen()
          context.get.finish()
          uiStartActivity[MainActivity]
        }
      ) <~ bottomContentStyle
    ) <~ rootStyle
  )

  def pagination(position: Int)(implicit appContext: AppContext, context: ActivityContext) = getUi(
    w[ImageView] <~ paginationItemStyle <~ vTag("position_%d".format(position))
  )

}

class FragmentLayout(implicit appContext: AppContext, context: ActivityContext) {

  var image = slot[ImageView]

  var title = slot[TextView]

  var description = slot[TextView]

  val content = getUi(
    l[LinearLayout](
      w[ImageView] <~ placeHolderStyle <~ wire(image),
      w[TextView] <~ titleStepStyle <~ wire(title),
      w[TextView] <~ descriptionStepStyle <~ wire(description)
    ) <~ contentStepsStyle
  )

  def layout = content

}