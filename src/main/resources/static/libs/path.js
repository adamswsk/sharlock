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
        var datamodel;

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

        let selectID;
        var group = new THREE.Group();
       let stayTime=new Map();

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

        function createPoint(x,z,color,name,hight) {
            var color1 = color;
            let color_red=[];
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //前
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //后
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //上
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //下
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //左
            color_red.push(new THREE.MeshPhongMaterial({color: color1}));  //右
            let cylinderGeometry = new THREE.CylinderGeometry(5, 5, hight*1,100);
            let cube = new THREE.Mesh( cylinderGeometry, color_red );
            cube.position.x = x;
            cube.position.y = hight*0.5;
            cube.position.z = z;
            cube.rotation.y += 0;  //-逆时针旋转,+顺时针
            cube.name = name;
            group.add(cube);
            //scene.add(cube);
        };

//region 库区
/** 放置虚线框区域和库区名称 */
function addArea(x,z,width,length,scene,name,textColor,font_size,textposition) {
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
        group.add(mesh);
    });
}

       function getPathData(tagname)
        {
         scene.remove(group);
        group=new THREE.Group();
        stayTime=new Map();
        addArea(-400,0,450,300,scene,"ID1$"+tagname+":  20分钟内数据","FF0000",40,"居中");
        console.log(tagname);
        //获取标签color 和 tagid
        jQuery.ajax({
            url : "http://localhost:8001/frontend/getTagBaseInfo?name="+tagname,
		    dataType : 'json',
		    async : true,
		    success : function(data, textStatus, jqXHR) {
			    console.log(data);
                let tagid = data[0]["tagId"];
                let color = data[0]["tagColor"];
                jQuery.ajax({
                    url : "http://localhost:8001/frontend/getTagPositionTrail?tagid="+tagid,
		            dataType : 'json',
		            async : true,
		            success : function(data, textStatus, jqXHR) {
			            console.log(data);
                        for (i=0;i<data.length;i++)
                        {
                            var loc=data[i]["tagSmoothedpostionx"]+','+data[i]["tagSmoothedpostiony"];
                            if(!stayTime.has(loc))
                            {
                                //stayTime.push(loc,1);
                                stayTime.set(loc,1);
                                //console.log(stayTime[loc]);
                            }
                            else
                            {
                                var i = stayTime.get(loc);
                                i+=1;
                                stayTime.set(loc,i);
                            }
                        }
                        stayTime.forEach(function(value,key){
                            var n = key.split(",");//name,color,x,z
                            createPoint(n[0],n[1],color,tagname,value);
                            if(value>100)
                            {
                                addArea(-400,100,450,300,scene,"ID1$"+n[0]+','+n[1]+"  区域停留超时","FF0000",20,"居中");
                            }
　　　　　　　　　　　　//console.log(value,key);
　　　　　　　　　　    });
                        scene.add(group);
			            //setTimeout(o.__pollLocation, o.updateInterval, o);
		            },
		            error : function(jqXHR, textStatus, errorThrown) {
			            console.log('error', 'loading tag positions failed, ' + textStatus);
			            if(new Date().getTime() - o.__lastNotificationShownTS > 20000) {
				            Q.notificationManager.showNotification("Network error while retrieving tag locations.", 4000);
				            o.__lastNotificationShownTS = new Date().getTime();
			            }
			            //setTimeout(o.__pollLocation, o.updateInterval, o);
		            }
	            });

			    //setTimeout(o.__pollLocation, o.updateInterval, o);
		    },
		    error : function(jqXHR, textStatus, errorThrown) {
			    console.log('error', 'loading tag positions failed, ' + textStatus);
			    if(new Date().getTime() - o.__lastNotificationShownTS > 20000) {
				    Q.notificationManager.showNotification("Network error while retrieving tag locations.", 4000);
				    o.__lastNotificationShownTS = new Date().getTime();
			    }
			    //setTimeout(o.__pollLocation, o.updateInterval, o);
		    }
	    });
        //根据tagid获取标签位置
        /*
        if(!window.localStorage){
            alert("浏览器支持localstorage");
        }else{
            var storage=window.localStorage;
            var name;
            var color;
            //第一种方法读取
            for(item in storage) {
                if(item=='length'){break;}
                var data = storage.getItem(item);
                var m = data.split(",");//name,color,x,z
                if(m[0]===tagname)
                {
                    name=m[0];
                    color=m[1];
                    var loc=m[2]+','+m[3];
                    if(!stayTime.has(loc))
                    {
                        //stayTime.push(loc,1);
                        stayTime.set(loc,1);
                        //console.log(stayTime[loc]);
                    }
                    else
                    {
                        var i = stayTime.get(loc);
                        i+=1;
                        stayTime.set(loc,i);
                    }
                    //createPoint(m[2],m[3],m[1],m[0]);
                }
            }
            stayTime.forEach(function(value,key){
                        var n = key.split(",");//name,color,x,z
                        createPoint(n[0],n[1],color,name,value);
                        if(value>100)
                        {
                            addArea(-400,100,450,300,scene,"ID1$"+n[0]+','+n[1]+"  区域停留超时","FF0000",20,"居中");
                        }
　　　　　　　　　　　　//console.log(value,key);
　　　　　　　　　　});
            scene.add(group);
            //var a=storage.a;
            //console.log(a);
            //第二种方法读取
            //var b=storage["b"];
            //console.log(b);
            //第三种方法读取
            //var c=storage.getItem("c");
            //console.log(c);
        }*/
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

	var tags_name = new function () {
            this.sharlock001 =function(){this.selectID='Test-1';getPathData(this.selectID);};
            this.sharlock002 =function(){this.selectID='Standard-1';getPathData(this.selectID);};
            this.sharlock003 =function(){this.selectID='sharlock-003';getPathData(this.selectID);};
            this.sharlock004 =function(){this.selectID='sharlock-004';getPathData(this.selectID);};
            this.sharlock005 =function(){this.selectID='sharlock-005';getPathData(this.selectID);};
            this.sharlock006 =function(){this.selectID='sharlock-006';getPathData(this.selectID);};
            this.sharlock007 =function(){this.selectID='sharlock-007';getPathData(this.selectID);};
            this.sharlock008 =function(){this.selectID='sharlock-008';getPathData(this.selectID);};
        };
        var gui = new dat.GUI();
        gui.domElement.style = 'position:absolute;top:10px;right:0px;height:600px';
        gui.add(tags_name, 'sharlock001').name("sharlock-001：");
        gui.add(tags_name, 'sharlock002').name("sharlock-002：");
        gui.add(tags_name, 'sharlock003').name("sharlock-003：");
        gui.add(tags_name, 'sharlock004').name("sharlock-004：");
        gui.add(tags_name, 'sharlock005').name("sharlock-005：");
        gui.add(tags_name, 'sharlock006').name("sharlock-006：");
        gui.add(tags_name, 'sharlock007').name("sharlock-007：");
        gui.add(tags_name, 'sharlock008').name("sharlock-008：").listen();

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


        // 初始化
        function init() {
            initScene();
            initCamera();
            initRenderer();
            initContent();
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
        }

function getVoiceAlarm(){
    jQuery.ajax({
		url : "http://111.229.7.173:8000/api/v1.0/service/location",
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

        //刷新标签
        //画数据展示板
        //报警
        //开门
