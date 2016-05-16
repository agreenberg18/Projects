import {Page} from 'ionic-angular';
import {HillaryPage} from '../hillary/hillary';
import {TrumpPage} from '../trump/trump';
import {PrimaryPage} from '../primary/primary';
import {Vibration} from 'ionic-native';
@Page({
  templateUrl: 'build/pages/home/home.html'
})
export class HomePage {
  hillaryPage = HillaryPage;
  trumpPage = TrumpPage;
  primaryPage = PrimaryPage;
  constructor() {

  }
  buttonClicked(){
    Vibration.vibrate(1000);
  }
}
