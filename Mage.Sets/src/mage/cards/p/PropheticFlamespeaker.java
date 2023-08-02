
package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class PropheticFlamespeaker extends CardImpl {

    public PropheticFlamespeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Whenever Prophetic Flamespeaker deals combat damage to a player, exile the top card of your library. You may play it this turn.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new PropheticFlamespeakerExileEffect(), false));
    }

    private PropheticFlamespeaker(final PropheticFlamespeaker card) {
        super(card);
    }

    @Override
    public PropheticFlamespeaker copy() {
        return new PropheticFlamespeaker(this);
    }
}

class PropheticFlamespeakerExileEffect extends OneShotEffect {

    public PropheticFlamespeakerExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "exile the top card of your library. You may play it this turn";
    }

    public PropheticFlamespeakerExileEffect(final PropheticFlamespeakerExileEffect effect) {
        super(effect);
    }

    @Override
    public PropheticFlamespeakerExileEffect copy() {
        return new PropheticFlamespeakerExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                if (controller.moveCardsToExile(card, source, game, true, source.getSourceId(),
                        CardUtil.createObjectRealtedWindowTitle(source, game, "<this card may be played the turn it was exiled>"))) {
                    CardUtil.makeCardPlayable(game, source, card, Duration.EndOfTurn);
                }
            }
            return true;
        }
        return false;
    }
}