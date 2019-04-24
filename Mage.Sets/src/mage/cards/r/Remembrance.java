
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class Remembrance extends CardImpl {

    public Remembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a nontoken creature you control dies, you may search your library for a card with the same name as that creature, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new RemembranceTriggeredAbility());
    }

    public Remembrance(final Remembrance card) {
        super(card);
    }

    @Override
    public Remembrance copy() {
        return new Remembrance(this);
    }
}

class RemembranceTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nontoken creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(Predicates.not(new TokenPredicate()));
    }

    public RemembranceTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemembranceEffect());
        this.optional = true;
    }

    public RemembranceTriggeredAbility(final RemembranceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RemembranceTriggeredAbility copy() {
        return new RemembranceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            MageObject mageObject = game.getObject(sourceId);
            if (permanent != null
                    && filter.match(permanent, game)) {
                game.getState().setValue(mageObject + "nameOfPermanent", permanent.getName());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, you may search your library for a card with the same name as that creature, reveal it, and put it into your hand. If you do, shuffle your library.";
    }
}

class RemembranceEffect extends OneShotEffect {

    private String cardName;

    RemembranceEffect() {
        super(Outcome.Benefit);
    }

    RemembranceEffect(final RemembranceEffect effect) {
        super(effect);
    }

    @Override
    public RemembranceEffect copy() {
        return new RemembranceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source.getSourceId());
        cardName = (String) game.getState().getValue(mageObject + "nameOfPermanent");
        if (controller != null
                && cardName != null) {
            FilterCard filterCard = new FilterCard("card named " + cardName);
            filterCard.add(new NamePredicate(cardName));
            return new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filterCard), true, true).apply(game, source);
        }
        return false;
    }
}
