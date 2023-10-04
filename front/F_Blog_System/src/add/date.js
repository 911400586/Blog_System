function ParDate (val)
{
    let date = new Date(val);  
  
    let year = date.getFullYear();  
    let month = ("0" + (date.getMonth() + 1)).slice(-2); // 注意，getMonth() 返回的是从0开始的月份，所以我们需要加1，并确保总是两位数  
    let day = ("0" + date.getDate()).slice(-2); // 确保日期总是两位数  
      
    let hours = ("0" + date.getHours()).slice(-2); // 确保小时总是两位数  
    let minutes = ("0" + date.getMinutes()).slice(-2); // 确保分钟总是两位数  
    let seconds = ("0" + date.getSeconds()).slice(-2); // 确保秒数总是两位数  
      
    let formattedDateString = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;  
    return formattedDateString;
}
export {
    ParDate
    
}