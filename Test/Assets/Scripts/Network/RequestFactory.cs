using UnityEngine;

namespace Network
{
    public class RequestFactory
    {
        public static WWW CreateLogin(string username)
        {
            WWWForm postparams = new WWWForm();
            postparams.AddField(NetworkConstants.ACTION, NetworkConstants.ACTION_LOGIN);
            postparams.AddField(NetworkConstants.PARAM_NAME, username);
            WWW resul = new WWW(NetworkConstants.SERVER_URL, postparams);
            return resul;
        }
        
        public static WWW CreateStart()
        {
            WWWForm postparams = new WWWForm();
            postparams.AddField(NetworkConstants.ACTION, NetworkConstants.ACTION_START);
            WWW resul = new WWW(NetworkConstants.SERVER_URL, postparams);
            return resul;
        }
        
        public static WWW CreateAnswer(int userId, string answer)
        {
            WWWForm postparams = new WWWForm();
            postparams.AddField(NetworkConstants.ACTION, NetworkConstants.ACTION_ANSWER);
            postparams.AddField(NetworkConstants.PARAM_USER_ID, userId);
            postparams.AddField(NetworkConstants.PARAM_ANSWER, answer);
            WWW resul = new WWW(NetworkConstants.SERVER_URL, postparams);
            return resul;
        }
        
        public static WWW CreateAnswerState(int userId, string answerstate)
        {
            WWWForm postparams = new WWWForm();
            postparams.AddField(NetworkConstants.ACTION, NetworkConstants.ACTION_ANSWER_CHECK);
            postparams.AddField(NetworkConstants.PARAM_USER_ID, userId);
            postparams.AddField(NetworkConstants.PARAM_ANSWER, answerstate);
            WWW resul = new WWW(NetworkConstants.SERVER_URL, postparams);
            return resul;
        }
        
        public static WWW CreateAnswerCheckSubmit()
        {
            WWWForm postparams = new WWWForm();
            postparams.AddField(NetworkConstants.ACTION, NetworkConstants.ACTION_ANSWER_CHECK_SUBMIT);
            WWW resul = new WWW(NetworkConstants.SERVER_URL, postparams);
            return resul;
        }
    }
}