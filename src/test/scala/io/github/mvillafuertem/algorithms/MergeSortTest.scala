package io.github.mvillafuertem.algorithms

import io.github.mvillafuertem.algorithms.MergeSort.{mergeSortedIntLists, mergeSortedLists}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

final class MergeSortTest extends AnyWordSpecLike with Matchers {

  "mergeSortedIntLists" should {

    "return an empty List when leftList and rightList are empty" in {
      mergeSortedIntLists(List.empty[Int], List.empty[Int]) shouldBe List.empty[Int]
    }

    "return the same List when leftList contains only one element and rightList is empty" in {
      mergeSortedIntLists(List(1), List.empty[Int]) shouldBe List(1)
    }

    "return the same List when rightList contains only one element and leftList is empty" in {
      mergeSortedIntLists(List.empty[Int], List(1)) shouldBe List(1)
    }

    "return the ordered List when leftList contains elements and rightList is empty" in {
      mergeSortedIntLists(List(13, 8, 5, 3, 2, 1), List.empty[Int]) shouldBe List(1, 2, 3, 5, 8, 13)
    }

    "return the ordered List when rightList contains elements and leftList is empty" in {
      mergeSortedIntLists(List.empty[Int], List(13, 8, 5, 3, 2, 1)) shouldBe List(1, 2, 3, 5, 8, 13)
    }

    "return the ordered List when leftList contains ordered elements and rightList is unordered" in {
      mergeSortedIntLists(List(1, 2, 3, 5, 8, 13), List(55, 21, 34)) shouldBe List(1, 2, 3, 5, 8, 13, 21, 34, 55)
    }

    "return the ordered List when rightList contains ordered elements and leftList is unordered" in {
      mergeSortedIntLists(List(55, 21, 34), List(1, 2, 3, 5, 8, 13)) shouldBe List(1, 2, 3, 5, 8, 13, 21, 34, 55)
    }
  }

  "mergeSortedLists[Int]" should {

    "return an empty List when leftList and rightList are empty" in {
      mergeSortedLists(List.empty[Int], List.empty[Int])(_ < _) shouldBe List.empty[Int]
    }

    "return the same List when leftList contains only one element and rightList is empty" in {
      mergeSortedLists(List(1), List.empty[Int])(_ < _) shouldBe List(1)
    }

    "return the same List when rightList contains only one element and leftList is empty" in {
      mergeSortedLists(List.empty[Int], List(1))(_ < _) shouldBe List(1)
    }

    "return the ordered List when leftList contains elements and rightList is empty" in {
      mergeSortedLists(List(13, 8, 5, 3, 2, 1), List.empty[Int])(_ < _) shouldBe List(1, 2, 3, 5, 8, 13)
    }

    "return the ordered List when rightList contains elements and leftList is empty" in {
      mergeSortedLists(List.empty[Int], List(13, 8, 5, 3, 2, 1))(_ < _) shouldBe List(1, 2, 3, 5, 8, 13)
    }

    "return the ordered List when leftList contains ordered elements and rightList is unordered" in {
      mergeSortedLists(List(1, 2, 3, 5, 8, 13), List(55, 21, 34))(_ < _) shouldBe List(1, 2, 3, 5, 8, 13, 21, 34, 55)
    }

    "return the ordered List when rightList contains ordered elements and leftList is unordered" in {
      mergeSortedLists(List(55, 21, 34), List(1, 2, 3, 5, 8, 13))(_ < _) shouldBe List(1, 2, 3, 5, 8, 13, 21, 34, 55)
    }
  }

  "mergeSortedLists[String]" should {
    "return an empty List when leftList and rightList are empty" in {
      mergeSortedLists(List.empty[String], List.empty[String])(_ < _) shouldBe List.empty[String]
    }

    "return the same List when leftList contains only one element and rightList is empty" in {
      mergeSortedLists(List("a"), List.empty[String])(_ < _) shouldBe List("a")
    }

    "return the same List when rightList contains only one element and leftList is empty" in {
      mergeSortedLists(List.empty[String], List("a"))(_ < _) shouldBe List("a")
    }

    "return the ordered List when leftList contains elements and rightList is empty" in {
      mergeSortedLists(List("f", "e", "d", "c", "b", "a"), List.empty[String])(_ < _) shouldBe List("a", "b", "c", "d", "e", "f")
    }

    "return the ordered List when rightList contains elements and leftList is empty" in {
      mergeSortedLists(List.empty[String], List("f", "e", "d", "c", "b", "a"))(_ < _) shouldBe List("a", "b", "c", "d", "e", "f")
    }

    "return the ordered List when leftList contains ordered elements and rightList is unordered" in {
      mergeSortedLists(List("a", "b", "c", "d", "e", "f"), List("i", "h", "g"))(_ < _) shouldBe List("a", "b", "c", "d", "e", "f", "g", "h", "i")
    }

    "return the ordered List when rightList contains ordered elements and leftList is unordered" in {
      mergeSortedLists(List("i", "h", "g"), List("a", "b", "c", "d", "e", "f"))(_ < _) shouldBe List("a", "b", "c", "d", "e", "f", "g", "h", "i")
    }
  }

}
