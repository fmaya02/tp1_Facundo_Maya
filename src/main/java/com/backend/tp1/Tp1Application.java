package com.backend.tp1;

import com.backend.tp1.entidades.*;
import com.backend.tp1.respositorios.ClienteRepository;
import com.backend.tp1.respositorios.PedidoRepository;
import com.backend.tp1.respositorios.ProductoRepository;
import com.backend.tp1.respositorios.RubroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class Tp1Application {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RubroRepository rubroRepository;

	@Autowired
	private ProductoRepository productoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	public static void main(String[] args) {

		SpringApplication.run(Tp1Application.class, args);
	}

	@Bean
	CommandLineRunner init(ClienteRepository clienteRepository, RubroRepository rubroRepository, ProductoRepository productoRepository, PedidoRepository pedidoRepository){
		return args -> {
		System.out.println("------------ESTOY FUNCIONANDO-------------");
		//Rubro "Sushi"
		Rubro rubro1 = Rubro.builder()
				.denominacion("Sushi")
				.productos(new ArrayList<Producto>())
				.build();

		//Productos que pertenecen al rubro "Sushi"
		Producto producto1 = Producto.builder()
				.denominacion("Roll Buenos Aires")
				.precioVenta(3000)
				.stockMinimo(20)
				.build();

		Producto producto2 = Producto.builder()
				.denominacion("Roll Corona Real")
				.precioVenta(5000)
				.stockMinimo(25)
				.build();

		Producto producto3 = Producto.builder()
				.denominacion("Roll Caliente")
				.precioVenta(4250)
				.stockMinimo(40)
				.build();

		//Añadir productos al rubro que corresponden
		rubro1.addProducto(producto1);
		rubro1.addProducto(producto2);
		rubro1.addProducto(producto3);

		//Persistir rubro
		rubroRepository.save(rubro1);

		//Crear clientes
		Cliente cli1 = Cliente.builder()
				.nombre("Pedro")
				.apellido("Martinez")
				.telefono("154784")
				.email("pedrmarti@gmail.com")
				.pedidos(new ArrayList<Pedido>())
				.domicilios(new ArrayList<Domicilio>())
				.build();

		Cliente cli2 = Cliente.builder()
				.nombre("Ksante")
				.apellido("Empireo")
				.telefono("154784")
				.email("ksantempireo@gmail.com")
				.pedidos(new ArrayList<Pedido>())
				.domicilios(new ArrayList<Domicilio>())
				.build();

		//Crear domicilios
		Domicilio dom1 = Domicilio.builder()
				.calle("San Martin")
				.numero("485")
				.localidad("Mendoza")
				.build();

		Domicilio dom2 = Domicilio.builder()
				.calle("Libertad")
				.numero("1356")
				.localidad("Shurima")
				.build();

		//Asignar domicilios a los clientes
		cli1.addDomicilio(dom1);
		cli2.addDomicilio(dom2);

		//Persistir clientes
		clienteRepository.save(cli1);
		clienteRepository.save(cli2);

		//Creacion de pedido1
		Pedido pedido1 = Pedido.builder()
				.estado("Iniciado")
				.tipoEnvio("A domicilio")
				.fecha(new Date())
				.detalles(new ArrayList<DetallePedido>())
				.build();

		Producto prodRecuperado3 = productoRepository.findById(producto3.getId()).orElse(null);
		//Detalle de pedido1
		DetallePedido detalle1 = DetallePedido.builder()
				.cantidad(5)
				.producto(prodRecuperado3)
				.build();

		detalle1.setSubtotal(detalle1.getCantidad() * prodRecuperado3.getPrecioVenta());
		pedido1.addDetallePedido(detalle1);
		pedido1.setTotal(detalle1.getSubtotal());

		//Creacion de pedido2
		Pedido pedido2 = Pedido.builder()
				.estado("Iniciado")
				.tipoEnvio("Retirar")
				.fecha(new Date())
				.detalles(new ArrayList<DetallePedido>())
				.build();
		Producto prodRecuperado1 = productoRepository.findById(producto1.getId()).orElse(null);
		Producto prodRecuperado2 = productoRepository.findById(producto2.getId()).orElse(null);
		//Detalles de pedido2
		DetallePedido detalle2 = DetallePedido.builder()
				.cantidad(3)
				.producto(prodRecuperado1)
				.build();

		DetallePedido detalle3 = DetallePedido.builder()
				.cantidad(2)
				.producto(prodRecuperado2)
				.build();

		detalle2.setSubtotal(detalle2.getCantidad() * prodRecuperado1.getPrecioVenta());
		detalle3.setSubtotal(detalle3.getCantidad() * prodRecuperado2.getPrecioVenta());
		pedido2.addDetallePedido(detalle2);
		pedido2.addDetallePedido(detalle3);
		pedido2.setTotal(detalle2.getSubtotal() + detalle3.getSubtotal());

		//Creacion de facturas
		Factura factura1 = Factura.builder()
				.numero(1)
				.fecha(new Date())
				.total((int)pedido1.getTotal())
				.build();
		Factura factura2 = Factura.builder()
				.numero(2)
				.fecha(new Date())
				.total((int)pedido2.getTotal())
				.build();

		//Setear facturas en los pedidos
		pedido1.setFactura(factura1);
		pedido2.setFactura(factura2);

		//Añadir pedidos a los clientes
		cli1.addPedido(pedido2);
		cli2.addPedido(pedido1);

		//Persistir pedido
		pedidoRepository.save(pedido1);
		pedidoRepository.save(pedido2);

		//Persistir clientes nuevamente, ahora que tienen los pedidos
		clienteRepository.save(cli1);
		clienteRepository.save(cli2);
		};
	}

}
