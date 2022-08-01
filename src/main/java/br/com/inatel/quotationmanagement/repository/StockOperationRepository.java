package br.com.inatel.quotationmanagement.repository;

import br.com.inatel.quotationmanagement.model.entity.StockOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockOperationRepository extends JpaRepository<StockOperation, String> {

    List<StockOperation> findByStockId(String stockId);

}
