import { Component } from '@angular/core';
import { ServiciosService } from 'src/app/Servicios/servicios.service';

@Component({
  selector: 'app-grupo',
  templateUrl: './grupo.component.html',
  styleUrls: ['./grupo.component.scss']
})
export class GrupoComponent {

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
  grupo: string = ''; 
  nombre: string = ''; 
  estado: number = 0;
  
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
    this.obtenerGrupos();
  }

  obtenerGrupos(){
    this.service.ObtenerGrupos().then(data =>{
      this.items = data;
      console.log("DATOS obtenerGrupos: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  obtenerGruposFiltro(){
    let dataB = {
      "nombre": this.nombre,
    }
    this.service.FiltrarGrupo(dataB).then(data =>{
      this.items = data;
      console.log("DATOS obtenerProductosBuscar: ", data)
    }).catch(error =>{
      console.log(error);
    });
  }

  Cargar(item:any){
    this.estado = 1;
    localStorage.setItem("id", item.idGrupo);     
    this.grupo = item.nombre;
  }

  cancelar(){
    this.estado = 0;
    this.limpiar();
  }


  limpiar(){
    this.grupo = "";
    this.onReset1();
  }

  EditarGrupo(){
    let id = localStorage.getItem("id");
    let dataB = {
      "nombre": this.grupo,
    }
    this.service.EditarGrupo(id, dataB).then(response =>{
      if(response != null){
        this.estado = 0;
        this.limpiar();
        this.obtenerGrupos();
        this.toggleToast();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }

  IngresarGrupo(){
    let data={
      "nombre": this.grupo
    }
    this.service.IngresarGrupo(data).then(response =>{
      if(response != null){
        this.toggleToast();
        this.obtenerGrupos();
      }
      }).catch(error =>{
        console.log(error);
      });
     
  }


  eliminar(id:string){
    this.service.EliminarGrupos(id).then(response =>{
        this.obtenerGrupos();
      }).catch(error =>{
        this.obtenerGrupos();
        console.log(error);
      });
     
  }

  onSubmit1() {
    this.customStylesValidated = true;
    this.IngresarGrupo();
    //this.onResetDismiss();
  }

  onReset1() {
    this.customStylesValidated = false;
    console.log('Reset... 1');
  }
}
