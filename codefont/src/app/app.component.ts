import { Component } from '@angular/core';
import { Platform } from 'ionic-angular';
import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';

import { TabsPage } from '../pages/tabs/tabs';

import { DatabaseProvider } from '../providers/database/database'

@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage:any = null //TabsPage;

  constructor(
    platform: Platform, 
    statusBar: StatusBar, 
    splashScreen: SplashScreen,
    databaseProvider: DatabaseProvider) {
    platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      databaseProvider.createDatabase().then(() =>{
        this.openTabsPage(splashScreen);
        alert('deu tud certo. banco criado');
      })
      .catch(e => {
        console.error(e)
        alert('banco nao foi criado');
        this.openTabsPage(splashScreen);
      });

      statusBar.styleDefault();

    });
  }

  public openTabsPage(splashScreen: SplashScreen){
    splashScreen.hide();
    this.rootPage = TabsPage;
  }
}
