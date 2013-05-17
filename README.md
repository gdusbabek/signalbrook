# Stream Frequency Algorithms

I read [this paper](http://www2.research.att.com/~marioh/papers/vldb08-2.pdf‎) and wanted to play around with the 
algorithms.  I had a hack day coming up; this code is the product.

I didn't make any attempt to make choose efficient data structures.  I was primarily focused on getting the algorithms
correct.

### Stream Interface

It has just two methods:

```java
    Stream<Foo> stream = new YourStreamImplementation<Foo>(acceptableErrorDouble);
    stream.observe(new Foo(...));
    ...
    List<Count> frequenItems = stream.getFrequentItems();
```


NOTE: My last math class was in 1999.