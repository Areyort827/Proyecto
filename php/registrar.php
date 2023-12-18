<?PHP
$hostname="localhost";
$database="taskmaster";
$username="root";
$password="";
$json=array();

	if(isset($_GET["id"])&&isset($_GET["nombre"]) && isset($_GET["pwd"]) && isset($_GET["correo"])){
		$id=$_GET['id'];
		$nombre=$_GET['nombre'];
		$pwd=sha1($_GET['pwd']);
		$pwdHash= password_hash($pwd,PASSWORD_BCRYPT);
		$correo=$_GET['correo'];

		

		
		$conexion=mysqli_connect($hostname,$username,$password,$database);
		
		$consulta="INSERT INTO usuario(id, nombre, pwd, correo) VALUES ('{$id}','{$nombre}' , '{$pwd}','{$correo}')";
		$resultado=mysqli_query($conexion,$consulta);

		if($consulta){
		   $consulta="SELECT * FROM usuario  WHERE id='{$id}'";
		   $resultado=mysqli_query($conexion,$consulta);

			if($reg=mysqli_fetch_array($resultado)){
				$json['datos'][]=$reg;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}



		else{
			$results["id"]='';
			$results["nombre"]='';
			$results["pwd"]='';
			$results["correo"]='';
			$json['datos'][]=$results;
			echo json_encode($json);
		}
		
	}
	else{   
		    $results["id"]='';
		   	$results["nombre"]='';
			$results["pwd"]='';
			$results["correo"]='';
			$json['datos'][]=$results;
			echo json_encode($json);
		}
?>