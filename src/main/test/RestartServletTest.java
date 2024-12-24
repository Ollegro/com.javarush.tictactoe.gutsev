import com.tictactoe.RestartServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class RestartServletTest {
    private RestartServlet restartServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        restartServlet = new RestartServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);

        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testDoPost_ShouldInvalidateSessionAndRedirect() throws IOException {
        // Act
        restartServlet.doPost(request, response);

        // Assert
        verify(session).invalidate(); // Проверяем, что сессия была завершена
        verify(response).sendRedirect("/start"); // Проверяем, что произошел редирект
    }
}
