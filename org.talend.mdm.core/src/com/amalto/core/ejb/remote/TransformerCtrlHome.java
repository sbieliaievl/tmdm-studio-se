/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.remote;

/**
 * Home interface for TransformerCtrl.
 * @deprecated - use TransformerV2 package
 * @xdoclet-generated at 28-09-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface TransformerCtrlHome
   extends javax.ejb.EJBHome
{
   public static final String COMP_NAME="java:comp/env/ejb/TransformerCtrl";
   public static final String JNDI_NAME="amalto/remote/core/transformerctrl";

   public com.amalto.core.ejb.remote.TransformerCtrl create()
      throws javax.ejb.CreateException,java.rmi.RemoteException;

}
