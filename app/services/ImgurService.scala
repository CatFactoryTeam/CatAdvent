package services

import models.Cat
import play.api.Play
import me.verticale.imgur._

object ImgurService {

    var cats = List[Cat]()
    val imgur = new Imgur(Play.current.configuration.getString("imgur.key").get)

    def createCats: Unit = {
      val images = imgur.albumImages("Flh9c")

      for (image <- images)
        cats = Cat(image.id.get, image.link.get) :: cats
    }
    createCats
}
