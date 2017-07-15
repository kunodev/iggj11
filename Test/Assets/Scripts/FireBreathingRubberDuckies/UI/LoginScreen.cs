using Network;
using Structure;
using UnityEngine;

namespace FireBreathingRubberDuckies.UI
{
    public class LoginScreen : AOneButtonOneTextField
    {
        
        protected override void OnButtonHit()
        {
            string name = _textInputField.text;
            Service.Get<UserData>().MyName = name;
            WWW loginReq = RequestFactory.CreateLogin(name);
            Service.Get<RequestHandler>().HandleRequest(loginReq, this.FinishLogin);
        }

        private void FinishLogin(GameState gs)
        {
            JSONObject myUser = gs.Users[gs.Users.Count - 1];
            Service.Get<UserData>().MyId = (int) myUser[NetworkConstants.USER_ID].i;
            Service.Get<UserData>().IsMaster =
                (bool) myUser[NetworkConstants.USER_ROLE].str.Equals(NetworkConstants.ROLE_MASTER);
            
            Service.Get<FBRDUISystem>().JoinGame(gs);
            Service.Get<StatePollingHandler>().Init();
        }
    }
}