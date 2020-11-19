/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package object;

import java.util.ArrayList;


/**
 *
 * @author Jose Pio y Giselt Parra
 */
public class UserActions {
    private int pointer;
    private int firstAction;
    private int lastAction;
    private int count;
    private int capacity;
    private ArrayList stack;
    private boolean ready;
    
    public UserActions(int cap) {
        capacity = cap;
        stack = new ArrayList<Stack>(capacity);
        count = 0;
        ready = false;
    }  
    
    public void addStep(Stack step) {
        if(count == 0) {
            addFirstAction(step);
        } else {
            addAction(step);
        }        
    }
    
    public void addAction(Stack step) {
        pointer = (pointer + 1) % capacity;
        lastAction = pointer;
        if(lastAction == firstAction) firstAction = (firstAction + 1) % capacity;
        count++;
        if(count <= capacity) {
            System.out.println("Pointer Add:" + pointer);
            stack.add(step);
        } else {
            System.out.println("Pointer Set: " + pointer);
            stack.set(pointer, step);
        }
    }
    
    public void addFirstAction(Stack step) {
        stack.add(step);
        count = 1; // count++
        pointer = 0;
        firstAction = 0;
        lastAction = 0;
        System.out.println("Pointer Init: " + pointer);

    }
    
    public void resetSteps() {
        stack.clear();
        count = 0;
        pointer = -1;
        firstAction = -1;
        lastAction = -1;
        ready = false;
        System.out.println("RESET " + pointer);
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
    
     
    public boolean canIncreaseView(int num) {
        for(int i = 0; i <= num; i++) {
            int fordward = pointer + i > capacity - 1 ? Math.abs(capacity - (pointer + i)) : pointer + i;
            if(fordward == lastAction) {
                return num - i == 0;
            }
        }
        return true;
    }
    
    
    
    public boolean canDecreaseView(int num) {
        for(int i = 0; i <= num; i++) {
            int backward = pointer - i < 0 ? capacity - Math.abs(pointer -i) : pointer - i;
            if(backward == firstAction) {
                return num - i == 0;
            }
        }
        return true;
    }
    
    public void increasePointer() {
        pointer = (pointer + 1) % capacity;   
        System.out.println("Pointer: " +pointer);
        if(ready == false) ready = true;
    }
    
    public void decreasePointer() {
        pointer = pointer == 0 ? capacity - 1 : pointer - 1;
        System.out.println("Pointer: " +pointer);
        if(ready == false) ready = true;
    }
    
    public Stack getStackPointer() {
        return (Stack) stack.get(pointer);        
    }
    
    public Stack getPointer(int point) {
        return (Stack) stack.get(point);        
    }

    public int getPointer() {
        return pointer;
    }

    public int getCapacity() {
        return capacity;
    }
    
    
    
}
