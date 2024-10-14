-- Use the ClothingStore database
USE [ClothingStore]
GO
-- Drop Foreign Key Constraints
IF EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_tblOrders_tblUsers]'))
ALTER TABLE [dbo].[tblOrders] DROP CONSTRAINT [FK_tblOrders_tblUsers]
GO

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_tblOrderDetails_tblOrders]'))
ALTER TABLE [dbo].[tblOrderDetails] DROP CONSTRAINT [FK_tblOrderDetails_tblOrders]
GO

IF EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_tblOrderDetails_tblProducts]'))
ALTER TABLE [dbo].[tblOrderDetails] DROP CONSTRAINT [FK_tblOrderDetails_tblProducts]
GO

-- Drop Tables
IF OBJECT_ID('dbo.tblOrderDetails', 'U') IS NOT NULL
DROP TABLE [dbo].[tblOrderDetails]
GO

IF OBJECT_ID('dbo.tblOrders', 'U') IS NOT NULL
DROP TABLE [dbo].[tblOrders]
GO

IF OBJECT_ID('dbo.tblProducts', 'U') IS NOT NULL
DROP TABLE [dbo].[tblProducts]
GO

IF OBJECT_ID('dbo.tblUsers', 'U') IS NOT NULL
DROP TABLE [dbo].[tblUsers]
GO
