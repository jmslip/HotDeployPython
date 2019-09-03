package com.prosegur.active;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Arquivos que foram alterados
 * 
 * @author Lucas Gabriel
 *
 */
public class FilesSaved implements Serializable{

	private static final String DIR_SERVER = "C:\\ambiente\\sw\\Oracle\\Middleware12212\\Oracle_Home\\user_projects\\domains\\cash\\servers\\AdminServer";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8771809304032750139L;
	
	private static FilesSaved instance;
	
	public static FilesSaved getInstance() {
		if(instance==null) {
			instance=new FilesSaved();
		}
		return instance;
	}
	
	private Set<String> files;
	
	
	public FilesSaved() {
		files=new HashSet<>();
	}
	
	public void add(String file) {
		files.add(file);
	}

	public Set<String> getFiles() {
		return files;
	}

	public void reload() throws IOException {
		Path server = Paths.get(DIR_SERVER+File.separator+"tmp"+File.separator+"_WL_user");
		List<String> ears = getEARs();
		for (String file : files) {
			Path pathFileChanged = Paths.get(file);
			final File fileChanged = pathFileChanged.toFile();
			try(Stream<Path> stream = Files.find(server, Integer.MAX_VALUE, searchFileChangedOnServer(fileChanged,ears))){
				Path findedFile = stream.findFirst().orElse(null);
				if(Objects.nonNull(findedFile)) {				
					Files.copy(pathFileChanged, findedFile,StandardCopyOption.REPLACE_EXISTING);
				}
			}catch (Exception e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			}
		}
	}
	
	public List<String> getEARs() throws IOException{
		try(Stream<Path> stream = Files.walk(Paths.get(DIR_SERVER+File.separator+"upload"))){
			return stream.map(t ->t.toFile().getName()).collect(Collectors.toList());
		}
	}

	private BiPredicate<Path, BasicFileAttributes> searchFileChangedOnServer(File fileChanged, List<String> ears) {
		return (path,basic)->{
			File fileSearched = path.toFile();
			return !basic.isDirectory() && fileSearched.getName().equals(fileChanged.getName()) && containsEar(fileSearched,ears);
		};
	}

	private boolean containsEar(File fileSearch, List<String> ears) {
		return ears.stream().anyMatch(t-> fileSearch.getAbsolutePath().contains(t));
	}
}
