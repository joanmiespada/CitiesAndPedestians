import akka.actor.{Actor, ActorLogging, ActorRef}

/**
  * Created by joanmi on 13/07/2017.
  */

object Person {
  case object FindWay
  case object Walk
  case object Start
}


case class Person(var city: ActorRef ) extends Actor with ActorLogging {

  var Currentloc: Location = new Location(0,0);
  var Destination: Location = new Location(0,0);


  def receive = {
    case Person.Start =>
      log.info("I'm ready at:" + Currentloc )

    case Person.Walk =>
      log.info("I'm walking")

    case Person.FindWay =>
      log.info("I'm finding new way")
      city ! City.FindSpace

  }

}
