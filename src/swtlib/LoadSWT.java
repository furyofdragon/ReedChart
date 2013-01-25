package swtlib;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class LoadSWT{

	public static String getArchFilename(String prefix)
	{
		String swtver = "3.7.2";
		return prefix + "-" + swtver + "-" + getOSName() + "-" + getArchName() + ".jar";
	}
	
	private static String getOSName()
	{
		String osNameProperty = System.getProperty("os.name");
	
		if (osNameProperty == null)
		{
			throw new RuntimeException("os.name property is not set");
		}
		else
		{
			osNameProperty = osNameProperty.toLowerCase();
		}
	
		if (osNameProperty.contains("win"))
		{
			return "win32";
		}
		//else if (osNameProperty.contains("mac"))
		//{
		//	return "osx";
		//}
		else if (osNameProperty.contains("linux") || osNameProperty.contains("nix"))
		{
			return "gtk-linux";
		}
		else
		{
			throw new RuntimeException("Unknown OS name: " + osNameProperty);
		}
	}
	
	private static String getArchName()
	{
		String osArch = System.getProperty("os.arch");
	
		if (osArch != null && osArch.contains("64"))
		{
			return "x86_64";
		}
		else if (osArch != null && osArch.contains("32") || osArch.contains("86"))
		{
			return "x86";
		}
		else
		{
			throw new RuntimeException("Unknown architecture: " + osArch);
		}
	}
	
	public static void addJarToClasspath(File jarFile)
	{
		try
		{
			URL url = jarFile.toURI().toURL();
			URLClassLoader urlClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
			Class<?> urlClass = URLClassLoader.class;
			Method method = urlClass.getDeclaredMethod("addURL", new Class<?>[] { URL.class });
			method.setAccessible(true);
			method.invoke(urlClassLoader, new Object[] { url });
		}
		catch (Throwable t)
		{
			t.printStackTrace();
		}
	}
}