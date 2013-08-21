package dom.utilidades;

 
import java.util.List;

import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;


import org.apache.isis.applib.filter.Filter;
import org.apache.isis.applib.AbstractFactoryAndRepository;

import com.google.common.base.Objects;

import dom.utilidades.Categoria;

@Named("Utilidades")
public class UtilidadesServicio extends AbstractFactoryAndRepository {

	//{{ Marcas
	@MemberOrder(sequence = "1") // Carga de Marca	
	public Marca CargarMarca(@Named("Marca") String marca) { 
		final String ownedBy = currentUserName();
		return laMarca(marca, ownedBy); 
	}
	// }}
	
	// {{	
	@Hidden // for use by fixtures
	public Marca laMarca(
		String marca,
		String userName) {
	final Marca aux = newTransientInstance(Marca.class);
		aux.setNombre(marca);
		aux.setOwnedBy(userName);
		
		persist(aux);
		return aux;
	}
	// }}
	
	// {{		
	@MemberOrder(sequence = "2") // Listado de Marca 
	public List<Marca> ListarMarcas() {
		final List<Marca> aux= allInstances(Marca.class);
		return aux; 
    }
	// }}
	
	// {{ 
	@Hidden    
	public List<Marca> autoComplete(final String marcas) {
		return allMatches(Marca.class, new Filter<Marca>() {
		@Override
		public boolean accept(final Marca t) {		
		return ownedByCurrentUser(t) && t.getNombre().contains(marcas); 
		}
	  });				
	}
	// }}
	
	/*
	// {{ Listado de Autos filtrado por Marcas 
	@NotInServiceMenu
	public List<Auto> AutosPorMarca(final Marca lista) {
		return allMatches(Auto.class, new Filter<Auto>() {
		@Override
		public boolean accept(Auto t){
		return  lista.equals(t.getMarca());
		}
	  });
	}
	// }} */
		
	// {{ Categoria
	@MemberOrder(sequence = "3") // Listado de Categoria
	public List<Categoria> ListarCategorias(){ 
		final List<Categoria> categoria= allInstances(Categoria.class);
		return categoria;
	}
	// }}s
	
	// {{ Helpers
	protected boolean ownedByCurrentUser(final Marca t)	{
		return Objects.equal(t.getOwnedBy(), currentUserName()); 
	}
	protected String currentUserName(){
		return getContainer().getUser().getName(); 
	}
	// }}	
}
