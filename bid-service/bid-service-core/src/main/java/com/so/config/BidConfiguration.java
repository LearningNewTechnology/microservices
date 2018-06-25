package com.so.config;

import com.so.repository.BidRepository;
import com.so.sagas.BidPaymentSaga;
import com.so.sagas.BidPaymentSagaData;
import com.so.service.BidCommandHandler;
import com.so.service.BidService;
import io.eventuate.tram.commands.consumer.CommandDispatcher;
import io.eventuate.tram.sagas.orchestration.Saga;
import io.eventuate.tram.sagas.orchestration.SagaManager;
import io.eventuate.tram.sagas.orchestration.SagaManagerImpl;
import io.eventuate.tram.sagas.participant.SagaCommandDispatcher;
import io.eventuate.tram.sagas.participant.SagaLockManager;
import io.eventuate.tram.sagas.participant.SagaLockManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.so.repository")
@EnableTransactionManagement
@ComponentScan("com.so")
@EntityScan(basePackages = "com.so.domain")
public class BidConfiguration {

    @Autowired
    private BidRepository bidRepository;

    @Bean
    public SagaLockManager sagaLockManager() {
        return new SagaLockManagerImpl();
    }

    @Bean
    public SagaManager<BidPaymentSagaData> createOrderSagaManager(Saga<BidPaymentSagaData> saga) {
        return new SagaManagerImpl<>(saga);
    }

    @Bean
    public BidService bidService() {
        return new BidService(bidRepository);
    }

    @Bean
    public BidPaymentSaga createBidPaymentSaga() {
        return new BidPaymentSaga();
    }

    @Bean
    public BidCommandHandler bidCommandHandler() {
        return new BidCommandHandler();
    }

    @Bean
    public CommandDispatcher bidCommandDispatcher(BidCommandHandler target, SagaLockManager sagaLockManager) {
        return new SagaCommandDispatcher("bidCommandDispatcher", target.commandHandlerDefinitions());
    }
}
