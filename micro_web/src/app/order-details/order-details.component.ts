import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-order-details',
  templateUrl: './order-details.component.html',
  styleUrls: ['./order-details.component.css']
})
export class OrderDetailsComponent {
  orderDetails :any;
  orderId!:number;
  orderStatusOptions: string[] = ['CREATED', 'PENDING', 'CANCELLED', 'COMPLETED'];
  selectedStatus: string = '';
  constructor(private http:HttpClient, private router: Router, private route:ActivatedRoute) {
    this.orderId=route.snapshot.params['orderId'];
  }

  ngOnInit(): void {
    this.http.get("http://localhost:9999/order-service/fullOrder/"+this.orderId)
      .subscribe({
        next : (data)=>{
          this.orderDetails=data;
        },
        error : (err)=>{}
      });
  }

  getOrderDetails(o: any) {
    this.router.navigateByUrl("/order-details/"+o.id);
  }

  updateOrderStatus() {
    const status = this.selectedStatus;
    // const status = 'PENDING'; // replace with the new status
    const url = `http://localhost:9999/order-service/fullOrder/${this.orderId}/${status}`;
    this.http.put(url, { status }).subscribe({
      next : (data)=>{
        this.orderDetails=data;
      },
      error :(err)=>{}
      // this.orderDetails.status = status; // update the status in the UI
    });
    
  }
}
