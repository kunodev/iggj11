using System;

namespace ToyShop.Structure.Commands
{
    public interface ICommand
    {
        event Action OnFinished;
        
        void Execute();

        CommandType GetCommandType();
    }

    public enum CommandType
    {
        Sync, Async
    }
}