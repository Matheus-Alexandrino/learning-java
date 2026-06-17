package br.com.orderhub.fundamentals;

import java.math.BigDecimal;
import java.util.*;

/**
 * DIA 1 — Tipos, Coleções e Strings
 *
 * Se você vem do JavaScript:
 *   JS: let x = 10           → Java: int x = 10  (tipagem estática, sem var implícito de tipo)
 *   JS: const PI = 3.14      → Java: final double PI = 3.14
 *   JS: let price = 19.99    → Java: BigDecimal price = new BigDecimal("19.99")  (use BigDecimal para dinheiro!)
 *   JS: []                   → Java: List<T>
 *   JS: {}                   → Java: Map<K,V>
 *   JS: new Set()            → Java: Set<T>
 */
public class Dia01_TiposEColecoes {

    public static void main(String[] args) {
        exercicio1_tipos();
        exercicio2_strings();
        exercicio3_listas();
        exercicio4_mapas();
        exercicio5_sets();
        desafio_carrinho();
    }

    // ─── EXERCÍCIO 1: Tipos primitivos vs Wrappers ───────────────────────────

    static void exercicio1_tipos() {
        System.out.println("\n=== EXERCÍCIO 1: Tipos ===");

        // Primitivos — armazenados na stack, não podem ser null
        int quantidade = 5;
        double peso = 1.75;
        boolean ativo = true;
        char inicial = 'M';

        // Wrappers — objetos, podem ser null, têm métodos úteis
        // Java faz autoboxing: Integer i = 42  ←→  int i = 42
        Integer qtdWrapper = 5;
        Double pesoWrapper = 1.75;
        Boolean ativoWrapper = null; // wrapper pode ser null!

        // BigDecimal para valores monetários — NUNCA use double para dinheiro
        BigDecimal preco = new BigDecimal("29.90");
        BigDecimal desconto = new BigDecimal("5.00");
        BigDecimal total = preco.subtract(desconto);

        System.out.println("Preço: R$ " + preco);
        System.out.println("Desconto: R$ " + desconto);
        System.out.println("Total: R$ " + total);

        // parseInt / valueOf — equivale ao Number() do JS
        String numeroTexto = "42";
        int numero = Integer.parseInt(numeroTexto);
        System.out.println("String → int: " + numero);

        // Comparação: == compara referência em objetos, use .equals()
        Integer a = 200;
        Integer b = 200;
        System.out.println("== (errado para objetos): " + (a == b));      // pode ser false
        System.out.println(".equals() (correto): " + a.equals(b));        // sempre true
    }

    // ─── EXERCÍCIO 2: Strings ────────────────────────────────────────────────

    static void exercicio2_strings() {
        System.out.println("\n=== EXERCÍCIO 2: Strings ===");

        String nome = "  Matheus Alexandrino  ";

        // Operações essenciais
        System.out.println(nome.trim());                         // remove espaços
        System.out.println(nome.trim().toUpperCase());          // maiúsculas
        System.out.println(nome.trim().contains("Matheus"));    // true
        System.out.println(nome.trim().replace("Matheus", "M")); // substituição
        System.out.println(nome.trim().startsWith("Matheus"));  // true

        // split — equivale ao .split() do JS
        String csv = "order-001,order-002,order-003";
        String[] pedidos = csv.split(",");
        System.out.println("Total de pedidos: " + pedidos.length);

        // String.format — equivale ao template literal do JS
        String msg = String.format("Olá, %s! Você tem %d pedidos.", "Matheus", 3);
        System.out.println(msg);

        // StringBuilder — quando precisar concatenar em loop (performance)
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 3; i++) {
            sb.append("item-").append(i).append(", ");
        }
        System.out.println(sb.toString());

        // Texto em bloco (Java 15+) — multiline string, equivale ao template literal
        String json = """
                {
                    "id": 1,
                    "status": "PENDING"
                }
                """;
        System.out.println(json);
    }

    // ─── EXERCÍCIO 3: List ───────────────────────────────────────────────────

    static void exercicio3_listas() {
        System.out.println("\n=== EXERCÍCIO 3: List ===");

        // ArrayList — lista dinâmica, equivale ao [] do JS
        List<String> produtos = new ArrayList<>();
        produtos.add("Notebook");
        produtos.add("Mouse");
        produtos.add("Teclado");
        produtos.add("Monitor");

        System.out.println("Tamanho: " + produtos.size());           // .length no JS
        System.out.println("Primeiro: " + produtos.get(0));
        System.out.println("Contém Mouse: " + produtos.contains("Mouse"));

        produtos.remove("Mouse");
        System.out.println("Após remover Mouse: " + produtos);

        // Iterar (equivale ao for...of do JS)
        for (String produto : produtos) {
            System.out.println("  - " + produto);
        }

        // Lista imutável — use quando não precisa modificar
        List<String> statusPossiveis = List.of("PENDING", "PROCESSING", "SHIPPED", "DELIVERED", "CANCELLED");
        System.out.println("Status: " + statusPossiveis);
        // statusPossiveis.add("X"); // lança UnsupportedOperationException!

        // Collections.sort
        List<String> nomes = new ArrayList<>(List.of("Carlos", "Ana", "Bruno"));
        Collections.sort(nomes);
        System.out.println("Ordenado: " + nomes);
    }

    // ─── EXERCÍCIO 4: Map ────────────────────────────────────────────────────

    static void exercicio4_mapas() {
        System.out.println("\n=== EXERCÍCIO 4: Map ===");

        // HashMap — equivale ao objeto {} ou Map do JS
        Map<String, Double> precos = new HashMap<>();
        precos.put("Notebook", 3500.00);
        precos.put("Mouse", 120.00);
        precos.put("Teclado", 250.00);

        System.out.println("Preço Notebook: " + precos.get("Notebook"));
        System.out.println("Tem Mouse: " + precos.containsKey("Mouse"));

        // getOrDefault — evita NullPointerException
        double precoHD = precos.getOrDefault("HD", 0.0);
        System.out.println("Preço HD (default): " + precoHD);

        // Iterar entradas
        for (Map.Entry<String, Double> entry : precos.entrySet()) {
            System.out.printf("  %s → R$ %.2f%n", entry.getKey(), entry.getValue());
        }

        // putIfAbsent — adiciona só se a chave não existe
        precos.putIfAbsent("Notebook", 9999.0); // NÃO substitui
        precos.putIfAbsent("Monitor", 1200.0);  // adiciona

        // Map imutável
        Map<String, String> httpStatus = Map.of(
            "200", "OK",
            "404", "Not Found",
            "500", "Internal Server Error"
        );
        System.out.println("HTTP 404: " + httpStatus.get("404"));
    }

    // ─── EXERCÍCIO 5: Set ────────────────────────────────────────────────────

    static void exercicio5_sets() {
        System.out.println("\n=== EXERCÍCIO 5: Set ===");

        // HashSet — sem duplicatas, sem ordem garantida
        Set<String> categorias = new HashSet<>();
        categorias.add("Eletrônicos");
        categorias.add("Periféricos");
        categorias.add("Eletrônicos"); // ignorado — já existe

        System.out.println("Categorias: " + categorias);
        System.out.println("Tamanho: " + categorias.size()); // 2, não 3

        // LinkedHashSet — mantém ordem de inserção
        Set<String> ordenado = new LinkedHashSet<>();
        ordenado.add("Z");
        ordenado.add("A");
        ordenado.add("M");
        System.out.println("LinkedHashSet: " + ordenado); // [Z, A, M]

        // TreeSet — ordena automaticamente
        Set<String> alfabetico = new TreeSet<>(categorias);
        System.out.println("TreeSet: " + alfabetico);
    }

    // ─── DESAFIO: Simular um carrinho de compras ─────────────────────────────

    static void desafio_carrinho() {
        System.out.println("\n=== DESAFIO: Carrinho de Compras ===");

        Map<String, BigDecimal> catalogo = new HashMap<>();
        catalogo.put("Notebook", new BigDecimal("3500.00"));
        catalogo.put("Mouse", new BigDecimal("120.00"));
        catalogo.put("Teclado", new BigDecimal("250.00"));

        List<String> carrinho = new ArrayList<>();
        carrinho.add("Notebook");
        carrinho.add("Mouse");
        carrinho.add("Notebook"); // 2 notebooks

        BigDecimal total = BigDecimal.ZERO;
        Map<String, Integer> quantidades = new HashMap<>();

        for (String item : carrinho) {
            quantidades.merge(item, 1, Integer::sum); // merge é como o || {} do JS
            total = total.add(catalogo.getOrDefault(item, BigDecimal.ZERO));
        }

        System.out.println("Itens no carrinho:");
        for (Map.Entry<String, Integer> entry : quantidades.entrySet()) {
            BigDecimal subtotal = catalogo.get(entry.getKey())
                .multiply(new BigDecimal(entry.getValue()));
            System.out.printf("  %dx %s = R$ %s%n", entry.getValue(), entry.getKey(), subtotal);
        }
        System.out.println("TOTAL: R$ " + total);
    }
}
