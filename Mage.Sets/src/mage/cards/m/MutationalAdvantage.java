package mage.cards.m;

import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MutationalAdvantage extends CardImpl {

    private static final FilterPermanent filter =
            new FilterControlledPermanent("permanents you control with counters on them");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public MutationalAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{U}");

        // Permanents you control with counters on them gain hexproof and indestructible until end of turn. Prevent all damage that would be dealt to those permanents this turn. Proliferate.
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        HexproofAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("Permanents you control with counters on them gain hexproof")
        );
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn, filter
                ).setText("and indestructible until end of turn")
        );
        this.getSpellAbility().addEffect(
                new PreventAllDamageToAllEffect(Duration.EndOfTurn, filter)
                        .setText("Prevent all damage that would be dealt to those permanents this turn")
        );
        this.getSpellAbility().addEffect(new ProliferateEffect());
    }

    private MutationalAdvantage(final MutationalAdvantage card) {
        super(card);
    }

    @Override
    public MutationalAdvantage copy() {
        return new MutationalAdvantage(this);
    }
}
