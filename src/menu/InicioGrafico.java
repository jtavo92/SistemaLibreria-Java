
package menu;

import com.librerianova.vista.LibreriaVentana;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

public class InicioGrafico {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                LibreriaVentana ventana = new LibreriaVentana();
                ventana.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    null,
                    "Error al iniciar el sistema: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                e.printStackTrace();
            }
        });
    }
}
