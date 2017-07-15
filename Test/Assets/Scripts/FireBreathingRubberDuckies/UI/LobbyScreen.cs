using System.Collections.Generic;
using System.Text;
using Network;
using Structure;
using UnityEngine;
using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public class LobbyScreen : AOneButtonSystem
    {
        public GameObject LobbyText;
        public GameObject LobbyEntryPrefab;
        private int _alreadyKnownUsers;
        public void Init()
        {
            this._btn.interactable = Service.Get<UserData>().IsMaster;
            _alreadyKnownUsers = 0;
        }
        
        public void CheckState(GameState gs)
        {
            List<JSONObject> users = gs.Users;
            for(; _alreadyKnownUsers<users.Count;_alreadyKnownUsers++)
            {
                JSONObject user = users[_alreadyKnownUsers];
                GameObject newGo = GameObject.Instantiate(this.LobbyEntryPrefab);
                newGo.transform.SetParent(this.LobbyText.transform);
                newGo.GetComponent<LobbyEntry>().Init(gs, (int) user[NetworkConstants.USER_ID].i);
            }
        }
        
        protected override void OnButtonHit()
        {
            WWW startGameRequest = RequestFactory.CreateStart();
            Service.Get<RequestHandler>().HandleRequest(startGameRequest, gs => { });
        }
    }
    
    
}