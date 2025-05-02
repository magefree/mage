package mage.cards.f;

import mage.abilities.triggers.BeginningOfFirstMainTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FourKnocks extends CardImpl {

    public FourKnocks(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Vanishing 4
        this.addAbility(new VanishingAbility(4));

        // At the beginning of your precombat main phase, draw a card.
        this.addAbility(new BeginningOfFirstMainTriggeredAbility(
                new DrawCardSourceControllerEffect(1)
        ));
    }

    private FourKnocks(final FourKnocks card) {
        super(card);
    }

    @Override
    public FourKnocks copy() {
        return new FourKnocks(this);
    }
}
