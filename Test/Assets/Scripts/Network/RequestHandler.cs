using System;
using System.Collections;
using Structure;
using UnityEngine;

namespace Network
{
    public class RequestHandler
    {

        private bool _busy = false;
        
        public void HandleRequest(WWW req, Action<GameState> callback)
        {
            if (!_busy)
            {
                Service.Get<CoroutineManager>().AddCoroutine(this.Coroutine(req, callback));
                _busy = true;
            }
        }

        IEnumerator Coroutine(WWW req,  Action<GameState> callback)
        {
            yield return req;
            Debug.Log(req.error);
            
            JSONObject state = JSONObject.Create(req.text);
            callback(new GameState(state));
            _busy = false;
        }
    }
}