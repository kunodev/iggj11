using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace Structure
{
    public class CoroutineManager : MonoBehaviour
    {

        public void AddCoroutine(IEnumerator corot)
        {
            base.StartCoroutine(corot);
        }


    }
}