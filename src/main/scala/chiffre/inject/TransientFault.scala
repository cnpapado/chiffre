package chiffre.inject

import chisel3._
import chiffre.ChiffreInjector

import chiffre.{InjectorInfo, ScanField, HasWidth}

// An always-on, not runtime configurable injector
class TransientFaultInjector(bitWidth: Int, val cycleWidth: Int, injectStart: Int, injectStop: Int, mask: Int) extends Injector(bitWidth) {
  val cycleStart = RegInit(injectStart.U(cycleWidth.W))
  val cycleEnd = RegInit(injectStop.U(cycleWidth.W))
  val cycleCounter = RegInit(0.U(cycleWidth.W))
  val flipMask = RegInit(mask.U(bitWidth.W))

  val info = NoInjectorInfo
  io.scan.out := io.scan.in
  
  val firing = (cycleCounter >= cycleStart) && (cycleCounter < cycleEnd)
  io.out := Mux(firing, io.in ^ flipMask, io.in)

  cycleCounter := cycleCounter + 1.U
  
  // printf(s"""|[info] $name (always) enabled
  //            |[info]   - target: 0x%x
  //            |[info]   - mask: 0x%x
  //            |""".stripMargin, cycleTarget, flipMask)
  
  when (firing) {
    printf(s"[info] $name injecting 0x%x into 0x%x to output 0x%x!\n", flipMask, io.in, io.out)
  }

}

class TransientFaultInjectorStatic(bitWidth: Int, val scanId: String) extends TransientFaultInjector(bitWidth, 32, 1, 10, 1) with ChiffreInjector