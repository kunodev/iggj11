using UnityEngine;
using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public class LobbyEntry : MonoBehaviour
    {

        public Text playerName;
        public Image colorImage;
        public GameObject masterObject;

        public void Init(JSONObject player)
        {
            playerName = GetComponentInChildren<Text>();
            Image[] imgs = GetComponentsInChildren<Image>();
            colorImage = imgs[0];
            masterObject = imgs[1].gameObject;
            this.playerName.text = player[NetworkConstants.PARAM_NAME].str;
            this.colorImage.color = ParseColor(player[NetworkConstants.PARAM_COLOR].str);
            this.masterObject.SetActive(player[NetworkConstants.USER_ROLE].str.Equals(NetworkConstants.ROLE_MASTER));
        }

        private Color ParseColor(string col)
        {
            int r = int.Parse(col.Substring(1, 2), System.Globalization.NumberStyles.HexNumber);
            int g = int.Parse(col.Substring(3, 2), System.Globalization.NumberStyles.HexNumber);
            int b = int.Parse(col.Substring(5, 2), System.Globalization.NumberStyles.HexNumber);
            Color result = new Color(r/255f,g/255f,b/255f);
            return result;
        }
    }
}