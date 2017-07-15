using Network;
using UnityEngine;
using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public class LobbyEntry : MonoBehaviour
    {

        public Text playerName;
        public Image colorImage;
        public GameObject masterObject;

        public void Init(GameState gs, int id)
        {
            playerName = GetComponentInChildren<Text>();
            Image[] imgs = GetComponentsInChildren<Image>();
            colorImage = imgs[0];
            masterObject = imgs[1].gameObject;
            JSONObject player = gs.GetPlayerById(id);
            this.playerName.text = player[NetworkConstants.PARAM_NAME].str;
            this.colorImage.color = gs.GetColorOfId(id);
            this.masterObject.SetActive(player[NetworkConstants.USER_ROLE].str.Equals(NetworkConstants.ROLE_MASTER));
        }
    }
}