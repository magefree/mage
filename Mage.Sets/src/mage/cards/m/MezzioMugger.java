package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class MezzioMugger extends CardImpl {

    public MezzioMugger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.VIASHINO);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Mezzio Mugger attacks, exile the top card of each player's library. You may play those cards this turn, and you may spend mana as though it were mana of any color to cast those spells.
        this.addAbility(new AttacksTriggeredAbility(new MezzioMuggerEffect()));

        // Blitz {2}{R}
        this.addAbility(new BlitzAbility(this, "{2}{R}"));
    }

    private MezzioMugger(final MezzioMugger card) {
        super(card);
    }

    @Override
    public MezzioMugger copy() {
        return new MezzioMugger(this);
    }
}

class MezzioMuggerEffect extends OneShotEffect {

    MezzioMuggerEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of each player's library. You may play those cards this turn, " +
                "and you may spend mana as though it were mana of any color to cast those spells";
    }

    private MezzioMuggerEffect(final MezzioMuggerEffect effect) {
        super(effect);
    }

    @Override
    public MezzioMuggerEffect copy() {
        return new MezzioMuggerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Card> cards = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getLibrary)
                .map(library -> library.getFromTop(game))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards) {
            CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn, true);
        }
        return true;
    }
}
