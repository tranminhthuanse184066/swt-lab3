USE [master]
GO

-- Drop the database if it exists
IF EXISTS (SELECT name FROM sys.databases WHERE name = N'ClothingStore')
DROP DATABASE [ClothingStore]
GO

-- Create a new database
CREATE DATABASE [ClothingStore]
GO

USE [ClothingStore]
GO

-- Clear existing data in tables (if they exist)
IF OBJECT_ID('dbo.tblUsers', 'U') IS NOT NULL DELETE dbo.tblUsers WHERE 1=1
IF OBJECT_ID('dbo.tblOrders', 'U') IS NOT NULL DELETE dbo.tblOrders WHERE 1=1
IF OBJECT_ID('dbo.tblOrderDetails', 'U') IS NOT NULL DELETE dbo.tblOrderDetails WHERE 1=1
IF OBJECT_ID('dbo.tblProducts', 'U') IS NOT NULL DELETE dbo.tblProducts WHERE 1=1
GO

-- Create Users table
CREATE TABLE [dbo].[tblUsers](
    [userID] NVARCHAR(50) NOT NULL,
    [fullName] NVARCHAR(50) NOT NULL,
    [password] NVARCHAR(50) NOT NULL,
    [roleID] NVARCHAR(50),
    [status] BIT,
    CONSTRAINT [PK_tblUsers] PRIMARY KEY ([userID] ASC)
)
GO

-- Create Orders table with orderID as INT
CREATE TABLE [dbo].[tblOrders](
    [orderID] INT IDENTITY(1,1) NOT NULL,
    [userID] NVARCHAR(50),
    [orderDate] DATE,
    [total] DECIMAL(18, 2),
    [status] BIT,
    CONSTRAINT [PK_tblOrders] PRIMARY KEY ([orderID] ASC)
)
GO

-- Create OrderDetails table with orderID as INT
CREATE TABLE [dbo].[tblOrderDetails](
    [orderID] INT,
    [productID] NVARCHAR(50),
    [price] DECIMAL(18, 2),
    [quantity] INT,
    [status] BIT,
    CONSTRAINT [PK_tblOrderDetails] PRIMARY KEY ([orderID] ASC, [productID] ASC)
)
GO

-- Create Products table
CREATE TABLE [dbo].[tblProducts](
    [productID] NVARCHAR(50),
    [productName] NVARCHAR(100),
    [price] DECIMAL(18, 2),
    [quantity] INT,
    [image] NVARCHAR(250),
    [status] BIT,
    CONSTRAINT [PK_tblProducts] PRIMARY KEY ([productID] ASC)
)
GO

-- Add foreign key constraints
ALTER TABLE [dbo].[tblOrders] ADD CONSTRAINT [FK_tblOrders_tblUsers] FOREIGN KEY([userID])
REFERENCES [dbo].[tblUsers] ([userID])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[tblOrderDetails] ADD CONSTRAINT [FK_tblOrderDetails_tblOrders] FOREIGN KEY([orderID])
REFERENCES [dbo].[tblOrders] ([orderID])
ON DELETE CASCADE
GO

ALTER TABLE [dbo].[tblOrderDetails] ADD CONSTRAINT [FK_tblOrderDetails_tblProducts] FOREIGN KEY([productID])
REFERENCES [dbo].[tblProducts] ([productID])
ON DELETE CASCADE
GO

-- Insert sample data into Users table
INSERT [dbo].[tblUsers] ([userID], [fullName], [password], [roleID], [status]) VALUES 
(N'admin', N'Hieu Phi', N'1', N'AD', 1),
(N'admin2', N'Khanh Linh', N'1', N'AD', 1),
(N'klinh', N'Khanh Linh', N'1', N'US', 1),
(N'hainam', N'Hai Nam', N'1', N'US', 1),
(N'huuan', N'Huu An', N'1', N'US', 1),
(N'hmkhang', N'Hoang Minh Khang', N'1', N'US', 1)
GO

-- Insert sample data into Products table
INSERT [dbo].[tblProducts] ([productID], [productName], [price], [quantity], [image], [status]) VALUES 
(N'P001', N'Pink Tee', 30.00, 100, 'https://product.hstatic.net/1000201524/product/ua-v1-tee-lider-ao-ua-hong_dc5bbd2fd73347cbb352e0de29692b1a_master.jpg', 1),
(N'P002', N'Draco Polo - WHITE', 15.00, 200, 'https://product.hstatic.net/1000201524/product/dracopolo_-white_ba528fd5e46b4accbc1a5aa8e986c313_master.jpg', 1),
(N'P003', N'Jeans', 50.00, 200, 'https://product.hstatic.net/1000201524/product/gunsmoke_pants_b937304857724223be03d0c34f11d55d_master.jpg', 1),
(N'P004', N'Jacket', 100.00, 50, 'https://product.hstatic.net/1000201524/product/breslin_lider_32e1ff4e26944338a97f304d15ed6959_master.jpg', 1),
(N'P005', N'Short', 25.00, 80, 'https://product.hstatic.net/1000201524/product/lider-boxer-shorts-striped-red-front_a6fd6c07095a4d4ba8d22b2521a4f50f_master.jpg', 1),
(N'P006', N'Hoodie', 100.00, 150, 'https://product.hstatic.net/1000201524/product/42_a2e36d1e4d094072bce447edb0d803bf_master.jpg', 1),
(N'P007', N'Draco Polo - DARK GREY', 15.00, 150, 'https://product.hstatic.net/1000201524/product/draco_polo_shirt_-_grey_884b2c10e7d54985aa6c8c01642bc58d_master.jpg', 1),
(N'P008', N'Dragon Dream Dralan Tee', 18.00, 100,'https://product.hstatic.net/1000201524/product/draco_dream_raglan_tee_f6874dbe7ad941a687654d920b5e5b60_master.jpg', 1),
(N'P009', N'Captain Tiff Tee', 12.99, 120,'https://product.hstatic.net/1000201524/product/caption_tiff_tee_121f2447280444b1afbf0f751f8b8c95_master.jpg', 1),
(N'P010', N'SongLong Tee', 13.00, 130,'https://product.hstatic.net/1000201524/product/songlong_grey_fba69583367a44f09ae2ddf3dca88db2_master.jpg', 1),
(N'P011', N'Original Boxy Tee - MATCHA', 21.00, 300,'https://product.hstatic.net/1000201524/product/basic_tee_lider_-_matcha_989a116ea2e6435ea5f7e4d07390b0fe_master.jpg', 1),
(N'P012', N'Original Boxy Tee - WHITE', 21.00, 200,'https://product.hstatic.net/1000201524/product/basic_tee_lider_-_white_4250537ee0dd4db388bc12d99a21dbe4_master.jpg', 1),
(N'P013', N'Double Boxy Tee', 25.00, 250,'https://product.hstatic.net/1000201524/product/blue_tee_lider_c6bcdadf2f214f18ae7fc48325f48ce8_master.jpg', 1),
(N'P014', N'Long-Sleeve Tee', 30.00, 200,'https://product.hstatic.net/1000201524/product/ua_scale7_2cfcdd8443644a7182b86642eed85d87_master.jpg', 1),
(N'P015', N'Low-Key Unreal Tee', 22.00, 300,'https://product.hstatic.net/1000201524/product/img_4655_e1602db87cf747a0af4f8d5c4d85d7e4_master.jpg', 1),
(N'P016', N'Surreal See-Thru Tee', 30.00, 400,'https://product.hstatic.net/1000201524/product/img_1977_ee08205e01104dec956029a89ffce096_master.jpg', 1)
GO

-- Insert sample null data into Orders table
INSERT [dbo].[tblOrders] ([userID], [orderDate], [total], [status]) VALUES 
(NULL, NULL, NULL, 0),
(NULL, NULL, NULL, 1)
GO

-- Insert sample null data into OrderDetails table
INSERT [dbo].[tblOrderDetails] ([orderID], [productID], [price], [quantity], [status]) VALUES 
(1, N'P001', 0, NULL, 0)
GO
