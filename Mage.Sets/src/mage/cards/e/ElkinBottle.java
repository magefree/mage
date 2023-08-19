package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author L_J
 */
public final class ElkinBottle extends CardImpl {

    public ElkinBottle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {tap}, Exile the top card of your library. Until the beginning of your next upkeep, you may play that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElkinBottleExileEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ElkinBottle(final ElkinBottle card) {
        super(card);
    }

    @Override
    public ElkinBottle copy() {
        return new ElkinBottle(this);
    }
}

class ElkinBottleExileEffect extends OneShotEffect {

    public ElkinBottleExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "Exile the top card of your library. Until the beginning of your next upkeep, you may play that card";
    }

    public ElkinBottleExileEffect(final ElkinBottleExileEffect effect) {
        super(effect);
    }

    @Override
    public ElkinBottleExileEffect copy() {
        return new ElkinBottleExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.moveCardsToExile(card, source, game, true, source.getSourceId(), CardUtil.createObjectRealtedWindowTitle(source, game, null));
                CardUtil.makeCardPlayable(game, source, card, Duration.UntilYourNextUpkeepStep, false);
            }
            return true;
        }
        return false;
    }
}