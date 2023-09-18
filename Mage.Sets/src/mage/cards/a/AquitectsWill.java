package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author ilcartographer
 */
public final class AquitectsWill extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MERFOLK);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public AquitectsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.SORCERY}, "{U}");
        this.subtype.add(SubType.MERFOLK);

        // Put a flood counter on target land.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.FLOOD.createInstance()));
        this.getSpellAbility().addTarget(new TargetLandPermanent());

        // That land is an Island in addition to its other types for as long as it has a flood counter on it.
        this.getSpellAbility().addEffect(new AquitectsWillEffect());

        // If you control a Merfolk, draw a card.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), condition,
                "If you control a Merfolk, draw a card"
        ));
    }

    private AquitectsWill(final AquitectsWill card) {
        super(card);
    }

    @Override
    public AquitectsWill copy() {
        return new AquitectsWill(this);
    }
}

class AquitectsWillEffect extends BecomesBasicLandTargetEffect {

    AquitectsWillEffect() {
        super(Duration.Custom, false, false, SubType.ISLAND);
        staticText = "That land is an Island in addition to its other types for as long as it has a flood counter on it";
    }

    private AquitectsWillEffect(final AquitectsWillEffect effect) {
        super(effect);
    }

    @Override
    public AquitectsWillEffect copy() {
        return new AquitectsWillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent land = game.getPermanent(this.targetPointer.getFirst(game, source));
        if (land == null || land.getCounters(game).getCount(CounterType.FLOOD) < 1) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
