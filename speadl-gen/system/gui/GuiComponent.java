package system.gui;

import system.gui.interfaces.IGui;

@SuppressWarnings("all")
public abstract class GuiComponent {
  public interface Requires {
  }
  
  public interface Component extends GuiComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IGui printer();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements GuiComponent.Component, GuiComponent.Parts {
    private final GuiComponent.Requires bridge;
    
    private final GuiComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_printer() {
      assert this.printer == null: "This is a bug.";
      this.printer = this.implementation.make_printer();
      if (this.printer == null) {
      	throw new RuntimeException("make_printer() in system.gui.GuiComponent should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_printer();
    }
    
    public ComponentImpl(final GuiComponent implem, final GuiComponent.Requires b, final boolean doInits) {
      this.bridge = b;
      this.implementation = implem;
      
      assert implem.selfComponent == null: "This is a bug.";
      implem.selfComponent = this;
      
      // prevent them to be called twice if we are in
      // a specialized component: only the last of the
      // hierarchy will call them after everything is initialised
      if (doInits) {
      	initParts();
      	initProvidedPorts();
      }
    }
    
    private IGui printer;
    
    public IGui printer() {
      return this.printer;
    }
  }
  
  /**
   * Used to check that two components are not created from the same implementation,
   * that the component has been started to call requires(), provides() and parts()
   * and that the component is not started by hand.
   * 
   */
  private boolean init = false;;
  
  /**
   * Used to check that the component is not started by hand.
   * 
   */
  private boolean started = false;;
  
  private GuiComponent.ComponentImpl selfComponent;
  
  /**
   * Can be overridden by the implementation.
   * It will be called automatically after the component has been instantiated.
   * 
   */
  protected void start() {
    if (!this.init || this.started) {
    	throw new RuntimeException("start() should not be called by hand: to create a new component, use newComponent().");
    }
  }
  
  /**
   * This can be called by the implementation to access the provided ports.
   * 
   */
  protected GuiComponent.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define the provided port.
   * This will be called once during the construction of the component to initialize the port.
   * 
   */
  protected abstract IGui make_printer();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected GuiComponent.Requires requires() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("requires() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if requires() is needed to initialise the component.");
    }
    return this.selfComponent.bridge;
  }
  
  /**
   * This can be called by the implementation to access the parts and their provided ports.
   * 
   */
  protected GuiComponent.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized GuiComponent.Component _newComponent(final GuiComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of GuiComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    GuiComponent.ComponentImpl  _comp = new GuiComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public GuiComponent.Component newComponent() {
    return this._newComponent(new GuiComponent.Requires() {}, true);
  }
}
