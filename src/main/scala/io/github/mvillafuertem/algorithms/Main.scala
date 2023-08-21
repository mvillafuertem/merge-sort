package io.github.mvillafuertem.algorithms

import io.github.mvillafuertem.algorithms.BackwardsList.BWLNil

object Main {

  def main(args: Array[String]): Unit = {

    println("Hello world!")

    val list1: BackwardsList[Int] = BackwardsList.BWLCons(1, BackwardsList.BWLCons(2, BWLNil))
    val list2: BackwardsList[Int] = BackwardsList.BWLCons(3, BackwardsList.BWLCons(4, BackwardsList.BWLCons(5, BWLNil)))

    val concatenatedList: BackwardsList[Int] = list1 ++ (list2)

    def printList[A](fa: BackwardsList[A]): Unit = fa match {
      case BWLNil                            => ()
      case BackwardsList.BWLCons(head, tail) =>
        print(s"$head ")
        printList(tail)
    }

    printList(concatenatedList)

    println("////////////")

    val list: BackwardsList[Int] = BackwardsList.BWLCons(4, BackwardsList.BWLCons(2, BackwardsList.BWLCons(3, BackwardsList.BWLCons(1, BWLNil))))
    val sortedList               = BackwardsList.mergeSortedBottomUpF(list, list)
    printList(sortedList)

  }

}
