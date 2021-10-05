package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CurseOfUnbinding extends CardImpl {

    public CurseOfUnbinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{U}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of enchanted player's upkeep, that player reveals cards from the top of their library until they reveal a creature card. Put that card onto the battlefield under your control. That player puts the rest of the revealed cards into their graveyard.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CurseOfUnbindingEffect(), TargetController.ENCHANTED, false
        ));
    }

    private CurseOfUnbinding(final CurseOfUnbinding card) {
        super(card);
    }

    @Override
    public CurseOfUnbinding copy() {
        return new CurseOfUnbinding(this);
    }
}

class CurseOfUnbindingEffect extends OneShotEffect {

    CurseOfUnbindingEffect() {
        super(Outcome.Benefit);
        staticText = "that player reveals cards from the top of their library " +
                "until they reveal a creature card. Put that card onto the battlefield under your control. " +
                "That player puts the rest of the revealed cards into their graveyard";
    }

    private CurseOfUnbindingEffect(final CurseOfUnbindingEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfUnbindingEffect copy() {
        return new CurseOfUnbindingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card toHand = null;
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isCreature(game)) {
                toHand = card;
                break;
            }
        }
        player.revealCards(source, cards, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (toHand != null && controller != null) {
            controller.moveCards(toHand, Zone.BATTLEFIELD, source, game);
        }
        cards.retainZone(Zone.LIBRARY, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
