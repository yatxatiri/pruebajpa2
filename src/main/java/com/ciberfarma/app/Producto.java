package com.ciberfarma.app;

import com.ciberfarma.model.Categorias;
import com.ciberfarma.model.Proveedor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_productos")
public class Producto {
	@Id
	private String id_prod;
	private String des_prod;
	private int   stk_prod;
	private double pre_prod;
	private int est_prod;
	
	@ManyToOne
	@JoinColumn(name = "idproveedor", insertable = false, updatable = false)
	private Proveedor proveedor;
	
	@ManyToOne
	@JoinColumn(name = "idcategoria", insertable = false, updatable = false)
	private Categorias categoria;
}
