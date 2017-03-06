#include <stdio.h>
#include <stdlib.h>

void merge(int *v, int left, int mid, int right, int *buff)
{
  /* The pointer to iterate through the first array. */
  int i = left;
  /* The pointer to iterate through the second array. */
  int j = mid + 1;
  /* The pointer of the buffer array. */
  int k = 0;

  /* At each iteration, copy min(v[i], v[j]) into the buffer
   * and update the pointer of the array that contained the min. */
  while (i <= mid && j <= right) {
    if (v[i] < v[j])
      buff[k++] = v[i++];
    else
      buff[k++] = v[j++];
  }

  /* Handle the leftovers from the first array, if any. */
  while (i <= mid)
    buff[k++] = v[i++];

  /* Handle the leftovers from the second array, if any. */
  while (j <= right)
    buff[k++] = v[j++];

  /* Copy elements to the original array. */
  for (k = left; k <= right; k++)
    v[k] = buff[k - left];
}

void mergesort_helper(int *v, int left, int right, int *buff)
{
  int mid;

  if (left >= right)
    return;

  /* Get the median index. */
  mid = left + (right - left) / 2;

  /* Split into two arrays: [left, mid] and (mid, right].
   * Sort them recursively. */
  mergesort_helper(v, left, mid, buff);
  mergesort_helper(v, mid + 1, right, buff);

  /* Merge the two sorted array. */
  merge(v, left, mid, right, buff);
}

void mergesort(int *v, int left, int right)
{
  int *buff;

  buff = malloc((right - left + 1) * sizeof(int));
  mergesort_helper(v, left, right, buff);

  free(buff);
}

int main(int argc, char **argv)
{
  int v[] = {5, 2, 3, 1, 6, 4, 7, 9, 8};
  int n = sizeof(v) / sizeof(int);

  mergesort(v, 0, n - 1);

  for (int i = 0; i < n; i++)
    printf("%d ", v[i]);
  printf("\n");

  return 0;
}
