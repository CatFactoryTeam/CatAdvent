package services

import play.Logger
import play.api.Play

import models.Cat

import me.verticale.imgur._

/** Imgur service.
  * Handle everything Imgur related (retrieve cats data mainly).
  */
object ImgurService {
    var cats = List[Cat]()
    val imgur = new Imgur(Play.current.configuration.getString("imgur.key").get)

    /** Create all cats from the Imgur album.
      */
    def createCats(): Unit = {
      Logger.info("[ImgurService] Creating cats...")

      val images = imgur.albumImages("Flh9c")

      for (image <- images)
        cats = Cat(image.id.get, image.link.get) :: cats

      Logger.info("[ImgurService] Cats created !")
    }

    /** Automatically create cats when the service is called the first time.
      * Only the first user will make an Imgur API request, so it will take more time for him.
      * But everything will be instantaneous after, for everyone (and we save a lot of Imgur requests #quota).
      */
    createCats()
}
