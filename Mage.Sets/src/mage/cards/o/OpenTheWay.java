package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OpenTheWay extends CardImpl {

    public OpenTheWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{G}{G}");

        // X can't be greater than the number of players in the game.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new InfoEffect("X can't be greater than the number of players in the game")
        ).setRuleAtTheTop(true));
        this.getSpellAbility().setCostAdjuster(OpenTheWayAdjuster.instance);

        // Reveal cards from the top of your library until you reveal X land cards. Put those land cards onto the battlefield tapped and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new OpenTheWayEffect());
    }

    private OpenTheWay(final OpenTheWay card) {
        super(card);
    }

    @Override
    public OpenTheWay copy() {
        return new OpenTheWay(this);
    }
}

enum OpenTheWayAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int playerCount = game.getPlayers().size();
        CardUtil.castStream(ability.getCosts().stream(), VariableManaCost.class)
                .forEach(cost -> cost.setMaxX(playerCount));
    }
}

class OpenTheWayEffect extends OneShotEffect {

    OpenTheWayEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal X land cards. Put those " +
                "land cards onto the battlefield tapped and the rest on the bottom of your library in a random order";
    }

    private OpenTheWayEffect(final OpenTheWayEffect effect) {
        super(effect);
    }

    @Override
    public OpenTheWayEffect copy() {
        return new OpenTheWayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = source.getManaCostsToPay().getX();
        if (player == null || xValue < 1) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Cards lands = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isLand(game)) {
                lands.add(card);
            }
            if (lands.size() >= xValue) {
                break;
            }
        }
        player.revealCards(source, toReveal, game);
        player.moveCards(
                lands.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        toReveal.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
