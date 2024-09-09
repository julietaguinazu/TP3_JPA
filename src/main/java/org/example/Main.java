package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("en marcha Alberto");



        try{
            entityManager.getTransaction().begin();

            Factura factura1 = Factura.builder().numero(12).fecha("10/08/2024").build();
            Domicilio dom = Domicilio.builder().nombrecalle("San Martin").numero(1222).build();
            Cliente cliente= Cliente.builder().nombre("Pablo").apellido("Muñoz").dni(42793521).build();
            cliente.setDomicilio(dom);
            dom.setCliente(cliente);
            factura1.setCliente(cliente);

            Categoria perecederos= Categoria.builder().denominacion("perecederos").build();
            Categoria lacteos= Categoria.builder().denominacion("lacteos").build();
            Categoria limpieza= Categoria.builder().denominacion("limpieza").build();

            Articulo articulo1= Articulo.builder().cantidad(200).denominacion("Yogurt Ser Sabor Frutilla").precio(20).build();
            Articulo articulo2= Articulo.builder().cantidad(300).denominacion("Detergente Magristral").precio(80).build();

            articulo1.getCategorias().add(perecederos);
            articulo1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(articulo1);
            perecederos.getArticulos().add(articulo1);

            articulo1.getCategorias().add(limpieza);
            limpieza.getArticulos().add(articulo2);

            DetalleFactura det1= DetalleFactura.builder().cantidad(2).subtotal(40).build();

            articulo1.getDetalle().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2= DetalleFactura.builder().cantidad(1).subtotal(80).build();
            articulo2.getDetalle().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(120);
            entityManager.persist(factura1);


            entityManager.flush();
            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
        }
        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}
