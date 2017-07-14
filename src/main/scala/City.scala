import akka.actor.{Actor, ActorLogging}

import scala.collection.mutable.ListBuffer

/**
  * Created by joanmi on 13/07/2017.
  */

object City
{
  case object FindSpace
  case object AddPedestrian
}

object State extends Enumeration
{
  val Free, Busy, Building = Value
  val types = Seq(Free,Busy)
}

class City extends Actor with ActorLogging
{

  ///var map = Array.ofDim[State.Value](100,100);
  var mapa = Array.fill(100,100)(State.Free);

  //var pedestrians = ListBuffer[Person];

  BuildBuildings()

  def receive ={

    case City.FindSpace => FindSpace()
    //case _ => log.info("message received")
    //case City.AddPedestrian=> pedestrians += new Person()

  }

  def FindSpace():Unit = {
    log.info("looking for a new route")
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


