
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class KolaghanAspirant extends CardImpl {

    public KolaghanAspirant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Kolaghan Aspirant becomes blocked by a creature, Kolaghan Aspirant deals 1 damage to that creature.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DamageTargetEffect(1, true, "that creature"), false));
    }

    private KolaghanAspirant(final KolaghanAspirant card) {
        super(card);
    }

    @Override
    public KolaghanAspirant copy() {
        return new KolaghanAspirant(this);
    }
}
