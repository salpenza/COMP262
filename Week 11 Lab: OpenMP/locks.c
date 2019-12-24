#include <stdio.h>
#include <omp.h>

int main() {
	
	double start_time = omp_get_wtime();
    int frequency[26] = {}; 
	
	omp_set_num_threads(10);
 
    omp_lock_t res_lock[26] = {};
 
    #pragma omp parallel 
    
	#pragma omp for schedule(auto)
 
    for(int i = 0; i < 26; i++) { 
		omp_init_lock(&res_lock[i]); 
	}
       
        
	#pragma omp for schedule(static, 1)
	for(int i = 0;i < 10; i++){
		char c;
		char books[] = {'0' + i,'.','t','x','t'};
		FILE *fp = fopen(books,"r");
		if (fp){  
			while ((c = fgetc(fp)) != EOF) {
				if (c >= 'a' && c <= 'z') {
					c -= 32;
				}
				if (c >= 'A' && c <= 'Z') {
					omp_set_lock(&res_lock[c - 'A']);
					frequency[c - 'A']++;
					omp_unset_lock(&res_lock[c - 'A']);
				}
			}
		}
	}
		
	#pragma omp for schedule(auto)
    for(int i = 0; i < 26; i++) { 
		omp_destroy_lock(&res_lock[i]); 
	}
    
	double time = omp_get_wtime() - start_time;
	printf("Completed in %f seconds", time);
	printf("\nThe letter frequencies are:\n");
	for(int i = 0; i < 26; i++) {
		printf("%c's: %d\n", i + 'A', frequency[i]);
	}
	
	return 0;
}