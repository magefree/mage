
package mage.cards.t;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PersistAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class TwilightShepherd extends CardImpl {

    public TwilightShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Twilight Shepherd enters the battlefield, return to your hand all cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TwilightShepherdEffect(), false), new CardsPutIntoGraveyardWatcher());

        // Persist
        this.addAbility(new PersistAbility());
    }

    public TwilightShepherd(final TwilightShepherd card) {
        super(card);
    }

    @Override
    public TwilightShepherd copy() {
        return new TwilightShepherd(this);
    }
}

class TwilightShepherdEffect extends OneShotEffect {

    boolean applied = false;

    public TwilightShepherdEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all cards in your graveyard that were put there from the battlefield this turn";
    }

    public TwilightShepherdEffect(final TwilightShepherdEffect effect) {
        super(effect);
    }

    @Override
    public TwilightShepherdEffect copy() {
        return new TwilightShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = game.getState().getWatcher(CardsPutIntoGraveyardWatcher.class);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && watcher != null) {
            Set<MageObjectReference> cardsInGraveyard = watcher.getCardsPutToGraveyardFromBattlefield();
            Cards cardsToHand = new CardsImpl();
            for (MageObjectReference mor : cardsInGraveyard) {
                if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                    Card card = game.getCard(mor.getSourceId());
                    if (card != null
                            && card.isOwnedBy(source.getControllerId())) {
                        cardsToHand.add(card);
                    }
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }
}
