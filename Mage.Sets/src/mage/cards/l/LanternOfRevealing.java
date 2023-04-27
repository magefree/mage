package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LanternOfRevealing extends CardImpl {

    public LanternOfRevealing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {4}, {T}: Look at the top card of your library. If it's a land card, you may put it onto the battlefield tapped. If you don't put the card onto the battlefield, you may put it on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(new LanternOfRevealingEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LanternOfRevealing(final LanternOfRevealing card) {
        super(card);
    }

    @Override
    public LanternOfRevealing copy() {
        return new LanternOfRevealing(this);
    }
}

class LanternOfRevealingEffect extends OneShotEffect {

    LanternOfRevealingEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a land card, " +
                "you may put it onto the battlefield tapped. If you don't put the card " +
                "onto the battlefield, you may put it on the bottom of your library";
    }

    private LanternOfRevealingEffect(final LanternOfRevealingEffect effect) {
        super(effect);
    }

    @Override
    public LanternOfRevealingEffect copy() {
        return new LanternOfRevealingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top card of library", card, game);
        if (card.isLand(game) && player.chooseUse(
                outcome, "Put " + card.getName() + " onto the battlefield tapped?", source, game
        )) {
            player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        if (game.getState().getZone(card.getId()) == Zone.LIBRARY && player.chooseUse(
                outcome, "Put " + card.getName() + " on the bottom of your library?", source, game
        )) {
            player.putCardsOnBottomOfLibrary(card, game, source, false);
        }
        return true;
    }
}
