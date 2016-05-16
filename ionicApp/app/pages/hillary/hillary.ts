import {Page, NavController} from 'ionic-angular';
import {Http} from 'angular2/http';
import {DemData} from '../../providers/dem-data/dem-data';
/*
  Generated class for the HillaryPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/hillary/hillary.html',
  providers: [DemData]
})

export class HillaryPage {
  demData;
  hillVote;
  bernVote;
  updated;

  constructor(public nav: NavController, demData:DemData ) {
    this.demData = demData;
  }

  ngOnInit(){
    this.demData.load().then((data) => {
      this.demData = data;
        //console.log(data);
        this.hillVote = data.estimates[0].value;
        this.bernVote = data.estimates[1].value;
        this.updated = data.last_updated;
    });
}
}
