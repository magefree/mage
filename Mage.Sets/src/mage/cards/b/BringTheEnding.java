package mage.cards.b;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author @stwalsh4118
 */
public final class BringTheEnding extends CardImpl {

    public BringTheEnding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");
        

        // Counter target spell unless its controller pays {2}.
        // Corrupted -- Counter that spell instead if its controller has three or more poison counters.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new BringTheEndingCounterEffect());
    }

    private BringTheEnding(final BringTheEnding card) {
        super(card);
    }

    @Override
    public BringTheEnding copy() {
        return new BringTheEnding(this);
    }
}

class BringTheEndingCounterEffect extends OneShotEffect {

    public BringTheEndingCounterEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell unless its controller pays {2}.<br>" + AbilityWord.CORRUPTED.formatWord() + "Counter that spell instead if its controller has three or more poison counters.";
    }

    public BringTheEndingCounterEffect(final BringTheEndingCounterEffect effect) {
        super(effect);
    }

    @Override
    public BringTheEndingCounterEffect copy() {
        return new BringTheEndingCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Effect hardCounterEffect = new CounterTargetEffect();
        Effect softCounterEffect = new CounterUnlessPaysEffect(new GenericManaCost(2));

        UUID targetId = source.getFirstTarget();
        UUID controllerId = game.getControllerId(targetId);
        Player player = game.getPlayer(controllerId);

        if (player != null && player.getCounters().getCount(CounterType.POISON) >= 3) {
            hardCounterEffect.setTargetPointer(this.getTargetPointer());
            return hardCounterEffect.apply(game, source);
        } else {
            softCounterEffect.setTargetPointer(this.getTargetPointer());
            return softCounterEffect.apply(game, source);
        }

    }

}
