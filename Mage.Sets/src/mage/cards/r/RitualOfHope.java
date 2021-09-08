package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RitualOfHope extends CardImpl {

    public RitualOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control get +1/+1 until end of turn.
        // Coven — If you control three or more creatures with different powers, creatures you control get +2/+1 until end of turn instead.
        this.getSpellAbility().addEffect(new RitualOfHopeEffect());
    }

    private RitualOfHope(final RitualOfHope card) {
        super(card);
    }

    @Override
    public RitualOfHope copy() {
        return new RitualOfHope(this);
    }
}

class RitualOfHopeEffect extends OneShotEffect {

    RitualOfHopeEffect() {
        super(Outcome.Benefit);
        staticText = "Creatures you control get +1/+1 until end of turn." +
                "<br>" + AbilityWord.COVEN.formatWord() +
                "If you control three or more creatures with different powers, " +
                "creatures you control get +2/+1 until end of turn instead";
    }

    private RitualOfHopeEffect(final RitualOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public RitualOfHopeEffect copy() {
        return new RitualOfHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new BoostControlledEffect(
                CovenCondition.instance.apply(game, source) ? 2 : 1, 1, Duration.EndOfTurn
        ), source);
        return true;
    }
}
