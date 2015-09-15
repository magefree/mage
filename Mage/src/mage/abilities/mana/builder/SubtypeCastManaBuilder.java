/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.mana.builder;

import java.util.UUID;
import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.mana.conditional.CreatureCastManaCondition;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SubtypeCastManaBuilder extends ConditionalManaBuilder {

    private final String subtype;

    public SubtypeCastManaBuilder(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public ConditionalMana build(Object... options) {
        this.mana.setFlag(true); // indicates that the mana is from second ability
        return new SubtypeCastConditionalMana(this.mana, subtype);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast a " + subtype + " spell.";
    }
}

class SubtypeCastConditionalMana extends ConditionalMana {

    SubtypeCastConditionalMana(Mana mana, String subtype) {
        super(mana);
        staticText = "Spend this mana only to cast a " + subtype + " spell.";
        addCondition(new SubtypeCastManaCondition(subtype));
    }
}

class SubtypeCastManaCondition extends CreatureCastManaCondition {

    private final String subtype;

    public SubtypeCastManaCondition(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public boolean apply(Game game, Ability source, UUID manaProducer) {
        if (super.apply(game, source)) {
            MageObject object = game.getObject(source.getSourceId());
            if (object.hasSubtype(subtype)) {
                return true;
            }
        }
        return false;
    }
}
