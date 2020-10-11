package com.github.ruanshengwei;

import java.io.File;
import java.io.FileInputStream;

public class MyClassLoader extends ClassLoader{

    public Class<?> load(String namefile){

        try {
            FileInputStream in=new FileInputStream(namefile);
		    byte[] classbyte=new byte[(int)new File(namefile).length()];
	    	int readsize;
		    readsize=in.read(classbyte);
		    in.close();
            return defineClass(null, classbyte, 0,readsize);
        }catch (Exception e){
            return null;
        }

	}
}
