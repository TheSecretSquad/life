life
====

Implementation of Conway's Game of Life. Just for fun!

Originally implemented it with AWT/Swing and a hacked-together GUI using GridLayout to draw the cells. Obviously GridLayout is not ideal, because it's meant for laying out UI components like fields and buttons. It worked well-enough, but the performace was pretty bad using cell grids above size 100x100 (10,000 cells).

I decided I should use an actual drawing API for the visuals. I thought I would take that as an opportunity to experiment with something I've never worked with before. Instead of using Swing graphics, I decided to try using JavaFX. While there is some controversy as to the worth of JavaFX as a library, using it was merely an experiment to work with an unfamiliar product.

The first JavaFX version was just to learn the classes in the libary, so I basically implemented the same thing I did in AWT/Swing. I used the JavaFX GridPane (which is similar to th GridLayout, and again is a layout tool not a drawing tool). It performed a bit better. I was able to get reasonable performance with grids of around 140x140 (19,600 cells).

Once I familiarized myself with JavaFX, I made another version using Canvas which is much better. With it I was able to experiment with grids up to 280x280 (78,400 cells). It looks like I can go higher if I adjust the timing, and redesign certain parts (like removing some extra mapping from the view model).

Overall I just like watching the thing run on random.
