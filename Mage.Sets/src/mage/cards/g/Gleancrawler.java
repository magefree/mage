
package mage.cards.g;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

/**
 *
 * @author LevelX2
 */
public final class Gleancrawler extends CardImpl {

    public Gleancrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B/G}{B/G}{B/G}");
        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // <i>({B/G} can be paid with either {B} or {G}.)</i>
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("<i>({B/G} can be paid with either {B} or {G}.)</i>")));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of your end step, return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new GleancrawlerEffect(), TargetController.YOU, false), new CardsPutIntoGraveyardWatcher());

    }

    public Gleancrawler(final Gleancrawler card) {
        super(card);
    }

    @Override
    public Gleancrawler copy() {
        return new Gleancrawler(this);
    }
}

class GleancrawlerEffect extends OneShotEffect {

    boolean applied = false;

    public GleancrawlerEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return to your hand all creature cards in your graveyard that were put there from the battlefield this turn";
    }

    public GleancrawlerEffect(final GleancrawlerEffect effect) {
        super(effect);
    }

    @Override
    public GleancrawlerEffect copy() {
        return new GleancrawlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CardsPutIntoGraveyardWatcher watcher = (CardsPutIntoGraveyardWatcher) game.getState().getWatchers().get(CardsPutIntoGraveyardWatcher.class.getSimpleName());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && watcher != null) {
            Set<MageObjectReference> cardsToGraveyardThisTurn = watcher.getCardsPutToGraveyardFromBattlefield();
            Cards cardsToHand = new CardsImpl();
            for (MageObjectReference mor : cardsToGraveyardThisTurn) {
                if (game.getState().getZoneChangeCounter(mor.getSourceId()) == mor.getZoneChangeCounter()) {
                    Card card = game.getCard(mor.getSourceId());
                    if (card != null && card.isCreature()
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
