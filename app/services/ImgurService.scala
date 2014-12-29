package services

import play.Logger
import play.api.Play

import java.util.Calendar

import models.Cat

import me.verticale.imgur._

/** Imgur service.
  * Handle everything Imgur related (retrieve cats data mainly).
  */
object ImgurService {
    var cats = Array[Cat]()
    var calendar = Array[Cat]()
    val imgur = new Imgur(Play.current.configuration.getString("imgur.key").get)

    /** Create all cats from the Imgur album.
      */
    def createCats(): Unit = {
      Logger.info("[ImgurService] Creating cats...")

      val images = imgur.albumImages(Play.current.configuration.getString("imgur.mainAlbum").get)

      for (image <- images)
        cats = Cat(image.id.get, image.link.get) +: cats

      Logger.info("[ImgurService] Cats created !")
    }

    /** Create the advent calendar for the given month.
      * An advent calendar is a special Imgur album specified in app configuration.
      * The key name is like this : imgur.advent{monthIndex}, example imgur.advent11 (for december).
      *
      * @param month Index of the month, from 0 (Jan) to 11 (Dec)
      */
    def createAdventCalendar(month: Int): Unit = {
      Logger.info("[ImgurService] Creating calendar advent...")

      val calendarKey = Play.current.configuration.getString("imgur.advent" + month).get
      val images = imgur.albumImages(calendarKey)

      for (image <- images)
        calendar = Cat(image.id.get, image.link.get) +: calendar

      Logger.info("[ImgurService] Calendar advent created !")
    }

    /** Automatically create cats when the service is called the first time.
      * Only the first user will make an Imgur API request, so it will take more time for him.
      * But everything will be instantaneous after, for everyone (and we save a lot of Imgur requests #quota).
      */
    createCats()

    /** Automatically create the calendar of the month on service's first use.
      * Work like createCats() method.
      */
    val now = Calendar.getInstance.getTime
    createAdventCalendar(now.getMonth)
}
