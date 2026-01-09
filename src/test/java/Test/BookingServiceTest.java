package Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceTest.class);
    @Mock
    private BookingService bookingService = new BookingService();


    @Test
    void positiveCreateBookTest() throws CantBookException {
        logger.info("Тест positiveCreateBookTest запущен");
        logger.debug("Формирование мока");
        Mockito.when(bookingService.checkTimeInBD(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);
        Mockito.when(bookingService.createBook(anyString(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(true);
        logger.debug("Запись создана");

        assertTrue(bookingService.checkTimeInBD(LocalDateTime.now(), LocalDateTime.now()));
        assertTrue(bookingService.createBook("User", LocalDateTime.now(), LocalDateTime.now()));

        verify(bookingService).checkTimeInBD(any(LocalDateTime.class), any(LocalDateTime.class));
        verify(bookingService).createBook(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));

    }

    @Test
    void failCreateBookTest() throws CantBookException {
        logger.info("Тест failCreateBookTest запущен");
        logger.debug("Формирование мока");
        Mockito.doThrow(CantBookException.class)
                .when(bookingService).book(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        logger.debug("Возврат CantBookException");
        assertThrows(CantBookException.class, () -> bookingService.book("User", LocalDateTime.now(), LocalDateTime.now()));

        verify(bookingService, times(1)).book(anyString(), any(LocalDateTime.class), any(LocalDateTime.class));
        verifyNoMoreInteractions(bookingService);
    }
}