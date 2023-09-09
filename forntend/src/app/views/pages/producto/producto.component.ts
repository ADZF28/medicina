import { Component } from '@angular/core';
import { ServiciosService } from '../../../Servicios/servicios.service';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-producto',
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.scss']
})
export class ProductoComponent {

  constructor(private service:ServiciosService) { }

  customStylesValidated = false;
  browserDefaultsValidated = false;
  tooltipValidated = false;

  tipos: any = [];
  grupos: any = [];
  items:any = [];
  pageSize: number = 10; // Número de elementos por página
  p: number = 1; // Página actual
  position = 'top-end';
  visible = false;
  percentage = 0;
  visibleAlert = true;
  estado: number = 0;

  //VAriables para post
  nombre_producto: string = ''; grupo: string = ''; tipo: string = ''; stock: number = 0; cantidad: number = 0;
  observacion: string = ''; fecha: Date = new Date();

  //BUSCAR
  nombre_productoB: string = ''; grupoB: string = ''; tipoB: string = '';
  
  onVisibleChangeAlert(eventValue: boolean) {
    this.visibleAlert = eventValue;
  }

  onResetDismiss() {
    this.visibleAlert = true;
  }

  toggleToast() {
    this.visible = !this.visible;
  }

  onVisibleChange($event: boolean) {
    this.visible = $event;
    this.percentage = !this.visible ? 0 : this.percentage;
  }

  onTimerChange($event: number) {
    this.percentage = $event * 25;
  }

  ngOnInit(): void { 
    this.obtenerProductos();
    this.obtenerGrupos();
    this.obtenerTipos();
  }

  obtenerProductos(){
    this.service.ObtenerProductos().then(data =>{
      this.items = data;
      console.log("DATOS obtenerProductos: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  obtenerGrupos(){
    this.service.ObtenerGrupos().then(data =>{
      this.grupos = data;
      console.log("DATOS obtenerGrupos: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  obtenerTipos(){
    this.service.ObtenerTipos().then(data =>{
      this.tipos = data;
      console.log("DATOS obtenerTipos: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  IngresarProducto(){
    let data={
      "grupo": this.grupo,
      "tipo": this.tipo,
      "nombre": this.nombre_producto,
      "stock": this.stock,
      "cantidadStock": this.cantidad,
      "observacion": this.observacion,
      "fechaVencimiento": this.fecha
    }
    this.service.IngresarProductos(data).then(response =>{
      if(response != null){
        this.limpiar();
        this.toggleToast();
        this.obtenerProductos();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }

  cancelar(){
    this.estado = 0;
    this.limpiar();
  }

  EditarProducto(){
    let id = localStorage.getItem("id");
    let data={
      "grupo": this.grupo,
      "tipo": this.tipo,
      "nombre": this.nombre_producto,
      "stock": this.stock,
      "cantidadStock": this.cantidad,
      "observacion": this.observacion,
      "fechaVencimiento": this.fecha
    }
    this.service.EditarProductos(id, data).then(response =>{
      if(response != null){
        this.estado = 0;
        this.limpiar();
        this.toggleToast();
        this.obtenerProductos();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }

  Cargar(item:any){
    this.estado = 1;
    localStorage.setItem("id", item.idProducto);     
    this.grupo = item.grupo;
    this.tipo = item.tipo;
    this.nombre_producto = item.nombre;
    this.stock = item.stock;
    this.cantidad = item.cantidadStock;
    this.observacion = item.observacion;
    const f = new Date(item.fechaVencimiento); 
    this.fecha = f;
  }

  limpiar(){
    this.grupo = "";
    this.tipo = "";
    this.nombre_producto = "";
    this.stock = 0;
    this.cantidad = 0; 
    this.observacion = "";
    this.fecha = new Date();
    this.onReset1();
  }

  obtenerProductosFiltro(){
    let dataB = {
      "grupo": this.grupoB,
      "tipo": this.tipoB,
      "nombre": this.nombre_productoB,
    }
    this.service.FiltrarProductos(dataB).then(data =>{
      this.items = data;
      console.log("DATOS obtenerProductosBuscar: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  sumar(id:string){
    let data={
      "cantidad": 1
    }
    this.service.Sumar(id, data).then(response =>{
        this.obtenerProductos();
      }).catch(error =>{
        this.obtenerProductos();
        console.log(error);
      });
      
  }

  restar(id:string){
    let data={
      "cantidad": 1
    }
    this.service.Restar(id, data).then(response =>{
        this.obtenerProductos();
      }).catch(error =>{
        this.obtenerProductos();
        console.log(error);
      });
     
  }

  eliminar(id:string){
    this.service.EliminarProducto(id).then(response =>{
        this.obtenerProductos();
      }).catch(error =>{
        this.obtenerProductos();
        console.log(error);
      });
     
  }

  onSubmit1() {
    this.customStylesValidated = true;
    this.IngresarProducto();
    //this.onResetDismiss();
    console.log("Valor: ", this.nombre_producto);
  }

  onReset1() {
    this.customStylesValidated = false;
    console.log('Reset... 1');
  }
}
