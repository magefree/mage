
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jeffwadsworth
 */
public final class VedalkenGhoul extends CardImpl {

    public VedalkenGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Vedalken Ghoul becomes blocked, defending player loses 4 life.
        this.addAbility(new BecomesBlockedTriggeredAbility(new LoseLifeDefendingPlayerEffect(4, true), false));

    }

    public VedalkenGhoul(final VedalkenGhoul card) {
        super(card);
    }

    @Override
    public VedalkenGhoul copy() {
        return new VedalkenGhoul(this);
    }
}
