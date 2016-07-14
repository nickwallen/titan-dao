import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.TitanGraph;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.thinkaurelius.titan.core.util.TitanCleanup;
import com.thinkaurelius.titan.example.GraphOfTheGodsFactory;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.Iterator;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {

  public static void main(String[] args) {

    /*
     * titan 0.5.4
     *
     *   only support elasticsearch 1.5.x
     */
    TitanFactory.Builder config = TitanFactory.build();
    config.set("storage.backend", "hbase");
    config.set("storage.hostname", "node1");
    config.set("storage.hbase.table", "titan_0_5_4");
    config.set("index.search.backend", "inmemory");
    TitanGraph graph = config.open();

    // use the management system
    graph.getManagementSystem().set("index.search.backend", "inmemory");
    graph.getManagementSystem().commit();

    GraphOfTheGodsFactory.load(graph);
    graph.getVertices().forEach(v -> System.out.println(v.toString()));
    System.out.println("Success!");


    // TODO changed znode parent for hbase to '/hbase' - which setting is used in Titan 0.5.4 to change this??
    // set("storage.hbase.ext.zookeeper.znode.parent", "/hbase-unsecure").

    /*
     * titan 1.0.0
     */
//    TitanGraph graph = TitanFactory.build().
//            set("storage.backend", "hbase").
//            set("storage.hostname", "node1").
//            set("storage.hbase.ext.hbase.zookeeper.property.clientPort", "2181").
//            set("storage.hbase.ext.zookeeper.znode.parent", "/hbase-unsecure").
//            set("storage.hbase.table", "titan_1_0_0").
//            open();
//    GraphOfTheGodsFactory.load(graph);
//    toStream(graph.vertices()).forEach(v -> System.out.println(v.toString()));
//    System.out.println("Success");
  }

  private static <T> Stream<T> toStream(Iterator<T> sourceIterator) {
    Iterable<T> iterable = () -> sourceIterator;
    return toStream(iterable);
  }

  private static <T> Stream<T> toStream(Iterable<T> iterable) {
    return StreamSupport.stream(iterable.spliterator(), false);
  }
}
