CREATE DATABASE faculdade;
GO
USE faculdade;
GO

--Tabela serao geradas pelo proprio FrameWork (pq eu confio nele)

CREATE TABLE HistoricoCuriosidade (
    id_clube VARCHAR(11) NOT NULL,
    id_curiosidade INT NOT NULL,
    hora_escolhido DATETIME DEFAULT GETDATE()
)


SELECT * 
From clube

SELECT *
FROM candidato

SELECT *
FROM curiosidade
WHERE clube_id = 'Santos'

SELECT * 
FROM HistoricoCuriosidade
ORDER BY hora_escolhido ASC

EXEC sp_receberCuriosidade 'Corinthians'


SELECT hora_escolhido
FROM HistoricoCuriosidade
ORDER BY hora_escolhido ASC

DELETE FROM HistoricoCuriosidade
DELETE FROM clube
DELETE FROM curiosidade

-----------------------------------------------------------------------------------------------

CREATE PROCEDURE sp_receberCuriosidade @id_clube VARCHAR(11)
AS
BEGIN

    DECLARE @curiosidade_id INT;
    DECLARE @curiosidadeInvalida BIT ---0 = Curiosidade valida, 1 = Curiosidade invalida

    SET @curiosidadeInvalida = 1
    
    -- Tentar encontrar uma curiosidade que nao esteja nas ultimas 3
    WHILE @curiosidadeInvalida = 1
    BEGIN

        SELECT TOP 1 @curiosidade_id = id
        FROM Curiosidade
        WHERE clube_id = @id_clube
        ORDER BY NEWID()
        
        SET @curiosidadeInvalida =  dbo.verificarHistoriCuriosidade(@id_clube, @curiosidade_id)
    END
    
    -- Registrar a nova escolha
    INSERT INTO HistoricoCuriosidade (id_clube, id_curiosidade)
    VALUES (@id_clube, @curiosidade_id)
    
    -- Retornar a curiosidade selecionada
    SELECT *
    FROM Curiosidade
    WHERE id = @curiosidade_id
END

-----------------------------------------------------------------------------------------------

CREATE FUNCTION dbo.verificarHistoriCuriosidade(@id_clube VARCHAR(11), @id_curiosidade INT)
RETURNS BIT
AS
BEGIN
    DECLARE @resultado BIT = 0 --0 = Nao esta no historico, 1 = Esta no historico.
    
    IF EXISTS(
        SELECT 1 
        FROM HistoricoCuriosidade 
        WHERE id_clube = @id_clube AND id_curiosidade = @id_curiosidade
    )
    BEGIN
        SET @resultado = 1
    END
    RETURN @resultado
END

-----------------------------------------------------------------------------------------------

CREATE TRIGGER tgr_Inserir_HistoricoCuriosidade
ON HistoricoCuriosidade
AFTER INSERT
AS
BEGIN
    
    DECLARE @hora_mais_antiga DATETIME
    DECLARE @clube VARCHAR(11)    
    DECLARE @qtd_linhas INT      

    SELECT @clube = id_clube
    FROM inserted

    SELECT TOP 1 @hora_mais_antiga = hora_escolhido
    FROM HistoricoCuriosidade
    ORDER BY hora_escolhido ASC


    SELECT @qtd_linhas = COUNT(*)
    FROM HistoricoCuriosidade
    WHERE id_clube = @clube

    -- Se o número de linhas para esse clube for maior que 3, deletar a mais antiga
    IF (@qtd_linhas > 3)
    BEGIN
        DELETE
        FROM HistoricoCuriosidade
        WHERE id_clube = @clube AND hora_escolhido = @hora_mais_antiga
    END
END


-----------------------------------------------------------------------------------------------

CREATE TRIGGER trg_ProtegerCuriosidades
ON Curiosidade
AFTER UPDATE, DELETE
AS
BEGIN

    IF EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido modificar curiosidades após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
     
    IF EXISTS (SELECT 1 FROM deleted) AND NOT EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido excluir curiosidades após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
END

-----------------------------------------------------------------------------------------------

CREATE TRIGGER trg_ProtegerCandidato
ON Candidato
AFTER UPDATE, DELETE
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido modificar candidatos após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
     
    IF EXISTS (SELECT 1 FROM deleted) AND NOT EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido excluir candidatos após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
END

-----------------------------------------------------------------------------------------------

CREATE TRIGGER trg_ProtegerClube
ON clube
AFTER UPDATE, DELETE
AS
BEGIN
    IF EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido modificar os clubes após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
     
    IF EXISTS (SELECT 1 FROM deleted) AND NOT EXISTS (SELECT 1 FROM inserted)
    BEGIN
        RAISERROR('Não é permitido excluir os clubes após o cadastro.', 16, 1)
        ROLLBACK TRANSACTION;
        RETURN
    END
END