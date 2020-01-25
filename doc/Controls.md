
# Introduction

    A Control is an abstract class that implements the KeyListener and
MouseListener interfaces. It contains one inner class that represents
an Action that can be executed by the Control. Tied in with Controls are
ControlEvents and ControlListeners. A Control can cause a ControlEvent
which is then sent to all of its ControlListeners. The Control class
also contains some shared methods to manipulate and observe instances of
the Control class. There are several classes derived from the Control
class including Button, ScrollMenu, and ScrollMenuItem. Part of the
reason I created these classes was to practice making my own control
devices instead of using Java's classes and to see what I could do with
my own creations.




# The ControlListener Interface

    The ControlListener interface is a very small interface consisting of
one method, specifically, the method

```java
public void controlOccurred (ControlEvent e)
```

Each implementor of this interface must have a version of this method, 
which, in the simplest of cases, is usually just a call to some kind of
repaint method.




# The Control Class

    The Control class is the organizing superclass of all Control sub-
classes, representing some kind of UI control system.

## Methods

    The Control class has several methods, here we list and explain their
functionality.

```java
public void addControlListener (ControlListener cl)
```

This methods adds the given ControlListener to an internal list of
ControlListeners. This list is kept private so that a subclass cannot
specify which ControlListener they send updates to, instead they must
use the next method:

```java
public void updateControlListeners (ControlEvent e)
```

This method simply calls all of the ControlListener's controlOccurred
method. The next method is a basic utility method, namely

```java
public boolean over (int x, int y)
```

which determines if the specified point sits over the Control object. One
thing to note is that this method can be incorrect if the implementations
set up the coordinates of the Control incorrectly.

```java
public void setVisible (boolean visible)

public void setDisabled (boolean disable)
```

These methods are simple methods to alter the state of the Control and
depend on the implementation. The idea is that when a Control is disabled
it is still drawn, but does not function (to show this I have a dimming
effect enabled). When a Control is not visible it is neither drawn or
executes an action upon its location being clicked.

There other methods are just empty implementations of the KeyListener and
MouseListener methods as well as an empty paint method. This way it is up
to the implementor which to override in the subclass.

## Notes

When using any descendent of the Control class, remember to add the parent
ControlListener to the Control. Moreover, you should add the Control as a
mouse and key listener in order for the Control to recieve mouse and key
input (as is most often desired).




# The Subclasses of Control

## Button

    The Button class represents a standard Button on a display. When a
Button is disabled, it is dimmed and will not change color upon being
clicked. When the Button is not set visible, it will not be drawn even
with a call to its paint method is made. The functionality of a Button is
simple: when it is clicked, it highlights itself and invokes its action.

### Methods

Most of the methods are derived from the Control class, with the exception
of the reset method:

```java
public void reset ()
```

which resets the color, clicked state, and visibility of the button.

### Notes

Remember that the Button's constructor requires the x and y values to be
the center coordinates of the button.


## ScrollMenu

    The ScrollMenu subclass represents some sort of menu with a variable
number of items. In the event that the number of items surpasses the space
alloted, the scroll bar becomes activated, allowing the user to scroll
up and down to see the hidden items. When an item is selected, the Scroll-
Menu's action is envoked. The ScrollMenu is actually both a Control and a 
ControlListener since the ScrollMenuItems themselves are Controls.

### Methods

One of the most important methods available is the method to add an item
to the menu.

```java
public void addItem (String title, String text)
```

Not surprisingly, this method adds an item with the specified title and
text. The other most important method is:

```java
public String getSelected ()
```

which returns null if no item on the menu has been selected and retrieves
the title of the selected item otherwise. The two other methods that are
of importance are

```java
public boolean overScroll (int x, int y)

public boolean overScrollBar (int x, int y)
```

Their names really describe what they do, simply tell the caller if the
point is over the menu scroller or the menu scroll bar, respectively.

### Notes

Note that for each panel with a ScrollMenu, the ScrollMenu should be drawn
last, otherwise other items will not necessarily be drawn to the screen.

## ScrollMenuItem

    ScrollMenuItems are handled by the ScrollMenu and, more
specifically, the `<addItem (String title, String text)>`
method.