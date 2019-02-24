package com.mod.loan.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

@Table(name = "tb_manager_resource")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long pid;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "resource_url")
    private String resourceUrl;

    @Column(name = "resource_icon")
    private String resourceIcon;

    @Column(name = "resource_order")
    private Integer resourceOrder;

    @Column(name = "resource_status")
    private Integer resourceStatus;

    //是否拥有当前菜单权限,默认false
    @Transient
  	private boolean hasResource=false;		
  	//子权限菜单
    @Transient
  	private List<Resource> childResource;
    
    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return pid
     */
    public Long getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * @return resource_name
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * @param resourceName
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * @return resource_url
     */
    public String getResourceUrl() {
        return resourceUrl;
    }

    /**
     * @param resourceUrl
     */
    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    /**
     * @return resource_icon
     */
    public String getResourceIcon() {
        return resourceIcon;
    }

    /**
     * @param resourceIcon
     */
    public void setResourceIcon(String resourceIcon) {
        this.resourceIcon = resourceIcon;
    }

    /**
     * @return resource_order
     */
    public Integer getResourceOrder() {
        return resourceOrder;
    }

    /**
     * @param resourceOrder
     */
    public void setResourceOrder(Integer resourceOrder) {
        this.resourceOrder = resourceOrder;
    }

    /**
     * @return resource_status
     */
    public Integer getResourceStatus() {
        return resourceStatus;
    }

    /**
     * @param resourceStatus
     */
    public void setResourceStatus(Integer resourceStatus) {
        this.resourceStatus = resourceStatus;
    }

	public boolean isHasResource() {
		return hasResource;
	}

	public void setHasResource(boolean hasResource) {
		this.hasResource = hasResource;
	}

	public List<Resource> getChildResource() {
		return childResource;
	}

	public void setChildResource(List<Resource> childResource) {
		this.childResource = childResource;
	}
    
    
}