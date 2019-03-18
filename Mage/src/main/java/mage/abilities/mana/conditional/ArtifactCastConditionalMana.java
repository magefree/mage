

package mage.abilities.mana.conditional;

import mage.ConditionalMana;
import mage.Mana;

/**
 *
 * @author LevelX2
 */

public class ArtifactCastConditionalMana extends ConditionalMana {

    public ArtifactCastConditionalMana(Mana mana) {
        super(mana);
        staticText = "Spend this mana only to cast artifact spells";
        addCondition(new ArtifactCastManaCondition());
    }
}