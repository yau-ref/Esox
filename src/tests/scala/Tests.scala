package tests

import org.scalatest.FunSuite
import esox._

class Tests extends FunSuite{

  val simpleList = List(1,2,3,2,1)

  test("Common test"){
    simpleList.rem.filter(0 <)

  }

}
