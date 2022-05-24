package com.iftm.client.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iftm.client.entities.Client;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Implementar novas funcionalidades na classe ClientRepository:
    /* • criar um método personalizado para buscar um cliente pelo seu nome. (Sugestão: Aplicar a função LOWER)
         - Utilizando a clausula Lower o método se torna ignoreCase;
         - sintaxe: ....... Lower(campo)*/

    public Optional<Client> findByNameIgnoreCase(String name);

    /*  • criar um método personalizado para buscar os clientes pelo nome. (Sugestão: Aplicar a função LOWER)
          - Vocês deverão retornar uma List<Client> contendo todos os clientes que apresentam no nome a palavra
            passada como parâmetro ao método. (utilizar Like)
          - sintaxe: ....... campo LIKE %:parametro%*/

    public List<Client> findByNameLikeIgnoreCase(String name);

    /*  • criar métodos personalizados para buscar os clientes pelo salário. Criem três métodos:       
        ◦  Retornar uma List<Client> contendo todos os clientes que atendem a pesquisa cada uma das pesquisas.*/

    // ▪ um para buscar clientes com salários superiores a um valor.
    public List<Client> findByIncomeGreaterThan(Double income);

    // ▪ um para buscar clientes com salários inferiores a um valor.
    public List<Client> findByIncomeLessThan(Double income);

    //▪ um para buscara clientes que tenham salários em uma determinada faixa de valores
    public List<Client> findByIncomeBetween(Double incomeMin, Double incomeMax);

   /*  • criar um método personalizado para buscar os clientes que tenham data de nascimento em uma determinada faixa de valores.
        ◦ Não precisa criar a @Query;
        ◦ necessário o método: List<Client> findClientBybirthDateBetween(Instant DataInicio, Instant DataTermino); */

    public List<Client> findClientBybirthDateBetween(Instant dataInicio, Instant dataTermino);

}
