using System.Collections.Generic;

namespace FireBreathingRubberDuckies
{
    public class GameConstants
    {
        public const string LOCAL_ANNOUNCEMENT_STATE = "localAnnouncement";
        
        public static readonly Dictionary<string, int> GAMESTATE_TO_STATE_ID = new Dictionary<string, int>()
        {
            {NetworkConstants.GAMESTATE_LOBBY, 1},
            {NetworkConstants.GAMESTATE_WORLD, 2},
            {NetworkConstants.GAMESTATE_QUESTION,3},
            {NetworkConstants.GAMESTATE_ANSWER_CHECK,4},
            {LOCAL_ANNOUNCEMENT_STATE, -2}
        };
    }
}