package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseManagerTest {

    private Connection connection;

    @BeforeEach
    public void setUp() {
        connection = mock(Connection.class);
    }

    @Test
    public void testGetConnection() throws Exception {
        when(connection.isClosed()).thenReturn(false);
        DatabaseManager.setConnection(connection);
        Connection result = DatabaseManager.getConnection();
        assertEquals(connection, result);
        
    }

    @Test
    public void testCloseConnection() throws Exception {
        DatabaseManager.setConnection(connection);
        DatabaseManager.closeConnection();
        verify(connection, times(1)).close();
     // Cria um objeto simulado de Connection
        Connection mockConnection = Mockito.mock(Connection.class);

        // Programa o objeto simulado para lançar uma SQLException quando o método close() for chamado
        Mockito.doThrow(SQLException.class).when(mockConnection).close();

        // Define a conexão no DatabaseManager para o objeto simulado
        DatabaseManager.setConnection(mockConnection);

        // Chama o método closeConnection()
        DatabaseManager.closeConnection();

        // Verifica se o método close() foi chamado no objeto simulado
        Mockito.verify(mockConnection, Mockito.times(1)).close();
        

    }

    @AfterEach
    public void tearDown() {
        DatabaseManager.setConnection(null);
    }
}
