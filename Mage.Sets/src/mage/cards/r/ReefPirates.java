package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LoneFox
 */
public final class ReefPirates extends CardImpl {

    public ReefPirates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Reef Pirates deals damage to an opponent, that player puts the top card of their library into their graveyard.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(new MillCardsTargetEffect(1),
                false, false, true));
    }

    private ReefPirates(final ReefPirates card) {
        super(card);
    }

    @Override
    public ReefPirates copy() {
        return new ReefPirates(this);
    }
}
