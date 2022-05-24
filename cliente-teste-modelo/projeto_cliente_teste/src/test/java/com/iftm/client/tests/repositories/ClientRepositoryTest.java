package com.iftm.client.tests.repositories;

import com.iftm.client.entities.Client;
import com.iftm.client.repositories.ClientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository repositorio;

    /* Cenário de Teste 1
        Testar o método que retorna o cliente com nome existente;
        - Testar um nome existente;
        - Testar um nome não existente;
    */
    @Test
    public void testarSeFindByNameComNomeExistente() {
        String nomeExistente = "conceição evaristo";
        Optional<Client> cliente = repositorio.findByNameIgnoreCase(nomeExistente);

        Assertions.assertTrue(cliente.isPresent());
        Assertions.assertEquals(nomeExistente, cliente.get().getName().toLowerCase(Locale.ROOT));
    }
      
    @Test
    public void testarSeFindByNameComNomeInexistente() {
        String nomeInexistente = "phabliny Martins";
        Optional<Client> cliente = repositorio.findByNameIgnoreCase(nomeInexistente);
        Assertions.assertFalse(cliente.isPresent());
    }

    /* Cenário de Teste 2
    * Testar o método que retorna vários cliente com parte do nome similar ao texto informado;
       - Testar um texto existente;
       - Testar um texto não existente;
       - Testar find para nome vazio (Neste caso teria que retornar todos os clientes);
    */
    @Test
    public void testarSeFindByNameLikeComCaracteresExistentes() {
        String caracterExistente = "%to%";
        List<Client> clientesRecebidos = repositorio.findByNameLikeIgnoreCase(caracterExistente);

        String clientesEsperados[] = {"Conceição Evaristo","Clarice Lispector", "Gilberto Gil","Toni Morrison"};

        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(4, clientesRecebidos.size());

        for( int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    @Test
    public void testarSeFindByNameLikeComCaracteresInexistentes() {
        String caracterInexistente = "%ph%";
        List<Client> clientesRecebidos = repositorio.findByNameLikeIgnoreCase(caracterInexistente);

        Integer qntEsperada = 0;

        Assertions.assertTrue(clientesRecebidos.isEmpty());
        Assertions.assertEquals(qntEsperada, clientesRecebidos.size());
    }

    @Test
    public void testarSeFindByNameLikeComCaracterVazio() {
        String caracterVazio = "%";
        List<Client> clientesRecebidos = repositorio.findByNameLikeIgnoreCase(caracterVazio);

        String clientesEsperados[] = {"Conceição Evaristo","Lázaro Ramos","Clarice Lispector","Carolina Maria de Jesus", "Gilberto Gil",
        "Djamila Ribeiro", "Jose Saramago", "Toni Morrison", "Yuval Noah Harari", "Chimamanda Adichie", "Silvio Almeida", "Jorge Amado"};

        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(12, clientesRecebidos.size());

        for( int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    /* Cenário de Teste 3
       Testar o método que retorna vários clientes baseado no salário;
         - Testar o método que busca clientes com salários superiores a um valor;
         - Testar o método que busca clientes com salários inferiores a um valor;
         - Testar o método que busca clientes com salários que esteja no intervalo entre dois valores informados.
    */
    @Test
    public void testarSefindByIncomeGreaterThanRetornaSalariosSuperioresAoValor() {
        Double valor = 4000.00;
        List<Client> clientesRecebidos = repositorio.findByIncomeGreaterThan(valor);
        String clientesEsperados[] = {"Carolina Maria de Jesus", "Djamila Ribeiro", "Jose Saramago", "Toni Morrison", "Silvio Almeida"};

        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(5, clientesRecebidos.size());

        for(int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    @Test
    public void testarSefindByIncomeLessThanRetornaSalariosInferioresAoValor() {
        Double valor = 2000.00;
        List<Client> clientesRecebidos = repositorio.findByIncomeLessThan(valor);

        String clientesEsperados[] = {"Conceição Evaristo", "Yuval Noah Harari", "Chimamanda Adichie"};

        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(3, clientesRecebidos.size());

        for(int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    @Test
    public void testarSeFindByIncomeBetweenRetornaSalariosEntreRangeDeValor() {
        Double min = 2000.00;
        Double max = 5000.00;
        List<Client> clientesRecebidos = repositorio.findByIncomeBetween(min, max);
        String clientesEsperados[] = {"Lázaro Ramos", "Clarice Lispector", "Gilberto Gil", "Djamila Ribeiro", "Jose Saramago", "Silvio Almeida", "Jorge Amado"};

        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(7, clientesRecebidos.size());

        for (int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    /* Cenário de Teste 4
       Testar o método que retorna vários clientes baseado na sua data de aniversário.
        - Teste o método buscando clientes que nasceram entre duas datas, sugestão uma data qualquer e a data atual.
    */
    @Test
    public void testarSeFindClientBybirthDateBetweenComDataDeAnimersarioEntreDuasDatas() {
        Instant inicio = Instant.parse("1996-12-23T07:00:00Z");
        Instant fim = Instant.now();
        List<Client> clientesRecebidos = repositorio.findClientBybirthDateBetween(inicio, fim);

        String clientesEsperados[] = {"Conceição Evaristo", "Lázaro Ramos", "Carolina Maria de Jesus", "Jose Saramago"};
        Assertions.assertFalse(clientesRecebidos.isEmpty());
        Assertions.assertEquals(4, clientesRecebidos.size());

        for (int i=0; i < clientesEsperados.length; i++) {
            Assertions.assertEquals(clientesEsperados[i], clientesRecebidos.get(i).getName());
        }
    }

    /* Cenário de Teste 5
     * Testar o update (save) de um cliente. Modifique o nome, o salário e o aniversário e utilize os métodos criados
     * anteriormente para verificar se realmente foram modificados.
     * */
    @Test
    public void testarSeFindByNameMostraNomeExistenteAposAlterarNome() {
        Long id = 1L;
        Optional<Client> cliente = repositorio.findById(id);

        Assertions.assertTrue(cliente.isPresent());

        cliente.get().setName("Phabliny Martins");
        cliente.get().setIncome(2000.00);
        cliente.get().setBirthDate(Instant.now());

        repositorio.save(cliente.get());
        Optional<Client> clienteAtualizado = repositorio.findByNameIgnoreCase("Phabliny Martins");

        Assertions.assertTrue(clienteAtualizado.isPresent());
        Assertions.assertEquals("Phabliny Martins", clienteAtualizado.get().getName());
    }
}
