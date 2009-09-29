/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.objects.routing.v2.ejb.remote;

/**
 * Remote interface for RoutingRuleCtrl.
 * @xdoclet-generated at 28-09-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public interface RoutingRuleCtrl
   extends javax.ejb.EJBObject
{
   /**
    * Creates or updates a menu
    * @throwsXtentisxception 
    */
   public com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK putRoutingRule( com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJO routingRule )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get menu
    * @throws XtentisException
    */
   public com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJO getRoutingRule( com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Get a RoutingRule - no exception is thrown: returns null if not found
    * @throws XtentisException
    */
   public com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJO existsRoutingRule( com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Remove a RoutingRule
    * @throws XtentisException
    */
   public com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK removeRoutingRule( com.amalto.core.objects.routing.v2.ejb.RoutingRulePOJOPK pk )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

   /**
    * Retrieve all RoutingRule PKs
    * @throws XtentisException
    */
   public java.util.Collection getRoutingRulePKs( java.lang.String regex )
      throws com.amalto.core.util.XtentisException, java.rmi.RemoteException;

}
