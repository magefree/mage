
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public final class HibernationsEnd extends CardImpl {

    public HibernationsEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl("{1}")));
        // Whenever you pay Hibernation's End's cumulative upkeep, you may search your library for a creature card with converted mana cost equal to the number of age counters on Hibernation's End and put it onto the battlefield. If you do, shuffle your library.
        this.addAbility(new HibernationsEndAbility());
    }

    public HibernationsEnd(final HibernationsEnd card) {
        super(card);
    }

    @Override
    public HibernationsEnd copy() {
        return new HibernationsEnd(this);
    }
}

class HibernationsEndAbility extends TriggeredAbilityImpl {

    public HibernationsEndAbility() {
        super(Zone.BATTLEFIELD, new HibernationsEndEffect(), true);
    }

    public HibernationsEndAbility(final HibernationsEndAbility ability) {
        super(ability);
    }

    @Override
    public HibernationsEndAbility copy() {
        return new HibernationsEndAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.PAID_CUMULATIVE_UPKEEP;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId() != null && event.getSourceId().equals(this.getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever you pay {this}'s cumulative upkeep, " + super.getRule();
    }
}

class HibernationsEndEffect extends OneShotEffect {

    public HibernationsEndEffect() {
        super(Outcome.Benefit);
        this.staticText = "search your library for a creature card with converted mana cost equal to the number of age counters on {this} and put it onto the battlefield. If you do, shuffle your library.";
    }

    public HibernationsEndEffect(final HibernationsEndEffect effect) {
        super(effect);
    }

    @Override
    public HibernationsEndEffect copy() {
        return new HibernationsEndEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourcePermanent != null && player != null) {
            int newConvertedCost = sourcePermanent.getCounters(game).getCount("age");
            FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, newConvertedCost));
            filter.add(new CardTypePredicate(CardType.CREATURE));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            return new SearchLibraryPutInPlayEffect(target).apply(game, source);
        }
        return false;
    }
}
