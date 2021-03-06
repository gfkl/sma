package system.decisionmaker;

import system.collector.interfaces.ICollector;
import system.decisionmaker.interfaces.IDecisionMaker;

@SuppressWarnings("all")
public abstract class DecisionMakerComponent {
  public interface Requires {
    /**
     * This can be called by the implementation to access this required port.
     * 
     */
    public ICollector translation();
  }
  
  public interface Component extends DecisionMakerComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IDecisionMaker decision();
  }
  
  public interface Parts {
  }
  
  public static class ComponentImpl implements DecisionMakerComponent.Component, DecisionMakerComponent.Parts {
    private final DecisionMakerComponent.Requires bridge;
    
    private final DecisionMakerComponent implementation;
    
    public void start() {
      this.implementation.start();
      this.implementation.started = true;
    }
    
    protected void initParts() {
      
    }
    
    private void init_decision() {
      assert this.decision == null: "This is a bug.";
      this.decision = this.implementation.make_decision();
      if (this.decision == null) {
      	throw new RuntimeException("make_decision() in system.decisionmaker.DecisionMakerComponent should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_decision();
    }
    
    public ComponentImpl(final DecisionMakerComponent implem, final DecisionMakerComponent.Requires b, final boolean doInits) {
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
    
    private IDecisionMaker decision;
    
    public IDecisionMaker decision() {
      return this.decision;
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
  
  private DecisionMakerComponent.ComponentImpl selfComponent;
  
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
  protected DecisionMakerComponent.Provides provides() {
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
  protected abstract IDecisionMaker make_decision();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected DecisionMakerComponent.Requires requires() {
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
  protected DecisionMakerComponent.Parts parts() {
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
  public synchronized DecisionMakerComponent.Component _newComponent(final DecisionMakerComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of DecisionMakerComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    DecisionMakerComponent.ComponentImpl  _comp = new DecisionMakerComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
}
