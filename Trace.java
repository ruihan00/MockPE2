/**
 * @author https://youtu.be/dQw4w9WgXcQ
 */

import java.util.List;
import java.util.ArrayList;
public final class Trace<T> {
  private T value;
  private ArrayList<T> history;

  private Trace(T value) {
    this.value = value;
    this.history = new ArrayList<T>();
  }

  private Trace(T value, ArrayList<T> history) {
    this.value = value;
    this.history = history;
  }

  public static <T> Trace<T> of (T value, T... param) {
    ArrayList<T> history = new ArrayList<T>();
    for (T item : param) {
      history.add(item);
    }
    return new Trace<T>(value, history);
  }

  public static <T> Trace<T> of (T value) {
    return new Trace<T>(value);
  }


  public T get() {
    return this.value;
  }
  
  public String history() {
    return this.history.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Trace) {
      Trace<T> temp = (Trace<T>) o;
      return this.value == temp.value && this.history.equals(temp.history);
    }
    return false;
  }
 
}
