package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Neoform extends CardImpl {

    public Neoform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(
                1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true
        )));

        // Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost,
        // put that card onto the battlefield with an additional +1/+1 counter on it, then shuffle your library.
        this.getSpellAbility().addEffect(new NeoformEffect());
    }

    private Neoform(final Neoform card) {
        super(card);
    }

    @Override
    public Neoform copy() {
        return new Neoform(this);
    }
}

class NeoformEffect extends OneShotEffect {

    NeoformEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card with converted mana cost equal to " +
                "1 plus the sacrificed creature's converted mana cost, " +
                "put that card onto the battlefield with an additional +1/+1 counter on it, then shuffle your library.";
    }

    private NeoformEffect(final NeoformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                if (!sacrificeCost.getPermanents().isEmpty()) {
                    sacrificedPermanent = sacrificeCost.getPermanents().get(0);
                }
                break;
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (sacrificedPermanent == null || controller == null) {
            return false;
        }
        int newConvertedCost = sacrificedPermanent.getConvertedManaCost() + 1;
        FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
        filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, newConvertedCost));
        filter.add(new CardTypePredicate(CardType.CREATURE));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                ContinuousEffectImpl effect = new NeoformReplacementEffect();
                effect.setTargetPointer(new FixedTarget(card, game));
                game.addEffect(effect, source);
                if (!controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                    effect.discard();
                }
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public NeoformEffect copy() {
        return new NeoformEffect(this);
    }
}

class NeoformReplacementEffect extends ReplacementEffectImpl {

    NeoformReplacementEffect() {
        super(Duration.EndOfStep, Outcome.BoostCreature);
    }

    private NeoformReplacementEffect(NeoformReplacementEffect effect) {
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
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), source, game, event.getAppliedEffects());
        }
        discard();
        return false;
    }

    @Override
    public NeoformReplacementEffect copy() {
        return new NeoformReplacementEffect(this);
    }
}