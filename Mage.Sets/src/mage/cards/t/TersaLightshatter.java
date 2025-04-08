package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardAndDrawThatManyEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TersaLightshatter extends CardImpl {

    public TersaLightshatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Tersa Lightshatter enters, discard up to two cards, then draw that many cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DiscardAndDrawThatManyEffect(2)));

        // Whenever Tersa Lightshatter attacks, if there are seven or more cards in your graveyard, exile a card at random from your graveyard. You may play that card this turn.
        this.addAbility(new AttacksTriggeredAbility(new TersaLightshatterEffect()).withInterveningIf(ThresholdCondition.instance));
    }

    private TersaLightshatter(final TersaLightshatter card) {
        super(card);
    }

    @Override
    public TersaLightshatter copy() {
        return new TersaLightshatter(this);
    }
}

class TersaLightshatterEffect extends OneShotEffect {

    TersaLightshatterEffect() {
        super(Outcome.Benefit);
        staticText = "exile a card at random from your graveyard. You may play that card this turn";
    }

    private TersaLightshatterEffect(final TersaLightshatterEffect effect) {
        super(effect);
    }

    @Override
    public TersaLightshatterEffect copy() {
        return new TersaLightshatterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getGraveyard().getRandom(game);
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CardUtil.makeCardPlayable(game, source, card, false, Duration.EndOfTurn, false);
        return true;
    }
}
