$(document).ready(function(){const themeSwitchers=document.querySelectorAll('span');const dynamicInputs=document.querySelectorAll('input.input-color-picker');const handleThemeUpdate=(cssVars)=>{const root=document.querySelector(':root');const keys=Object.keys(cssVars);keys.forEach(key=>{root.style.setProperty(key,cssVars[key]);});}
themeSwitchers.forEach((item)=>{item.addEventListener('click',(e)=>{const bgColor=e.target.getAttribute('data-bg-color')
const color=e.target.getAttribute('data-color')
handleThemeUpdate({'--primary-bg-color':bgColor,'--primary-color':color});console.log(bgColor,color)
$("input.input-color-picker[data-id='color']").val(color)
$("input.input-color-picker[data-id='bg-color']").val(bgColor)});});dynamicInputs.forEach((item)=>{item.addEventListener('input',(e)=>{const cssPropName=`--primary-${e.target.getAttribute('data-id')}`;console.log(cssPropName)
handleThemeUpdate({[cssPropName]:e.target.value});});});});