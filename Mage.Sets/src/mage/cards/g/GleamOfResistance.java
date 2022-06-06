
package mage.cards.g;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.BasicLandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class GleamOfResistance extends CardImpl {

    public GleamOfResistance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{W}");

        // Creatures you control get +1/+2 until end of turn. Untap those creatures.
        this.getSpellAbility().addEffect(new BoostControlledEffect(1, 2, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, "Untap those creatures"));
        // Basic landcycling {1}{W}({1}{W}, Discard this card: Search your library for a basic land card, reveal it, and put it into your hand. Then shuffle your library.)
        this.addAbility(new BasicLandcyclingAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private GleamOfResistance(final GleamOfResistance card) {
        super(card);
    }

    @Override
    public GleamOfResistance copy() {
        return new GleamOfResistance(this);
    }
}
