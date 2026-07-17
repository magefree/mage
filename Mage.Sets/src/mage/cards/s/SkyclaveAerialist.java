package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.TransformingDoubleFacedCard;
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
public final class SkyclaveAerialist extends TransformingDoubleFacedCard {

    public SkyclaveAerialist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.MERFOLK, SubType.SCOUT}, "{1}{U}",
                "Skyclave Invader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.PHYREXIAN, SubType.MERFOLK, SubType.SCOUT}, "UG"
        );

        // Skyclave Aerialist
        this.getLeftHalfCard().setPT(2, 1);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // {4}{G/P}: Transform Skyclave Aerialist. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{4}{G/P}")));

        // Skyclave Invader
        this.getRightHalfCard().setPT(2, 4);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature transforms into Skyclave Invader, look at the top card of your library. If it's a land card, you may put it onto the battlefield. If you don't put the card onto the battlefield, put it into your hand.
        this.getRightHalfCard().addAbility(new TransformIntoSourceTriggeredAbility(new SkyclaveInvaderEffect()));
    }

    private SkyclaveAerialist(final SkyclaveAerialist card) {
        super(card);
    }

    @Override
    public SkyclaveAerialist copy() {
        return new SkyclaveAerialist(this);
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
