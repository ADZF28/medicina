import { Component } from '@angular/core';
import { ServiciosService } from 'src/app/Servicios/servicios.service';

@Component({
  selector: 'app-tipo',
  templateUrl: './tipo.component.html',
  styleUrls: ['./tipo.component.scss']
})
export class TipoComponent {

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
  tipo: string = ''; 
  estado: number = 0;
  nombre: string = '';
  
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
    this.obtenerTipos();
  }

  obtenerTipos(){
    this.service.ObtenerTipos().then(data =>{
      this.items = data;
      console.log("DATOS obtenerTipos: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  obtenerTiposFiltro(){
    let dataB = {
      "nombre": this.nombre,
    }
    this.service.FiltrarTipo(dataB).then(data =>{
      this.items = data;
      console.log("DATOS obtenerTiposFiltro: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  Cargar(item:any){
    this.estado = 1;
    localStorage.setItem("id", item.idTipo);     
    this.tipo = item.nombre;
  }

  cancelar(){
    this.estado = 0;
    this.limpiar();
  }


  limpiar(){
    this.tipo = "";
    this.onReset1();
  }

  EditarTipo(){
    let id = localStorage.getItem("id");
    let dataB = {
      "nombre": this.tipo,
    }
    this.service.EditarTipo(id, dataB).then(response =>{
      if(response != null){
        this.estado = 0;
        this.limpiar();
        this.obtenerTipos();
        this.toggleToast();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }

  IngresarTipo(){
    let data={
      "nombre": this.tipo
    }
    this.service.IngresarTipo(data).then(response =>{
      if(response != null){
        this.toggleToast();
        this.obtenerTipos();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }


  eliminar(id:string){
    this.service.EliminarTipo(id).then(response =>{
        this.obtenerTipos();
      }).catch(error =>{
        this.obtenerTipos();
        console.log(error);
      });
     
  }

  onSubmit1() {
    this.customStylesValidated = true;
    this.IngresarTipo();
    //this.onResetDismiss();
  }

  onReset1() {
    this.customStylesValidated = false;
    console.log('Reset... 1');
  }
  
}
