package ru.apzakharov.util;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class QuickSort {

  public static void main(String[] args) {
    int[] ints = IntStream.range(0, new Random().nextInt(5, 10)).map(i -> new Random().nextInt(10))
        .toArray();
    System.out.println(Arrays.toString(ints));
    sort(ints);
    System.out.println(Arrays.toString(ints));
  }

  public static void sort(int[] arr) {

    quickSort(arr, 0, arr.length - 1);

  }

  private static void quickSort(int[] arr, int start, int end) {
    if (arr.length == 0 || start >= end) {
      return;
    }

    int pivot = arr[start + (end - start) / 2];

    int leftIndex = start, rightIndex = end;

    while (leftIndex <= rightIndex) {
      while (arr[leftIndex] < pivot) {
        leftIndex++;
      }
      while (arr[rightIndex] > pivot) {
        rightIndex--;
      }

      if (leftIndex <= rightIndex) {
        int temp = arr[leftIndex];
        arr[leftIndex] = arr[rightIndex];
        arr[rightIndex] = temp;
        leftIndex++;
        rightIndex--;
      }
    }

    if (start < rightIndex) {
      quickSort(arr, start, pivot - 1);
    }
    if (end > leftIndex) {
      quickSort(arr, pivot + 1, end);
    }

  }
}
