
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author ilcartographer
 */
public final class AquitectsWill extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Merfolk");

    static {
        filter.add(new SubtypePredicate(SubType.MERFOLK));
    }

    public AquitectsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{U}");
        this.subtype.add(SubType.MERFOLK);

        // Put a flood counter on target land.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FLOOD.createInstance()));
        this.getSpellAbility().addTarget(new TargetLandPermanent());

        // That land is an Island in addition to its other types for as long as it has a flood counter on it.
        this.getSpellAbility().addEffect(new AquitectsWillEffect(Duration.Custom, false, false, SubType.ISLAND));

        // If you control a Merfolk, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new PermanentsOnTheBattlefieldCondition(filter),
                "If you control a Merfolk, draw a card"));
    }

    public AquitectsWill(final AquitectsWill card) {
        super(card);
    }

    @Override
    public AquitectsWill copy() {
        return new AquitectsWill(this);
    }
}

class AquitectsWillEffect extends BecomesBasicLandTargetEffect {

    public AquitectsWillEffect(Duration duration, boolean chooseLandType, boolean loseType, SubType... landNames) {
        super(duration, chooseLandType, loseType, landNames);
        staticText = "That land is an Island in addition to its other types for as long as it has a flood counter on it";
    }

    public AquitectsWillEffect(final AquitectsWillEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land == null) {
            // if permanent left battlefield the effect can be removed because it was only valid for that object
            this.discard();
        } else if (land.getCounters(game).getCount(CounterType.FLOOD) > 0) {
            // only if Flood counter is on the object it becomes an Island.(it would be possible to remove and return the counters with e.g. Fate Transfer if the land becomes a creature too)
            super.apply(layer, sublayer, source, game);
        }
        return true;
    }

    @Override
    public AquitectsWillEffect copy() {
        return new AquitectsWillEffect(this);
    }
}
