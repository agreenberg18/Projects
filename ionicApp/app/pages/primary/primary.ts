import {Page, NavController} from 'ionic-angular';
import {GetData} from '../../providers/get-data/get-data';

/*
  Generated class for the PrimaryPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/primary/primary.html',
  providers:[GetData],
})
export class PrimaryPage {
  myData;
  GetData;
  getData;
  voteHill;
  voteTrump;
  voteWho;
  updated;
  constructor(public nav: NavController, getData:GetData) {
    this.getData= getData;
  }

ngOnInit(){
  this.getData.load().then((data) => {
    this.myData = data;
      //console.log(data);
      this.voteHill = data.estimates[0].value;
      this.voteTrump = data.estimates[1].value;
      this.voteWho = data.estimates[2].value;
      this.updated = data.last_updated;
  });

}
}
