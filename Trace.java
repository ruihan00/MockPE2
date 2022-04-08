/**
 * @author https://youtu.be/dQw4w9WgXcQ
 */

import java.util.List;
import java.util.ArrayList;

public class Trace<T> {
  private T value;
  private List<T> history;

  Trace(T something) {
    this.value = something;
    this.history = new ArrayList<T>();
  }

  Trace(T something, List<T> items) {
    this.history = new ArrayList<T>();
    this.history.addAll(items);
    this.value = something;
  }

  static <T> Trace<T> of(T something) {
    return new Trace<T>(something);
  }
  
  @SafeVarargs
  static <T> Trace<T> of(T... items) {
    List<T> hist = new ArrayList<T>();
    T v = items[0];
    for (int i = 1; i < items.length; i++) {
      hist.add(items[i]);
    }
    return new Trace<T>(v, hist);
  }

  public T get() {
    return this.value;
  }

  public List<T> history() {
    return this.history;
  }

  public Trace<T> back(int n) {
    if (n == 0 || this.history.size() == 0) return this;
    Trace<T> newTrace = new Trace<T>(this.history.get(this.history.size() - 1), this.history);
    newTrace.history.remove(newTrace.history.size() - 1);
    return newTrace.back(n-1);
  }
  
  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Trace)) {
      return false;
    }

    @SuppressWarnings("unchecked")
    Trace<T> temp = (Trace<T>) other;
    
    if (temp.value == null) {
      return temp.value == this.value && temp.history.equals(this.history);
    }

    return temp.value.equals(this.value) && temp.history.equals(this.history);
  }

  public Trace<T> map(Transformer<? super T, ? extends T> t) {
    Trace<T> newTrace = new Trace<T>(t.transform(this.value), this.history);
    newTrace.history.add(this.value);
    return newTrace;
  }

  public Trace<T> flatMap(Transformer<? super T, ? extends Trace<? extends T>> t) {
    @SuppressWarnings("unchecked")
    Trace<T> result = (Trace<T>) t.transform(this.value);
    
    Trace<T> newTrace = new Trace<T>(result.value, this.history);
    newTrace.history.add(this.value);
    newTrace.history.addAll(result.history);
    return newTrace;
  }

}
