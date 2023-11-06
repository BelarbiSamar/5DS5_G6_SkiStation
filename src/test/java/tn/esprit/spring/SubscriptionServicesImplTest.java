package tn.esprit.spring;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.TypeSubscription;
import tn.esprit.spring.repositories.ISkierRepository;
import tn.esprit.spring.repositories.ISubscriptionRepository;
import tn.esprit.spring.services.SubscriptionServicesImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

    @ExtendWith(MockitoExtension.class)
    public class SubscriptionServicesImplTest {

        @InjectMocks
        private SubscriptionServicesImpl subscriptionService;

        @Mock
        private ISubscriptionRepository subscriptionRepository;

        @Mock
        private ISkierRepository skierRepository;

        @BeforeEach
        public void setUp() {
            // Reset mock behavior before each test
            reset(subscriptionRepository);
        }

        @Test
        public void testAddSubscription() {
            // Create a Subscription object
            Subscription subscription = new Subscription();
            subscription.setTypeSub(TypeSubscription.ANNUAL);

            // Define the expected behavior of the repository mock
            when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscription);

            // Call the method under test
            Subscription result = subscriptionService.addSubscription(subscription);

            // Assert the result
            assertEquals(TypeSubscription.ANNUAL, result.getTypeSub());
        }

        @Test
        public void testRetrieveSubscriptionById() {
            // Define a subscription ID for testing
            Long subscriptionId = 1L;

            // Create a mock Subscription object
            Subscription mockSubscription = new Subscription();
            mockSubscription.setNumSub(subscriptionId);

            // Define the expected behavior of the repository mock
            when(subscriptionRepository.findById(subscriptionId)).thenReturn(java.util.Optional.of(mockSubscription));

            // Call the method under test
            Subscription result = subscriptionService.retrieveSubscriptionById(subscriptionId);

            // Assert the result
            assertEquals(subscriptionId, result.getNumSub());
        }

        @Test
        public void testGetSubscriptionByType() {
            // Define a TypeSubscription for testing
            TypeSubscription type = TypeSubscription.ANNUAL;

            // Create a set of mock Subscription objects
            Subscription subscription1 = new Subscription();
            subscription1.setTypeSub(type);
            Subscription subscription2 = new Subscription();
            subscription2.setTypeSub(type);
            Set<Subscription> mockSubscriptions = new HashSet<>();
            mockSubscriptions.add(subscription1);
            mockSubscriptions.add(subscription2);

            // Define the expected behavior of the repository mock
            when(subscriptionRepository.findByTypeSubOrderByStartDateAsc(type)).thenReturn(mockSubscriptions);

            // Call the method under test
            Set<Subscription> result = subscriptionService.getSubscriptionByType(type);

            // Assert the result
            assertEquals(2, result.size());
        }
    }

