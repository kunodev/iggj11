using System;

namespace ToyShop.Structure.Commands
{
    public abstract class ASyncCommand : ICommand
    {
        //Unused in this case
        #pragma warning disable 0067
        
        public event Action OnFinished;
        
        #pragma warning restore 0067
        
        public abstract void Execute();

        public CommandType GetCommandType()
        {
            return CommandType.Sync;
        }
    }
}