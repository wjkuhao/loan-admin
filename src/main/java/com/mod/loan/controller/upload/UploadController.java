package com.mod.loan.controller.upload;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mod.loan.common.enums.ResponseEnum;
import com.mod.loan.common.model.ResultMessage;
import com.mod.loan.config.Constant;
import com.mod.loan.util.AliOssStaticUtil;
import com.mod.loan.util.FileUtil;

@RestController
@RequestMapping(value = "upload")
public class UploadController {

	/**
	 * 上传图片文件
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "img_upload_ajax", method = { RequestMethod.POST })
	public String img_upload_ajax(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		if (!".jpg".equalsIgnoreCase(suffixName) && !".png".equalsIgnoreCase(suffixName) && !".jpeg".equalsIgnoreCase(suffixName) && !".gif".equalsIgnoreCase(suffixName)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件格式不正确！").toString();
		}
		// 大小不超过5MB
		if (file.getSize() > 1024 * 1024 * 5) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件大小超过限制！").toString();
		}
		return this.fileUpload(file, suffixName).toString();
	}

	/**
	 * 上传android版本包
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "version_android_upload_ajax", method = { RequestMethod.POST })
	public String version_android_upload_ajax(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		if (!".apk".equalsIgnoreCase(suffixName)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件格式不正确！").toString();
		}
		// 大小不超过20MB
		if (file.getSize() > 1024 * 1024 * 20) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件大小超过限制！").toString();
		}
		return this.fileUpload(file, suffixName).toString();
	}

	/**
	 * 上传ios版本包
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "version_ios_upload_ajax", method = { RequestMethod.POST })
	public String version_ios_upload_ajax(MultipartFile file) {
		String originalFileName = file.getOriginalFilename();
		String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."), originalFileName.length());
		if (!".ipa".equalsIgnoreCase(suffixName)) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件格式不正确！").toString();
		}
		// 大小不超过20MB
		if (file.getSize() > 1024 * 1024 * 20) {
			return new ResultMessage(ResponseEnum.M4000.getCode(), "文件大小超过限制！").toString();
		}

		Map<String, Object> data = new HashMap<>();
		InputStream is = null;
		String fileName = UUID.randomUUID().toString().replaceAll("-", "");
		String ipaName = fileName + ".ipa";
		String plistName = fileName + ".plist";
		String appName = originalFileName.substring(0, originalFileName.lastIndexOf("."));
		try {
			is = file.getInputStream();
			String ipaUrl = AliOssStaticUtil.ossUploadFile(Constant.env, is, ipaName);

			// 替换模板内的相关变量
			Resource resource = new ClassPathResource("static/plistModel.plist");
			is = FileUtil.fileReplace(resource.getInputStream(), "*ipa地址*", Constant.oss_static_prefix + ipaUrl);
			is = FileUtil.fileReplace(is, "*app名称*", appName);

			String plistUrl = AliOssStaticUtil.ossUploadFile(Constant.env, is, plistName);
			data.put("absolutePath", "itms-services://?action=download-manifest&url=" + Constant.oss_static_prefix + plistUrl);
			return new ResultMessage(ResponseEnum.M2000, data).toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResultMessage(ResponseEnum.M4000.getCode(), "上传失败，请重试").toString();
	}

	
	private ResultMessage fileUpload(MultipartFile file, String suffixName) {
		Map<String, Object> data = new HashMap<>();
		InputStream is = null;
		String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffixName;
		try {
			is = file.getInputStream();
			String fileUrl = AliOssStaticUtil.ossUploadFile(Constant.env, is, fileName);
			data.put("relativePath", fileUrl);
			data.put("absolutePath", Constant.oss_static_prefix + fileUrl);
			return new ResultMessage(ResponseEnum.M2000, data);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new ResultMessage(ResponseEnum.M4000.getCode(), "上传失败，请重试");
	}
}
