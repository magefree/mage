package mage.game.command.emblems;

import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.NotManaAbilityPredicate;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class RowanKenrithEmblem extends Emblem {
    // Target player gets an emblem with "Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy."
    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability that isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
    }

    public RowanKenrithEmblem() {
        super("Emblem Rowan Kenrith");
        this.getAbilities().add(new ActivateAbilityTriggeredAbility(Zone.COMMAND, new CopyStackObjectEffect("it"), filter, SetTargetPointer.SPELL));
    }

    private RowanKenrithEmblem(final RowanKenrithEmblem card) {
        super(card);
    }

    @Override
    public RowanKenrithEmblem copy() {
        return new RowanKenrithEmblem(this);
    }
}
