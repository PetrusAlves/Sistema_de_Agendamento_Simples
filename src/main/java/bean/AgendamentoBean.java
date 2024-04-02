package bean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import dao.AgendaDAO;
import entidades.Agenda;
import util.JPAUtil;

@ManagedBean
public class AgendamentoBean {

	private Agenda agenda = new Agenda();

	private Map<Integer, Boolean> selectedItems = new HashMap<>();

	private List<Agenda> lista;

	
	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public List<Agenda> getLista() {
		if (lista == null) {
			lista = AgendaDAO.listar();
		}
		return lista;
	}

	public Map<Integer, Boolean> getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(Map<Integer, Boolean> selectedItems) {
		this.selectedItems = selectedItems;
	}
	
	public void salvar() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			AgendaDAO.salvar(agenda);
			agenda = new Agenda();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Parabens!",
					"Sua Consulta foi Agendada com Sucesso! "));

		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu ao Agendar a Consulta! "));
			e.printStackTrace();
		}
	}

	// Método para excluir os itens selecionados
	public void excluirSelecionados() {
		FacesContext context = FacesContext.getCurrentInstance();
		List<Agenda> lista = getLista();
		try {
			for (Map.Entry<Integer, Boolean> entry : selectedItems.entrySet()) {
				Integer id = entry.getKey();
				Boolean selected = entry.getValue();
				if (selected) {
					// Excluir o item com o ID correspondente
					AgendaDAO.excluir(new Agenda(id, null, null, null));
					// Remover o item da lista
					for (Agenda agendamento : lista) {
						if (agendamento.getId().equals(id)) {
							lista.remove(agendamento);
							break;
						}
					}
					// Exibir mensagem de sucesso
					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_WARN, "Sucesso!", "Consulta excluída com sucesso."));
				}
			}
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao salvar as alterações."));
			e.printStackTrace();
		}
		// Limpar os itens selecionados
		selectedItems.clear();
	}
	public void updateConsultaSelecionada() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    EntityManager entityManager = null;

	    try {
	        entityManager = JPAUtil.criarEntityManager();
	        entityManager.getTransaction().begin();

	        for (Map.Entry<Integer, Boolean> entry : selectedItems.entrySet()) {
	            Integer id = entry.getKey();
	            Boolean selected = entry.getValue();
	            if (selected) {
	                Agenda agendamento = entityManager.find(Agenda.class, id);
	                if (agendamento != null) {
	                    // Aplicar as alterações feitas na página à entidade Agenda correspondente
	                    agendamento.setPaciente(getAgenda().getPaciente());
	                    agendamento.setMedico(getAgenda().getMedico());
	                    agendamento.setDataHoraConsulta(getAgenda().getDataHoraConsulta());

	                    entityManager.merge(agendamento);
	                    // Exibir mensagem de sucesso
	                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
	                            "Item atualizado com sucesso."));
	                }
	            }
	        }

	        entityManager.getTransaction().commit();
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
	                "Alterações salvas com sucesso."));
	    } catch (Exception e) {
	        if (entityManager != null && entityManager.getTransaction().isActive()) {
	            entityManager.getTransaction().rollback();
	        }
	        context.addMessage(null,
	                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao salvar as alterações."));
	        e.printStackTrace();
	    } finally {
	        if (entityManager != null) {
	            entityManager.close();
	        }
	    }

	    // Limpar os itens selecionados
	    selectedItems.clear();
	}

	
	
	public int quantidadeAgendamentos() {
		int contar = 0;
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			for (Agenda contagem : lista) {
				contar++;
			}
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Quantidade de Agendamento!", "" + contar));
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro ao salvar as alterações."));
			e.printStackTrace();
		}
		return contar;
	}

	private boolean isDataDisponivel(Date date) {
		// Lógica para determinar se a data está disponível ou ocupada
		// Verifica se há consultas agendadas para a data especificada
		List<Agenda> consultas = AgendaDAO.listarPorData(date);
		return consultas.isEmpty(); // Retorna true se não houver consultas agendadas, false caso contrário
	}
}
