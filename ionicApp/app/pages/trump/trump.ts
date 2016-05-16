import {Page, NavController} from 'ionic-angular';
import {GopData} from '../../providers/gop-data/gop-data';
/*
  Generated class for the TrumpPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/trump/trump.html',
  providers:[GopData],
})
export class TrumpPage {
  gopData;
  repData;
  trumpVote;
  cruzVote;
  kasichVote;
  updated;
  constructor(public nav: NavController, gopData:GopData) {
    this.gopData= gopData
  }
  ngOnInit(){
    this.gopData.load().then((data) => {
      this.repData = data;
        //console.log(data);
        this.trumpVote = data.estimates[0].value;
        this.cruzVote = data.estimates[1].value;
        this.kasichVote = data.estimates[2].value;
        this.updated = data.last_updated;
    });
}
}
