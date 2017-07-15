using System.Collections.Generic;
using System.Linq;
using Network;
using UnityEngine;
using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public class WorldScreen : MonoBehaviour
    {
        public bool Inited;

        private Dictionary<string, Image> _overlays;
        
        public void Init(GameState gs)
        {
            Image[] allImages = GetComponentsInChildren<Image>();
            List<JSONObject> countries = gs.Countries;
            _overlays = new Dictionary<string, Image>();
            
            foreach (JSONObject country in countries)
            {
                string cc = country[NetworkConstants.COUNTRY_CODE].str;
                Image ci = allImages.FirstOrDefault(img => img.gameObject.name.StartsWith(cc));
                _overlays.Add(cc, ci);
            }
            foreach (KeyValuePair<string, Image> keyVal in _overlays)
            {
                keyVal.Value.material = new Material(keyVal.Value.material);
            }
            Inited = true;
        }

        public void UpdateState(GameState gs)
        {
            
        }
    }
}