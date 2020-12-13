//  Global vars
//Canvas handle
var canvas;
//Used for drawing
var ctx;
//Sprite obj
var sprites;
//Base asset location
const assetPath = "assets/";
//Loop status
var loop;

//  Called on load, get canvas handle and context (2d)
function start() {
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");
}

//  Get String name from char[]
//Java can't send memory references to this script
//due to the Principle of Least Privelege
//it would have to expose more memory
//so only primitive types are sent
function getNameFrom(nameChars) {
    var name = "";
    for(var j = 0; j < nameChars.length; j++) {
        name += String.fromCharCode(nameChars[j]);
    }
    return name;
}

//  Create Image assets for each Sprite
function loadSprites() {
    sprites = {};
    var noAssets = window.mainHandler.getNoSprites();
    for(var i = 0; i < noAssets; i++) {
        var path = assetPath;
        var name = getNameFrom(window.mainHandler.getSpriteName(i));
        path += name;
        sprites[name] = new Image();
        sprites[name].src = path;
    }
}

//  Call drawing loop every 35ms
function startLoop() {
    loop = setInterval(draw, 35);
}

//  Stop drawing loop
function pauseLoop() {
    clearInterval(loop);
}

//  Draw each asset with its respective sprite
//in its respective position, scaling if necessary
function draw() {
    ctx.clearRect(0,0,canvas.width, canvas.height);
    var noAssets = window.mainHandler.getNoAssets();
    for(var i = 0; i < noAssets; i++) {
        var name = getNameFrom(window.mainHandler.getAssetSprite(i));
        if(!(name in sprites))
            continue;
        [x, y, width, height] = window.mainHandler.getAssetPosition(i);
        if(width !== undefined && height !== undefined)
            ctx.drawImage(sprites[name], x, y, width, height);
        else
            ctx.drawImage(sprites[name], x, y);
    }
}