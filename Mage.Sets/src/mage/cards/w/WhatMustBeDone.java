package mage.cards.w;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.HistoricPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author anonymous
 */
public final class WhatMustBeDone extends CardImpl {

    private static final FilterPermanent filterArtifactsAndCreatures = new FilterPermanent("artifacts and creatures");
    static {
        filterArtifactsAndCreatures.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    private static final FilterCard filterHistoricPermanentCard = new FilterPermanentCard("historic permanent card from your graveyard");
    static {
        filterHistoricPermanentCard.add(HistoricPredicate.instance);
    }

    public WhatMustBeDone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{W}");

        // Choose one --
        // * Let the World Burn -- Destroy all artifacts and creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filterArtifactsAndCreatures));
        this.getSpellAbility().withFirstModeFlavorWord("Let the World Burn");

        // * Release Juno -- Return target historic permanent card from your graveyard to the battlefield. It enters with two additional +1/+1 counters on it if it's a creature.
        this.getSpellAbility().addMode(new Mode(
                new WhatMustBeDoneReplacementEffect(
                )).addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect()
        ).addTarget(new TargetCardInYourGraveyard(filterHistoricPermanentCard)
        ).addEffect(new InfoEffect("It enters with two additional +1/+1 counters on it if it's a creature.")
        ).withFlavorWord("Release Juno"));
    }

    private WhatMustBeDone(final WhatMustBeDone card) {
        super(card);
    }

    @Override
    public WhatMustBeDone copy() {
        return new WhatMustBeDone(this);
    }
}

class WhatMustBeDoneReplacementEffect extends ReplacementEffectImpl {

    WhatMustBeDoneReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private WhatMustBeDoneReplacementEffect(final WhatMustBeDoneReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null && creature.isCreature(game)
                && !event.getTargetId().equals(source.getSourceId());
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
    public WhatMustBeDoneReplacementEffect copy() {
        return new WhatMustBeDoneReplacementEffect(this);
    }
}