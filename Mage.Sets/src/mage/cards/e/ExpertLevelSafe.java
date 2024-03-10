package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryControllerEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 * @author Cguy7777
 */
public final class ExpertLevelSafe extends CardImpl {

    public ExpertLevelSafe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When Expert-Level Safe enters the battlefield, exile the top two cards of your library face down.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ExileCardsFromTopOfLibraryControllerEffect(2, true, true)));

        // {1}, {T}: You and target opponent each secretly choose 1, 2, or 3. Then those choices are revealed.
        // If they match, sacrifice Expert-Level Safe and put all cards exiled with it into their owners' hands.
        // Otherwise, exile the top card of your library face down.
        Ability ability = new SimpleActivatedAbility(new ExpertLevelSafeEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ExpertLevelSafe(final ExpertLevelSafe card) {
        super(card);
    }

    @Override
    public ExpertLevelSafe copy() {
        return new ExpertLevelSafe(this);
    }
}

class ExpertLevelSafeEffect extends OneShotEffect {

    ExpertLevelSafeEffect() {
        super(Outcome.Benefit);
        staticText = "you and target opponent each secretly choose 1, 2, or 3. Then those choices are revealed. " +
                "If they match, sacrifice {this} and put all cards exiled with it into their owners' hands. " +
                "Otherwise, exile the top card of your library face down";
    }

    protected ExpertLevelSafeEffect(ExpertLevelSafeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (controller == null || opponent == null) {
            return false;
        }

        int controllerChoice = controller.getAmount(1, 3, "Choose a number", game);
        int opponentChoice = opponent.getAmount(1, 3, "Choose a number", game);

        game.informPlayers(controller.getLogName() + " chose " + controllerChoice);
        game.informPlayers(opponent.getLogName() + " chose " + opponentChoice);

        if (controllerChoice == opponentChoice) {
            new SacrificeSourceEffect().apply(game, source);
            new ReturnFromExileForSourceEffect(Zone.HAND).apply(game, source);
            return true;
        }

        new ExileCardsFromTopOfLibraryControllerEffect(1, true, true).apply(game, source);
        return true;
    }

    @Override
    public ExpertLevelSafeEffect copy() {
        return new ExpertLevelSafeEffect(this);
    }
}
