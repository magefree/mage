package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

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
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(new MarcusMutantMayorEffect(),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PERMANENT, true);
        this.addAbility(ability);
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

    private DrawCardSourceControllerEffect drawEffect = new DrawCardSourceControllerEffect(1);
    private AddCountersTargetEffect counterEffect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());

    MarcusMutantMayorEffect() {
        super(Outcome.Benefit);
        this.staticText = "draw a card if that creature has a +1/+1 counter on it. If it doesn't, put a +1/+1 counter on it.";
    }

    private MarcusMutantMayorEffect(final MarcusMutantMayorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for(UUID target : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(target);
            Counters counters = permanent.getCounters(game);
            if (counters.getCount(CounterType.P1P1) > 0) {
                this.drawEffect.setTargetPointer(this.getTargetPointer());
                return this.drawEffect.apply(game, source);
            } else {
                this.counterEffect.setTargetPointer(this.getTargetPointer());
                return this.counterEffect.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public MarcusMutantMayorEffect copy() {
        return new MarcusMutantMayorEffect(this);
    }

}
