package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class AlleyGrifters extends CardImpl {

    public AlleyGrifters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Alley Grifters becomes blocked, defending player discards a card.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new DiscardTargetEffect(1).setText("defending player discards a card"),
                false, true));
    }

    private AlleyGrifters(final AlleyGrifters card) {
        super(card);
    }

    @Override
    public AlleyGrifters copy() {
        return new AlleyGrifters(this);
    }
}
