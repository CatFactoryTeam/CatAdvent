package controllers

import play.api._
import play.api.mvc._
import services.ImgurService

object CatApp extends Controller {

  def index = Action {
    val cats = ImgurService.cats
    Ok(views.html.index("Ready to work ! #catfactoryteam", cats))
  }
}
