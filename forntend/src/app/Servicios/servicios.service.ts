import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  constructor(private http: HttpClient) { }

  ObtenerProductos() {
    let  url = 'http://localhost:8000/productos/obtener'
    return new Promise ((resolve, reject) => {
      this.http.get(url).subscribe(res => {
        resolve(res);
      }, error => {
        reject(error);
      });
    });
  }

  IngresarProductos(data:any){
    let  url = 'http://localhost:8000/productos/guardar';
    return new Promise ((resolve, reject) => {
      this.http.post(url,data).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  EditarProductos( id:any, data:any){
    let  url = 'http://localhost:8000/productos/actualizar/'+ id;
    return new Promise ((resolve, reject) => {
      this.http.put(url,data).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  FiltrarProductos(data:any) {
    let  url = 'http://localhost:8000/productos/buscarProducto';
    var formData = new FormData();
    formData.append('nombre', data.nombre);
    formData.append('grupo', data.grupo);
    formData.append('tipo', data.tipo);
    return new Promise ((resolve, reject) => {
      this.http.post(url, formData ).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  Sumar(id:string, data:any){
    let  url = 'http://localhost:8000/productos/sumar/'+id;
    var formData = new FormData();
    formData.append('cantidad', data.cantidad);
    return new Promise ((resolve, reject) => {
      this.http.put(url, formData).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  Restar(id:string, data:any){
    let  url = 'http://localhost:8000/productos/restar/'+id;
    var formData = new FormData();
    formData.append('cantidad', data.cantidad);
    return new Promise ((resolve, reject) => {
      this.http.put(url, formData).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  EliminarProducto(id:string) {
    let  url = 'http://localhost:8000/productos/eliminar/'+id;
    return new Promise ((resolve, reject) => {
      this.http.delete(url).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  ObtenerGrupos() {
    let  url = 'http://localhost:8000/grupos/obtener'
    return new Promise ((resolve, reject) => {
      this.http.get(url).subscribe(res => {
        resolve(res);
      }, error => {
        reject(error);
      });
    });
  }

  EliminarGrupos(id:string) {
    let  url = 'http://localhost:8000/grupos/eliminar/'+id;
    return new Promise ((resolve, reject) => {
      this.http.delete(url).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  IngresarGrupo(data:any){
    let  url = 'http://localhost:8000/grupos/guardar';
    return new Promise ((resolve, reject) => {
      this.http.post(url,data).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  EditarGrupo(id:any, data:any){
    let  url = 'http://localhost:8000/grupos/actualizar/'+ id;
    return new Promise ((resolve, reject) => {
      this.http.put(url,data).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  FiltrarGrupo(data:any) {
    let  url = 'http://localhost:8000/grupos/buscarNombre';
    var formData = new FormData();
    formData.append('nombre', data.nombre);
    return new Promise ((resolve, reject) => {
      this.http.post(url, formData ).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  ObtenerTipos() {
    let  url = 'http://localhost:8000/tipos/obtener'
    return new Promise ((resolve, reject) => {
      this.http.get(url).subscribe(res => {
        resolve(res);
      }, error => {
        reject(error);
      });
    });
  }


  EliminarTipo(id:string) {
    let  url = 'http://localhost:8000/tipos/eliminar/'+id;
    return new Promise ((resolve, reject) => {
      this.http.delete(url).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }

  IngresarTipo(data:any){
    let  url = 'http://localhost:8000/tipos/guardar';
    return new Promise ((resolve, reject) => {
      this.http.post(url,data).subscribe(res => {
        resolve(res);{
        }
      }, error => {
        reject(error);
      });
    });
  }


}
