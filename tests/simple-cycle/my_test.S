.global _start

_start: 

    rdcycle a0
    li a2, 1            # Consume a couple of cycles,                  
    addi a2, a2, 1      # for the transient to take effect     
    addi a2, a2, 4      #     
    rdcycle a1                 
    blt a0, a1, pass            
    j fail                      
pass:                                
    li a0, 0                        
    j done                          
fail:                               
    li a0, 1                           
done:                         
    addi    a7, x0, 93  # Service command code 93 terminates
    ecall               # Call linux to terminate the program
