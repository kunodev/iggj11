using System;
using System.Collections;
using Network;
using Structure;
using UnityEngine;

namespace FireBreathingRubberDuckies
{
    public class StatePollingHandler
    {
        private const float WAIT_POLL_TIME = 1f;
        private bool _gameRunning;
        public event Action<GameState> FreshGameState;

        public IEnumerator PollStatus()
        {
            WaitForSeconds wait = new WaitForSeconds(WAIT_POLL_TIME);
            
            while (_gameRunning)
            {
                WWW thePollingRequest = new WWW(NetworkConstants.SERVER_URL);
                yield return thePollingRequest;
                JSONObject json = new JSONObject(thePollingRequest.text);
                GameState gs = new GameState(json);
                OnFreshGameState(gs);
                //TODO: foobar
            }
        }

        public void Init()
        {
            this._gameRunning = true;
            Service.Get<CoroutineManager>().AddCoroutine(PollStatus());
        }

        protected virtual void OnFreshGameState(GameState obj)
        {
            Action<GameState> handler = FreshGameState;
            if (handler != null) handler(obj);
        }
    }
}