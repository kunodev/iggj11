using System.Collections.Generic;
using System.Linq;
using UnityEngine;

namespace Network
{
    public class GameState
    {
        private JSONObject _state;

        public List<JSONObject> Users
        {
            get
            {
                JSONObject jsonObject = this._state[NetworkConstants.STATE_USERS];
                return jsonObject.list;
            }
        }

 

        public string State
        {
            get { return this._state[NetworkConstants.STATE_GAMESTATE].str; }
        }

        public GameState(JSONObject state)
        {
            this._state = state;
        }

        public JSONObject CountryOwners
        {
            get { return _state[NetworkConstants.STATE_DATA][NetworkConstants.STATE_COUNTRIES]; }
        }
        
        public List<JSONObject> Countries
        {
            get { return this._state[NetworkConstants.STATE_COUNTRIES].list; }
        }

        public Color GetColorOfId(int id)
        {
             JSONObject player = GetPlayerById(id);
            return ParseColor(player[NetworkConstants.PARAM_COLOR].str);
        }
        public JSONObject GetPlayerById(int id)
        {
            return Users.FirstOrDefault(u => u.i == id);
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