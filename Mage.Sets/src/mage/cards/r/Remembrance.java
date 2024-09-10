
package mage.cards.r;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Remembrance extends CardImpl {

    public Remembrance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever a nontoken creature you control dies, you may search your library for a card with the same name as that creature, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new RemembranceTriggeredAbility());
    }

    private Remembrance(final Remembrance card) {
        super(card);
    }

    @Override
    public Remembrance copy() {
        return new Remembrance(this);
    }
}

class RemembranceTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.FALSE);
    }

    RemembranceTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    private RemembranceTriggeredAbility(final RemembranceTriggeredAbility ability) {
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
        if (!((ZoneChangeEvent) event).isDiesEvent()) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (permanent != null && filter.match(permanent, game)) {
            FilterCard filterCard = new FilterCard("card named " + permanent.getName());
            filterCard.add(new NamePredicate(permanent.getName()));
            this.getEffects().clear();
            this.addEffect(new SearchLibraryPutInHandEffect(
                    new TargetCardInLibrary(filterCard), true
            ));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, " +
                "you may search your library for a card with the same name as that creature, " +
                "reveal it, put it into your hand, then shuffle.";
    }
}
