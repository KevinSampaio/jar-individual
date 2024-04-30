package Main;

import Entidades.Janelas;
import Models.JanelasDAO;
import com.github.britooo.looca.api.core.Looca;
import com.github.britooo.looca.api.group.janelas.JanelaGrupo;
import com.github.britooo.looca.api.group.janelas.Janela;

import java.util.List;


public class App {
    public static void main(String[] args) {

        Looca looca = new Looca();

        JanelaGrupo janelas = looca.getGrupoDeJanelas();

        // Janelas
        List<Janela> janela1 = janelas.getJanelas();
        Long idJanela;
        String titulo;
        Long pidJanela;
        Integer totalJanelas = janelas.getTotalJanelas();
        System.out.println("------ Janelas ------");

        for (Janela janela : janela1) {
            idJanela = janela.getJanelaId();
            titulo = janela.getTitulo();
            pidJanela = janela.getPid();

            Entidades.Janelas janela00 = new Janelas(idJanela, titulo, pidJanela, totalJanelas);

            if (titulo != null && !titulo.isEmpty()) {
                System.out.println("Titulo: " + titulo);
                System.out.println("Pid: " + pidJanela);
                if (titulo.startsWith("ChatGPT")) {
                    System.out.println("O aluno está usando o ChatGPT");
                    break;
                }
                JanelasDAO.cadastrarJanelas(janela00);
            }


        }
    }
}
