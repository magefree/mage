package mage.cards.m;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MentalJourney extends CardImpl {

    public MentalJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3));

        // Basic landcycling {1}{U}
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{U}")));
    }

    private MentalJourney(final MentalJourney card) {
        super(card);
    }

    @Override
    public MentalJourney copy() {
        return new MentalJourney(this);
    }
}
