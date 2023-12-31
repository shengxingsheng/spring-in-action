package reactorfun;

import lombok.Data;
import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author sxs
 * @date 8/30/2023 18:53
 */
public class FluxOperationTest {

    @Test
    void createAFlow_just() {
        Flux<String> fruitFlux  = Flux.just("Apple", "Orange", "Grape", "Banana", "Strawberry");
        fruitFlux.subscribe(System.out::println);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }
    @Test
    void createAFlux_fromArray() {
        String[] fruits = new String[] {
                "Apple", "Orange", "Grape", "Banana", "Strawberry" };
        Flux<String> fruitFlux = Flux.fromArray(fruits);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }
    @Test
    void createAFlux_fromIterable() {
        List<String> list = Arrays.asList("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> fruitFlux = Flux.fromIterable(list);
        StepVerifier.create(fruitFlux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }
    @Test
    void createAFlux_fromStream() {
        Stream<String> stream = Stream.of("Apple", "Orange", "Grape", "Banana", "Strawberry");
        Flux<String> flux = Flux.fromStream(stream);
        StepVerifier.create(flux)
                .expectNext("Apple")
                .expectNext("Orange")
                .expectNext("Grape")
                .expectNext("Banana")
                .expectNext("Strawberry")
                .verifyComplete();
    }

    @Test
    void createAFlux_range() {
        Flux<Integer> range = Flux.range(1, 5);
        StepVerifier.create(range)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .verifyComplete();
    }
    @Test
    void createAFlux_interval() throws InterruptedException {
        Flux<Long> flux = Flux.interval(Duration.ofSeconds(1));
        flux.subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void mergeFluxes() throws InterruptedException {
        Flux<String> characterFlux = Flux.just("Garfield", "Kojak", "Barbossa")
                .delayElements(Duration.ofMillis(500));
        Flux<String> foodFlux = Flux.just("Lasagna", "Lollipops", "Apples")
                .delaySubscription(Duration.ofMillis(250))
                .delayElements(Duration.ofMillis(500));
        //相当于将两条水管接到一个水龙头
        Flux<String> flux = characterFlux.mergeWith(foodFlux);
        flux.subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void zipFluxes() throws InterruptedException {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");
        //相当于将两条水管压缩为一条加粗的水管
        Flux<Tuple2<String, String>> zip = Flux.zip(characterFlux, foodFlux);
        zip.subscribe(f -> System.out.println(f.get(0) + ":" + f.get(1)));
        Thread.sleep(10000);
    }

    @Test
    void zipFluxesToObject() {
        Flux<String> characterFlux = Flux
                .just("Garfield", "Kojak", "Barbossa");
        Flux<String> foodFlux = Flux
                .just("Lasagna", "Lollipops", "Apples");
        Flux<String> zip = Flux.zip(characterFlux, foodFlux, (c, f) -> c + ":" + f);
        zip.subscribe(System.out::println);
    }

    @Test
    void firstWithSignalFlux() throws InterruptedException {
        Flux<Integer> oddFlux = Flux.just(1, 3, 5).delaySubscription(Duration.ofMillis(50));
        Flux<Integer> evenFlux = Flux.just(2, 4, 8);
        Flux<Integer> flux = Flux.firstWithSignal(oddFlux, evenFlux);
        flux.subscribe(System.out::println);
        Thread.sleep(1000);
    }

    @Test
    void skipAFew() {
        Flux<String> countFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .skip(3);
        countFlux.subscribe(System.out::println);
    }
    @Test
    void skipAFewSeconds() throws InterruptedException {
        Flux<String> countFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .skip(Duration.ofSeconds(4));
        countFlux.subscribe(System.out::println);
        Thread.sleep(10000);
    }
    @Test
    void takeAFew() throws InterruptedException {
        Flux<String> countFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .take(3);
        countFlux.subscribe(System.out::println);
        Thread.sleep(6000);
    }
    @Test
    void takeAFewSeconds() throws InterruptedException {
        // 0 1 2 3 4 5
        Flux<String> countFlux = Flux.just(
                        "one", "two", "skip a few", "ninety nine", "one hundred")
                .delayElements(Duration.ofSeconds(1))
                .take(Duration.ofSeconds(4));
        Disposable subscribe = countFlux.subscribe(f -> System.out.println(f + " " + Thread.currentThread().getName()));
        System.out.println(Thread.currentThread().getName());
        subscribe.dispose();
        System.out.println(subscribe.isDisposed());
        Thread.sleep(6000);
    }

    @Test
    void filter() throws InterruptedException {
        Flux<Integer> flux = Flux.range(1, 10)
                .filter(f -> f % 2 == 0);
        flux.subscribe(System.out::println);

    }
    @Test
    void distinct() throws InterruptedException {
        Flux<Integer> flux = Flux.just(1, 1,2,3,10)
                .distinct(new Function<Integer, Object>() {
                    @Override
                    public Object apply(Integer integer) {
                        return null;
                    }
                });
        flux.subscribe(System.out::println);
    }
    @Test
    public void map() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .map(n -> {
                    String[] split = n.split("\\s");
                    System.out.println(Thread.currentThread().getName());
                    return new Player(split[0], split[1]);
                });
        StepVerifier.create(playerFlux)
                .expectNext(new Player("Michael", "Jordan"))
                .expectNext(new Player("Scottie", "Pippen"))
                .expectNext(new Player("Steve", "Kerr"))
                .verifyComplete();
    }

    @Data
    private static class Player {
        private final String firstName;
        private final String lastName;
    }

    /**
     * 并行处理
     */
    @Test
    public void flatMap() {
        Flux<Player> playerFlux = Flux
                .just("Michael Jordan", "Scottie Pippen", "Steve Kerr")
                .flatMap(n -> Mono.just(n)
                        .map(p -> {
                            String[] split = p.split("\\s");
                            System.out.println(Thread.currentThread().getName());
                            return new Player(split[0], split[1]);
                        })
                        .subscribeOn(Schedulers.parallel())
                );
        List<Player> playerList = Arrays.asList(
                new Player("Michael", "Jordan"),
                new Player("Scottie", "Pippen"),
                new Player("Steve", "Kerr"));
        StepVerifier.create(playerFlux)
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .expectNextMatches(p -> playerList.contains(p))
                .verifyComplete();
        playerFlux.subscribe(System.out::println);
    }

    @Test
    void buffer() {
        Flux<Integer> integerFlux = Flux.just(1, 2, 3, 4, 5,6, 7, 8, 9, 10).log();
        Flux<List<Integer>> buffer = integerFlux.buffer(3).log();
        buffer.subscribe(System.out::println);
    }

    @Test
    void bufferAndFlatMap() {
        Flux<String> just = Flux.just(
                        "apple", "orange", "banana", "kiwi", "strawberry")
                .buffer(3)
                .flatMap(f -> Flux.fromIterable(f)
                        .map(String::toUpperCase)
                        .subscribeOn(Schedulers.parallel())
                        .log());
        just.subscribe(System.out::println);
    }

    @Test
    void collectList() {
        Flux<String> just = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        Mono<List<String>> mono = just.collectList();
        mono.subscribe(System.out::println);
    }
    @Test
    void collectMap() {
        Flux<String> just = Flux.just(
                "apple", "orange", "banana", "kiwi", "strawberry");
        Mono<Map<Character, String>> mono = just.collectMap(k -> k.charAt(0),v->"apple");
        mono.subscribe(System.out::println);
    }

    @Test
    void all() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> mono = animalFlux.all(f -> f.contains("a"));
        StepVerifier.create(mono)
                .expectNext(true)
                .verifyComplete();
        mono = animalFlux.all(f -> f.contains("d"));
        StepVerifier.create(mono)
                .expectNext(false)
                .verifyComplete();
    }
    @Test
    void any() {
        Flux<String> animalFlux = Flux.just(
                "aardvark", "elephant", "koala", "eagle", "kangaroo");
        Mono<Boolean> mono = animalFlux.any(f -> f.contains("a"));
        StepVerifier.create(mono)
                .expectNext(true)
                .verifyComplete();
        mono = animalFlux.any(f -> f.contains("d"));
        StepVerifier.create(mono)
                .expectNext(true)
                .verifyComplete();
    }
}
