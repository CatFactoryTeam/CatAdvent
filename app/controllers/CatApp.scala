package controllers

import play.api._
import play.api.mvc._

import services.ImgurService

/** CatAdvent main controller
  */
object CatApp extends Controller {

  /** Index
    *
    * Route: /
    */
  def index = Action {
    val cats = ImgurService.cats

    Ok(views.html.index("Ready to work ! #catfactoryteam", cats))
  }
}
