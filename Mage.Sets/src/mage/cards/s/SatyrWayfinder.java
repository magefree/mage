
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class SatyrWayfinder extends CardImpl {

    public SatyrWayfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Satyr Wayfinder enters the battlefield, reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SatyrWayfinderEffect()));
    }

    private SatyrWayfinder(final SatyrWayfinder card) {
        super(card);
    }

    @Override
    public SatyrWayfinder copy() {
        return new SatyrWayfinder(this);
    }
}

class SatyrWayfinderEffect extends OneShotEffect {

    private static final FilterLandCard filterPutInHand = new FilterLandCard("land card to put in hand");

    public SatyrWayfinderEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. You may put a land card from among them into your hand. Put the rest into your graveyard";
    }

    public SatyrWayfinderEffect(final SatyrWayfinderEffect effect) {
        super(effect);
    }

    @Override
    public SatyrWayfinderEffect copy() {
        return new SatyrWayfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
            boolean properCardFound = cards.count(filterPutInHand, source.getSourceId(), source, game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getIdName(), cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutInHand);
                if (properCardFound
                        && controller.chooseUse(outcome, "Put a land card into your hand?", source, game)
                        && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.HAND, source, game);
                    }

                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
