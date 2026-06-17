package br.com.orderhub.fundamentals;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.*;

/**
 * DIA 2 — Lambdas, Streams e Optional
 *
 * Streams são o equivalente ao .map/.filter/.reduce do JavaScript em arrays.
 * A diferença: Streams são lazy (executam só quando há uma operação terminal).
 *
 * JS:  array.filter(x => x > 10).map(x => x * 2)
 * Java: list.stream().filter(x -> x > 10).map(x -> x * 2).collect(toList())
 *
 * Optional é o equivalente ao ?. (optional chaining) do JS — evita NullPointerException.
 */
public class Dia02_StreamsEOptional {

    record Produto(String nome, String categoria, BigDecimal preco, boolean disponivel) {}
    record Pedido(String id, String clienteId, BigDecimal valor, String status) {}

    public static void main(String[] args) {
        exercicio1_lambdas();
        exercicio2_streams_basico();
        exercicio3_streams_avancado();
        exercicio4_optional();
        desafio_relatorio();
    }

    // ─── EXERCÍCIO 1: Lambdas e Method References ─────────────────────────────

    static void exercicio1_lambdas() {
        System.out.println("\n=== EXERCÍCIO 1: Lambdas ===");

        List<String> nomes = new ArrayList<>(List.of("Carlos", "Ana", "Bruno", "Diana"));

        // Lambda — equivale à arrow function do JS: (x) => ...
        nomes.sort((a, b) -> a.compareTo(b));
        System.out.println("Ordenado com lambda: " + nomes);

        // Method reference — atalho para lambda que chama um método existente
        // String::compareToIgnoreCase  ≡  (a, b) -> a.compareToIgnoreCase(b)
        nomes.sort(String::compareToIgnoreCase);
        System.out.println("Ordenado com method ref: " + nomes);

        // forEach com lambda
        nomes.forEach(nome -> System.out.println("  Olá, " + nome));

        // forEach com method reference de instância
        nomes.forEach(System.out::println);

        // Functional interfaces — você já usa sem saber:
        // Predicate<T>  → teste que retorna boolean  (usado em .filter)
        // Function<T,R> → transforma T em R          (usado em .map)
        // Consumer<T>   → consome T, sem retorno     (usado em .forEach)
        // Supplier<T>   → produz T sem parâmetros    (usado em Optional.orElseGet)
    }

    // ─── EXERCÍCIO 2: Streams — Operações básicas ─────────────────────────────

    static void exercicio2_streams_basico() {
        System.out.println("\n=== EXERCÍCIO 2: Streams básico ===");

        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        // filter — equivale ao .filter() do JS
        List<Integer> pares = numeros.stream()
            .filter(n -> n % 2 == 0)
            .collect(Collectors.toList());
        System.out.println("Pares: " + pares);

        // map — equivale ao .map() do JS
        List<Integer> dobros = numeros.stream()
            .map(n -> n * 2)
            .collect(Collectors.toList());
        System.out.println("Dobros: " + dobros);

        // reduce — equivale ao .reduce() do JS
        int soma = numeros.stream()
            .reduce(0, Integer::sum);
        System.out.println("Soma: " + soma);

        // count, min, max
        long qtdPares = numeros.stream().filter(n -> n % 2 == 0).count();
        Optional<Integer> maior = numeros.stream().max(Integer::compareTo);
        System.out.println("Qtd pares: " + qtdPares);
        System.out.println("Maior: " + maior.orElse(0));

        // sorted, limit, skip — equivale ao slice do JS
        List<Integer> top3 = numeros.stream()
            .sorted(Comparator.reverseOrder())
            .limit(3)
            .collect(Collectors.toList());
        System.out.println("Top 3: " + top3);
    }

    // ─── EXERCÍCIO 3: Streams — Operações avançadas ───────────────────────────

    static void exercicio3_streams_avancado() {
        System.out.println("\n=== EXERCÍCIO 3: Streams avançado ===");

        List<Produto> produtos = List.of(
            new Produto("Notebook", "Eletrônicos", new BigDecimal("3500.00"), true),
            new Produto("Mouse",    "Periféricos",  new BigDecimal("120.00"),  true),
            new Produto("Teclado",  "Periféricos",  new BigDecimal("250.00"),  true),
            new Produto("Monitor",  "Eletrônicos",  new BigDecimal("1200.00"), false),
            new Produto("Headset",  "Periféricos",  new BigDecimal("350.00"),  true)
        );

        // filter + map + collect: nomes dos produtos disponíveis, ordenados
        List<String> disponiveis = produtos.stream()
            .filter(Produto::disponivel)
            .map(Produto::nome)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Disponíveis: " + disponiveis);

        // Soma total dos disponíveis usando reduce
        BigDecimal totalDisp = produtos.stream()
            .filter(Produto::disponivel)
            .map(Produto::preco)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total disponíveis: R$ " + totalDisp);

        // groupingBy — equivale ao Object.groupBy do JS (mas existe desde Java 8!)
        Map<String, List<Produto>> porCategoria = produtos.stream()
            .collect(Collectors.groupingBy(Produto::categoria));
        porCategoria.forEach((cat, lista) ->
            System.out.println(cat + ": " + lista.stream().map(Produto::nome).toList())
        );

        // Preço médio por categoria
        Map<String, Double> mediaPreco = produtos.stream()
            .collect(Collectors.groupingBy(
                Produto::categoria,
                Collectors.averagingDouble(p -> p.preco().doubleValue())
            ));
        mediaPreco.forEach((cat, media) ->
            System.out.printf("  Média %s: R$ %.2f%n", cat, media)
        );

        // anyMatch, allMatch, noneMatch — equivale ao .some/.every do JS
        boolean temCaro = produtos.stream()
            .anyMatch(p -> p.preco().compareTo(new BigDecimal("1000")) > 0);
        System.out.println("Tem produto acima de R$1000: " + temCaro);

        // findFirst — retorna o primeiro que satisfaz a condição (retorna Optional)
        Optional<Produto> primeiroPeriferico = produtos.stream()
            .filter(p -> p.categoria().equals("Periféricos"))
            .findFirst();
        primeiroPeriferico.ifPresent(p -> System.out.println("Primeiro periférico: " + p.nome()));
    }

    // ─── EXERCÍCIO 4: Optional ────────────────────────────────────────────────

    static void exercicio4_optional() {
        System.out.println("\n=== EXERCÍCIO 4: Optional ===");

        // Optional representa "pode ter um valor ou não"
        // É o equivalente ao ?. (optional chaining) e ?? (nullish coalescing) do JS

        // Criando Optionals
        Optional<String> comValor = Optional.of("OrderHub");
        Optional<String> vazio = Optional.empty();
        Optional<String> talvez = Optional.ofNullable(buscarNome(true));

        // isPresent / isEmpty
        System.out.println("Tem valor: " + comValor.isPresent());
        System.out.println("Está vazio: " + vazio.isEmpty());

        // orElse — equivale ao  ?? "default"  do JS
        String nome = vazio.orElse("Anônimo");
        System.out.println("Nome: " + nome);

        // orElseGet — lazy: só executa a função se for necessário (melhor para operações custosas)
        String nomeCalc = vazio.orElseGet(() -> "Anônimo Calculado");
        System.out.println("Nome calc: " + nomeCalc);

        // orElseThrow — lança exceção se vazio
        try {
            String obrigatorio = vazio.orElseThrow(() -> new RuntimeException("Campo obrigatório"));
        } catch (RuntimeException e) {
            System.out.println("Erro capturado: " + e.getMessage());
        }

        // map no Optional — equivale ao ?.campo do JS
        Optional<Integer> tamanho = comValor.map(String::length);
        System.out.println("Tamanho: " + tamanho.orElse(0));

        // ifPresent — executa só se tiver valor
        talvez.ifPresent(n -> System.out.println("Nome encontrado: " + n));

        // ifPresentOrElse — Java 9+
        Optional<String> resultado = Optional.ofNullable(buscarNome(false));
        resultado.ifPresentOrElse(
            n -> System.out.println("Encontrado: " + n),
            () -> System.out.println("Não encontrado")
        );
    }

    static String buscarNome(boolean encontrou) {
        return encontrou ? "Matheus" : null;
    }

    // ─── DESAFIO: Relatório de pedidos ────────────────────────────────────────

    static void desafio_relatorio() {
        System.out.println("\n=== DESAFIO: Relatório de Pedidos ===");

        List<Pedido> pedidos = List.of(
            new Pedido("P001", "C01", new BigDecimal("1500.00"), "DELIVERED"),
            new Pedido("P002", "C02", new BigDecimal("320.00"),  "PENDING"),
            new Pedido("P003", "C01", new BigDecimal("890.00"),  "PROCESSING"),
            new Pedido("P004", "C03", new BigDecimal("2100.00"), "DELIVERED"),
            new Pedido("P005", "C02", new BigDecimal("450.00"),  "CANCELLED"),
            new Pedido("P006", "C01", new BigDecimal("670.00"),  "DELIVERED")
        );

        // 1. Total de pedidos entregues
        BigDecimal totalEntregues = pedidos.stream()
            .filter(p -> p.status().equals("DELIVERED"))
            .map(Pedido::valor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total entregues: R$ " + totalEntregues);

        // 2. Pedidos do cliente C01, ordenados por valor desc
        List<String> pedidosC01 = pedidos.stream()
            .filter(p -> p.clienteId().equals("C01"))
            .sorted(Comparator.comparing(Pedido::valor).reversed())
            .map(p -> p.id() + " (R$ " + p.valor() + ")")
            .toList();
        System.out.println("Pedidos C01: " + pedidosC01);

        // 3. Contagem por status
        Map<String, Long> porStatus = pedidos.stream()
            .collect(Collectors.groupingBy(Pedido::status, Collectors.counting()));
        porStatus.forEach((status, count) ->
            System.out.printf("  %-12s: %d pedido(s)%n", status, count)
        );

        // 4. Pedido de maior valor
        pedidos.stream()
            .max(Comparator.comparing(Pedido::valor))
            .ifPresent(p -> System.out.println("Maior pedido: " + p.id() + " - R$ " + p.valor()));
    }
}
