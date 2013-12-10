package com.johnny.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * jad_complier文件处理类
 * 
 * @author Johnny
 * @date 10/12/2013
 * 
 */
public class FileUtil {

	public static void main(String[] args) {
		String fileUrl = "D:\\project\\workspace\\pt2\\src";
		String filterUrl = "package-info.java";
		try {
			fileClean(fileUrl, filterUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清理class注释,与底部文件名称
	 * java文件的clean
	 * @param fileUrl
	 *            文件夹路径 如：D:\\project
	 * @param filterUrl
	 *            过滤不处理的特殊文件名称如：XXX.java
	 * @throws IOException
	 */
	public static void fileClean(String fileUrl, String filterUrl)
			throws IOException {
		File file = new File(fileUrl);
		File[] fileList = file.listFiles();
		String path = "";
		String prefixPath = "";
		int tempNum = 0;
		String tempStr = "";
		String tempStr2 = "";
		if (fileList != null && fileList.length > 0) {
			//开始遍历文件夹
			for (int i = 0; i < fileList.length; i++) {
				//判断是否是文件夹
				if (fileList[i].isDirectory()) {
					//递归调用
					fileClean(fileList[i].getAbsolutePath(), filterUrl);
				} else {
					//文件开始处理
					path = fileList[i].getPath();
					System.out.println(path);
					//判断是否为正常需处理的文件(排除特殊文件)
					if (path.indexOf(filterUrl) == -1) {
						prefixPath = path.substring(path.lastIndexOf("."), path
								.length());
						//判断是否为java文件(只处理java文件)
						if (".java".equals(prefixPath)) {
							//根据文件路径读取文件
							FileReader reader = new FileReader(path);
							BufferedReader br = new BufferedReader(reader);
							String str = null;
							StringBuffer sb = new StringBuffer("");
							tempNum = 0;
							//逐行读取
							while ((str = br.readLine()) != null) {
								if (!"".equals(str)) {
									//判断是否为底部注释
									if (str.indexOf("/* Location:") != -1) {
										str = "";
									} else if (str.indexOf(" * Qualified Name:") != -1) {
										str = "";
									} else if (str.indexOf(" * JD-Core Version:") != -1) {	//是否为最后一个底部标记
										str = "";
										tempNum = 1;
									} else {
										tempStr2 = str;
										//开始处理行注释
										if (tempStr2.length() > 2) {
											tempStr = tempStr2.substring(0, 2);
											//判断开头是否为'/*'
											if ("/*".equals(tempStr)) {
												//特殊文件，判断开头是否为package(无注释，不处理)
												if (str.length() > 6
														&& !"package".equals(str.substring(0,6))) {
													if (tempNum != 1) {	//不是底部注释，是行注释、
														//去除行注释，从行注释后一位开始截取
														if (str.indexOf("*/ ") == 8)
															str = str.substring(10,str.length());
														else if (str.indexOf("*/ ") == 5)
															str = str.substring(8,str.length());
														else
															str = str.substring(9,str.length());
													} else {
														str = "";
													}
												} else {
													//已到底部注释位置，开始下个文件处理
													break;
												}
											} else if (" *".equals(tempStr)) {
												str = "";
											}
										}
									}
									sb.append(str + "\n");	//拼接文件的字符串
								}
								// System.out.println(sb);
							}
							//关闭文件读取
							br.close();
							reader.close();
							//开始文件写入
							FileWriter writer = new FileWriter(path);
							BufferedWriter bw = new BufferedWriter(writer);
							// bw.write(new
							// String(sb.toString().getBytes("iso-8859-1"),"UTF-8"));
							//写入文件
							bw.write(sb.toString());
							//关闭文件写入
							bw.close();
							writer.close();
						} else {
							continue;
						}
					} else {
						continue;
					}
				}
			}
		}
	}
}
