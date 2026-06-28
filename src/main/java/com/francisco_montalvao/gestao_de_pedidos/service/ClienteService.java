package com.francisco_montalvao.gestao_de_pedidos.service;

import com.francisco_montalvao.gestao_de_pedidos.dto.request.ClienteRequestDTO;
import com.francisco_montalvao.gestao_de_pedidos.dto.response.ClienteResponseDTO;
import com.francisco_montalvao.gestao_de_pedidos.exception.RegraNegocioException;
import com.francisco_montalvao.gestao_de_pedidos.model.Cliente;
import com.francisco_montalvao.gestao_de_pedidos.model.valueobjects.Email;
import com.francisco_montalvao.gestao_de_pedidos.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    @Transactional
    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO request){
        Email emailValue = new Email(request.email());

        if (repository.existsByEmail(emailValue)){
            throw new RegraNegocioException(
                    "Já existe um cliente cadastrado com o e-mail '" + request.email() + "'.",
                    HttpStatus.CONFLICT
            );
        }


        Cliente cliente = new Cliente(request.nome(), request.email(), request.telefone());

        Cliente clienteSalvo = repository.save(cliente);

        return ClienteResponseDTO.toDTO(clienteSalvo);
    }


    public List<ClienteResponseDTO> listarClientes (){
        return repository.findAll()
                .stream()
                .map(ClienteResponseDTO::toDTO)
                .toList();
    }

    public ClienteResponseDTO listarPorId(Long id){
        return ClienteResponseDTO.toDTO(buscarCliente(id));
    }



    @Transactional
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO requestDTO){
        Cliente clienteAtual = buscarCliente(id);

        Optional<Cliente> donoDoEmail = repository.findByEmail_Email(requestDTO.email());

        if (donoDoEmail.isPresent() && !donoDoEmail.get().getId().equals(id)) {
            throw new RegraNegocioException("Já existe um cliente cadastrado com o e-mail '" + requestDTO.email() + "'.", HttpStatus.CONFLICT);
        }

        clienteAtual.atualizarNome(requestDTO.nome());
        clienteAtual.atualizarEmail(requestDTO.email());
        clienteAtual.atualizarTelefone(requestDTO.telefone());

        clienteAtual = repository.save(clienteAtual);

        return ClienteResponseDTO.toDTO(clienteAtual);
    }


    @Transactional
    public void deletarCliente(Long id){
        Cliente cliente = buscarCliente(id);

        if (!cliente.getPedidos().isEmpty()){
            throw new RegraNegocioException("Não é possível remover o cliente '" + cliente.getNomePessoa().nome() + "' pois ele possui " + cliente.getPedidos().size() + " pedido(s) registrado(s).", HttpStatus.BAD_REQUEST);
        }

        repository.delete(cliente);
    }


    private Cliente buscarCliente(Long id){
        return repository.findById(id)
                .orElseThrow(
                        ()-> new RegraNegocioException("Cliente com id " + id + " não encontrado.", HttpStatus.NOT_FOUND )
                );
    }
}
