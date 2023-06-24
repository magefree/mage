package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class VigorMortis extends CardImpl {

    public VigorMortis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Return target creature card from your graveyard to the battlefield. If {G} was spent to cast Vigor Mortis, that creature enters the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new VigorMortisReplacementEffect()); // has to be added before the moving effect
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addEffect(new InfoEffect("If {G} was spent to cast this spell, that creature enters the battlefield with an additional +1/+1 counter on it"));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
    }

    private VigorMortis(final VigorMortis card) {
        super(card);
    }

    @Override
    public VigorMortis copy() {
        return new VigorMortis(this);
    }
}

class VigorMortisReplacementEffect extends ReplacementEffectImpl {

    private static final Condition condition = ManaWasSpentCondition.GREEN;

    VigorMortisReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    VigorMortisReplacementEffect(VigorMortisReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            return condition.apply(game, source);
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public VigorMortisReplacementEffect copy() {
        return new VigorMortisReplacementEffect(this);
    }

    @Override
    public Condition getCondition() {
        return condition;
    }
}
