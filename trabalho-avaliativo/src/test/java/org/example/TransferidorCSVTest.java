package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransferidorCSVTest {

    private TransferidorCSV transferidorCSV;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;

    @BeforeEach
    public void setUp() {
        transferidorCSV = new TransferidorCSV();
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        statement = mock(Statement.class);
        resultSet = mock(ResultSet.class);
        DatabaseManager.setConnection(connection);
    }

    @Test
    public void testMain() throws Exception {
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt(1)).thenReturn(1);

        // Aqui você deve substituir "caminho/para/seu/arquivo.csv" pelo caminho do arquivo CSV que você quer testar
        System.setIn(new ByteArrayInputStream("caminho/para/seu/arquivo.csv".getBytes()));
        TransferidorCSV.main(new String[]{});

        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).execute();
        verify(connection, times(1)).createStatement();
        verify(statement, times(2)).executeQuery(anyString());
        verify(resultSet, times(2)).next();
        verify(resultSet, times(2)).getInt(1);
    }

    @Test
    public void testMainWithFileNotFoundException() throws Exception {
        System.setIn(new ByteArrayInputStream("caminho/para/arquivo/inexistente.csv".getBytes()));
        assertThrows(FileNotFoundException.class, () -> TransferidorCSV.main(new String[]{}));
    }
}
