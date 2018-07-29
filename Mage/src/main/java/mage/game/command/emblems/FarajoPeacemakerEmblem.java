package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayManaAllEffect;
import mage.constants.Zone;
import mage.game.command.Emblem;

public class FarajoPeacemakerEmblem extends Emblem {

    //-7: You get an emblem with “Creatures can’t attack you or planeswalkers you control unless their controller pays {5} for each attacking creature they control.”
    public FarajoPeacemakerEmblem() {
        this.setName("Emblem Farajo");
        this.setExpansionSetCodeForImage("LDO");
        Ability ability = new SimpleStaticAbility(
                Zone.COMMAND,
                new CantAttackYouUnlessPayManaAllEffect(
                        new ManaCostsImpl<>("{5}"), true));
        this.getAbilities().add(ability);
    }
}
