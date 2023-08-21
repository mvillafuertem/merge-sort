package io.github.mvillafuertem.algorithms

import scala.annotation.tailrec

sealed trait BackwardsList[+A] {
  def head: A

  def tail: BackwardsList[A]

  def isEmpty: Boolean

  def reverse: BackwardsList[A]

  def ::[S >: A](elem: S): BackwardsList[S]

  def ++[S >: A](anotherList: BackwardsList[S]): BackwardsList[S]

  def flatMap[S](f: A => BackwardsList[S]): BackwardsList[S]

  def map[S](f: A => S): BackwardsList[S]

  def foldLeft[S](seed: S)(f: (S, A) => S): S

  def foldRight[S](seed: S)(f: (A, S) => S): S

}

object BackwardsList {

  // Constructor auxiliar para BackwardsList
  def apply[A](items: A*): BackwardsList[A] = {
    @scala.annotation.tailrec
    def loop(items: Seq[A], acc: BackwardsList[A]): BackwardsList[A] =
      items match {
        case Nil          => acc
        case head +: tail => loop(tail, BWLCons(head, acc))
      }

    loop(items.reverse, BWLNil)
  }

  case object BWLNil extends BackwardsList[Nothing] {
    override def isEmpty: Boolean = true

    override def head: Nothing = throw new NoSuchElementException

    override def tail: BackwardsList[Nothing] = throw new NoSuchElementException

    override def reverse: BackwardsList[Nothing] = BWLNil

    override def ++[S >: Nothing](anotherList: BackwardsList[S]): BackwardsList[S] = anotherList

    override def flatMap[S](f: Nothing => BackwardsList[S]): BackwardsList[S] = BWLNil

    override def map[S](f: Nothing => S): BackwardsList[S] = BWLNil

    def ::[S >: Nothing](elem: S): BackwardsList[S] = BWLCons(elem, this)

    override def foldLeft[S](seed: S)(f: (S, Nothing) => S): S = seed

    override def foldRight[S](seed: S)(f: (Nothing, S) => S): S = seed

  }

  case class BWLCons[A](head: A, tail: BackwardsList[A]) extends BackwardsList[A] {
    override def isEmpty: Boolean = false

    override def reverse: BackwardsList[A] =
      foldLeft(empty[A])((acc, elem) => BWLCons(elem, acc))

    override def foldLeft[S](seed: S)(f: (S, A) => S): S = {
      @tailrec
      def foldRec(list: BackwardsList[A], accumulator: S): S = list match {
        case BWLNil              => accumulator
        case BWLCons(head, tail) => foldRec(tail, f(accumulator, head))
      }

      foldRec(this, seed)
    }

    override def foldRight[S](seed: S)(f: (A, S) => S): S = {
      @tailrec
      def foldRec(list: BackwardsList[A], accumulator: S): S = list match {
        case BWLNil              => accumulator
        case BWLCons(head, tail) => foldRec(tail, f(head, accumulator))
      }

      foldRec(this, seed)
    }

    override def ++[S >: A](anotherList: BackwardsList[S]): BackwardsList[S] =
      foldRight(anotherList)(_ :: _)

    override def flatMap[S](f: A => BackwardsList[S]): BackwardsList[S] =
      foldLeft(empty[S])((acc, elem) => f(elem) ++ acc)

    override def map[S](f: A => S): BackwardsList[S] =
      foldRight(empty[S])((elem, acc) => f(elem) :: acc)

    def ::[S >: A](elem: S): BackwardsList[S] = BWLCons(elem, this)

  }

  def empty[A]: BackwardsList[A] = BWLNil

  @scala.annotation.unused
  def mergeSortedBottomUpI[A](left: BackwardsList[A], right: BackwardsList[A])(implicit ordering: Ordering[A]): BackwardsList[A] = {
    // Función auxiliar para mezclar dos sublistas ordenadas
    def merge(listA: BackwardsList[A], listB: BackwardsList[A]): BackwardsList[A] = {
      var mergedList: BackwardsList[A] = BWLNil
      var tempListA                    = listA
      var tempListB                    = listB

      while (!tempListA.isEmpty && !tempListB.isEmpty)
        if (ordering.lteq(tempListA.head, tempListB.head)) {
          mergedList = tempListA.head :: mergedList
          tempListA = tempListA.tail
        } else {
          mergedList = tempListB.head :: mergedList
          tempListB = tempListB.tail
        }

      while (!tempListA.isEmpty) {
        mergedList = tempListA.head :: mergedList
        tempListA = tempListA.tail
      }

      while (!tempListB.isEmpty) {
        mergedList = tempListB.head :: mergedList
        tempListB = tempListB.tail
      }

      mergedList.reverse
    }

    // Función para dividir la lista en sublistas de tamaño 1
    def splitList(list: BackwardsList[A]): BackwardsList[BackwardsList[A]] = {
      var tempList                                = list
      var result: BackwardsList[BackwardsList[A]] = BWLNil

      while (!tempList.isEmpty) {
        result = (tempList.head :: BWLNil) :: result
        tempList = tempList.tail
      }

      result
    }

    // Función para mezclar todas las sublistas ordenadas
    def mergeAll(sortedLists: BackwardsList[BackwardsList[A]]): BackwardsList[A] = {
      var tempList = sortedLists

      while (tempList.tail != BWLNil) {
        val mergedList = merge(tempList.head, tempList.tail.head)
        tempList = mergedList :: tempList.tail.tail
      }

      tempList.head
    }

    val mergedList = mergeAll(splitList(left ++ right))
    mergedList
  }

  def mergeSortedBottomUpF[A](left: BackwardsList[A], right: BackwardsList[A])(implicit ordering: Ordering[A]): BackwardsList[A] = {
    // Función auxiliar para mezclar dos sublistas ordenadas
    def merge(listA: BackwardsList[A], listB: BackwardsList[A]): BackwardsList[A] = {
      @tailrec
      def mergeRec(tempListA: BackwardsList[A], tempListB: BackwardsList[A], mergedList: BackwardsList[A]): BackwardsList[A] =
        (tempListA, tempListB) match {
          case (BWLNil, _)                        =>
            mergedList.foldLeft(tempListB)((acc, elem) => elem :: acc)
          case (_, BWLNil)                        =>
            mergedList.foldLeft(tempListA)((acc, elem) => elem :: acc)
          case (BWLCons(hA, tA), BWLCons(hB, tB)) =>
            if (ordering.lteq(hA, hB)) {
              mergeRec(tA, tempListB, hA :: mergedList)
            } else {
              mergeRec(tempListA, tB, hB :: mergedList)
            }
        }

      mergeRec(listA, listB, BWLNil)
    }

    // Función para dividir la lista en sublistas de tamaño 1
    def splitList(list: BackwardsList[A]): BackwardsList[BackwardsList[A]] = {
      @tailrec
      def splitRec(tempList: BackwardsList[A], result: BackwardsList[BackwardsList[A]]): BackwardsList[BackwardsList[A]] =
        tempList match {
          case BWLNil        =>
            result
          case BWLCons(h, t) =>
            splitRec(t, (h :: BWLNil) :: result)
        }

      splitRec(list, BWLNil).reverse
    }

    // Función para mezclar todas las sublistas ordenadas
    @tailrec
    def mergeAll(sortedLists: BackwardsList[BackwardsList[A]]): BackwardsList[A] =
      sortedLists match {
        case BWLNil             =>
          BWLNil
        case BWLCons(h, BWLNil) =>
          h
        case BWLCons(h, t)      =>
          val mergedList = merge(h, t.head)
          mergeAll(mergedList :: t.tail)
      }

    val splitLeft  = splitList(left)
    val splitRight = splitList(right)
    val mergedList = mergeAll(splitLeft ++ splitRight)
    mergedList
  }

}
