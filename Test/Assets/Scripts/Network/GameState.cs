using System.Collections.Generic;

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
    }
}