package system.robot;

import system.collector.CollectorComponent;
import system.collector.interfaces.ICollector;
import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.interfaces.IDecisionMaker;
import system.effector.EffectorComponent;
import system.effector.interfaces.IEffector;

@SuppressWarnings("all")
public abstract class RobotComponent {
  public interface Requires {
  }
  
  public interface Component extends RobotComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEffector action();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public DecisionMakerComponent.Component decisionmaker();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public CollectorComponent.Component collector();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EffectorComponent.Component effector();
  }
  
  public static class ComponentImpl implements RobotComponent.Component, RobotComponent.Parts {
    private final RobotComponent.Requires bridge;
    
    private final RobotComponent implementation;
    
    public void start() {
      assert this.decisionmaker != null: "This is a bug.";
      ((DecisionMakerComponent.ComponentImpl) this.decisionmaker).start();
      assert this.collector != null: "This is a bug.";
      ((CollectorComponent.ComponentImpl) this.collector).start();
      assert this.effector != null: "This is a bug.";
      ((EffectorComponent.ComponentImpl) this.effector).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_decisionmaker() {
      assert this.decisionmaker == null: "This is a bug.";
      assert this.implem_decisionmaker == null: "This is a bug.";
      this.implem_decisionmaker = this.implementation.make_decisionmaker();
      if (this.implem_decisionmaker == null) {
      	throw new RuntimeException("make_decisionmaker() in system.robot.RobotComponent should not return null.");
      }
      this.decisionmaker = this.implem_decisionmaker._newComponent(new BridgeImpl_decisionmaker(), false);
      
    }
    
    private void init_collector() {
      assert this.collector == null: "This is a bug.";
      assert this.implem_collector == null: "This is a bug.";
      this.implem_collector = this.implementation.make_collector();
      if (this.implem_collector == null) {
      	throw new RuntimeException("make_collector() in system.robot.RobotComponent should not return null.");
      }
      this.collector = this.implem_collector._newComponent(new BridgeImpl_collector(), false);
      
    }
    
    private void init_effector() {
      assert this.effector == null: "This is a bug.";
      assert this.implem_effector == null: "This is a bug.";
      this.implem_effector = this.implementation.make_effector();
      if (this.implem_effector == null) {
      	throw new RuntimeException("make_effector() in system.robot.RobotComponent should not return null.");
      }
      this.effector = this.implem_effector._newComponent(new BridgeImpl_effector(), false);
      
    }
    
    protected void initParts() {
      init_decisionmaker();
      init_collector();
      init_effector();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final RobotComponent implem, final RobotComponent.Requires b, final boolean doInits) {
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
    
    public IEffector action() {
      return this.effector().applier();
    }
    
    private DecisionMakerComponent.Component decisionmaker;
    
    private DecisionMakerComponent implem_decisionmaker;
    
    private final class BridgeImpl_decisionmaker implements DecisionMakerComponent.Requires {
      public final ICollector translation() {
        return RobotComponent.ComponentImpl.this.collector().translator();
      }
    }
    
    public final DecisionMakerComponent.Component decisionmaker() {
      return this.decisionmaker;
    }
    
    private CollectorComponent.Component collector;
    
    private CollectorComponent implem_collector;
    
    private final class BridgeImpl_collector implements CollectorComponent.Requires {
    }
    
    public final CollectorComponent.Component collector() {
      return this.collector;
    }
    
    private EffectorComponent.Component effector;
    
    private EffectorComponent implem_effector;
    
    private final class BridgeImpl_effector implements EffectorComponent.Requires {
      public final IDecisionMaker decision() {
        return RobotComponent.ComponentImpl.this.decisionmaker().decision();
      }
    }
    
    public final EffectorComponent.Component effector() {
      return this.effector;
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
  
  private RobotComponent.ComponentImpl selfComponent;
  
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
  protected RobotComponent.Provides provides() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("provides() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if provides() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected RobotComponent.Requires requires() {
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
  protected RobotComponent.Parts parts() {
    assert this.selfComponent != null: "This is a bug.";
    if (!this.init) {
    	throw new RuntimeException("parts() can't be accessed until a component has been created from this implementation, use start() instead of the constructor if parts() is needed to initialise the component.");
    }
    return this.selfComponent;
  }
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract DecisionMakerComponent make_decisionmaker();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract CollectorComponent make_collector();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract EffectorComponent make_effector();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized RobotComponent.Component _newComponent(final RobotComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of RobotComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    RobotComponent.ComponentImpl  _comp = new RobotComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public RobotComponent.Component newComponent() {
    return this._newComponent(new RobotComponent.Requires() {}, true);
  }
}
