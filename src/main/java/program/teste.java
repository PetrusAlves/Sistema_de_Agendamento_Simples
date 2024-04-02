package program;

import dao.AgendaDAO;
import entidades.Agenda;

public class teste {

	public static void main(String[] args) {
		
		Agenda agenda = new Agenda();
		
		agenda.setId(1);
		AgendaDAO.excluir(agenda);
		
		System.out.println(AgendaDAO.listar());

	}

}
