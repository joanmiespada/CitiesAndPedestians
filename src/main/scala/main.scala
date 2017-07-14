import Person.SetDestination
import akka.actor.{ActorRef, ActorSystem, Props}

import scala.collection.mutable.ListBuffer

/**
  * Created by joanmi on 13/07/2017.
  */
object main{

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("Billioner")

    val city = system.actorOf(Props[City], "Barcelona")

    val listOfPedestrians = new ListBuffer[ActorRef];

    for(a <- 0 until 10) {
      val p1 = system.actorOf(Props( classOf[Person], city))
      //val p1 = system.actorOf(Props( new Person(city)))
      p1 ! SetDestination( new Location(100,100))
      listOfPedestrians += p1;
      p1 ! Person.Start
    }

    /*println ("start---------")
    val r = scala.util.Random
    for(_ <-1 until 100) {
      val i= r.nextInt(10)
      val j = r.nextInt(50)

      if(i % 2 == 0)
          listOfPedestrians(i) ! Person.FindWay
      else
          listOfPedestrians(i) ! Person.Walk

    }*/

    //system terminate

  }



}
