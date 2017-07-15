using UnityEngine;
using UnityEngine.UI;

namespace FireBreathingRubberDuckies.UI
{
    public abstract class AOneButtonSystem : MonoBehaviour
    {
        protected Button _btn;
        
        public virtual void Start()
        {
            this._btn = this.gameObject.GetComponentInChildren<Button>();
            this._btn.onClick.AddListener(OnButtonHit);
        }

        protected abstract void OnButtonHit();
        
        

    }
}