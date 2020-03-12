// Copyright 2019 UCARD Oy. All rights reserved.
        var Q = Q || {};
//Q.show3D = funtion(){
        let stats = initStats();
        let scene, camera, renderer, light;//controls
        let matArrayA=[];//内墙
        let matArrayB = [];//外墙

        //let group = new THREE.Group();
        //let group2 = new THREE.Group();
        let clipAction;
        let animationClip
        let mesh;
        //var floor;
        let controls;

        let position=0;
        //获取标签信息
        var datamodel = new Q.TagDataRetriever(100);

        let drawables = [];//标签显示集合 new
	let drawables_update =[];
        let drawables_delete =[];
	let drawables_new =[];

        var viewport = new Q.Viewport();
//1616 3798
	viewport.w=3798;
        viewport.h=1616;

        var mesh1;
        var currentVideoID;

        //模型材质信息
        var planeMat, LineMat;
        var loc_count=0;

        init();

        // 初始化场景
        function initScene() {
            scene = new THREE.Scene();
            scene.fog = new THREE.Fog( scene.background, 3000, 5000 );
        }

        // 初始化相机
        function initCamera() {
            camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 10000);
            camera.position.set(0, 800, 1500);
            camera.lookAt(new THREE.Vector3(0, 0, 0));
        }

        // 初始化灯光
        function initLight() {
            let directionalLight = new THREE.DirectionalLight( 0xffffff, 0.3 );//模拟远处类似太阳的光源
            directionalLight.color.setHSL( 0.1, 1, 0.95 );
            directionalLight.position.set( 0, 200, 0).normalize();
            scene.add( directionalLight );

            let ambient = new THREE.AmbientLight( 0xffffff, 1 ); //AmbientLight,影响整个场景的光源
            ambient.position.set(0,0,0);
            scene.add( ambient );
        }

        // 初始化性能插件
        function initStats() {
            let stats = new Stats();

            stats.domElement.style.position = 'absolute';
            stats.domElement.style.left = '0px';
            stats.domElement.style.top = '0px';

            document.body.appendChild(stats.domElement);
            return stats;
        }

        // 初始化渲染器
        function initRenderer() {
            renderer = new THREE.WebGLRenderer({antialias: true});
            renderer.setSize(window.innerWidth, window.innerHeight);
            renderer.setClearColor(0x4682B4,1.0);
            document.body.appendChild(renderer.domElement);
        }

        //创建地板
        function createFloor(){
            let loader = new THREE.TextureLoader();//floor.jpg
            loader.load("./source/floor.jpg",function(texture){
                texture.wrapS = texture.wrapT = THREE.RepeatWrapping;
                texture.repeat.set( 30, 30 );
		texture.rotation=Math.PI;
                let floorGeometry = new THREE.BoxGeometry(3798, 1616, 1);
                let floorMaterial = new THREE.MeshBasicMaterial( { map: texture, side: THREE.DoubleSide } );
                let floor = new THREE.Mesh(floorGeometry, floorMaterial);
                floor.position.y = -0.5;
                floor.rotation.x = Math.PI/2;
                floor.name = "地面";
                scene.add(floor);
            });
        }

        //创建墙
        function createCubeWall(width, height, depth, angle, material, x, y, z, name){
            let cubeGeometry = new THREE.BoxGeometry(width, height, depth );
            let cube = new THREE.Mesh( cubeGeometry, material );
            cube.position.x = x;
            cube.position.y = y;
            cube.position.z = z;
            cube.rotation.y += angle*Math.PI;  //-逆时针旋转,+顺时针
            cube.name = name;
            scene.add(cube);
        }

        //创建门_左侧
        function createDoor_left(width, height, depth, angle, x, y, z, name){
            let loader = new THREE.TextureLoader();
            loader.load("./source/door_left.png",function(texture){
                let doorgeometry = new THREE.BoxGeometry(width, height, depth);
                doorgeometry.translate(50, 0, 0);
                let doormaterial = new THREE.MeshBasicMaterial({map:texture,color:0xffffff});
                doormaterial.opacity = 1.0;
                doormaterial.transparent = true;
                let door = new THREE.Mesh( doorgeometry,doormaterial);
                door.position.set(x, y, z);
                door.rotation.y += angle*Math.PI;  //-逆时针旋转,+顺时针
                door.name = name;
                scene.add(door);
            });
        }

        //创建门_右侧
        function createDoor_right(width, height, depth, angle, x, y, z, name){
            let loader = new THREE.TextureLoader();
            loader.load("./source/door_right.png",function(texture){
                let doorgeometry = new THREE.BoxGeometry(width, height, depth);
                doorgeometry.translate(-50, 0, 0);
                let doormaterial = new THREE.MeshBasicMaterial({map:texture,color:0xffffff});
                doormaterial.opacity = 1.0;
                doormaterial.transparent = true;
                let door = new THREE.Mesh( doorgeometry,doormaterial);
                door.position.set(x, y, z);
                door.rotation.y += angle*Math.PI;  //-逆时针旋转,+顺时针
                door.name = name;
                scene.add(door);
            });
        }

        //创建墙纹理
        function createWallMaterail(){
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //前  0xafc0ca :灰色
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //后
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xd6e4ec}));  //上  0xd6e4ec： 偏白色
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xd6e4ec}));  //下
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //左    0xafc0ca :灰色
            matArrayA.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //右

            matArrayB.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //前  0xafc0ca :灰色
            matArrayB.push(new THREE.MeshPhongMaterial({color: 0x9cb2d1}));  //后  0x9cb2d1：淡紫
            matArrayB.push(new THREE.MeshPhongMaterial({color: 0xd6e4ec}));  //上  0xd6e4ec： 偏白色
            matArrayB.push(new THREE.MeshPhongMaterial({color: 0xd6e4ec}));  //下
            matArrayB.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //左   0xafc0ca :灰色
            matArrayB.push(new THREE.MeshPhongMaterial({color: 0xafc0ca}));  //右
        }
        //返回墙对象
        function returnWallObject(width, height, depth, angle, material, x, y, z, name){
            let cubeGeometry = new THREE.BoxGeometry(width, height, depth);
            let cube = new THREE.Mesh( cubeGeometry, material );
            cube.position.x = x;
            cube.position.y = y;
            cube.position.z = z;
            cube.rotation.y += angle*Math.PI;
            cube.name = name;
            return cube;
        }

        //墙上挖门窗，通过两个几何体生成BSP对象
        function createResultBsp(bsp,objects_cube){
            let material = new THREE.MeshPhongMaterial({color:0x9cb2d1,specular:0x9cb2d1,shininess:30,transparent:true,opacity:1});
            let BSP = new ThreeBSP(bsp);
            for(let i = 0; i < objects_cube.length; i++){
                let less_bsp = new ThreeBSP(objects_cube[i]);
                BSP = BSP.subtract(less_bsp);
            }
            let result = BSP.toMesh(material);
            result.material.flatshading = THREE.FlatShading;
            result.geometry.computeFaceNormals();  //重新计算几何体侧面法向量
            result.geometry.computeVertexNormals();
            result.material.needsUpdate = true;  //更新纹理
            result.geometry.buffersNeedUpdate = true;
            result.geometry.uvsNeedUpdate = true;
            scene.add(result);
        }

        //创建移动点
        function createPoint(angle, material, x, y, z, name){
            let cylinderGeometry = new THREE.CylinderGeometry(20, 20, 50,100);
            let cube = new THREE.Mesh( cylinderGeometry, material );
            cube.position.x = x;
            cube.position.y = y;
            cube.position.z = z;
            cube.rotation.y += angle*Math.PI;  //-逆时针旋转,+顺时针
            cube.name = name;
            scene.add(cube);
            return cube;
        }

/** 初始化材质信息 */
function initMat() {
    planeMat = new THREE.MeshLambertMaterial();
    LineMat = new THREE.MeshLambertMaterial();
    new THREE.TextureLoader().load( './source/plane.png', function( map ) {
        planeMat.map = map;
        planeMat.transparent = true;
        planeMat.opacity = 0.8;
        planeMat.needsUpdate = true;
    } );
    new THREE.TextureLoader().load( "./source/line.png", function( map ) {
        LineMat.map = map;
        LineMat.needsUpdate = true;
    } );
}
//region 矩形区域
function addPlane(x,z,width,length,scene) {
    var lineWidth = 8
    var geometry = new THREE.PlaneGeometry( lineWidth, length );
    var obj = new THREE.Mesh( geometry, LineMat );
    obj.position.set(x,10,z);
    obj.rotation.x = -Math.PI / 2.0;
    var obj2 = obj.clone();
    obj2.translateX(width);

    var geometry2 = new THREE.PlaneGeometry( lineWidth, width );
    var obj3 = new THREE.Mesh( geometry2, LineMat );
    obj3.position.set(x+width/2,10,z-length/2+lineWidth/2);
    obj3.rotation.x = -Math.PI / 2.0;
    obj3.rotation.z = -Math.PI / 2.0;
    var obj4 = obj3.clone();
    obj4.translateX(length-lineWidth);

    var group = new THREE.Group();
    group.add(obj);
    group.add(obj2);
    group.add(obj3);
    group.add(obj4);
    group.translateX(-width/2);
    scene.add( group );
}
//endregion

//region 库区
/** 放置虚线框区域和库区名称 */
function addArea(x,z,width,length,scene,name,textColor,font_size,textposition) {
    addPlane(x,z,width,length,scene);

    new THREE.FontLoader().load('./source/FZYaoTi_Regular.json',function(font){
        ////加入立体文字
        var text= new THREE.TextGeometry(name.split("$")[1],{
            // 设定文字字体
            font:font,
            //尺寸
            size:font_size,
            //厚度
            height:0.01
        });
        text.computeBoundingBox();
        //3D文字材质
        var m = new THREE.MeshStandardMaterial({color:"#" + textColor});
        var mesh = new THREE.Mesh(text,m)
        if(textposition == "左对齐"){
            mesh.position.x = x - width/2 + 10;
        }else if(textposition == "居中"){
            mesh.position.x = x - 15;
        }else if(textposition == "右对齐"){
            mesh.position.x = x + width/2 - 60;
        }
        mesh.position.y = 1.3;
        mesh.position.z = z + length/2 - 80;
        mesh.rotation.x = -Math.PI / 2.0;
        scene.add(mesh);
    });
}

        // 初始化模型
        function initContent() {
             createFloor();
            createWallMaterail();

            //构画墙体
            createCubeWall(10, 200, 410, 0, matArrayB, -1825, 100, -240, "墙面");//1
            createCubeWall(10, 200, 3636, 1.5, matArrayB, -10, 100, -446, "墙面");//2
            createCubeWall(10, 200, 420, 1.5, matArrayB, -1620, 100, -37, "墙面");//3

            //创建挖了门的墙 4
            //createCubeWall(10, 200, 1070, 0, matArrayB, -1467, 100, 275, "墙面");
            let wall = returnWallObject(10, 200, 750, 0, matArrayB, -1400, 100, 310, "墙面");
            let door_cube1 = returnWallObject(10, 150, 40, 0, matArrayB, -1400, 60, 180, "前门1");
            let door_cube2 = returnWallObject(10, 150, 40, 0, matArrayB, -1400, 60, 214, "前门2");
            let objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);

            createDoor_left(2, 135, 40, 0, -1450, 67, 180, "左门1");
            createDoor_right(2, 135, 40, 1, -1450, 67, 214, "右门1");
            //5
            wall = returnWallObject(10, 200, 380, 1.5, matArrayB, -1590, 100, 275, "墙面");
            door_cube1 = returnWallObject(10, 150, 40, 1.5, matArrayB, -1475, 60, 275, "前门1");
            door_cube2 = returnWallObject(10, 150, 40, 1.5, matArrayB, -1440, 60, 275, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);

            createDoor_left(2, 135, 40, 1.5, -1475, 67, 225, "左门1");
            createDoor_right(2, 135, 40, 0.5, -1440, 67, 225, "右门1");
            //6
            wall = returnWallObject(10, 200, 380, 1.5, matArrayB, -1590, 100, 590, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 1.5, matArrayB, -1630, 60, 590, "前门1");
            door_cube2 = returnWallObject(10, 150, 50, 1.5, matArrayB, -1600, 60, 590, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);

            createDoor_left(2, 135, 50, 1.5, -1640, 67, 540, "左门1");
            createDoor_right(2, 135, 50, 0.5, -1590, 67, 540, "右门1");
            //7
            createCubeWall(10, 200, 435, 0, matArrayB, -1780, 100, 473, "墙面");
            //8
            createCubeWall(10, 200, 310, 0, matArrayB, -1650, 100, 110, "墙面");
            //9
            createCubeWall(10, 200, 3200, 1.5, matArrayB, 200, 100, 690, "墙面");
            //10
            createCubeWall(10, 200, 250, 0, matArrayB, -1450, 100, -320, "墙面");
            //11
            createCubeWall(10, 200, 250, 0, matArrayB, -1650, 100, -320, "墙面");
            //12
            createCubeWall(10, 200, 290, 1.5, matArrayB, -1600, 100, -200, "墙面");
            //13
            //createCubeWall(10, 200, 645, 1.5, matArrayB, 1540, 100, 630, "墙面");
            //14
            createCubeWall(10, 200, 1402, 0, matArrayB, 1802, 100, -6, "墙面");
            //15
            createCubeWall(10, 200, 370, 0, matArrayB, -1020, 100, -260, "墙面");
            //16
            createCubeWall(10, 200, 270, 1.5, matArrayB, -1150, 100, -70, "墙面");
            //17
            wall = returnWallObject(10, 200, 367, 0, matArrayB, -495, 100, -260, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 0, matArrayB, -495, 60, -195, "前门1");
            door_cube2 = returnWallObject(10, 150, 50, 0, matArrayB, -495, 60, -145, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);

            createDoor_left(2, 135, 50, 0, -545, 67, -195, "左门1");
            createDoor_right(2, 135, 50, 1, -545, 67, -145, "右门1");
            //18 楼梯
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1580, 150, 80, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1580, 130, 40, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1580, 110, 0, "台阶");
            createCubeWall(40, 20, 240, 1.5, matArrayB, -1520, 90, -20, "台阶");
            //createCubeWall(40, 20, 129, 1.5, matArrayB, -1480, 115, -40, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1460, 70, 20, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1460, 50, 60, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1460, 30, 100, "台阶");
            createCubeWall(40, 20, 120, 1.5, matArrayB, -1460, 10, 140, "台阶");
            //19前台
            createCubeWall(80, 50, 129, 1.5, matArrayB, -1647, 20, 400, "前台");
            //20
            createCubeWall(10, 200, 460, 1.5, matArrayB, -720, 100, -80, "墙面");
            //21
            //createCubeWall(10, 200, 270, 0, matArrayB, 1210, 100, 555, "墙面");
            wall = returnWallObject(10, 200, 230, 0, matArrayB, 924, 100, 570, "墙面");
            door_cube1 = returnWallObject(10, 150, 40, 0, matArrayB, 924, 60, 570, "前门1");
            door_cube2 = returnWallObject(10, 150, 40, 0, matArrayB, 924, 60, 600, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);
            //22
            wall = returnWallObject(10, 200, 650, 0, matArrayB, -1280, 100, 130, "墙面");
            door_cube1 = returnWallObject(10, 150, 40, 0, matArrayB, -1280, 60, 05, "前门1");
            door_cube2 = returnWallObject(10, 150, 40, 0, matArrayB, -1280, 60, 45, "前门2");
            door_cube3 = returnWallObject(10, 150, 50, 0, matArrayB, -1280, 60, 240, "前门3");
            door_cube4 = returnWallObject(10, 150, 50, 0, matArrayB, -1280, 60, 420, "前门4");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            objects_cube.push(door_cube3);
            objects_cube.push(door_cube4);
            createResultBsp(wall, objects_cube);
            //23
            createCubeWall(10, 200, 360, 1.5, matArrayB, -860, 100, 465, "墙面");
            //24
            //createCubeWall(10, 200, 420, 1.5, matArrayB, -1110, 100, 460, "墙面");
            wall = returnWallObject(10, 200, 360, 1.5, matArrayB, -1214, 100, 450, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 1.5, matArrayB, -1360, 60, 450, "前门1");
            objects_cube = [];
            objects_cube.push(door_cube1);
            createResultBsp(wall, objects_cube);
            //25
            createCubeWall(10, 200, 410, 0, matArrayB, -1036, 100, 480, "墙面");
            //26
            createCubeWall(10, 200, 250, 1.5, matArrayB, -1154, 100, 284, "墙面");
            //27
            createCubeWall(10, 200, 70, 1.5, matArrayB, -980, 100, -140, "墙面");
            //28
            createCubeWall(10, 200, 60, 0, matArrayB, -945, 100, -110, "墙面");
            //29
            //createCubeWall(10, 200, 210, 0, matArrayB, -445, 100, 585, "墙面");
            wall = returnWallObject(10, 200, 226, 0, matArrayB, -685, 100, 580, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 0, matArrayB, -685, 60, 500, "前门1");
            objects_cube = [];
            objects_cube.push(door_cube1);
            createResultBsp(wall, objects_cube);
            //30
            //createCubeWall(10, 200, 1110, 1.5, matArrayB, 235, 100, -385, "墙面");
            wall = returnWallObject(10, 200, 990, 1.5, matArrayB, -5, 100, -240, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 1.5, matArrayB, -150, 60, -240, "前门1");
            door_cube2 = returnWallObject(10, 150, 50, 1.5, matArrayB, 445, 60, -240, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);
            //31
            createCubeWall(10, 200, 210, 0, matArrayB, -110, 100, -343, "墙面");
            //32
            //createCubeWall(10, 200, 625, 0, matArrayB, 785, 100, -310, "墙面");
            wall = returnWallObject(10, 200, 535, 0, matArrayB, 490, 100, -180, "墙面");
            door_cube1 = returnWallObject(10, 150, 50, 0, matArrayB, 490, 60, -10, "前门1");
            door_cube2 = returnWallObject(10, 150, 50, 0, matArrayB, 490, 60, 5, "前门2");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            createResultBsp(wall, objects_cube);
            //33
            createCubeWall(10, 200, 440, 1.5, matArrayB, 710, 100, 80, "墙面");
            //34
            createCubeWall(10, 200, 760, 0, matArrayB, 1526, 100, -60, "墙面");
            //35
            createCubeWall(10, 200, 236, 0, matArrayB, 924, 100, 200, "墙面");
            //36
            createCubeWall(10, 200, 770, 1.5, matArrayB, 1136, 100,310, "墙面");
            //37
            createCubeWall(10, 200, 376, 0, matArrayB, 1360, 100, 500, "墙面");
            //38
            wall = returnWallObject(10, 200, 380, 0, matArrayB, 756, 100, 496, "墙面");
            door_cube1 = returnWallObject(10, 150, 40, 0, matArrayB, 756, 60, 496, "前门1");
            door_cube2 = returnWallObject(10, 150, 40, 0, matArrayB, 756, 60, 530, "前门2");
            door_cube3 = returnWallObject(10, 150, 60, 0, matArrayB, 756, 60, 370, "前门3");
            objects_cube = [];
            objects_cube.push(door_cube1);
            objects_cube.push(door_cube2);
            objects_cube.push(door_cube3);
            createResultBsp(wall, objects_cube);
            //39
            createCubeWall(40, 20, 60, 0, matArrayB, -1040, 90, -110, "台阶");
            createCubeWall(40, 20, 60, 0, matArrayB, -1080, 70, -110, "台阶");
            createCubeWall(40, 20, 60, 0, matArrayB, -1120, 50, -110, "台阶");
            createCubeWall(40, 20, 60, 0, matArrayB, -1160, 30, -110, "台阶");
            createCubeWall(40, 20, 60, 0, matArrayB, -1200, 10, -110, "台阶");
            //40 #3号机
            createCubeWall(45, 80, 500, 1.5, matArrayB, -670, 40, -10, "上/下料");
            createCubeWall(70, 80, 130, 1.5, matArrayB, -610, 40, 10, "热压");
            createCubeWall(70, 80, 140, 1.5, matArrayB, -810, 40, 10, "绑定");
            //41 #1号机
            createCubeWall(90, 80, 590, 1.5, matArrayB, -620, 40, 380, "上/下料");
            //42 #2号机
            createCubeWall(90, 80, 590, 1.5, matArrayB, 65, 40, 380, "上/下料");
        }

        // 初始化轨迹球控件
        function initControls() {
            controls = new THREE.OrbitControls( camera, renderer.domElement );
            controls.enableDamping = true;
            controls.dampingFactor = 0.5;
            // 视角最小距离
            controls.minDistance = 50;
            // 视角最远距离
            controls.maxDistance = 5000;
            // 最大角度
            controls.maxPolarAngle = Math.PI;
	    controls.target = new THREE.Vector3(50,50,0);
        }

        // 更新控件
        function update(delta) {
            stats.update();
            controls.update(delta);
        }

//region 放置视频面板
function addVideoPlane( x,y,z,width,length,scene,videoId ) {
  currentVideoID=videoId;
  var planeGeometry = new THREE.PlaneGeometry(width, length);
  var material = new THREE.MeshPhongMaterial();
  material.side = THREE.DoubleSide;
  var video = document.getElementById(videoId);
  var texture = new THREE.VideoTexture(video);
  texture.minFilter = THREE.LinearFilter;
  texture.magFilter = THREE.LinearFilter;
  texture.format = THREE.RGBFormat;
  material.map = texture;
  mesh1 = new THREE.Mesh(planeGeometry, material);
  mesh1.position.set(x,y,z);
  scene.add(mesh1);
}

//endregion

function deleteVideoPlane( ) {
  deleteGroup(mesh1);
  scene.remove(mesh1);
}
// 删除group，释放内存
function deleteGroup(group) {
	//console.log(group);
    if (!group) return;
    // 删除掉所有的模型组内的mesh
    group.traverse(function (item) {
        if (item instanceof THREE.Mesh) {
            item.geometry.dispose(); // 删除几何体
            item.material.dispose(); // 删除材质
        }
    });
}

        // 初始化
        function init() {
            initScene();
            initCamera();
            initRenderer();
            initContent();
            addArea(-750,-250,450,300,scene,"ID1$Zone001","FF0000",20,"居中");
            //addArea(-1175,0,190,110,scene,"ID1$Zone004","FF0000",20,"居中");
            //addArea(-1175,130,190,130,scene,"ID1$Zone005","FF0000",20,"居中");
            //addArea(-950,0,190,110,scene,"ID1$Zone006","FF0000",20,"左对齐");
            //addArea(-950,130,190,130,scene,"ID1$Zone007","FF0000",20,"居中");
            addArea(-770,100,1000,350,scene,"ID1$Zone002","FF0000",20,"居中");
            addArea(-770,100,1000,350,scene,"ID1$Zone002","FF0000",20,"居中");
            addArea(-400,-150,200,170,scene,"ID1$Zone003","FF0000",20,"居中");
            initLight();
            initControls();
            document.addEventListener('resize', onWindowResize, false);
        }

        // 窗口变动触发的方法
        function onWindowResize() {
            camera.aspect = window.innerWidth / window.innerHeight;
            camera.updateProjectionMatrix();
            renderer.setSize(window.innerWidth, window.innerHeight);
        }

        function animate() {
            requestAnimationFrame(animate);
            renderer.render(scene, camera);
            update();
        }
        animate();
        function showAuto()
        {
	    TWEEN.update();
            //发送url获取标签位置信息
            refreshTags();

        }
	function openDoor()
        {
            //initRelay();
		//data_response();
		$("#label").attr("style","display:none;");//隐藏说明性标签
		var tags = this.datamodel.getTags();//获得标签信息
		for(var i = 0; i < tags.length; i++) {
			var tag = tags[i];
			if(tag.info.zones!== null)
			{
		   	   var zones=tag.info.zones;
                   	   for(var j = 0; j < zones.length; j++) {
                      		var zone = zones[j];
                                
				if(zone.name=='Zone003'){
				    initRelay();
				}
				if (zone.name=='Zone001')
				{
				   if(tag.id=='d46055a6a2d1'||tag.info.name=='sharlock-007'||tag.info.name=='sharlock-006'||tag.info.name=='sharlock-005'){
				     //console.log(zone.name);
                                     getVoiceAlarm();
  				   }
				    
				    //console.log(tag);
				    //sendRequest2Tag(tag.id);
				    //getRequestResponse(tag.id);
				    //alarm
				}

		   	   }
		        }
		}
   
        }
	//

function initRelay(){
    jQuery.ajax({
		url : "samplecode",
		// url : "http://localhost:8080/ChinaCardMap/samplecode",
		dataType : 'html',
		async : true,
		success : function() {
			console.log('success', 'door has been opened');
		},
		error : function() {
			console.log('error', 'open failed');
		}
	});
}

function sendRequest2Tag(id,flag){
    var cmd = "0xff 0x5d 0x00 0x01 0xaa 0x03";
    if(!flag)
    {
	cmd = "0xff 0x5d 0x00 0x00 0x00 0x00";
    }
    jQuery.ajax({
		url : "../qpe/sendQuuppaRequest?tag="+id+"&requestData="+cmd+"&humanReadable=true&version=2",
		dataType : 'json',
		async : true,
		success : function(data, textStatus, jqXHR) {
			//console.log(data);
			console.log('success', 'bell has alarmed');
		},
		error : function() {
			console.log('error', 'alarm failed');
		}
	});
}

function getRequestResponse(id){
    jQuery.ajax({
		url : "../qpe/getQuuppaRequestResponse?tag="+id+"&humanReadable&version=2",
		dataType : 'json',
		async : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data);
			console.log('success', 'bell has alarmed');
		},
		error : function() {
			console.log('error', 'alarm failed');
		}
	});
}

function getVoiceAlarm(){
    jQuery.ajax({
		url : "http://111.229.7.173:8000/api/v1.0/service/location?content=您已非法闯入库房请退出",
		dataType : 'json',
		async : true,
		success : function() {
			console.log('success', 'bell has alarmed');
		},
		error : function() {
			console.log('error', 'alarm failed');
		}
	});
}


function data_response() {
	jQuery.ajax({
		url : "../qpe/getTagPayloadData?tag=d46055a6a2d1&version=2",
		dataType : 'json',
		async : true,
		success : function(data, textStatus, jqXHR) {
			console.log(data.tags[0].payloadData);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			console.log('error', 'loading tag info failed, ' + textStatus);
		}
	});
}


function refreshTags()
{
    //data_response();
    var tags = this.datamodel.getTags();//获得标签信息
    //console.log(tags);
    //添加标签到图像中
    for(var i = 0; i < tags.length; i++) {
		var tag = tags[i];
		//console.log("MAP tag add " + tag.id);
                //console.log('1: '+tag.info.lastPacketTS+' id '+tag.id);
		var name = tag.info.name || tag.id;
		var buttonState = tag.info.buttonState;//
//alarm plan 2,its not work as well as i thought
/*
		var zones=tag.info.zones;
	     	if(zones != undefined){
             	    for(var k = 0; k < zones.length; k++) {
                    var zone = zones[k];
		    if (zone.name=='Zone002')
		    {
		        console.log(tag.info.zones);
		        sendRequest2Tag(tag.id,true);
		    }
		    else
		    {
			sendRequest2Tag(tag.id,false);
		    }
	     	}
	     }
*/
/*
		if(tag.id=='d46055a6a2d1')
{
		var zones=tag.info.zones;
	     	if(zones != undefined){
             	    for(var k = 0; k < zones.length; k++) {
                    var zone = zones[k];
		    //console.log(zone.name);
		    if (zone.name=='Zone005')
		    {
			if(mesh1!=undefined && currentVideoID!='video1')
			{
			  deleteVideoPlane();
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video1' );
			}
			if(mesh1==undefined)
			{
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video1' );
			}
		    }
		    if(zone.name=='Zone004')
		    {
			if(mesh1!=undefined && currentVideoID!='video2')
			{
			  deleteVideoPlane();
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video2' );
			}
			if(mesh1==undefined)
			{
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video2' );
			}
			
		    }
                    if(zone.name=='Zone006'){
			if(mesh1!=undefined && currentVideoID!='video3')
			{
			  deleteVideoPlane();
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video3' );
			}
			if(mesh1==undefined)
			{
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video3' );
			}
			
		    }
                    if(zone.name=='Zone007'){
			if(mesh1!=undefined && currentVideoID!='video4')
			{
			  deleteVideoPlane();
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video4' );
			}
			if(mesh1==undefined)
			{
			  addVideoPlane( 0, 400, 0, 400, 200 ,scene, 'video4' );
			}
			
		    }
	     	}
	     }
}
*/
        var drawable = new Q.TagDrawable(tag, "");
		drawable.name = name;
        drawable.isSelected = false;
	if(drawables.length==0)
	{
	   //console.log('add 0');
           drawables.push(drawable);
	   drawable.addTagImage(scene,viewport);
        if(buttonState=='pushed')
		{
            	drawables[0].showMessage('SOS!!');
                //console.log(tag.info.name);
                drawables[0].count++;
                options.batchNo = drawables[0].tag.info.name;
            	options.qty = drawables[0].count;
                options.qtyUom = "SOS!!";
		}
           break;
	}
        var flag=false;
        for(var j=0;j<drawables.length;j++)
	{
	  if(drawables[j].name==drawable.name)
	  {
            //console.log('update');
	    //drawables[j].tag=drawable.tag;
	    drawables[j].updateTagPosition(drawable.tag);
            flag=true;
        if(buttonState=='pushed')
		{
            	drawables[j].showMessage('SOS!!');
                //console.log(tag.info.name);
                drawables[j].count++;
                options.batchNo = drawables[j].tag.info.name;
            	options.qty = drawables[j].count;
                options.qtyUom = "SOS!!";
		}
            break;
          }
	}
        if(!flag)
	{
            //console.log('add');
	    drawables.push(drawable);
	    drawable.addTagImage(scene,viewport);
        if(buttonState=='pushed')
		{
            	drawables.showMessage('SOS!!');
                //console.log(tag.info.name);
                drawables.count++;
                options.batchNo = drawable.tag.info.name;
            	options.qty = drawables.count;
                options.qtyUom = "SOS!!";
		}
	}
        }


	//
	var timestamp = Date.parse(new Date());
	for(var l=0;l<drawables.length; l++)
	{
	     var time = timestamp - drawables[l].tag.info.lastPacketTS;
             //console.log('time '+time + ' id :'+drawables[l].tag.id);
             if(time>=60000)
	     {
		//console.log('delete');
                drawables[l].hidTagPosition();
	     }

	}
/*
        for(var k=0;k<drawables.length;k++)
	{
	  var flag=false;
	  for(var l=0;l<tags.length; l++)
	  {

	     var name = tags[l].info.name || tags[l].id;
             if(drawables[k].name==name)
	     {
		flag=true;
                break;
	     }

	  }
          if(!flag)
	  {
	     console.log('delete');
             scene.remove(drawables[k].currentPosition);
             //delete drawables[k];
	     drawables.splice(k-1,1);
	  }
	}
*/
}

setInterval("showAuto()", 100);
setInterval("openDoor()", 5000);

        //刷新标签
        //画数据展示板
        //报警
        //开门
//};

//Q.show3D.prototype.render = function () {
//};
