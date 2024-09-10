package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class Galvanoth extends CardImpl {

    public Galvanoth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you may look at the top card of your library. 
        // If it's an instant or sorcery card, you may cast it without paying its mana cost.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GalvanothEffect(), TargetController.YOU, true));
    }

    private Galvanoth(final Galvanoth card) {
        super(card);
    }

    @Override
    public Galvanoth copy() {
        return new Galvanoth(this);
    }
}

class GalvanothEffect extends OneShotEffect {

    GalvanothEffect() {
        super(Outcome.PlayForFree);
        staticText = "look at the top card of your library. "
                + "You may cast it without paying its mana cost "
                + "if it's an instant or sorcery spell";
    }

    private GalvanothEffect(final GalvanothEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                controller.lookAtCards(source, null, new CardsImpl(card), game);
                if (card.isInstantOrSorcery(game)) {
                    new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST)
                            .setTargetPointer(new FixedTarget(card.getId()))
                            .apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GalvanothEffect copy() {
        return new GalvanothEffect(this);
    }
}
