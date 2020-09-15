# SimpleTimeGraph
There are hundreds of charting libraries significantly more sophisticated than the SimpleTimeGraph. But there were compromises I needed to make to use them. Either they came with onerous license requirements or came with a price tag attached. For the simple use cases I needed a chart for, it tended to be a big over kill. The charts that came with JavaFX were fine except they wouldn't run on Linux.

So I wrote SimpleTimeGraph. As the name implies it serves one purpose. To plot values over time.... Typically seen if you were monitoring CPU usage. There's limited options but the basics are there i.e. Enable X/Y Axis, Set Chart Title, Specify Y-Axis labels, Series colours, Set fonts, Floating Legends etc. It comes with two "Look and Feels" Black and White. Outside of that you can modify the code to better suit your needs. I included a simple [test program](https://github.com/domgiles/SimpleTimeGraph/blob/master/src/com/dom/util/graphs/GraphTestFrame.java) to show some of the basics.

The code is sub optimal but it works for simple applications

![SimpleTimeGraph](https://github.com/domgiles/SimpleTimeGraph/blob/master/img/SimpleTimeGraph.png)

Hope someone finds it useful.
