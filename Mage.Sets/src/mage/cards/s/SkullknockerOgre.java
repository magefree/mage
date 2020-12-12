package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkullknockerOgre extends CardImpl {

    public SkullknockerOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Whenever Skullknocker Ogre deals damage to an opponent, that player discards a card at random. If the player does, they draw a card.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new SkullknockerOgreEffect(), false, false, true
        ));
    }

    private SkullknockerOgre(final SkullknockerOgre card) {
        super(card);
    }

    @Override
    public SkullknockerOgre copy() {
        return new SkullknockerOgre(this);
    }
}

class SkullknockerOgreEffect extends OneShotEffect {

    SkullknockerOgreEffect() {
        super(Outcome.Benefit);
        staticText = "that player discards a card at random. If the player does, they draw a card";
    }

    private SkullknockerOgreEffect(final SkullknockerOgreEffect effect) {
        super(effect);
    }

    @Override
    public SkullknockerOgreEffect copy() {
        return new SkullknockerOgreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        if (player.discard(1, true, false, source, game).size() > 0) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}