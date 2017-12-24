package eu.galjente.zooplus.system;

public interface Converter<T, V> {

	T convert(V value);
}
