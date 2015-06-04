package system.agentmanager;

import system.agentmanager.interfaces.IAgentManager;
import system.collector.CollectorComponent;
import system.collector.interfaces.ICollector;
import system.decisionmaker.DecisionMakerComponent;
import system.decisionmaker.interfaces.IDecisionMaker;
import system.effector.EffectorComponent;
import system.effector.interfaces.IEffector;
import system.log.LogComponent;
import system.persistence.PersistenceComponent;

@SuppressWarnings("all")
public abstract class AgentManagerComponent {
  public interface Requires {
  }
  
  public interface Component extends AgentManagerComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IAgentManager executeagents();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public LogComponent.Component log();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public PersistenceComponent.Component persistence();
  }
  
  public static class ComponentImpl implements AgentManagerComponent.Component, AgentManagerComponent.Parts {
    private final AgentManagerComponent.Requires bridge;
    
    private final AgentManagerComponent implementation;
    
    public void start() {
      assert this.log != null: "This is a bug.";
      ((LogComponent.ComponentImpl) this.log).start();
      assert this.persistence != null: "This is a bug.";
      ((PersistenceComponent.ComponentImpl) this.persistence).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_log() {
      assert this.log == null: "This is a bug.";
      assert this.implem_log == null: "This is a bug.";
      this.implem_log = this.implementation.make_log();
      if (this.implem_log == null) {
      	throw new RuntimeException("make_log() in system.agentmanager.AgentManagerComponent should not return null.");
      }
      this.log = this.implem_log._newComponent(new BridgeImpl_log(), false);
      
    }
    
    private void init_persistence() {
      assert this.persistence == null: "This is a bug.";
      assert this.implem_persistence == null: "This is a bug.";
      this.implem_persistence = this.implementation.make_persistence();
      if (this.implem_persistence == null) {
      	throw new RuntimeException("make_persistence() in system.agentmanager.AgentManagerComponent should not return null.");
      }
      this.persistence = this.implem_persistence._newComponent(new BridgeImpl_persistence(), false);
      
    }
    
    protected void initParts() {
      init_log();
      init_persistence();
    }
    
    private void init_executeagents() {
      assert this.executeagents == null: "This is a bug.";
      this.executeagents = this.implementation.make_executeagents();
      if (this.executeagents == null) {
      	throw new RuntimeException("make_executeagents() in system.agentmanager.AgentManagerComponent should not return null.");
      }
    }
    
    protected void initProvidedPorts() {
      init_executeagents();
    }
    
    public ComponentImpl(final AgentManagerComponent implem, final AgentManagerComponent.Requires b, final boolean doInits) {
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
    
    private IAgentManager executeagents;
    
    public IAgentManager executeagents() {
      return this.executeagents;
    }
    
    private LogComponent.Component log;
    
    private LogComponent implem_log;
    
    private final class BridgeImpl_log implements LogComponent.Requires {
    }
    
    public final LogComponent.Component log() {
      return this.log;
    }
    
    private PersistenceComponent.Component persistence;
    
    private PersistenceComponent implem_persistence;
    
    private final class BridgeImpl_persistence implements PersistenceComponent.Requires {
    }
    
    public final PersistenceComponent.Component persistence() {
      return this.persistence;
    }
  }
  
  public static abstract class AgentSpecies {
    public interface Requires {
    }
    
    public interface Component extends AgentManagerComponent.AgentSpecies.Provides {
    }
    
    public interface Provides {
      /**
       * This can be called to access the provided port.
       * 
       */
      public IEffector agentaction();
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
    
    public static class ComponentImpl implements AgentManagerComponent.AgentSpecies.Component, AgentManagerComponent.AgentSpecies.Parts {
      private final AgentManagerComponent.AgentSpecies.Requires bridge;
      
      private final AgentManagerComponent.AgentSpecies implementation;
      
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
        	throw new RuntimeException("make_decisionmaker() in system.agentmanager.AgentManagerComponent$AgentSpecies should not return null.");
        }
        this.decisionmaker = this.implem_decisionmaker._newComponent(new BridgeImpl_decisionmaker(), false);
        
      }
      
      private void init_collector() {
        assert this.collector == null: "This is a bug.";
        assert this.implem_collector == null: "This is a bug.";
        this.implem_collector = this.implementation.make_collector();
        if (this.implem_collector == null) {
        	throw new RuntimeException("make_collector() in system.agentmanager.AgentManagerComponent$AgentSpecies should not return null.");
        }
        this.collector = this.implem_collector._newComponent(new BridgeImpl_collector(), false);
        
      }
      
      private void init_effector() {
        assert this.effector == null: "This is a bug.";
        assert this.implem_effector == null: "This is a bug.";
        this.implem_effector = this.implementation.make_effector();
        if (this.implem_effector == null) {
        	throw new RuntimeException("make_effector() in system.agentmanager.AgentManagerComponent$AgentSpecies should not return null.");
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
      
      public ComponentImpl(final AgentManagerComponent.AgentSpecies implem, final AgentManagerComponent.AgentSpecies.Requires b, final boolean doInits) {
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
      
      public IEffector agentaction() {
        return this.effector().applier();
      }
      
      private DecisionMakerComponent.Component decisionmaker;
      
      private DecisionMakerComponent implem_decisionmaker;
      
      private final class BridgeImpl_decisionmaker implements DecisionMakerComponent.Requires {
        public final ICollector translation() {
          return AgentManagerComponent.AgentSpecies.ComponentImpl.this.collector().translator();
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
          return AgentManagerComponent.AgentSpecies.ComponentImpl.this.decisionmaker().decision();
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
    
    private AgentManagerComponent.AgentSpecies.ComponentImpl selfComponent;
    
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
    protected AgentManagerComponent.AgentSpecies.Provides provides() {
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
    protected AgentManagerComponent.AgentSpecies.Requires requires() {
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
    protected AgentManagerComponent.AgentSpecies.Parts parts() {
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
    public synchronized AgentManagerComponent.AgentSpecies.Component _newComponent(final AgentManagerComponent.AgentSpecies.Requires b, final boolean start) {
      if (this.init) {
      	throw new RuntimeException("This instance of AgentSpecies has already been used to create a component, use another one.");
      }
      this.init = true;
      AgentManagerComponent.AgentSpecies.ComponentImpl  _comp = new AgentManagerComponent.AgentSpecies.ComponentImpl(this, b, true);
      if (start) {
      	_comp.start();
      }
      return _comp;
    }
    
    private AgentManagerComponent.ComponentImpl ecosystemComponent;
    
    /**
     * This can be called by the species implementation to access the provided ports of its ecosystem.
     * 
     */
    protected AgentManagerComponent.Provides eco_provides() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
    }
    
    /**
     * This can be called by the species implementation to access the required ports of its ecosystem.
     * 
     */
    protected AgentManagerComponent.Requires eco_requires() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent.bridge;
    }
    
    /**
     * This can be called by the species implementation to access the parts of its ecosystem and their provided ports.
     * 
     */
    protected AgentManagerComponent.Parts eco_parts() {
      assert this.ecosystemComponent != null: "This is a bug.";
      return this.ecosystemComponent;
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
  
  private AgentManagerComponent.ComponentImpl selfComponent;
  
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
  protected AgentManagerComponent.Provides provides() {
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
  protected abstract IAgentManager make_executeagents();
  
  /**
   * This can be called by the implementation to access the required ports.
   * 
   */
  protected AgentManagerComponent.Requires requires() {
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
  protected AgentManagerComponent.Parts parts() {
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
  protected abstract LogComponent make_log();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract PersistenceComponent make_persistence();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized AgentManagerComponent.Component _newComponent(final AgentManagerComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of AgentManagerComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    AgentManagerComponent.ComponentImpl  _comp = new AgentManagerComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * This should be overridden by the implementation to instantiate the implementation of the species.
   * 
   */
  protected abstract AgentManagerComponent.AgentSpecies make_AgentSpecies(final int id);
  
  /**
   * Do not call, used by generated code.
   * 
   */
  public AgentManagerComponent.AgentSpecies _createImplementationOfAgentSpecies(final int id) {
    AgentManagerComponent.AgentSpecies implem = make_AgentSpecies(id);
    if (implem == null) {
    	throw new RuntimeException("make_AgentSpecies() in system.agentmanager.AgentManagerComponent should not return null.");
    }
    assert implem.ecosystemComponent == null: "This is a bug.";
    assert this.selfComponent != null: "This is a bug.";
    implem.ecosystemComponent = this.selfComponent;
    return implem;
  }
  
  /**
   * This can be called to create an instance of the species from inside the implementation of the ecosystem.
   * 
   */
  protected AgentManagerComponent.AgentSpecies.Component newAgentSpecies(final int id) {
    AgentManagerComponent.AgentSpecies _implem = _createImplementationOfAgentSpecies(id);
    return _implem._newComponent(new AgentManagerComponent.AgentSpecies.Requires() {},true);
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public AgentManagerComponent.Component newComponent() {
    return this._newComponent(new AgentManagerComponent.Requires() {}, true);
  }
}
