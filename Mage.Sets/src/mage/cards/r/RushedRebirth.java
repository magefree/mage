package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RushedRebirth extends CardImpl {

    public RushedRebirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Choose target creature. When that creature dies this turn, search your library for a creature card with lesser mana value, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new RushedRebirthEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RushedRebirth(final RushedRebirth card) {
        super(card);
    }

    @Override
    public RushedRebirth copy() {
        return new RushedRebirth(this);
    }
}

class RushedRebirthEffect extends OneShotEffect {

    RushedRebirthEffect() {
        super(Outcome.Benefit);
        staticText = "choose target creature. When that creature dies this turn, " +
                "search your library for a creature card with lesser mana value, " +
                "put it onto the battlefield tapped, then shuffle";
    }

    private RushedRebirthEffect(final RushedRebirthEffect effect) {
        super(effect);
    }

    @Override
    public RushedRebirthEffect copy() {
        return new RushedRebirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        game.addDelayedTriggeredAbility(new RushedRebirthDelayedTriggeredAbility(permanent, game), source);
        return true;
    }
}

class RushedRebirthDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private static final class RushedRebirthPredicate implements Predicate<Card> {

        private final Permanent permanent;

        private RushedRebirthPredicate(Permanent permanent) {
            this.permanent = permanent;
        }

        @Override
        public boolean apply(Card input, Game game) {
            return input.getManaValue() < permanent.getManaValue();
        }
    }

    private final MageObjectReference mor;

    RushedRebirthDelayedTriggeredAbility(Permanent permanent, Game game) {
        super(makeEffect(permanent), Duration.EndOfTurn, false, false);
        this.mor = new MageObjectReference(permanent, game);
    }

    private RushedRebirthDelayedTriggeredAbility(final RushedRebirthDelayedTriggeredAbility ability) {
        super(ability);
        this.mor = ability.mor;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent() && mor.refersTo(zEvent.getTarget(), game);
    }

    private static Effect makeEffect(Permanent permanent) {
        FilterCard filter = new FilterCreatureCard(
                "creature card with lesser mana value than " + permanent.getIdName()
        );
        filter.add(new RushedRebirthPredicate(permanent));
        return new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(filter), true);
    }

    @Override
    public RushedRebirthDelayedTriggeredAbility copy() {
        return new RushedRebirthDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, search your library for a creature card " +
                "with lesser mana value, put it onto the battlefield tapped, then shuffle.";
    }

}
