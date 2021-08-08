package algorithm;

public class Sort {
	//heapSort
	public static void heapSort(int[] arr) {
		int size = arr.length;
		buildHeap(arr);
		while(size > 1) {
			int max = arr[0];
			arr[0] = arr[size-1];
			arr[size-1] = max;
			size--;
			adjustHeap(arr,0,size);
		}
	}
	private static void buildHeap(int[] arr) {
		int parent = arr.length/2-1;
		while(parent >= 0) {
			adjustHeap(arr,parent--,arr.length);
		}
	}
	private static void adjustHeap(int[] arr, int target, int size) {
		int val = arr[target];
		int left = target*2+1;
		int right = target*2+2;
		if(left >= size)return;
		int child = left;
		if(right < size && arr[right] > arr[left])child = right;
		if(arr[child] > val) {
			arr[target] = arr[child];
			arr[child] = val;
			adjustHeap(arr,child,size);
		}
	}
	//fastSort
	public static void fastSort(int[] arr) {
		fastSort1(arr,0,arr.length-1);
	}
	private static void fastSort1(int[] arr, int l, int r) {
		if(l >= r)return;
		int mid = partion(arr,l,r);
		fastSort1(arr,l,mid-1);
		fastSort1(arr,mid+1,r);
	}
	private static int partion(int[] arr, int l, int r) {
		int pivot = l + (int)((r-l)*Math.random());
		swap(arr,pivot,r);
		pivot = l;
		for(int i=l;i<r;i++) {
			if(arr[i] < arr[r]) {
				swap(arr,pivot,i);
				pivot++;
			}
		}
		swap(arr,pivot,r);
		return pivot;
	}
	private static void swap(int[] arr, int l, int r) {
		int temp = arr[l];
		arr[l] = arr[r];
		arr[r] = temp;
	}
	//shellSort
	public static void shellSort(int[] arr) {
		int size = arr.length;
		int gap = (size)/2;
		while(gap > 0) {
			singleInsertSort(arr,gap);
			gap = gap/2;
		}
	}
	private static void singleInsertSort(int[] arr, int gap) {
		for(int i=gap;i<arr.length;i++) {
			int temp = arr[i];
			int pre = i-gap;
			while(pre >= 0 && arr[pre] > temp) {
				arr[pre+gap] = arr[pre];
				pre = pre - gap;
			}
			arr[pre+gap] = temp;
		}
	}
	//insertSort
	public static void insertSort(int[] arr) {
		singleInsertSort(arr,1);
	}
	//mergeSort
	public static void mergeSort(int[] arr) {
		doMergeSort(arr,0,arr.length-1);
	}
	private static void doMergeSort(int[] arr, int l, int r) {
		if(l >= r)return;
		int mid = (l+r)/2;
		doMergeSort(arr,l,mid);
		doMergeSort(arr,mid+1,r);
		merge(arr,l,mid,mid+1,r);
	}
	private static void merge(int[] arr, int l1, int r1, int l2, int r2) {
		if(l1>r1 || l2>r2)return;
		int size = r1-l1+1+r2-l2+1;
		int[] temp = new int[size];
		int i=0;
		int start = l1;
		while(l1<=r1 && l2<=r2) {
			if(arr[l1] < arr[l2]) {
				temp[i++] = arr[l1++];
			}else {
				temp[i++] = arr[l2++];
			}
		}
		while(l1<=r1) {
			temp[i++] = arr[l1++];
		}
		while(l2<=r2) {
			temp[i++] = arr[l2++];
		}
		for(int j=0;j<size;j++) {
			arr[start+j] = temp[j];
		}
	}
}
