# Stream Frequency Algorithms

I read [this paper](http://www2.research.att.com/~marioh/papers/vldb08-2.pdf‎) and wanted to play around with the 
algorithms.  I had a hack day coming up; this code is the product.

The following algorithms are implemented:
* Majority
* Frequent
* Lossy Counting
* Space Saving

I didn't make any attempt to make choose efficient data structures.  
I was primarily focused on getting the algorithms correct.
The code is kind of messy and could do with some cleanup.  
For example: φ and ε should be parameters to the `getFrequentItems()` and constructor methods.

The next step is to implement the quantile algorithms.  Maybe on another hack day.

### Stream Interface

It has just two methods:

```java
    Stream<Foo> stream = new YourStreamImplementation<Foo>(acceptableErrorDouble);
    stream.observe(new Foo(...));
    ...
    List<Count> frequenItems = stream.getFrequentItems();
```

`Foo` should have reasonable `hashCode()` and `equals()` implementations.


#### NOTE: My last math class was in 1999.