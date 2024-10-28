package mage.cards.s;

import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Subversion extends CardImpl {

    public Subversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");

        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsYouGainLifeLostEffect(1)));
    }

    private Subversion(final Subversion card) {
        super(card);
    }

    @Override
    public Subversion copy() {
        return new Subversion(this);
    }

}
