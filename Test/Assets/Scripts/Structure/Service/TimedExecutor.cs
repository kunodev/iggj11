using System;
using System.Collections.Generic;
using UnityEngine;

namespace ToyShop.Structure
{
    public class TimedExecutor : MonoBehaviour
    {
        //TODO: switch with a heap structure
        private List<TimedTask> _executeQueue;

        public void Awake()
        {
            _executeQueue = new List<TimedTask>();
        }
        
        public TimedTask ExecuteDelayed(Action a, float delay)
        {
            if (a == null)
            {
                return null;
            }
            float t = Time.time + delay;
            TimedTask task = new TimedTask(a , t);
            this._executeQueue.Add(task);
            Debug.Log("Task added " + a.Method.Name);
            return task;
        }

        public void Update()
        {
            int i = 0;
            while (_executeQueue.Count > i && _executeQueue[i].Time <= Time.time)
            {
                _executeQueue[i].Task();
                Debug.Log("Task Executed " + _executeQueue[i].Task.Method.Name);
                i++;
            }
            if (i > 0)
            {
                _executeQueue.RemoveRange(0, i);
                _executeQueue.Sort();        
            }
        }
    }
    
    

    public class TimedTask : IComparable
    {
        public Action Task;
        public float Time;

        public TimedTask(Action a, float t)
        {
            this.Task = a;
            this.Time = t;
        }

        public int CompareTo(object obj)
        {
            return this.Time.CompareTo(obj);
        }
    }
}