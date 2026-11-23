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
        // the object the ability knew: a permission keeps counting against it after its source moves (400.7)
        this.approvingMageObjectReference = new MageObjectReference(source, game);
    }

    public Ability getApprovingAbility() {
        return approvingAbility;
    }

    public MageObjectReference getApprovingMageObjectReference() {
        return approvingMageObjectReference;
    }
        
}
