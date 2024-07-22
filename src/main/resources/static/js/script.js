console.log("script is running")
alert("js is activated")

const paymentstart =()=>{
	
	console.log("payment started");
	let amount= $("#amount").val();
	console.log(amount);


$.ajax(
	{
		url:'/create_order',
		data:JSON.stringify({amount:amount,info:'order_request'}),
		contentType:'application/json',
		type:'POST',
		dataType:'json',
		success:function(response){
			console.log(response)
			if(response.status=="created"){
				
				let options={
					key:'rzp_test_y9PcfcR3YTRpHL',
					amount:response.amount,
					currency:'INR',
					name:'smart contact managre',
					description:'doantion',
					order_id:response.id,
					handler:function(response){
						console.log(response.razorpay_payment_id);
						console.log(response.razorpay_order_id);
						console.log(response.razorpay_signature);
						
						updatePaymenOnServer(response.razorpay_payment_id,
						                     response.razorpay_order_id,
						                     "paid");
						console.log('payment  scicedess');
						},
						 "prefill": { //We recommend using the prefill parameter to auto-fill customer's contact information especially their phone number
        "name": "Gaurav Kumar", //your customer's name
        "email": "gaurav.kumar@example.com",
        "contact": "9000090000" //Provide the customer's phone number for better conversion rates 
    },
    "notes": {
        "address": "Razorpay Corporate Office"
    },
    "theme": {
        "color": "#3399cc"
    }
};
			var rzp1 = new Razorpay(options);
        rzp1.on('payment.failed', function (response){
    alert(response.error.code);
    alert(response.error.description);
    alert(response.error.source);
    alert(response.error.step);
    alert(response.error.reason);
    alert(response.error.metadata.order_id);
    alert(response.error.metadata.payment_id);
});
    rzp1.open();			
					
					
					
				}
			
		},
		error:function(error){
	    
	    console.log(error)
		alert("something went wrong")
		
	}
	}
	);
};	
	function updatePaymenOnServer(payment_id,order_id,status){
	   	
	$.ajax({
		
		url:'/update_order',
		data:JSON.stringify({payment_id:payment_id,order_id:order_id,status:status}),
		contentType:'application/json',
		type:'POST',
		dataType:'json',
		success:function(response){
			
			console.log('payment  scicedess',response);
			
		},
		error:function(error){
			
			console.log('payment failed',error);
			
			}
		});
		
	}
	
	

