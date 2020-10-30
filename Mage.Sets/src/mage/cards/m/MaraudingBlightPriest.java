package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaraudingBlightPriest extends CardImpl {

    public MaraudingBlightPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you gain life, each opponent loses 1 life.
        this.addAbility(new GainLifeControllerTriggeredAbility(new LoseLifeOpponentsEffect(1), false));
    }

    private MaraudingBlightPriest(final MaraudingBlightPriest card) {
        super(card);
    }

    @Override
    public MaraudingBlightPriest copy() {
        return new MaraudingBlightPriest(this);
    }
}
