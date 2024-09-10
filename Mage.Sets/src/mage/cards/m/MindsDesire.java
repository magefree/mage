package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.StormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class MindsDesire extends CardImpl {

    public MindsDesire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost.
        this.getSpellAbility().addEffect(new MindsDesireEffect());

        // Storm
        this.addAbility(new StormAbility());
    }

    private MindsDesire(final MindsDesire card) {
        super(card);
    }

    @Override
    public MindsDesire copy() {
        return new MindsDesire(this);
    }
}

class MindsDesireEffect extends OneShotEffect {

    MindsDesireEffect() {
        super(Outcome.Benefit);
        this.staticText = "Shuffle your library. Then exile the top card of your library. Until end of turn, you may play that card without paying its mana cost";
    }

    private MindsDesireEffect(final MindsDesireEffect effect) {
        super(effect);
    }

    @Override
    public MindsDesireEffect copy() {
        return new MindsDesireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.shuffleLibrary(source, game);
            return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, controller.getLibrary().getFromTop(game),
                    TargetController.YOU, Duration.EndOfTurn, true, false, false);
        }
        return false;
    }
}