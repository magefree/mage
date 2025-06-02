package mage.game.command.emblems;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.command.Emblem;

/**
 * @author TheElk801
 */
public final class WrennAndRealmbreakerEmblem extends Emblem {

    private static final FilterPermanentCard filter = new FilterPermanentCard("play lands and cast permanent spells");

    // -7: You get an emblem with "You may play lands and cast permanent spells from your graveyard."
    public WrennAndRealmbreakerEmblem() {
        super("Emblem Wrenn");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new PlayFromGraveyardControllerEffect(filter)));
    }

    private WrennAndRealmbreakerEmblem(final WrennAndRealmbreakerEmblem card) {
        super(card);
    }

    @Override
    public WrennAndRealmbreakerEmblem copy() {
        return new WrennAndRealmbreakerEmblem(this);
    }
}
