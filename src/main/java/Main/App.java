package Main;

import Entidades.Janelas;
import Models.JanelasDAO;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.janelas.Janela;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Looca looca = new Looca();
        JanelaGrupo janelas = looca.getGrupoDeJanelas();

        // Janelas
        List<Janela> listaDeJanelas = janelas.getJanelas();
        Long idJanela;
        String titulo;
        Long pidJanela;
        Integer totalJanelas = janelas.getTotalJanelas();
        System.out.println("------ Janelas ------");

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new FileWriter("log.txt", true));

            boolean algumaJanelaProibidaAberta = true;
            while (algumaJanelaProibidaAberta) {
                algumaJanelaProibidaAberta = false;
                for (Janela janela : listaDeJanelas) {
                    idJanela = janela.getJanelaId();
                    titulo = janela.getTitulo();
                    pidJanela = janela.getPid();

                    Entidades.Janelas janela00 = new Janelas(idJanela, titulo, pidJanela, totalJanelas);

                    if (titulo != null && !titulo.isEmpty()) {
                        System.out.println("Titulo: " + titulo);
                        System.out.println("Pid: " + pidJanela);
                        if (titulo.startsWith("ChatGPT") || titulo.startsWith("Riot") || titulo.startsWith("Whatsapp") || titulo.endsWith("Discord")) {
                            System.out.println("O aluno está usando: " + titulo);
                            algumaJanelaProibidaAberta = true;
                            writer.println("Janela do " + titulo + " com PID " + pidJanela + " encerrada às: " + java.time.LocalDateTime.now());
                            try {
                                Runtime.getRuntime().exec("taskkill /f /pid " + pidJanela);
                                System.out.println("Janela do " + titulo + " com PID " + pidJanela + " encerrada com sucesso.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        JanelasDAO.cadastrarJanelas(janela00);
                    }
                }
                algumaJanelaProibidaAberta = false;
            }
            System.out.println("Todas as janelas proibidas foram fechadas.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
