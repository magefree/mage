package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Library;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class SongbirdsBlessing extends CardImpl {

    public SongbirdsBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks, reveal cards from the top of your library until you reveal an Aura card. You may put that card onto the battlefield.
        // If you don't, put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksAttachedTriggeredAbility(new SongbirdsBlessingEffect(), AttachmentType.AURA, false));
    }

    private SongbirdsBlessing(final SongbirdsBlessing card) {
        super(card);
    }

    @Override
    public SongbirdsBlessing copy() {
        return new SongbirdsBlessing(this);
    }
}

class SongbirdsBlessingEffect extends OneShotEffect {

    SongbirdsBlessingEffect() {
        super(Outcome.Benefit);
        staticText = "reveal cards from the top of your library until you reveal an Aura card. You may put that card onto the battlefield. " +
                "If you don't, put it into your hand. Put the rest on the bottom of your library in a random order.";
    }

    private SongbirdsBlessingEffect(final SongbirdsBlessingEffect effect) {
        super(effect);
    }

    @Override
    public SongbirdsBlessingEffect copy() {
        return new SongbirdsBlessingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Library library = player.getLibrary();
        if (!library.hasCards()) {
            return true;
        }
        Cards cards = new CardsImpl();
        Card aura = null;
        for (Card card : library.getCards(game)) {
            cards.add(card);
            if (card.hasSubtype(SubType.AURA, game)) {
                aura = card;
                break;
            }
        }

        player.revealCards(source, cards, game);
        if (aura != null) {
            if (player.chooseUse(outcome, "Put " + aura.getIdName() + " onto the battlefield?", source, game)) {
                player.moveCards(aura, Zone.BATTLEFIELD, source, game);
            } else {
                player.moveCards(aura, Zone.HAND, source, game);
            }
        }
        cards.remove(aura);
        if (!cards.isEmpty()) {
            player.putCardsOnBottomOfLibrary(cards, game, source, false);
        }
        return true;
    }
}
