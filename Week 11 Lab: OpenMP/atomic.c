#include <stdio.h>
#include <omp.h>

int main() {
	
	double start_time = omp_get_wtime();
    int frequency[26] = {}; 
	
	omp_set_num_threads(10);
	#pragma omp parallel for schedule(static,1)
		
    for(int i = 0;i < 10; i++){
		char c;
		char books[] = {'0' + i,'.','t','x','t'};
		FILE *fp = fopen(books,"r");
		if (fp) {  
			while ((c = fgetc(fp)) != EOF) {
				if (c >= 'a' && c <= 'z') {
					c -= 32;
				}
				if (c >= 'A' && c <= 'Z') {
					#pragma omp atomic
					frequency[c - 'A']++;
				}
			}
		}
	}
	double time = omp_get_wtime() - start_time;
	printf("Completed in %f seconds", time);
	printf("\nThe letter frequencies are:\n");
	for(int i = 0; i < 26; i++) {
		printf("%c's: %d\n", i + 'A', frequency[i]);
	}
	
	return 0;
}