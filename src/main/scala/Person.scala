import akka.actor.{Actor, ActorLogging, ActorRef, Cancellable}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/**
  * Created by joanmi on 13/07/2017.
  */

object Person {
  case class SetDestination(destination: Location)
  case object Walk
  case object Start
  case object Stop
}


class Person(var city: ActorRef ) extends Actor with ActorLogging {

  private var _Currentloc: Location = new Location(0,0)
  private var _Destination: Location = new Location(0,0)
  private var _cancel:Cancellable = null

  def CurrentLocation = _Currentloc
  def CurrentLocation_= (value: Location):Unit = _Currentloc=value

  def Destination = _Destination
  def Destination_= (value: Location):Unit = _Destination=value
  implicit val timeout = Timeout(5 seconds)

  def receive = {
    case Person.Start =>
      log.info("I'm starting at:" + _Currentloc )
      _cancel= context.system.scheduler.schedule( 0 milliseconds,100 milliseconds, self, Person.Walk)


    case Person.Walk =>
      val future = city ? City.FindNextSpaceAvailable(_Currentloc)
      val result:Location = Await.result(future, timeout.duration ).asInstanceOf[Location]
      _Currentloc = result
      log info("I'm walking to " + _Currentloc )

    case Person.SetDestination(destination) =>
      _Destination = destination

    case Person.Stop =>
      _cancel cancel()
      context stop self //end
    case _ => //do nothing
      log.info("no message correctly send")


  }

}
