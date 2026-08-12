package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.AffinityAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class CantankerousKeepers extends CardImpl {

    public CantankerousKeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Affinity for Elves
        this.addAbility(new AffinityAbility(AffinityType.ELVES));

        // When this creature enters, mill four cards, then put all Elf cards from among them into your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CantankerousKeepersEffect()));
    }

    private CantankerousKeepers(final CantankerousKeepers card) {
        super(card);
    }

    @Override
    public CantankerousKeepers copy() {
        return new CantankerousKeepers(this);
    }
}

class CantankerousKeepersEffect extends OneShotEffect {

    CantankerousKeepersEffect() {
        super(Outcome.DrawCard);
        staticText = "mill four cards, then put all Elf cards from among them into your hand";
    }

    private CantankerousKeepersEffect(final CantankerousKeepersEffect effect) {
        super(effect);
    }

    @Override
    public CantankerousKeepersEffect copy() {
        return new CantankerousKeepersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = player.millCards(4, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        for (Card card : cards.getCards(game)) {
            if (!card.hasSubtype(SubType.ELF, game)) {
                cards.remove(card);
            }
        }
        player.moveCardsToHandWithInfo(cards, source, game, true);
        return true;
    }
}
