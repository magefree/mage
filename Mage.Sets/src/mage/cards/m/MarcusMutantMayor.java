package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author sandbo00
 */
public final class MarcusMutantMayor extends CardImpl {

    public MarcusMutantMayor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(VigilanceAbility.getInstance());
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a creature you control deals combat damage to a player, draw a card if that creature has a +1/+1 counter on it. If it doesn't, put a +1/+1 counter on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new MarcusMutantMayorEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PERMANENT, true));
    }

    private MarcusMutantMayor(final MarcusMutantMayor card) {
        super(card);
    }

    @Override
    public MarcusMutantMayor copy() {
        return new MarcusMutantMayor(this);
    }

}

class MarcusMutantMayorEffect extends OneShotEffect {

    MarcusMutantMayorEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card if that creature has a +1/+1 counter on it. If it doesn't, put a +1/+1 counter on it.";
    }

    private MarcusMutantMayorEffect(final MarcusMutantMayorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Effect effect = (permanent.getCounters(game).getCount(CounterType.P1P1) >= 1)
                ? new DrawCardSourceControllerEffect(1)
                : new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setTargetPointer(this.getTargetPointer().copy());
        return effect.apply(game, source);
    }

    @Override
    public MarcusMutantMayorEffect copy() {
        return new MarcusMutantMayorEffect(this);
    }

}
