package com.ciberfarma.app;

import java.util.List;

import com.ciberfarma.model.Tipo;
import com.ciberfarma.model.Usuario;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Demo07 {
	//Listado de los tipos de usuario y sus usuarios
	public static void main(String[] args) {
		EntityManagerFactory conexion = Persistence.createEntityManagerFactory("jpa_sesion02");
		jakarta.persistence.EntityManager em = conexion.createEntityManager();	
		
		// sql select * from tb_tipos
		// jpql select from Tipo t
		
		
		List<Tipo> list_tipos = em.createNativeQuery("select * from tb_tipos", Tipo.class).getResultList();
		for (Tipo t : list_tipos) {
			System.out.println("Tipo de usuario. " + t.getDescripcion());	
			System.out.println("--------------------------------");
			for (Usuario u : t.getList_usuarios()) {
				System.out.println("Nombre: "+ u.getNom_usua());
				System.out.println("Apellido: " + u.getApe_usua());
				System.out.println("------------------------------");
			}
		}
		
		List<Tipo> list_tipos1 = em.createQuery("select u from Tipo u").getResultList();
		for (Tipo t : list_tipos1) {
			System.out.println("Tipo de usuario: " + t.getDescripcion());
			System.out.println("--------------------------------");
			for (Usuario u : t.getList_usuarios()) {
				System.out.println("Nombre: "+ u.getNom_usua());
				System.out.println("Apellido: " + u.getApe_usua());
				System.out.println("------------------------------");
			}
		}
		
	}
}
