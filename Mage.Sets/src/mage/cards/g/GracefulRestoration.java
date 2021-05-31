package mage.cards.g;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author weirddan455
 */
public final class GracefulRestoration extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("creature cards with power 2 or less from your graveyard");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public GracefulRestoration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{B}");

        // Choose one —
        // • Return target creature card from your graveyard to the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new GracefulRestorationReplacementEffect());
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new InfoEffect("with an additional +1/+1 counter on it"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // • Return up to two target creature cards with power 2 or less from your graveyard to the battlefield.
        Mode mode = new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(0, 2, filter));
        this.getSpellAbility().addMode(mode);
    }

    private GracefulRestoration(final GracefulRestoration card) {
        super(card);
    }

    @Override
    public GracefulRestoration copy() {
        return new GracefulRestoration(this);
    }
}

class GracefulRestorationReplacementEffect extends ReplacementEffectImpl {

    public GracefulRestorationReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private GracefulRestorationReplacementEffect(final GracefulRestorationReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GracefulRestorationReplacementEffect copy() {
        return new GracefulRestorationReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        discard();
        return false;
    }
}
