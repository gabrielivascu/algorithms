#include <stdio.h>

static int partition(int *v, int left, int right)
{
  int pivot, tmp;
  int i = left + 1;
  int j = right;

  /* Use the first element in the array as pivot. */
  pivot = left;

  while (1) {
    /* Scan from left to right for the first element greater than the pivot. */
    while (i < right && v[i] < v[pivot]) i++;
    /* Scan from right to left for the first element lower than the pivot. */
    while (j > left && v[j] > v[pivot]) j--;

    /* Return if the pointers cross. */
    if (i >= j) break;

    /* Swap the lower with the greater. */
    tmp = v[i];
    v[i] = v[j];
    v[j] = tmp;
  }

  /* v[j] is now the first element to be lower than v[pivot].
   * Swap v[pivot] and v[j] so that the pivot goes to its right place. */
  tmp = v[pivot];
  v[pivot] = v[j];
  v[j] = tmp;

  return j;
}

static void quicksort(int *v, int left, int right)
{
  int pivot;

  if (left >= right)
    return;

  pivot = partition(v, left, right);
  quicksort(v, left, pivot - 1);
  quicksort(v, pivot + 1, right);
}

int main(int argc, char **argv)
{
  int v[] = {5, 2, 3, 1, 6, 4, 7, 9, 8};
  int n = sizeof(v) / sizeof(int);

  quicksort(v, 0, n - 1);

  for (int i = 0; i < n; i++)
    printf("%d ", v[i]);
  printf("\n");

  return 0;
}
