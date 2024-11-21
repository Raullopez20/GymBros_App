DROP DATABASE IF EXISTS gymbros_db;
CREATE
DATABASE gymbros_db;
USE
gymbros_db;

-- Tabla de Usuarios
CREATE TABLE usuarios
(
    id_usuario                 INT AUTO_INCREMENT PRIMARY KEY,
    nombre                     VARCHAR(255)        NOT NULL,
    apellido                   VARCHAR(255)        NOT NULL,
    correo                     VARCHAR(255) UNIQUE NOT NULL,
    peso                       VARCHAR(255),
    altura                     VARCHAR(255),
    edad                       VARCHAR(255),
    nombre_usuario             VARCHAR(255) UNIQUE NOT NULL,
    contrasena                 VARCHAR(255)        NOT NULL,
    detalles_contacto          VARCHAR(255)        NOT NULL,
    preferencias_entrenamiento TEXT                NOT NULL
);

-- Tabla de Entrenadores
CREATE TABLE entrenadores
(
    id_entrenador     INT AUTO_INCREMENT PRIMARY KEY,
    nombre            VARCHAR(255)        NOT NULL,
    correo            VARCHAR(255) UNIQUE NOT NULL,
    especializacion   VARCHAR(255)        NOT NULL,
    detalles_contacto VARCHAR(255)        NOT NULL
);

-- Tabla de Rutinas
CREATE TABLE rutinas
(
    id_rutina     INT AUTO_INCREMENT PRIMARY KEY,
    id_entrenador INT          NOT NULL,
    titulo        VARCHAR(255) NOT NULL,
    descripcion   TEXT         NOT NULL,
    creado_en     VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_entrenador) REFERENCES entrenadores (id_entrenador)
);

-- Tabla de Ejercicios
CREATE TABLE ejercicios
(
    id_ejercicio INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(255) NOT NULL,
    descripcion  TEXT         NOT NULL,
    tipo         VARCHAR(255) NOT NULL
);

-- Tabla de Detalles de Rutinas
CREATE TABLE detalles_rutina
(
    id_detalle_rutina INT AUTO_INCREMENT PRIMARY KEY,
    id_rutina         INT NOT NULL,
    id_ejercicio      INT NOT NULL,
    series            INT NOT NULL,
    repeticiones      INT NOT NULL,
    duracion          INT NOT NULL,
    FOREIGN KEY (id_rutina) REFERENCES rutinas (id_rutina),
    FOREIGN KEY (id_ejercicio) REFERENCES ejercicios (id_ejercicio)
);

-- Tabla de Progreso del Usuario
CREATE TABLE progreso_usuario
(
    id_progreso              INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario               INT          NOT NULL,
    id_ejercicio             INT          NOT NULL,
    nombre                   VARCHAR(255) NOT NULL,
    apellido                 VARCHAR(255) NOT NULL,
    series_completadas       INT          NOT NULL,
    repeticiones_completadas INT          NOT NULL,
    duracion                 INT          NOT NULL,
    registrado_en            VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (id_ejercicio) REFERENCES ejercicios (id_ejercicio)
);

-- Tabla de Clases
CREATE TABLE clases
(
    id_clase          INT AUTO_INCREMENT PRIMARY KEY,
    titulo            VARCHAR(255) NOT NULL,
    id_entrenador     INT          NOT NULL,
    max_participantes INT,
    ubicacion         VARCHAR(255),
    inicio            VARCHAR(255),
    fin               VARCHAR(255),
    FOREIGN KEY (id_entrenador) REFERENCES entrenadores (id_entrenador)
);

-- Tabla de Reservas de Clases
CREATE TABLE reservas_clases
(
    id_reserva     INT AUTO_INCREMENT PRIMARY KEY,
    id_clase       INT          NOT NULL,
    id_usuario     INT          NOT NULL,
    nombre_usuario VARCHAR(255) NOT NULL,
    titulo         VARCHAR(255) NOT NULL,
    dia            VARCHAR(255) NOT NULL,
    hora           VARCHAR(255) NOT NULL,
    reservado_en   VARCHAR(255) NOT NULL,
    FOREIGN KEY (id_clase) REFERENCES clases (id_clase),
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla de Administradores
CREATE TABLE administradores
(
    id_admin       INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(255) UNIQUE NOT NULL,
    correo         VARCHAR(255) UNIQUE NOT NULL,
    nombre_usuario VARCHAR(255) UNIQUE NOT NULL,
    contrasena     VARCHAR(255) UNIQUE NOT NULL
);

-- Tabla de Instalaciones
CREATE TABLE instalaciones
(
    id_instalacion INT AUTO_INCREMENT PRIMARY KEY,
    nombre         VARCHAR(255) NOT NULL,
    descripcion    VARCHAR(255) NOT NULL,
    ruta_imagen    VARCHAR(255) NOT NULL
);

CREATE TABLE horarios
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    hora      VARCHAR(255),
    lunes     VARCHAR(255),
    martes    VARCHAR(255),
    miercoles VARCHAR(255),
    jueves    VARCHAR(255),
    viernes   VARCHAR(255),
    sabado    VARCHAR(255),
    domingo   VARCHAR(255)
);

CREATE TABLE configuracion
(
    clave VARCHAR(255) PRIMARY KEY,
    valor VARCHAR(255) NOT NULL
);

-- Insertar una entrada para controlar la inicialización de los datos de horarios
INSERT INTO configuracion (clave, valor)
VALUES ('datos_horarios_inicializados', 'false');

-- Insertar un administrador con nombre "admin" y contraseña "admin1234"
INSERT INTO administradores (nombre, nombre_usuario, correo, contrasena)
VALUES ('admin', 'admin', 'admin@example.com', 'admin1234');

-- Insertar datos en la tabla entrenadores para cumplir con las referencias de clases
INSERT INTO entrenadores (nombre, correo, especializacion, detalles_contacto)
VALUES ('Entrenador 1', 'entrenador1@example.com', 'Yoga', '123456789'),
       ('Entrenador 2', 'entrenador2@example.com', 'Pilates', '123456789'),
       ('Entrenador 3', 'entrenador3@example.com', 'HIIT', '123456789'),
       ('Entrenador 4', 'entrenador4@example.com', 'Spinning', '123456789'),
       ('Entrenador 5', 'entrenador5@example.com', 'Zumba', '123456789'),
       ('Entrenador 6', 'entrenador6@example.com', 'CrossFit', '123456789');

-- Insertar datos en la tabla clases
INSERT INTO clases (titulo, id_entrenador, max_participantes, ubicacion, inicio, fin)
VALUES ('Yoga', 1, 20, 'Sala 1', '06:00', '07:00'),
       ('Pilates', 2, 20, 'Sala 2', '07:00', '08:00'),
       ('HIIT', 3, 20, 'Sala 3', '08:00', '09:00'),
       ('Spinning', 4, 20, 'Sala 4', '09:00', '10:00'),
       ('Zumba', 5, 20, 'Sala 5', '10:00', '11:00'),
       ('CrossFit', 6, 20, 'Sala 6', '11:00', '12:00');

-- Insertar ejercicios en la tabla ejercicios
INSERT INTO ejercicios (id_ejercicio, nombre, descripcion, tipo)
VALUES
-- Ejercicios de Pecho
(1, 'Press de banca', 'Ejercicio de levantamiento de pesas con barra en un banco', 'Pecho'),
(2, 'Press de banca inclinado', 'Ejercicio de levantamiento de pesas con barra en un banco inclinado', 'Pecho'),
(3, 'Press de banca declinado', 'Ejercicio de levantamiento de pesas con barra en un banco declinado', 'Pecho'),
(4, 'Aperturas con mancuernas', 'Ejercicio de apertura de brazos con mancuernas en un banco plano', 'Pecho'),
(5, 'Fondos en paralelas', 'Ejercicio de dips en paralelas para pectorales', 'Pecho'),

-- Ejercicios de Espalda
(6, 'Dominadas', 'Ejercicio de levantamiento del cuerpo utilizando una barra fija', 'Espalda'),
(7, 'Remo con barra', 'Ejercicio de levantamiento de pesas con barra inclinada hacia adelante', 'Espalda'),
(8, 'Remo con mancuernas', 'Ejercicio de levantamiento de mancuernas inclinado hacia adelante', 'Espalda'),
(9, 'Pullover con mancuerna', 'Ejercicio de levantamiento de mancuernas detrás de la cabeza en un banco', 'Espalda'),
(10, 'Pulldown con cable', 'Ejercicio de tirar de una barra hacia el pecho en una máquina de cables', 'Espalda'),

-- Ejercicios de Piernas
(11, 'Sentadillas', 'Ejercicio de levantamiento de pesas con barra en los hombros', 'Piernas'),
(12, 'Prensa de piernas', 'Ejercicio en una máquina para empujar el peso con las piernas', 'Piernas'),
(13, 'Extensiones de piernas', 'Ejercicio en máquina para trabajar el cuádriceps', 'Piernas'),
(14, 'Curl de piernas', 'Ejercicio en máquina para trabajar los isquiotibiales', 'Piernas'),
(15, 'Peso muerto', 'Ejercicio de levantamiento de pesas desde el suelo hasta la posición de pie', 'Piernas'),

-- Ejercicios de Hombros
(16, 'Press militar con barra', 'Ejercicio de levantamiento de pesas con barra por encima de la cabeza', 'Hombros'),
(17, 'Elevaciones laterales', 'Ejercicio de levantamiento de mancuernas hacia los lados', 'Hombros'),
(18, 'Elevaciones frontales', 'Ejercicio de levantamiento de mancuernas hacia el frente', 'Hombros'),
(19, 'Press Arnold', 'Ejercicio de levantamiento de mancuernas con rotación de muñecas', 'Hombros'),
(20, 'Face Pulls', 'Ejercicio con cable para trabajar la parte trasera de los hombros', 'Hombros'),

-- Ejercicios de Bíceps
(21, 'Curl con barra', 'Ejercicio de levantamiento de pesas con barra para bíceps', 'Bíceps'),
(22, 'Curl con mancuernas', 'Ejercicio de levantamiento de mancuernas para bíceps', 'Bíceps'),
(23, 'Curl martillo', 'Ejercicio de levantamiento de mancuernas con agarre neutro', 'Bíceps'),
(24, 'Curl con cable', 'Ejercicio de levantamiento de pesas con cable para bíceps', 'Bíceps'),
(25, 'Predicador curl', 'Ejercicio de levantamiento de pesas en banco predicador', 'Bíceps'),

-- Ejercicios de Tríceps
(26, 'Press francés', 'Ejercicio de levantamiento de pesas con barra EZ para tríceps', 'Tríceps'),
(27, 'Extensión de tríceps con mancuernas', 'Ejercicio de levantamiento de mancuernas por encima de la cabeza', 'Tríceps'),
(28, 'Dips de tríceps', 'Ejercicio de levantamiento del cuerpo en paralelas', 'Tríceps'),
(29, 'Patada de tríceps', 'Ejercicio de levantamiento de mancuernas hacia atrás', 'Tríceps'),
(30, 'Pushdown con cable', 'Ejercicio de empuje de barra hacia abajo en máquina de cables', 'Tríceps'),

-- Ejercicios de Abdominales
(31, 'Crunch', 'Ejercicio de contracción abdominal en el suelo', 'Abdominales'),
(32, 'Elevación de piernas', 'Ejercicio de levantamiento de piernas colgado de una barra', 'Abdominales'),
(33, 'Plancha', 'Ejercicio de mantener el cuerpo en línea recta apoyado en los antebrazos y pies', 'Abdominales'),
(34, 'Bicicleta', 'Ejercicio de movimiento de piernas como si estuvieras pedaleando', 'Abdominales'),
(35, 'Russian twist', 'Ejercicio de rotación del torso con o sin peso', 'Abdominales');

DESCRIBE clases;
DESCRIBE reservas_clases;

SELECT *
FROM ejercicios;
