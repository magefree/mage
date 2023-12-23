package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.SpellMasteryCondition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author fireshoes
 */
public final class NecromanticSummons extends CardImpl {

    public NecromanticSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        this.getSpellAbility().addEffect(new NecromanticSummoningReplacementEffect());// has to be added before the moving effect
        // Put target creature card from a graveyard onto the battlefield under your control.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));

        // <i>Spell mastery</i> &mdash; If there are two or more instant and/or sorcery cards in your graveyard, 
        // that creature enters the battlefield with two additional +1/+1 counters on it.
        this.getSpellAbility().addEffect(new InfoEffect("<br><i>Spell mastery</i> &mdash; If there are two or more "
                + "instant and/or sorcery cards in your graveyard, that creature enters the "
                + "battlefield with two additional +1/+1 counters on it."));
    }

    private NecromanticSummons(final NecromanticSummons card) {
        super(card);
    }

    @Override
    public NecromanticSummons copy() {
        return new NecromanticSummons(this);
    }
}

class NecromanticSummoningReplacementEffect extends ReplacementEffectImpl {

    NecromanticSummoningReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private NecromanticSummoningReplacementEffect(final NecromanticSummoningReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(getTargetPointer().getFirst(game, source))) {
            return SpellMasteryCondition.instance.apply(game, source);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(2), source.getControllerId(), source, game, event.getAppliedEffects());
            discard();
        }
        return false;
    }

    @Override
    public NecromanticSummoningReplacementEffect copy() {
        return new NecromanticSummoningReplacementEffect(this);
    }
}
