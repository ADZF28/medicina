import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Page404Component } from './page404/page404.component';
import { Page500Component } from './page500/page500.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProductoComponent } from './producto/producto.component';
import { GrupoComponent } from './grupo/grupo.component';
import { TipoComponent } from './tipo/tipo.component';

const routes: Routes = [
  {
    path: '404',
    component: Page404Component,
    data: {
      title: 'Page 404'
    }
  },
  {
    path: '500',
    component: Page500Component,
    data: {
      title: 'Page 500'
    }
  },
  {
    path: 'login',
    component: LoginComponent,
    data: {
      title: 'Login Page'
    }
  },
  {
    path: 'register',
    component: RegisterComponent,
    data: {
      title: 'Register Page'
    }
  },
  {
    path: 'productos',
    component: ProductoComponent,
    data: {
      title: 'Productos'
    }
  },
  {
    path: 'grupos',
    component: GrupoComponent,
    data: {
      title: 'Grupos'
    }
  },
  {
    path: 'tipos',
    component: TipoComponent,
    data: {
      title: 'Tipos'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PagesRoutingModule {
}
