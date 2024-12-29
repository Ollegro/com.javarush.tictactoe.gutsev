import com.tictactoe.Field;
import com.tictactoe.InitServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InitServletTest {

    private InitServlet initServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private ServletContext servletContext;

    @BeforeEach
    void setUp() throws ServletException {
        initServlet = new InitServlet();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        servletContext = mock(ServletContext.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

        // Настройка мока для сессии
        when(request.getSession(true)).thenReturn(session);

        // Настройка ServletConfig и ServletContext
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);

        // Инициализация сервлета с настроенным ServletConfig
        initServlet.init(servletConfig);
    }

    @Test
    void testDoGet() throws IOException, ServletException {
        // Создаем реальный объект Field
        Field expectedField = new Field();

        // Вызов метода doGet
        initServlet.doGet(request, response);

        // Захват аргументов, переданных в setAttribute
        ArgumentCaptor<Object> fieldCaptor = ArgumentCaptor.forClass(Object.class);
        ArgumentCaptor<Object> dataCaptor = ArgumentCaptor.forClass(Object.class);

        verify(session).setAttribute(eq("field"), fieldCaptor.capture());
        verify(session).setAttribute(eq("data"), dataCaptor.capture());

        // Проверка значений
        Field actualField = (Field) fieldCaptor.getValue();
        assertEquals(expectedField.getField(), actualField.getField());
        assertEquals(expectedField.getFieldData(), dataCaptor.getValue());

        // Проверка перенаправления на index.jsp
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(servletContext).getRequestDispatcher(captor.capture());
        assertEquals("/index.jsp", captor.getValue());
    }
}
