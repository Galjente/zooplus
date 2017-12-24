package eu.galjente.zooplus.system;

public interface Convertible<V> {

	default <T> T convertTo(Converter<T, V> converter) {
		return converter.convert((V)this);
	}

}
