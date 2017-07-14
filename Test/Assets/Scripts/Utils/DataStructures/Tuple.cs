namespace ToyShop.Utils.DataStructures
{
    public class Tuple<TKey, TValue> 
    {
        public TKey key;
        public TValue value;


        public Tuple(TKey key, TValue val)
        {
            this.key = key;
            this.value = val;
        }
    }
}