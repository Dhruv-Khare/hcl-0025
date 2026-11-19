export const greet =(name)=>{
    return  `Hello ${ name }`;
};

export const greetInHindi =(name)=>{
    return  `Namaste, ${ name }`;
}

export const processOrder=(data,dependency)=>{
    const paymentInfo= dependency.processPayment(data.ammont);
    return paymentInfo;
}

const processPayment=(ammount)=>{
  console.log(`Processing payment of ${ammount}`);
  return {status:"success", id:Math.random(), ammount:ammount};  
}

