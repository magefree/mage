package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverwhelmingVictory extends CardImpl {

    public OverwhelmingVictory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}");

        this.subtype.add(SubType.LESSON);

        // Overwhelming Victory deals 5 damage to target creature. Each creature you control gains trample and gets +X/+0 until end of turn, where X is the amount of excess damage dealt this way.
        this.getSpellAbility().addEffect(new OverwhelmingVictoryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private OverwhelmingVictory(final OverwhelmingVictory card) {
        super(card);
    }

    @Override
    public OverwhelmingVictory copy() {
        return new OverwhelmingVictory(this);
    }
}

class OverwhelmingVictoryEffect extends OneShotEffect {

    OverwhelmingVictoryEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 5 damage to target creature. Each creature you control gains trample " +
                "and gets +X/+0 until end of turn, where X is the amount of excess damage dealt this way";
    }

    private OverwhelmingVictoryEffect(final OverwhelmingVictoryEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingVictoryEffect copy() {
        return new OverwhelmingVictoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int excess = Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .map(permanent -> permanent.damageWithExcess(5, source, game))
                .orElse(0);
        game.addEffect(new GainAbilityAllEffect(
                TrampleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURE
        ), source);
        if (excess > 0) {
            game.addEffect(new BoostControlledEffect(excess, 0, Duration.EndOfTurn), source);
        }
        return true;
    }
}
