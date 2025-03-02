package mage.cards.s;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author Jmlundeen
 */
public final class SpectacularPileup extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures and Vehicles");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public SpectacularPileup(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");
        

        // All creatures and Vehicles lose indestructible until end of turn, then destroy all creatures and Vehicles.
        this.getSpellAbility().addEffect(new LoseAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter)
                .setText("All creatures and Vehicles lose indestructible until end of turn")
        );
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter).concatBy(", then"));
        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));

    }

    private SpectacularPileup(final SpectacularPileup card) {
        super(card);
    }

    @Override
    public SpectacularPileup copy() {
        return new SpectacularPileup(this);
    }
}
