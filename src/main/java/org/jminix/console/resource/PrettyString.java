package org.jminix.console.resource;

import java.util.Set;

import javax.management.openmbean.CompositeData;

public class PrettyString {

	public static String toString(Object o){
		return toString(o, 0, true);
	}
	
	public static String toString(Object o, int tabs, boolean startObject){
		StringBuilder sb = new StringBuilder();
		
		if(o == null){
			for(int i = 0; i < tabs; i++) sb.append("\t");
			sb.append("null");
		}else if(o.getClass().isArray()){
			Object arr[] = (Object[])o;
			
			for(int i = 0; i < tabs; i++) sb.append("\t"); // array + 0
			sb.append("[#").append(arr.length).append("\n");
			
			int ind = 0;
			for(Object a : arr){
				for(int i = 0; i < tabs + 1; i++) sb.append("\t");
				sb.append("[$").append(ind).append("\n"); // object + 1
				
				sb.append( toString(a, tabs + 2, false) ); // inner content + 2
				
				for(int i = 0; i < tabs + 1; i++) sb.append("\t");
				sb.append("]\n");
				
				ind ++;
			}
			
			for(int i = 0; i < tabs; i++) sb.append("\t");
			sb.append("]\n");
			
		}else if(o instanceof CompositeData){
			if(startObject){
				for(int i = 0; i < tabs; i++) sb.append("\t"); // array + 0
				sb.append("[\n");
			}
			
			CompositeData s = (CompositeData)o;
			Set<String> keys = s.getCompositeType().keySet();
			for(String k : keys){
				for(int i = 0; i < tabs + ( startObject ? 0 : 1 ); i++) sb.append("\t");
				sb.append(k).append(":\n");
				
				sb.append( toString(s.get(k), tabs + ( startObject ? 1 : 2 ), true) ); // inner content + 1
				
				sb.append("\n\n");
			}
			
			if(startObject){
				for(int i = 0; i < tabs; i++) sb.append("\t"); // array + 0
				sb.append("]\n");
			}
		}else{
			for(int i = 0; i < tabs; i++) sb.append("\t");
			sb.append(o);
		}
		
		return sb.toString();
	}
	
}
