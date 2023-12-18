<?PHP
$hostname="localhost";
$database="taskmaster";
$username="root";
$password="";

$json=array();
	if(isset($_GET["nombre"]) && isset($_GET["pwd"])){
		$user=$_GET['nombre'];
		$pwd=sha1($_GET['pwd']);

		
		$conexion=mysqli_connect($hostname,$username,$password,$database);
		
		$consulta="SELECT id, nombre, pwd, correo FROM usuario WHERE nombre= '{$user}' AND pwd = '{$pwd}'";
		$resultado=mysqli_query($conexion,$consulta);
		
		if($consulta){
			
			if($reg=mysqli_fetch_assoc($resultado)){
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