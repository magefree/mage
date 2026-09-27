package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author muz
 */
public final class IdentityEcho extends CardImpl {

    public IdentityEcho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        // {3}{R}: Exile target creature or planeswalker you control. Reveal cards from the top of your library until you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new IdentityEchoEffect(),
            new ManaCostsImpl<>("{3}{R}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_CREATURE_OR_PLANESWALKER));
        this.addAbility(ability);
    }

    private IdentityEcho(final IdentityEcho card) {
        super(card);
    }

    @Override
    public IdentityEcho copy() {
        return new IdentityEcho(this);
    }
}

class IdentityEchoEffect extends OneShotEffect {

    IdentityEchoEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "exile target creature or planeswalker you control. Reveal cards from the top of your library "
            + "until you reveal a creature or planeswalker card. Put that card onto the battlefield and the rest "
            + "on the bottom of your library in a random order";
    }

    private IdentityEchoEffect(final IdentityEchoEffect effect) {
        super(effect);
    }

    @Override
    public IdentityEchoEffect copy() {
        return new IdentityEchoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }

        player.moveCards(permanent, Zone.EXILED, source, game);

        Cards toReveal = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : player.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.isCreature(game) || card.isPlaneswalker(game)) {
                toBattlefield = card;
                break;
            }
        }
        player.revealCards(source, toReveal, game);
        if (toBattlefield != null) {
            toReveal.remove(toBattlefield);
            player.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
        }
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
