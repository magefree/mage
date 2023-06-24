package mage.cards.n;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.*;
import mage.abilities.keyword.CompleatedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.token.PhyrexianHorrorGreenToken;
import mage.game.permanent.token.PhyrexianHorrorRedToken;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class NissaAscendedAnimist extends CardImpl {

    private static final DynamicValue xValue
            = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.FOREST));
    private static final Hint hint = new ValueHint("Forests you control", xValue);

    public NissaAscendedAnimist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}{G/P}{G/P}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NISSA);
        this.setStartingLoyalty(7);

        // Compleated
        this.addAbility(CompleatedAbility.getInstance());

        // +1: Create an X/X green Phyrexian Horror creature token, where X is Nissa, Ascended Animist's loyalty.
        this.addAbility(new LoyaltyAbility(new NissaAscendedAnimistEffect(), 1));

        // -1: Destroy target artifact or enchantment.
        Ability ability = new LoyaltyAbility(new DestroyTargetEffect(), -1);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);

        // -7: Until end of turn, creatures you control get +1/+1 for each Forest you control and gain trample.
        ability = new LoyaltyAbility(new BoostControlledEffect(
                xValue, xValue, Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE,
                false, true
        ).setText("until end of turn, creatures you control get +1/+1 for each Forest you control"), -7);
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain trample"));
        this.addAbility(ability.addHint(hint));
    }

    private NissaAscendedAnimist(final NissaAscendedAnimist card) {
        super(card);
    }

    @Override
    public NissaAscendedAnimist copy() {
        return new NissaAscendedAnimist(this);
    }
}

class NissaAscendedAnimistEffect extends OneShotEffect {

    NissaAscendedAnimistEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X green Phyrexian Horror creature token, where X is {this}'s loyalty";
    }

    private NissaAscendedAnimistEffect(final NissaAscendedAnimistEffect effect) {
        super(effect);
    }

    @Override
    public NissaAscendedAnimistEffect copy() {
        return new NissaAscendedAnimistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loyalty = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(permanent -> permanent.getCounters(game))
                .map(counters -> counters.getCount(CounterType.LOYALTY))
                .orElse(0);
        return new PhyrexianHorrorGreenToken(loyalty).putOntoBattlefield(1, game, source);
    }
}
