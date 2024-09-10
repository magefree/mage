package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyclaveInvader extends CardImpl {

    public SkyclaveInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setBlue(true);
        this.color.setGreen(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Skyclave Invader, look at the top card of your library. If it's a land card, you may put it onto the battlefield. If you don't put the card onto the battlefield, put it into your hand.
        this.addAbility(new TransformIntoSourceTriggeredAbility(new SkyclaveInvaderEffect()));
    }

    private SkyclaveInvader(final SkyclaveInvader card) {
        super(card);
    }

    @Override
    public SkyclaveInvader copy() {
        return new SkyclaveInvader(this);
    }
}

class SkyclaveInvaderEffect extends OneShotEffect {

    SkyclaveInvaderEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's a land card, you may put it onto the battlefield. " +
                "If you don't put the card onto the battlefield, put it into your hand";
    }

    private SkyclaveInvaderEffect(final SkyclaveInvaderEffect effect) {
        super(effect);
    }

    @Override
    public SkyclaveInvaderEffect copy() {
        return new SkyclaveInvaderEffect(this);
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
        player.lookAtCards(source, null, new CardsImpl(card), game);
        return player.moveCards(card, card.isLand(game) && player.chooseUse(
                Outcome.PutCardInPlay, "Put " + card.getName() + " onto the battlefield or into your hand?",
                null, "Battlefield", "Hand", source, game
        ) ? Zone.BATTLEFIELD : Zone.HAND, source, game);
    }
}
