package io.github.mvillafuertem.algorithms

import java.time.{ Duration, Instant }

object MergeSort {

  def mergeSortedIntLists(left: List[Int], right: List[Int]): List[Int] = {

    def loop(list: List[Int]): List[Int] =
      list match {
        case Nil         => Nil
        case head :: Nil => List(head)
        case _           =>
          val (left, right) = list.splitAt(list.length / 2)
          inner(loop(left), loop(right))
      }

    def inner(left: List[Int], right: List[Int]): List[Int] = (left, right) match {
      case (Nil, _)                                       => loop(right)
      case (_, Nil)                                       => loop(left)
      case (headLeft :: tailLeft, headRight :: tailRight) =>
        if (headLeft < headRight) headLeft :: inner(tailLeft, right)
        else headRight :: inner(left, tailRight)
    }

    val start = Instant.now()
    val value = inner(left, right)
    val end   = Instant.now()
    println("mergeSortedIntLists: " + Duration.between(start, end))
    value
  }

  def mergeSortedLists[A](left: List[A], right: List[A])(implicit lessThan: (A, A) => Boolean): List[A] = {

    def loop(list: List[A])(implicit lessThan: (A, A) => Boolean): List[A] =
      list match {
        case Nil         => Nil
        case head :: Nil => List(head)
        case _           =>
          val (left, right) = list.splitAt(list.length / 2)
          inner(loop(left), loop(right))
      }

    def inner(left: List[A], right: List[A]): List[A] = (left, right) match {
      case (Nil, _)                                       => loop(right)
      case (_, Nil)                                       => loop(left)
      case (headLeft :: tailLeft, headRight :: tailRight) =>
        if (lessThan(headLeft, headRight)) headLeft :: inner(tailLeft, right)
        else headRight :: inner(left, tailRight)
    }

    val start = Instant.now()
    val value = inner(left, right)
    val end   = Instant.now()
    println("mergeSortedLists: " + Duration.between(start, end))
    value
  }

}
