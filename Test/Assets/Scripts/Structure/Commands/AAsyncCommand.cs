using System;

namespace ToyShop.Structure.Commands
{
    public abstract class AAsyncCommand : ICommand
    {
        public event Action OnFinished;
        
        public abstract void Execute();

        public CommandType GetCommandType()
        {
            return CommandType.Async;
        }

        protected void Finished()
        {
            OnFinished();
        }
    }
}