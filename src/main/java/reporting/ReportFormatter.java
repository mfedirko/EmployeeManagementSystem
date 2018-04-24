package reporting;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;

public class ReportFormatter<T> {
	
	Delim delim;
	
	public ReportFormatter(Delim delimeter){
		this.delim=delimeter;
	}
	public enum Delim {
		 COMMA(','),TAB('\t'),SPACE(' '),COLON(':');
			
			char d;
			Delim(char c){
				d=c;
			}
			
			char getValue() {
				return this.d;
			};
			void setValue(char c) {
				this.d=c;
			}
		}
	
	
	
	
	
	public String formatAsDelimitedFile(List<T> objects) throws IllegalArgumentException, IllegalAccessException {
		String res="";
		for (T o: objects) {
			for (Field f: o.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				res+= (f.get(o)).toString()+delim.getValue();
			}
			res=res.substring(0,res.length()-1)+"\n";
		}
		
		return res;
	}
	
	public void export(String filename,String output) {
		File f;
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(
	              new FileOutputStream(filename), "utf-8"))) {
			
				writer.write(output);
		} catch (IOException e) {
			System.out.println("Access denied for file "+filename);
			e.printStackTrace();
		}
			catch (Exception e) {
			  e.printStackTrace();
			}
		
		
	}

}
