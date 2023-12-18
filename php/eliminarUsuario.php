<?PHP
$hostname="localhost";
$database="taskmaster";
$username="root";
$password="";
$json=array();

	if(isset($_GET["id"])){
		$id=$_GET['id'];
		

	
		$conexion=mysqli_connect($hostname,$username,$password,$database);
		
		$consulta="DELETE FROM usuario WHERE id='{$id}'";
		$resultado=mysqli_query($conexion,$consulta);

		if($consulta){
			$consulta="SELECT * FROM usuario";
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
			 $json['datos'][]=$results;
			 echo json_encode($json);
		 }
		 
	 }
	 else{   
			 $results["id"]='';
				$results["nombre"]='';
			 $results["pwd"]='';
			 $json['datos'][]=$results;
			 echo json_encode($json);
		 }
		
?>