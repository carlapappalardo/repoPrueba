package dom.autos;

import java.util.Date;
import java.util.List;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.annotation.ActionSemantics;
import org.apache.isis.applib.annotation.ActionSemantics.Of;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.filter.Filter;


import com.google.common.base.Objects;


import dom.autos.Auto;
import dom.autos.Auto.Estado;
import dom.autos.Auto.Seguro;
import dom.autos.Auto.TipoCombustible;
import dom.utilidades.Marca;
 

@Named("Flota")
public class AutoServicio extends AbstractFactoryAndRepository {
	
	// {{ 
	@MemberOrder(sequence = "1") // Carga de Autos
	public Auto CargarAuto(
		@Named("Patente") String patente,
		@Named("Marca") Marca marca, 
		@Named("Modelo") String modelo, 
		@Named("Año") int ano,
		@Named("Color") String color,
		@Named("Kilometraje") int kms,
		@Named("Capacidad Baul (lt)") int baul,
		@Named("Tipo de Combustible") TipoCombustible combustible,
		@Named("Estado de Alquiler") Estado estado,
		@Named("Fecha de Compra") Date fechaCompra,
		@Named("Compañía de Seguro")Seguro seguro,
		@Named("Activo")boolean activo) { 
		final String ownedBy = currentUserName();
	    return elAuto(patente,marca,modelo,ano,color,kms,baul,combustible,estado,fechaCompra,seguro,ownedBy,activo); 
	}
	// }}
	
	// {{
	@Hidden // for use by fixtures
	public Auto elAuto(
		String patente,
		Marca marca, 
		String modelo,
		int ano,
		String color,
		int kms, 
		int baul,
		TipoCombustible combustible,
		Estado estado,
		Date fechaCompra,
		Seguro seguro,
		String userName,
		boolean activo) {
	final Auto auto = newTransientInstance(Auto.class);
		auto.setPatente(patente);
		auto.setMarca(marca);
		auto.setModelo(modelo);
		auto.setAno(ano);
		auto.setColor(color);
		auto.setKilometraje(kms);
		auto.setCapacidadBaul(baul);
		auto.setTipoCombustible(combustible);
		auto.setEstado(estado);
		auto.setFechaCompra(fechaCompra);
		auto.setSeguro(seguro);
		auto.setOwnedBy(userName);
		auto.setActivo(activo);
		marca.agregarListaAutos(auto);
		
		persistIfNotAlready(auto);
		return auto;
    }
	// }}
	
	// {{ 
//	@MemberOrder(sequence = "2") // Listado de Autos
//	public List<Auto> ListarAutos() {
//		//final String currentUser = currentUserName();
//		final boolean activo=true;
//		final List<Auto> autos=allMatches(Auto.class, Auto.thoseActivos());
//		//final List<Auto> autos=allMatches(Auto.class, Auto.soloActivos(activo));
//				
//		 for(Auto au:autos){
//			 
//			 System.out.println(au.getActivo()+"  "+au.getPatente().toString());
//			 
//		 }
//				//allInstances(Auto.class); 
//				//allMatches(Auto.class,listado_autos);
//		return autos; 
//	}
//	// }}
	
	// {{ Helpers
	protected boolean ownedByCurrentUser(final Auto t) {
	    return Objects.equal(t.getOwnedBy(), currentUserName());
	}
	protected String currentUserName() {
	    return getContainer().getUser().getName();
	}
	// }}
	
	
    // {{ complete (action)
    @ActionSemantics(Of.SAFE)
    @MemberOrder(sequence = "2")
    public List<Auto> AutosActivos() {
        List<Auto> items = doComplete();
        if(items.isEmpty()) {
            getContainer().informUser("No hay autos activos :-(");
        }
        return items;
    }

    protected List<Auto> doComplete() {
        return allMatches(Auto.class, new Filter<Auto>() {
            @Override
            public boolean accept(final Auto t) {
                return ownedByCurrentUser(t) && t.getActivo();
            }
        });
    }
    // }}
	
}