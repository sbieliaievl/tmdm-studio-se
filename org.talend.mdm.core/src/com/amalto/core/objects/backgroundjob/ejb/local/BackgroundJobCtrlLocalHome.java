/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.backgroundjob.ejb.local;

/**
 * Local home interface for BackgroundJobCtrl.
 * @xdoclet-generated at 28-09-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface BackgroundJobCtrlLocalHome
   extends javax.ejb.EJBLocalHome
{
   public static final String COMP_NAME="java:comp/env/ejb/BackgroundJobCtrlLocal";
   public static final String JNDI_NAME="amalto/local/core/backgroundjobctrl";

   public com.amalto.core.objects.backgroundjob.ejb.local.BackgroundJobCtrlLocal create()
      throws javax.ejb.CreateException;

}
