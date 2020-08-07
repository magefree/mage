package mage.cards.b;

import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BondOfDiscipline extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public BondOfDiscipline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}");

        // Tap all creatures your opponents control. Creatures you control gain lifelink until end of turn.
        this.getSpellAbility().addEffect(new TapAllEffect(filter));
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ));
    }

    private BondOfDiscipline(final BondOfDiscipline card) {
        super(card);
    }

    @Override
    public BondOfDiscipline copy() {
        return new BondOfDiscipline(this);
    }
}
