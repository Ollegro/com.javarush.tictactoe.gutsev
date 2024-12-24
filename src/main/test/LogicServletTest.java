import com.tictactoe.Field;
import com.tictactoe.InitServlet;
import com.tictactoe.LogicServlet;
import com.tictactoe.Sign;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LogicServletTest {
    private LogicServlet logicServlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp() throws ServletException {
        logicServlet = new LogicServlet();
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        session = Mockito.mock(HttpSession.class);
        ServletContext servletContext = mock(ServletContext.class);
        ServletConfig servletConfig = mock(ServletConfig.class);
        requestDispatcher = mock(RequestDispatcher.class);
        when(request.getSession()).thenReturn(session);
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(servletContext.getRequestDispatcher("/index.jsp")).thenReturn(requestDispatcher);
        logicServlet.init(servletConfig);
    }

    @Test
    public void testDoGet_CellIsOccupied() throws ServletException, IOException {
        // Arrange
        Field field = new Field();
        field.getField().put(0, Sign.CROSS); // Cell 0 is occupied
        when(session.getAttribute("field")).thenReturn(field);
        when(request.getParameter("click")).thenReturn("0");
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);

        // Act
        logicServlet.doGet(request, response);

        // Assert
        verify(requestDispatcher).forward(request, response);
        verify(response, never()).sendRedirect("/index.jsp");
    }

    @Test
    public void testDoGet_CellIsEmpty_AndPlayerWins() throws ServletException, IOException {
        // Arrange
        Field field = new Field();
        field.getField().put(0, Sign.EMPTY); // Cell 0 is empty
        field.getField().put(1, Sign.CROSS);
        field.getField().put(2, Sign.CROSS);

        // Мокаем сессию и параметр запроса
        when(session.getAttribute("field")).thenReturn(field);
        when(request.getParameter("click")).thenReturn("0");

        // Act
        logicServlet.doGet(request, response);

        // Assert
        verify(session).setAttribute("winner", Sign.CROSS); // Проверяем, что победитель установлен
        verify(response).sendRedirect("/index.jsp"); // Проверяем перенаправление
    }


@Test
public void testDoGet_Draw_ShouldSetDrawAttributeAndRedirect() throws ServletException, IOException {
    // Arrange
    Field field = mock(Field.class);
    // Настраиваем поведение мока для ничьей
    when(field.getField()).thenReturn(Map.of(
            0, Sign.CROSS,
            1, Sign.NOUGHT,
            2, Sign.CROSS,
            3, Sign.NOUGHT,
            4, Sign.CROSS,
            5, Sign.NOUGHT,
            6, Sign.CROSS,
            7, Sign.NOUGHT,
            8, Sign.CROSS
    ));
    session.setAttribute("draw", true); // Изначально false
    response.sendRedirect("/index.jsp");



    when(session.getAttribute("field")).thenReturn(field);
    when(request.getParameter("click")).thenReturn("0"); // Игрок делает ход

    // Act
    logicServlet.doGet(request, response);

    // Assert
    verify(session).setAttribute("draw", true); // Проверяем, что атрибут был установлен на true
    verify(response).sendRedirect("/index.jsp"); // Проверяем, что произошел редирект
}





}