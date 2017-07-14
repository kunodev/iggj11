﻿using Structure;
using UnityEngine;

namespace FireBreathingRubberDuckies
{
    public class GameInit : MonoBehaviour
    {
        public FBRDUISystem UiSystem; 
        
        public void Start()
        {
            Service.Set(this.gameObject.AddComponent<CoroutineManager>());
            Service.Set(UiSystem);
        }
    }
}