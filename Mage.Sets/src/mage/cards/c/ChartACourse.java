package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChartACourse extends CardImpl {

    public ChartACourse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Draw two cards. Then discard a card unless you attacked with a creature this turn.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
        this.getSpellAbility().addEffect(new ChartACourseEffect());
        this.getSpellAbility().addWatcher(new PlayerAttackedWatcher());
        // this.getSpellAbility().setAbilityWord(AbilityWord.RAID); // no raid ability, only same conditional
        this.getSpellAbility().addHint(RaidHint.instance);
    }

    private ChartACourse(final ChartACourse card) {
        super(card);
    }

    @Override
    public ChartACourse copy() {
        return new ChartACourse(this);
    }
}

class ChartACourseEffect extends OneShotEffect {

    ChartACourseEffect() {
        super(Outcome.Neutral);
        this.staticText = "Then discard a card unless you attacked this turn.";
    }

    private ChartACourseEffect(final ChartACourseEffect effect) {
        super(effect);
    }

    @Override
    public ChartACourseEffect copy() {
        return new ChartACourseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && !RaidCondition.instance.apply(game, source)) {
            player.discard(1, false, false, source, game);
            return true;
        }
        return false;
    }
}
