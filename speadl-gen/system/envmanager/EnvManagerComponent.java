package system.envmanager;

import system.agentmanager.interfaces.IAgentManager;
import system.envmanager.interfaces.IEnvManager;
import system.gui.interfaces.IGui;

@SuppressWarnings("all")
public abstract class EnvManagerComponent {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IAgentManager agentmanager();
    
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public IGui gui();
  }
  
  public interface Component extends EnvManagerComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvManager runenv();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements EnvManagerComponent.Component, EnvManagerComponent.Parts {
    private final EnvManagerComponent.Requires bridge;
    
    private final EnvManagerComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_runenv() {
      assert this.runenv == null: "This is a bug.";
      this.runenv = this.implementation.make_runenv();
      if (this.runenv == null) {
      	throw new RuntimeException("make_runenv() in system.envmanager.EnvManagerComponent should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_runenv();
    }
    
    public ComponentImpl(final EnvManagerComponent implem, final EnvManagerComponent.Requires b, final boolean doInits) {
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
    
    private IEnvManager runenv;
    
    public IEnvManager runenv() {
      return this.runenv;
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
  
  private EnvManagerComponent.ComponentImpl selfComponent;
  
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
  protected EnvManagerComponent.Provides provides() {
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
  protected abstract IEnvManager make_runenv();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected EnvManagerComponent.Requires requires() {
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
  protected EnvManagerComponent.Parts parts() {
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
  public synchronized EnvManagerComponent.Component _newComponent(final EnvManagerComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of EnvManagerComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    EnvManagerComponent.ComponentImpl  _comp = new EnvManagerComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
