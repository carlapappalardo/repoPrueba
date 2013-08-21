package dom.utilidades;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.RegEx; 
import org.apache.isis.applib.util.TitleBuffer;


import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;


@javax.jdo.annotations.PersistenceCapable(identityType=IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY)
@javax.jdo.annotations.Query(name="listado", language="JDQL",value="SELECT * FROM dom.categoria.Categoria")
@javax.jdo.annotations.Version(strategy=VersionStrategy.VERSION_NUMBER, column="VERSION")

public class Categoria {
	// public static enum aireAcondicionado


	public String title()
	{
	final TitleBuffer buf = new TitleBuffer();
	        buf.append(getCategorias());        
	        return buf.toString();
	}	
	
	private String categorias;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence="1")
	public String getCategorias()
	{
		return categorias;
	}

	public void setCategorias(String categorias)
	{
		this.categorias=categorias;
	}

	 private int cantPuertas ;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence="2")
	public int getCantPuertas()
	{
	return cantPuertas;
	}

	public void setCantPuertas(int cantPuertas)
	{
	this.cantPuertas=cantPuertas;
	}
	     
	//-----------------------------------------
	private int cantPlazas ;

	@RegEx(validation = "\\w[@&:\\-\\,\\.\\+ \\w]*")
	@MemberOrder(sequence="3")
	public int getCantPlazas()
	{
	return cantPlazas;
	}

	public void setCantPlazas(int cantPlazas)
	{
	this.cantPlazas=cantPlazas;
	}
	 //--------------------------------------------


	// {{
	@SuppressWarnings("unused")
	private DomainObjectContainer container;

	public void setDomainObjectContainer(final DomainObjectContainer container)
	   {
	     this.container = container;
	   }
	  // }}
	    

}
