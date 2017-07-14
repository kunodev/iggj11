using System.Collections;
using UnityEngine;

namespace FireBreathingRubberDuckies
{
    public class StatePollingHandler
    {

        public bool GameRunning;
        public const string SERVER_URL = "localhost:8080/" ;


        public IEnumerator PollStatus()
        {
            WWW thePollingRequest = new WWW(SERVER_URL + NetworkConstants.STATUS_REQ);
            while (GameRunning)
            {
                yield return thePollingRequest;
                JSONObject json = new JSONObject(thePollingRequest.text);
                //TODO: foobar
            }
        }
    }
}