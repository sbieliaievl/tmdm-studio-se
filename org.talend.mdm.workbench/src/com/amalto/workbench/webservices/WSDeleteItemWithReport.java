// This class was generated by the JAXRPC SI, do not edit.
// Contents subject to change without notice.
// JAX-RPC Standard Implementation （1.1.2_01，编译版 R40）
// Generated source version: 1.1.2

package com.amalto.workbench.webservices;


public class WSDeleteItemWithReport {
    protected com.amalto.workbench.webservices.WSItemPK wsItemPK;
    protected java.lang.String source;
    protected java.lang.String operateType;
    protected java.lang.String updatePath;
    protected java.lang.String user;
    protected java.lang.Boolean invokeBeforeSaving;
    protected java.lang.Boolean pushToUpdateReport;
    
    public WSDeleteItemWithReport() {
    }
    
    public WSDeleteItemWithReport(com.amalto.workbench.webservices.WSItemPK wsItemPK, java.lang.String source, java.lang.String operateType, java.lang.String updatePath, java.lang.String user, java.lang.Boolean invokeBeforeSaving, java.lang.Boolean pushToUpdateReport) {
        this.wsItemPK = wsItemPK;
        this.source = source;
        this.operateType = operateType;
        this.updatePath = updatePath;
        this.user = user;
        this.invokeBeforeSaving = invokeBeforeSaving;
        this.pushToUpdateReport = pushToUpdateReport;
    }
    
    public com.amalto.workbench.webservices.WSItemPK getWsItemPK() {
        return wsItemPK;
    }
    
    public void setWsItemPK(com.amalto.workbench.webservices.WSItemPK wsItemPK) {
        this.wsItemPK = wsItemPK;
    }
    
    public java.lang.String getSource() {
        return source;
    }
    
    public void setSource(java.lang.String source) {
        this.source = source;
    }
    
    public java.lang.String getOperateType() {
        return operateType;
    }
    
    public void setOperateType(java.lang.String operateType) {
        this.operateType = operateType;
    }
    
    public java.lang.String getUpdatePath() {
        return updatePath;
    }
    
    public void setUpdatePath(java.lang.String updatePath) {
        this.updatePath = updatePath;
    }
    
    public java.lang.String getUser() {
        return user;
    }
    
    public void setUser(java.lang.String user) {
        this.user = user;
    }
    
    public java.lang.Boolean getInvokeBeforeSaving() {
        return invokeBeforeSaving;
    }
    
    public void setInvokeBeforeSaving(java.lang.Boolean invokeBeforeSaving) {
        this.invokeBeforeSaving = invokeBeforeSaving;
    }
    
    public java.lang.Boolean getPushToUpdateReport() {
        return pushToUpdateReport;
    }
    
    public void setPushToUpdateReport(java.lang.Boolean pushToUpdateReport) {
        this.pushToUpdateReport = pushToUpdateReport;
    }
}
