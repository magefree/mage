package mage.game.command.emblems;

import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.command.Emblem;
import mage.game.permanent.token.NinjaToken2;

/**
 * @author ciaccona007
 */
public class KaitoCunningInfiltratorEmblem extends Emblem {
    // −9: You get an emblem with “Whenever a player casts a spell, you create a 2/1 blue Ninja creature token.”

    public KaitoCunningInfiltratorEmblem() {
        super("Emblem Kaito");
        this.getAbilities().add(new SpellCastAllTriggeredAbility(
                Zone.COMMAND, new CreateTokenEffect(new NinjaToken2()), new FilterSpell(), false, SetTargetPointer.NONE
        ));
    }

    private KaitoCunningInfiltratorEmblem(final KaitoCunningInfiltratorEmblem card) {
        super(card);
    }

    @Override
    public KaitoCunningInfiltratorEmblem copy() {
        return new KaitoCunningInfiltratorEmblem(this);
    }
}
