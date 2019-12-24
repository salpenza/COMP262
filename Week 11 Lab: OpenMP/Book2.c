#include <stdio.h>
#include <omp.h>

int main() {
	
	double start_time = omp_get_wtime();
    char c;
    int frequency[26] = {}; 
	FILE *fp;
	
    for(int i = 0;i < 10; i++){
		char books[] = {'0' + i,'.','t','x','t'};
		fp = fopen(books,"r");
		if (fp) {  
			while ((c = fgetc(fp)) != EOF) {
				if (c >= 'a' && c <= 'z') {
					c -= 32;
				}
				if (c >= 'A' && c <= 'Z') {
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
	
	fclose(fp);
	return 0;
}