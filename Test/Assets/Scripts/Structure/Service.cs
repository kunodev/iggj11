using System;
using System.Collections.Generic;
using UnityEngine;

namespace Structure
{
    public class Service
    {

        private static Dictionary<Type, object> _services = new Dictionary<Type,object>();
        
        public static IServ Get<IServ>()
        {
            if (_services.ContainsKey(typeof(IServ)))
            {
                return (IServ) _services[typeof(IServ)];
            }
            throw new ArgumentException("Service not located in Registry");
        }

        public static void Set<IServ>(IServ newService)
        {
            if (_services.ContainsKey(typeof(IServ)))
            {
                Debug.LogWarning("Service is being overriden");
                _services[typeof(IServ)] = newService;
            }
            _services.Add(typeof(IServ), newService);
        }

        public static void UnMap<T>()
        {
            _services.Remove(typeof(T));
        }
    }
}