CREATE TABLE IF NOT EXISTS customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    date_of_birth DATE NOT NULL,
    phone_number VARCHAR(30) NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_customer_date_of_birth
    ON customer(date_of_birth);

INSERT INTO customer (
    name,
    date_of_birth,
    phone_number
)
SELECT *
FROM (
    VALUES
        ('Budi Santoso', '1955-03-15'::date, '081234567890'),
        ('Siti Rahayu', '1960-07-22'::date, '081345678901'),
        ('Agus Wijaya', '1963-11-05'::date, '081456789012'),
        ('Sri Wahyuni', '1968-09-12'::date, '081567890123'),
        ('Slamet Riyadi', '1972-01-28'::date, '081678901234'),
        ('Sulastri', '1975-06-18'::date, '081789012345'),
        ('Heru Susanto', '1978-04-30'::date, '081890123456'),
        ('Dewi Lestari', '1980-12-03'::date, '081901234567'),
        ('Joko Widodo', '1983-08-14'::date, '082012345678'),
        ('Rina Marlina', '1985-02-19'::date, '082123456789'),
        ('Hendra Gunawan', '1987-10-07'::date, '082234567890'),
        ('Nurul Hidayati', '1989-05-25'::date, '082345678901'),
        ('Rudi Hartono', '1991-09-11'::date, '082456789012'),
        ('Maya Sari', '1993-12-01'::date, '082567890123'),
        ('Doni Prasetyo', '1995-04-17'::date, '082678901234'),
        ('Rani Puspita', '1996-08-29'::date, '082789012345'),
        ('Andi Saputra', '1998-03-08'::date, '082890123456'),
        ('Tina Melati', '1999-11-21'::date, '082901234567'),
        ('Rio Ferdinan', '2000-07-14'::date, '083012345678'),
        ('Vina Anggraini', '2001-02-25'::date, '083123456789'),
        ('Gilang Ramadhan', '2002-10-09'::date, '083234567890'),
        ('Dinda Permatasari', '2003-05-16'::date, '083345678901'),
        ('Fajar Nugroho', '2004-12-30'::date, '083456789012'),
        ('Luna Kirana', '2005-08-13'::date, '083567890123'),
        ('Bayu Prakoso', '2006-03-27'::date, '083678901234'),
        ('Cindy Febriani', '2007-11-05'::date, '083789012345'),
        ('Eko Santoso', '2008-06-19'::date, '083890123456'),
        ('Putri Ayu', '2009-09-28'::date, '083901234567'),
        ('Ivan Kurniawan', '2010-04-12'::date, '084012345678'),
        ('Nadia Safira', '2011-07-30'::date, '084123456789'),
        ('Raka Putra', '2012-01-15'::date, '084234567890'),
        ('Aulia Ramadhani', '2013-10-22'::date, '084345678901'),
        ('Bima Sakti', '2014-05-06'::date, '084456789012'),
        ('Citra Dewi', '2015-09-17'::date, '084567890123'),
        ('Damar Wibowo', '2016-12-25'::date, '084678901234')
) AS seed(name, date_of_birth, phone_number)
WHERE NOT EXISTS (
    SELECT 1
    FROM customer
);