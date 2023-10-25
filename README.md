# SMSSimulation

This is my implementation of the SMS Simulation Exercise.  
I have created five classes as below:  
1. Message - Represents a single SMS message.  
2. Producer - Generates a set number of random messages.  
3. Sender - Simulates sending of SMS messages.  
4. ProgressMonitor - Monitors and displays the progress of sent and failed messages.  
5. Main - Entry point to start the simulation.

My assumptions:  
1. Message generating and message picking are concurrent. This saves time as the producer can produce messages while senders are picking them up
2. Message picking and progress monitoring are concurrent. The progress monitor would be able to update every N seconds (configurable) to display the progress of sending the messages
