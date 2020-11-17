/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;


/**
 *
 * @author Jose Pio
 */
public class UserActions {
    private int pointer;
    private int firstAction;
    private int lastAction;
    private int count;
    private int capacity;
    private ArrayList stack;
    
    public UserActions(int cap) {
        capacity = cap;
        stack = new ArrayList<Stack>(capacity);
        count = 0;
    }  
    
    public void addStep(Stack step) {
        if(count == 0) {
            addFirstAction(step);
        } else {
            addAction(step);
        }
        
//        System.out.println("Pointer: " +pointer + "  - Counter: " + count);
    }
    
    public void addAction(Stack step) {
        pointer = (pointer + 1) % capacity;
        lastAction = pointer;
        if(lastAction == firstAction) firstAction = (firstAction + 1) % capacity;
        count++;
        if(count <= capacity) {
            stack.add(step);
        } else {
            stack.set(pointer, step);
        }
    }
    
    public void addFirstAction(Stack step) {
        stack.add(step);
        count = 1; // count++
        pointer = 0;
        firstAction = 0;
        lastAction = 0;
    }
    
    public void resetSteps() {
        stack.clear();
        count = 0;
        pointer = -1;
        firstAction = -1;
        lastAction = -1;
    }
    
    public boolean canIncrease() {
        if(pointer != lastAction) {
            return true;
        } else{
            return false;
        }
    }
    
    public boolean canDecrease() {
        if(pointer != firstAction) {
            return true;
        } else{
            return false;
        }
    }
    
    public void increasePointer() {
        pointer = (pointer + 1) % capacity;   
//        System.out.println("Pointer: " +pointer + "  - Counter: " + count);        
    }
    
    public void decreasePointer() {
        pointer = pointer == 0 ? capacity - 1 : (pointer - 1) % capacity;
//        System.out.println("Pointer: " +pointer + "  - Counter: " + count);
    }
    
    public Stack getStackPointer() {
        return (Stack) stack.get(pointer);        
    }

    
}
