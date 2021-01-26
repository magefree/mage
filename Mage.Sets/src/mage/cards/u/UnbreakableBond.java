package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnbreakableBond extends CardImpl {

    public UnbreakableBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Return target creature card from your graveyard to the battlefield with a lifelink counter on it.
        this.getSpellAbility().addEffect(new UnbreakableBondReplacementEffect());
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new InfoEffect("with a lifelink counter on it"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private UnbreakableBond(final UnbreakableBond card) {
        super(card);
    }

    @Override
    public UnbreakableBond copy() {
        return new UnbreakableBond(this);
    }
}

class UnbreakableBondReplacementEffect extends ReplacementEffectImpl {

    UnbreakableBondReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private UnbreakableBondReplacementEffect(UnbreakableBondReplacementEffect effect) {
        super(effect);
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
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature == null) {
            return false;
        }
        creature.addCounters(CounterType.LIFELINK.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        discard();
        return false;
    }

    @Override
    public UnbreakableBondReplacementEffect copy() {
        return new UnbreakableBondReplacementEffect(this);
    }
}