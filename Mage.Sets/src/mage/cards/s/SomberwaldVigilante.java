
package mage.cards.s;

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
 * @author North
 */
public final class SomberwaldVigilante extends CardImpl {

    public SomberwaldVigilante(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Somberwald Vigilante becomes blocked by a creature, Somberwald Vigilante deals 1 damage to that creature.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DamageTargetEffect(1, true, "that creature"), false));
    }

    private SomberwaldVigilante(final SomberwaldVigilante card) {
        super(card);
    }

    @Override
    public SomberwaldVigilante copy() {
        return new SomberwaldVigilante(this);
    }
}
