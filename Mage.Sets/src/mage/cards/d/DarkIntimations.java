package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DarkIntimations extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Bolas planeswalker spell");
    private static final FilterCard filterCard = new FilterCard("a creature or planeswalker card from your graveyard");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
        filter.add(SubType.BOLAS.getPredicate());
        filterCard.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public DarkIntimations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{B}{R}");

        // Each opponent sacrifices a creature or planeswalker, then discards a card. You return a creature or planeswalker card from your graveyard to your hand, then draw a card.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_CREATURE_OR_PLANESWALKER_A));
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT)
                .setText(", then discards a card"));
        this.getSpellAbility().addEffect(new ReturnCardChosenFromGraveyardEffect(false, filterCard, PutCards.HAND)
                .concatBy("You"));
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1)
                .concatBy(", then"));

        // When you cast a Bolas planeswalker spell, exile Dark Intimations from your graveyard. That planeswalker enters the battlefield with an additional loyalty counter on it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD, new DarkIntimationsGraveyardEffect(),
                filter, false, SetTargetPointer.SPELL
        ).setTriggerPhrase("When you cast a Bolas planeswalker spell, "));
    }

    private DarkIntimations(final DarkIntimations card) {
        super(card);
    }

    @Override
    public DarkIntimations copy() {
        return new DarkIntimations(this);
    }
}

class DarkIntimationsGraveyardEffect extends OneShotEffect {

    DarkIntimationsGraveyardEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile {this} from your graveyard. That planeswalker enters the battlefield with an additional loyalty counter on it";
    }

    private DarkIntimationsGraveyardEffect(final DarkIntimationsGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public DarkIntimationsGraveyardEffect copy() {
        return new DarkIntimationsGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card sourceCard = controller.getGraveyard().get(source.getSourceId(), game);
            if (sourceCard != null) {
                controller.moveCards(sourceCard, Zone.EXILED, source, game);
            }
            Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
            if (spell != null) {
                ContinuousEffect effect = new DarkIntimationsReplacementEffect();
                effect.setTargetPointer(new FixedTarget(spell.getSourceId()));
                game.addEffect(effect, source);
            }
            return true;
        }
        return false;
    }
}

class DarkIntimationsReplacementEffect extends ReplacementEffectImpl {

    DarkIntimationsReplacementEffect() {
        super(Duration.OneUse, Outcome.Benefit);
        staticText = "That planeswalker enters the battlefield with an additional loyalty counter on it";
    }

    private DarkIntimationsReplacementEffect(final DarkIntimationsReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        return creature != null
                && event.getTargetId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.LOYALTY.createInstance(), source.getControllerId(), source, game);
        }
        return false;
    }

    @Override
    public DarkIntimationsReplacementEffect copy() {
        return new DarkIntimationsReplacementEffect(this);
    }
}
