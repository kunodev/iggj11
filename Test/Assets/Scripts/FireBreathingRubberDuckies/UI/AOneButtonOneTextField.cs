using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public abstract class AOneButtonOneTextField : AOneButtonSystem
    {
        protected InputField _textInputField;

        public override void Start()
        {
            base.Start();
            _textInputField = GetComponentInChildren<InputField>();
        }
    }
}