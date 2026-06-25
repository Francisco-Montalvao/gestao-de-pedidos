package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.util.List;

public record ProdutosRelatorioResponseDTO(
        PeriodoResponseDTO periodo,
        List<ProdutosResponseDTO> produtos
) {
}
