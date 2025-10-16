CREATE DATABASE faculdade;
GO
USE faculdade;
GO

CREATE TABLE listCuriosidade (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_clube VARCHAR(11) NOT NULL,
    id_curiosidade INT NOT NULL,
    hora_escolhido DATETIME DEFAULT GETDATE()
)

INSERT INTO Clube(id) VALUES
('Corinthians'),
('Palmeiras'),
('São Paulo'),
('Santos')
SELECT *
FROM Clube

SELECT *
FROM Curiosidade

SELECT * 
FROM Candidato

CREATE FUNCTION dbo.verificarUltimaEscolha(@id_clube VARCHAR(11), @id_curiosidade INT)
RETURNS BIT
AS
BEGIN
    DECLARE @resultado BIT = 0
    
    IF EXISTS(
        SELECT 1 
        FROM listCuriosidade 
        WHERE id_clube = @id_clube 
        AND id_curiosidade = @id_curiosidade
        AND id IN (
            SELECT TOP 3 id 
            FROM listCuriosidade 
            WHERE id_clube = @id_clube 
            ORDER BY hora_escolhido DESC
        )
    )
    BEGIN
        SET @resultado = 1
    END
    
    RETURN @resultado
END

CREATE PROCEDURE sp_receberCuriosidade @id_clube VARCHAR(11),
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @curiosidade_id INT;
    DECLARE @tentativas INT = 0;
    DECLARE @max_tentativas INT = 10; -- Evitar loop infinito
    
    -- Tentar encontrar uma curiosidade que não esteja nas últimas 3
    WHILE @tentativas < @max_tentativas
    BEGIN
        -- Selecionar curiosidade aleatória do clube
        SELECT TOP 1 @curiosidade_id = id
        FROM Curiosidade
        WHERE clube_id = @id_clube
        ORDER BY NEWID()
        
        -- Verificar se não é uma das últimas 3 usando a UDF
        IF dbo.verificarUltimaEscolha(@id_clube, @curiosidade_id) = 0
        BEGIN
            BREAK; -- Encontrou uma curiosidade válida
        END
        
        SET @tentativas = @tentativas + 1;
    END
    
    -- Se não encontrou após várias tentativas, pega qualquer uma
    IF @curiosidade_id IS NULL
    BEGIN
        SELECT TOP 1 @curiosidade_id = id
        FROM Curiosidade
        WHERE clube_id = @id_clube
        ORDER BY NEWID()
    END
    
    -- Registrar a nova escolha
    INSERT INTO listCuriosidade (id_clube, id_curiosidade)
    VALUES (@id_clube, @curiosidade_id)
    
    -- Manter apenas as 3 últimas curiosidades por clube
    DELETE FROM listCuriosidade
    WHERE id_clube = @id_clube
    AND id NOT IN (
        SELECT TOP 3 id 
        FROM listCuriosidade 
        WHERE id_clube = @id_clube 
        ORDER BY hora_escolhido DESC
    )
    
    -- Retornar a curiosidade selecionada
    SELECT *
    FROM Curiosidade
    WHERE id = @curiosidade_id
END

CREATE PROCEDURE sp_visualizarUltimasEscolhas @id_clube VARCHAR(11)
AS
BEGIN
    SET NOCOUNT ON;
    
    SELECT lc.hora_escolhido, lc.id_aluno, c.*
    FROM listCuriosidade lc
    INNER JOIN Curiosidade c ON lc.id_curiosidade = c.id
    WHERE lc.id_clube = @id_clube
    ORDER BY lc.hora_escolhido DESC
END







