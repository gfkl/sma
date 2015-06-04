package system;

import system.agentmanager.AgentManagerComponent;
import system.agentmanager.interfaces.IAgentManager;
import system.envmanager.EnvManagerComponent;
import system.envmanager.interfaces.IEnvManager;
import system.gui.GuiComponent;
import system.gui.interfaces.IGui;

@SuppressWarnings("all")
public abstract class SystemComponent {
  public interface Requires {
  }
  
  public interface Component extends SystemComponent.Provides {
  }
  
  public interface Provides {
    /**
     * This can be called to access the provided port.
     * 
     */
    public IEnvManager runsystem();
  }
  
  public interface Parts {
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public EnvManagerComponent.Component envmanager();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public GuiComponent.Component gui();
    
    /**
     * This can be called by the implementation to access the part and its provided ports.
     * It will be initialized after the required ports are initialized and before the provided ports are initialized.
     * 
     */
    public AgentManagerComponent.Component agentmanager();
  }
  
  public static class ComponentImpl implements SystemComponent.Component, SystemComponent.Parts {
    private final SystemComponent.Requires bridge;
    
    private final SystemComponent implementation;
    
    public void start() {
      assert this.envmanager != null: "This is a bug.";
      ((EnvManagerComponent.ComponentImpl) this.envmanager).start();
      assert this.gui != null: "This is a bug.";
      ((GuiComponent.ComponentImpl) this.gui).start();
      assert this.agentmanager != null: "This is a bug.";
      ((AgentManagerComponent.ComponentImpl) this.agentmanager).start();
      this.implementation.start();
      this.implementation.started = true;
    }
    
    private void init_envmanager() {
      assert this.envmanager == null: "This is a bug.";
      assert this.implem_envmanager == null: "This is a bug.";
      this.implem_envmanager = this.implementation.make_envmanager();
      if (this.implem_envmanager == null) {
      	throw new RuntimeException("make_envmanager() in system.SystemComponent should not return null.");
      }
      this.envmanager = this.implem_envmanager._newComponent(new BridgeImpl_envmanager(), false);
      
    }
    
    private void init_gui() {
      assert this.gui == null: "This is a bug.";
      assert this.implem_gui == null: "This is a bug.";
      this.implem_gui = this.implementation.make_gui();
      if (this.implem_gui == null) {
      	throw new RuntimeException("make_gui() in system.SystemComponent should not return null.");
      }
      this.gui = this.implem_gui._newComponent(new BridgeImpl_gui(), false);
      
    }
    
    private void init_agentmanager() {
      assert this.agentmanager == null: "This is a bug.";
      assert this.implem_agentmanager == null: "This is a bug.";
      this.implem_agentmanager = this.implementation.make_agentmanager();
      if (this.implem_agentmanager == null) {
      	throw new RuntimeException("make_agentmanager() in system.SystemComponent should not return null.");
      }
      this.agentmanager = this.implem_agentmanager._newComponent(new BridgeImpl_agentmanager(), false);
      
    }
    
    protected void initParts() {
      init_envmanager();
      init_gui();
      init_agentmanager();
    }
    
    protected void initProvidedPorts() {
      
    }
    
    public ComponentImpl(final SystemComponent implem, final SystemComponent.Requires b, final boolean doInits) {
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
    
    public IEnvManager runsystem() {
      return this.envmanager().runenv();
    }
    
    private EnvManagerComponent.Component envmanager;
    
    private EnvManagerComponent implem_envmanager;
    
    private final class BridgeImpl_envmanager implements EnvManagerComponent.Requires {
      public final IGui gui() {
        return SystemComponent.ComponentImpl.this.gui().printer();
      }
      
      public final IAgentManager agentmanager() {
        return SystemComponent.ComponentImpl.this.agentmanager().executeagents();
      }
    }
    
    public final EnvManagerComponent.Component envmanager() {
      return this.envmanager;
    }
    
    private GuiComponent.Component gui;
    
    private GuiComponent implem_gui;
    
    private final class BridgeImpl_gui implements GuiComponent.Requires {
    }
    
    public final GuiComponent.Component gui() {
      return this.gui;
    }
    
    private AgentManagerComponent.Component agentmanager;
    
    private AgentManagerComponent implem_agentmanager;
    
    private final class BridgeImpl_agentmanager implements AgentManagerComponent.Requires {
    }
    
    public final AgentManagerComponent.Component agentmanager() {
      return this.agentmanager;
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
  
  private SystemComponent.ComponentImpl selfComponent;
  
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
  protected SystemComponent.Provides provides() {
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
  protected SystemComponent.Requires requires() {
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
  protected SystemComponent.Parts parts() {
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
  protected abstract EnvManagerComponent make_envmanager();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract GuiComponent make_gui();
  
  /**
   * This should be overridden by the implementation to define how to create this sub-component.
   * This will be called once during the construction of the component to initialize this sub-component.
   * 
   */
  protected abstract AgentManagerComponent make_agentmanager();
  
  /**
   * Not meant to be used to manually instantiate components (except for testing).
   * 
   */
  public synchronized SystemComponent.Component _newComponent(final SystemComponent.Requires b, final boolean start) {
    if (this.init) {
    	throw new RuntimeException("This instance of SystemComponent has already been used to create a component, use another one.");
    }
    this.init = true;
    SystemComponent.ComponentImpl  _comp = new SystemComponent.ComponentImpl(this, b, true);
    if (start) {
    	_comp.start();
    }
    return _comp;
  }
  
  /**
   * Use to instantiate a component from this implementation.
   * 
   */
  public SystemComponent.Component newComponent() {
    return this._newComponent(new SystemComponent.Requires() {}, true);
  }
}
