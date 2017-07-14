using System;
using UnityEngine;

namespace ToyShop.Structure.Commands
{
    public class CommandSequence
    {
        private int _toDos;
        private int _current;
        private ICommand[] _commands;

        public CommandSequence(ICommand[] commands)
        {
            this._commands = commands;
        }

        public event Action OnFinishedAction;
        
        public void Execute()
        {
            _toDos = _commands.Length;
            _current = 0;
            ExecuteRecurse();
        }

        private void ExecuteRecurse()
        {
            if (_current >= _toDos)
            {
                if (OnFinishedAction != null) OnFinishedAction();
                return;
            }

            if (_commands[_current].GetCommandType() == CommandType.Sync)
            {
                _commands[_current].Execute();
                _current++;
                ExecuteRecurse();
            }
            else
            {
                _commands[_current].OnFinished += ExecuteRecurse;
                _current++;
                _commands[_current-1].Execute();
                Debug.Log("Exec Async");
            }
        }
        
    }
}