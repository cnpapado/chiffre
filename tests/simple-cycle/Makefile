.PHONY: default clean run

EXE = my_test.out

VERILATOR=/home/c/thesis/rocket-chip/emulator
EMU=emulator-freechips.rocketchip.system-freechips.rocketchip.system.DefaultConfig

TOOLS=/home/c/thesis/riscv-toolchain/bin
CC=$(TOOLS)/riscv64-unknown-elf-gcc
AS=$(TOOLS)/riscv64-unknown-elf-as
LD=$(TOOLS)/riscv64-unknown-elf-ld

default: $(EXE).out

%.out: %.o
	$(LD) $^ -o $@

%.o: %.S
	$(AS) $^ -o $@


clean:
	rm -f *.o *.out

run:
	$(VERILATOR)/$(EMU) pk $(EXE)

