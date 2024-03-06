package command;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class QueueStack {
    private List<Command> dataCollection;

    public QueueStack() {
        dataCollection = new LinkedList<>();
    }

   public void push(Command item) {
        dataCollection.add(0, item);
    }

   public Optional<Command> pop() {
        if(dataCollection.size() > 0)
            return Optional.of(dataCollection.remove(0));
        else
            return Optional.empty();
    }

   public void clear() {
        dataCollection.clear();
    }
   public boolean isEmpty(){
	   return dataCollection.isEmpty();
   }
}
