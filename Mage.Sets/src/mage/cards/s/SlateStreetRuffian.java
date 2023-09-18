package mage.cards.s;

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
 * @author LevelX2
 */
public final class SlateStreetRuffian extends CardImpl {

    public SlateStreetRuffian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Slate Street Ruffian becomes blocked, defending player discards a card.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new DiscardTargetEffect(1).setText("defending player discards a card"),
                false, true));
    }

    private SlateStreetRuffian(final SlateStreetRuffian card) {
        super(card);
    }

    @Override
    public SlateStreetRuffian copy() {
        return new SlateStreetRuffian(this);
    }
}
