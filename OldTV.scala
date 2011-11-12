/**
 *
 */
package org.fsdev

import scala.swing._
import java.awt.Color
import java.awt.event.ActionEvent
import javax.swing.{ Timer => SwingTimer, AbstractAction }
import scala.util.Random

/**
 * @author kjozsa
 */
object OldTV extends SimpleSwingApplication with Logger {
  val (screenWidth, screenHeight) = (600, 400)
  val (mapWidth, mapHeight) = (150, 100)
  val (x_offset, y_offset) = (screenWidth / mapWidth, screenHeight / mapHeight)
  info("offset: " + x_offset + ", " + y_offset)

  val random = new Random

  def randomMap(rx: Int, ry: Int): String = randomMap(rx, ry, rx, ry, "")
  def randomMap(rx: Int, ry: Int, cx: Int, cy: Int, partResult: String): String = {
    if (cx == 0 && cy == 1) partResult
    else {
      if (cx == 0) {
        randomMap(rx, ry, rx, cy - 1, "\n" + partResult)
      } else {
        val field = if (random.nextInt(10) < 5) "X" else "."
        randomMap(rx, ry, cx - 1, cy, field + partResult)
      }
    }
  }

  val map = randomMap(mapWidth, mapHeight)
  info("\n" + map)

  def top = new MainFrame {
    contents = new Panel {
      focusable = true
      preferredSize = new Dimension(screenWidth, screenHeight)

      override def paint(g: Graphics2D) {
        g.setColor(Color.black)
        g.fillRect(0, 0, size.width, size.height)

        def draw_field(g: Graphics2D, x: Int, y: Int) {
          g.setColor(Color.getHSBColor(random.nextFloat(), random.nextFloat(), random.nextFloat()))
          g.fillRect(x * x_offset, y * y_offset, x_offset, y_offset)
        }

        def draw_nothing(g: Graphics2D, x: Int, y: Int) {}

        map.replaceAll("\n", "").toCharArray.zipWithIndex.foreach {
          _ match {
            case ('X', index) => draw_field(g, index % mapWidth, index / mapWidth)
            case ('.', index) => draw_nothing(g, index % mapWidth, index / mapWidth)
            case _ =>
          }
        }
      }
    }

    new SwingTimer(100, new AbstractAction() {
      override def actionPerformed(e: ActionEvent) {
        repaint()
      }
    }).start
  }
}