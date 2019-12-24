#include <stdio.h>
#include <omp.h>

int main() {
	
	double start_time = omp_get_wtime();
    char c;
    int frequency[26] = {}; 
    FILE *fp = fopen("0.txt", "r");
    if (fp) {  
        while ((c = fgetc(fp)) != EOF) {
            if (c >= 'a' && c <= 'z') {
                c -= 32;
            }
            if (c >= 'A' && c <= 'Z') {
                frequency[c - 'A']++;
            }
        }
		double time = omp_get_wtime() - start_time;
		printf("Completed in %f seconds", time);
		printf("\nThe letter frequencies are:\n");
        for(int i = 0; i < 26; i++) {
            printf("%c's: %d\n", i + 'A', frequency[i]);
        }
        fclose(fp);
    } else {
        printf("File does not exist\n");
    }
    return 0;
}