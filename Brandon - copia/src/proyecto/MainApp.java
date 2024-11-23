package proyecto;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

// MainApp para iniciar la aplicación
public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

// Clase para el formulario de inicio de sesión
class LoginFrame extends JFrame {
    public static final Map<String, Usuarios> usuarios = new HashMap<>();
    private int intentos = 0; // Contador de intentos fallidos

    static {
        // Usuarios de ejemplo
        usuarios.put("admin", new Usuarios(1, "Admin", "admin@example.com", "admin"));
        usuarios.put("user", new Usuarios(2, "User", "user@example.com", "user123"));
    }

    public LoginFrame() {
        // Configuración de la ventana
        setTitle("Inicio de Sesión - DUTS Academy");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes del formulario
        JLabel lblUsuario = new JLabel("Usuario:");
        JTextField txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        JPasswordField txtPassword = new JPasswordField();

        JButton btnLogin = new JButton("Iniciar Sesión");
        JButton btnSalir = new JButton("Salir");

        // Añadir componentes al JFrame
        add(lblUsuario);
        add(txtUsuario);
        add(lblPassword);
        add(txtPassword);
        add(new JLabel()); // Espacio vacío
        add(btnLogin);
        add(new JLabel());
        add(btnSalir);

        // Acción del botón de inicio de sesión
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = txtUsuario.getText();
                String contraseña = new String(txtPassword.getPassword());

                Usuarios user = usuarios.get(usuario);
                if (user != null && user.getPassword().equals(contraseña)) {
                    JOptionPane.showMessageDialog(LoginFrame.this, "¡Inicio de sesión exitoso!");
                    dispose(); // Cerrar ventana de login
                    new MenuFrame(user).setVisible(true); // Abrir menú principal
                } else {
                    intentos++;
                    if (intentos >= 3) {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Ha excedido el número de intentos. El sistema se cerrará.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.exit(0); // Cerrar sistema
                    } else {
                        JOptionPane.showMessageDialog(LoginFrame.this, "Usuario o contraseña incorrectos. Intentos restantes: " + (3 - intentos), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Acción del botón salir
        btnSalir.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}

// Clase para el menú principal
class MenuFrame extends JFrame {
    private final Usuarios usuario;

    public MenuFrame(Usuarios usuario) {
        this.usuario = usuario;

        // Configuración de la ventana
        setTitle("Menú Principal - DUTS Academy");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 1, 10, 10));

        // Botones del menú principal
        JButton btnConsultarDUTS = new JButton("Consultar DUTS");
        JButton btnTransferirDUTS = new JButton("Transferir DUTS");
        JButton btnPromedioDUTS = new JButton("Ver Promedio de Transacciones");
        JButton btnEventos = new JButton("Gestionar Eventos");
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");

        // Añadir botones al JFrame
        add(btnConsultarDUTS);
        add(btnTransferirDUTS);
        add(btnPromedioDUTS);
        add(btnEventos);
        add(btnCerrarSesion);

        // Acción: Consultar DUTS
        btnConsultarDUTS.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "DUTS actuales: " + usuario.getCuenta().getSaldo() + " DUTS"));

        // Acción: Transferir DUTS
        btnTransferirDUTS.addActionListener(e -> new TransferFrame(usuario).setVisible(true));

        // Acción: Ver promedio de transacciones
        btnPromedioDUTS.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Promedio de transacciones: " + usuario.getCuenta().calcularPromedio() + " DUTS"));

        // Acción: Gestionar eventos
        btnEventos.addActionListener(e -> new EventoFrame(usuario).setVisible(true));

        // Acción: Cerrar sesión
        btnCerrarSesion.addActionListener(e -> {
            dispose(); // Cerrar menú principal
            new LoginFrame(); // Volver al login
        });

        setVisible(true);
    }
}

// Clase para gestionar eventos
class EventoFrame extends JFrame {
    public EventoFrame(Usuarios usuario) {
        // Configuración de la ventana
        setTitle("Gestionar Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(usuario.getNombre().equals("Admin") ? 4 : 3, 1, 10, 10)); // 4 filas si es admin

        // Botones
        JButton btnVerEventos = new JButton("Ver Eventos");
        JButton btnRegistrarEvento = new JButton("Registrarse en Evento");
        JButton btnCrearEvento = new JButton("Crear Evento");
        JButton btnVolver = new JButton("Volver");

        // Añadir botones al JFrame
        add(btnVerEventos);
        add(btnRegistrarEvento);

        // Solo mostrar "Crear Evento" si es admin
        if (usuario.getNombre().equals("Admin")) {
            add(btnCrearEvento);
        }

        add(btnVolver);

        // Acción: Ver eventos
        btnVerEventos.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Eventos disponibles:\n1. Evento A - Requiere 50 DUTS\n2. Evento B - Requiere 30 DUTS"));

        // Acción: Registrarse en evento
        btnRegistrarEvento.addActionListener(e -> {
            String evento = JOptionPane.showInputDialog(this, "Ingrese el ID del evento para registrarse:");
            JOptionPane.showMessageDialog(this, "Se ha registrado en el evento ID: " + evento);
        });

        // Acción: Crear evento (solo admin)
        btnCrearEvento.addActionListener(e -> {
            String nombreEvento = JOptionPane.showInputDialog(this, "Ingrese el nombre del evento:");
            String descripcion = JOptionPane.showInputDialog(this, "Ingrese la descripción del evento:");
            String montoStr = JOptionPane.showInputDialog(this, "Ingrese la cantidad de DUTS requeridos:");
            try {
                double monto = Double.parseDouble(montoStr);
                JOptionPane.showMessageDialog(this, "Evento '" + nombreEvento + "' creado con éxito.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción: Volver
        btnVolver.addActionListener(e -> dispose());

        setVisible(true);
    }
}

// Clase para la transferencia de DUTS
class TransferFrame extends JFrame {
    public TransferFrame(Usuarios usuario) {
        // Configuración de la ventana
        setTitle("Transferir DUTS");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        // Componentes del formulario
        JLabel lblDestinatario = new JLabel("Usuario destinatario:");
        JTextField txtDestinatario = new JTextField();

        JLabel lblMonto = new JLabel("Cantidad de DUTS:");
        JTextField txtMonto = new JTextField();

        JButton btnTransferir = new JButton("Transferir");
        JButton btnCancelar = new JButton("Cancelar");

        // Añadir componentes al JFrame
        add(lblDestinatario);
        add(txtDestinatario);
        add(lblMonto);
        add(txtMonto);
        add(new JLabel()); // Espacio vacío
        add(btnTransferir);
        add(new JLabel());
        add(btnCancelar);

        // Acción: Transferir
        btnTransferir.addActionListener(e -> {
            String destinatario = txtDestinatario.getText();
            Usuarios usuarioDestino = LoginFrame.usuarios.get(destinatario);

            if (usuarioDestino != null) {
                try {
                    double monto = Double.parseDouble(txtMonto.getText());
                    if (usuario.getCuenta().getSaldo() >= monto) {
                        usuario.getCuenta().setSaldo(usuario.getCuenta().getSaldo() - monto);
                        usuarioDestino.getCuenta().setSaldo(usuarioDestino.getCuenta().getSaldo() + monto);

                        usuario.getCuenta().agregarTransaccion(new Transaccion(-monto, new Date(), "enviar"));
                        usuarioDestino.getCuenta().agregarTransaccion(new Transaccion(monto, new Date(), "recibir"));

                        JOptionPane.showMessageDialog(this, "Transferencia exitosa de " + monto + " DUTS a " + destinatario);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Monto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuario destinatario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción: Cancelar
        btnCancelar.addActionListener(e -> dispose());

        setVisible(true);
    }
}
