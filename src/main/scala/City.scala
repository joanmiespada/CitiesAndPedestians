import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable.ListBuffer

/**
  * Created by joanmi on 13/07/2017.
  */

object City
{
  case class FindNextSpaceAvailable(current: Location)
  case object AddPedestrian
}

object State extends Enumeration
{
  val Free, Busy, Building = Value
  val types = Seq(Free,Busy,Building)
}

class City extends Actor with ActorLogging
{

  ///var map = Array.ofDim[State.Value](100,100);
  var mapa = Array.fill(100,100)(State.Free);

  //var pedestrians = ListBuffer[Person];

  BuildBuildings()

  def receive ={

      case City.FindNextSpaceAvailable(from) =>
        try {
          val resultTo = FindSpace(from)
          sender() ! resultTo
        }catch {
            case e: Exception =>
            sender() ! akka.actor.Status.Failure(e)
            throw e
        }
      case _ => log.info("message received")
      //case City.AddPedestrian=> pedestrians += new Person()


  }

  def FindSpace(from:Location):Location = {
    return new Location( from.x+1, from.y+1 )
  }

  def BuildBuildings():Unit =
  {

    val r = scala.util.Random

    for(i<- 0 until mapa.length)
      for(j<- 0 until mapa(0).length)
        {
          if (r.nextInt(88) % 5 == 0)
            mapa(i)(j) = State.Building
        }
  }

}


