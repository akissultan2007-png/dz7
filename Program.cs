public void SendMessage(string message, IUser sender, string channel)
        {
            if (!_channels.ContainsKey(channel))
            {
                Console.WriteLine("Channel does not exist!");
                return;
            }

            foreach (var user in _channels[channel])
            {
                if (user != sender)
                    user.Receive(sender.Name + ": " + message);
            }
        }
    }

    public class User : IUser
    {
        private IMediator _mediator;
        public string Name { get; }

        public User(string name, IMediator mediator)
        {
            Name = name;
            _mediator = mediator;
        }

        public void Send(string message, string channel)
        {
            _mediator.SendMessage(message, this, channel);
        }

        public void Receive(string message)
        {
            Console.WriteLine(Name + " received -> " + message);
        }
    }

    // ================= MAIN =================

    class Program
    {
        static void Main()
        {
            Console.WriteLine("===== COMMAND =====");

            var light = new Light();
            var tv = new TV();
            var remote = new RemoteControl();

            var lightOn = new LightOnCommand(light);
            var tvOn = new TVOnCommand(tv);

            remote.SetCommand(lightOn);
            remote.PressButton();
            remote.PressUndo();

            var macro = new MacroCommand(new ICommand[] { lightOn, tvOn });
            remote.SetCommand(macro);
            remote.PressButton();

            Console.WriteLine("\n===== TEMPLATE METHOD =====");

            ReportGenerator pdf = new PdfReport();
            pdf.GenerateReport();

            ReportGenerator excel = new ExcelReport();
            excel.GenerateReport();

            Console.WriteLine("\n===== MEDIATOR =====");

            IMediator mediator = new ChatMediator();

            var user1 = new User("Maksat", mediator);
            var user2 = new User("Ali", mediator);

            mediator.AddUser(user1, "General");
            mediator.AddUser(user2, "General");

            user1.Send("Hello!", "General");
        }
    }
}
