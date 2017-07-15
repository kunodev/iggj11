using FireBreathingRubberDuckies.UI;
using Network;
using Structure;
using UnityEngine;

namespace FireBreathingRubberDuckies
{
    public class FBRDUISystem : MonoBehaviour
    {
        public GameObject LoginScreen;
        public GameObject LobbyScreen;
        public GameObject QuestionScreen;
        public GameObject GamemasterScreen;
        public GameObject WorldScreen;
        public GameObject NextCountryScreen;

        private Animator _animator;

        public void Start()
        {
            _animator = GetComponent<Animator>();
        }

        public void JoinGame(GameState gs)
        {
            Service.Get<StatePollingHandler>().FreshGameState += UpdateUI;
            UpdateUI(gs);
        }

        private void UpdateUI(GameState obj)
        {
            _animator.SetInteger(UIAnimationConstants.PARAM_GAMESTATE, GameConstants.GAMESTATE_TO_STATE_ID[obj.State]);
            switch (obj.State)
            {
                case NetworkConstants.GAMESTATE_LOBBY : 
                    this.LobbyScreen.GetComponent<LobbyScreen>().CheckState(obj);
                    break;
                case NetworkConstants.GAMESTATE_WORLD :
                    this.WorldScreen.GetComponent<WorldScreen>().UpdateState(obj);
                    break;
            }
        }
    }
}