package controllers

import play.api._
import play.api.mvc._

import java.util.Calendar

import services.ImgurService

/** CatAdvent main controller
  */
object CatApp extends Controller {

  /** Index
    *
    * Route: /
    */
  def index = Action {
    val now = Calendar.getInstance
    val cats = ImgurService.calendar

    Ok(views.html.index(cats, now.get(Calendar.DAY_OF_MONTH)))
  }
}
