package mage;

import mage.abilities.Ability;
import mage.game.Game;

import java.io.Serializable;

/**
 *
 * @author LevelX2
 */
public class ApprovingObject implements Serializable {
        
    private final Ability approvingAbility;
    private final MageObjectReference approvingMageObjectReference;
    
    public ApprovingObject(Ability source, Game game) {
        this.approvingAbility = source;
        this.approvingMageObjectReference = new MageObjectReference(source.getSourceId(), game);
    }

    public Ability getApprovingAbility() {
        return approvingAbility;
    }

    public MageObjectReference getApprovingMageObjectReference() {
        return approvingMageObjectReference;
    }
        
}
