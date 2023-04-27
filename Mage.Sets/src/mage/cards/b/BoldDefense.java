package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author nantuko, Loki
 */
public final class BoldDefense extends CardImpl {

    public BoldDefense(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Kicker {3}{W} (You may pay an additional {3}{W} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}{W}"));

        // Creatures you control get +1/+1 until end of turn. If Bold Defense was kicked, instead creatures you control get +2/+2 and gain first strike until end of turn.
        this.getSpellAbility().addEffect(new BoldDefenseEffect());
    }

    private BoldDefense(final BoldDefense card) {
        super(card);
    }

    @Override
    public BoldDefense copy() {
        return new BoldDefense(this);
    }
}

class BoldDefenseEffect extends OneShotEffect {

    BoldDefenseEffect() {
        super(Outcome.Benefit);
        staticText = "Creatures you control get +1/+1 until end of turn. If this spell was kicked, " +
                "instead creatures you control get +2/+2 and gain first strike until end of turn.";
    }

    private BoldDefenseEffect(final BoldDefenseEffect effect) {
        super(effect);
    }

    @Override
    public BoldDefenseEffect copy() {
        return new BoldDefenseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (KickedCondition.ONCE.apply(game, source)) {
            game.addEffect(new BoostControlledEffect(2, 2, Duration.EndOfTurn), source);
            game.addEffect(new GainAbilityControlledEffect(
                    FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                    StaticFilters.FILTER_PERMANENT_CREATURE
            ), source);
        } else {
            game.addEffect(new BoostControlledEffect(1, 1, Duration.EndOfTurn), source);
        }
        return true;
    }
}
