/*
 * Generated by XDoclet - Do not edit!
 */
package com.amalto.core.ejb.remote;

/**
 * Primary key for Service.
 * @xdoclet-generated at 28-09-09
 * @copyright The XDoclet Team
 * @author XDoclet
 * @version ${version}
 */
public class ServicePK
   extends java.lang.Object
   implements java.io.Serializable
{

   public java.lang.String serviceName;

   public ServicePK()
   {
   }

   public ServicePK( java.lang.String serviceName )
   {
      this.serviceName = serviceName;
   }

   public java.lang.String getServiceName()
   {
      return serviceName;
   }

   public void setServiceName(java.lang.String serviceName)
   {
      this.serviceName = serviceName;
   }

   public int hashCode()
   {
      int _hashCode = 0;
         if (this.serviceName != null) _hashCode += this.serviceName.hashCode();

      return _hashCode;
   }

   public boolean equals(Object obj)
   {
      if( !(obj instanceof com.amalto.core.ejb.remote.ServicePK) )
         return false;

      com.amalto.core.ejb.remote.ServicePK pk = (com.amalto.core.ejb.remote.ServicePK)obj;
      boolean eq = true;

      if( obj == null )
      {
         eq = false;
      }
      else
      {
         if( this.serviceName != null )
         {
            eq = eq && this.serviceName.equals( pk.getServiceName() );
         }
         else  // this.serviceName == null
         {
            eq = eq && ( pk.getServiceName() == null );
         }
      }

      return eq;
   }

   /** @return String representation of this pk in the form of [.field1.field2.field3]. */
   public String toString()
   {
      StringBuffer toStringValue = new StringBuffer("[.");
         toStringValue.append(this.serviceName).append('.');
      toStringValue.append(']');
      return toStringValue.toString();
   }

}
