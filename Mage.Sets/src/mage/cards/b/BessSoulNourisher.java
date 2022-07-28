package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class BessSoulNourisher extends CardImpl {

    static final FilterCreaturePermanent filter = new FilterCreaturePermanent("one or more other creatures with base power and toughness 1/1");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, 1));
        filter.add(new ToughnessPredicate(ComparisonType.EQUAL_TO, 1));
    }

    public BessSoulNourisher(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.HUMAN, SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever one or more other creatures with base power and toughness 1/1 enter the battlefield under your control,
        // put a +1/+1 counter on Bess, Soul Nourisher.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                filter)
        );

        // Whenever Bess attacks, each other creature you control with base power and toughness 1/1 gets
        // +X/+X until end of turn, where X is the number of +1/+1 counters on Bess.
        this.addAbility(new AttacksTriggeredAbility(new BessSoulNourisherAddCountersEffect()));
    }

    private BessSoulNourisher(final BessSoulNourisher card) {
        super(card);
    }

    @Override
    public BessSoulNourisher copy() {
        return new BessSoulNourisher(this);
    }
}

class BessSoulNourisherAddCountersEffect extends OneShotEffect {

    BessSoulNourisherAddCountersEffect() {
        super(Outcome.BoostCreature);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent bessSoulNourisher = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (bessSoulNourisher == null || controller == null) {
            return false;
        }

        int counterNumber = bessSoulNourisher.getCounters(game).getCount(CounterType.P1P1);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(BessSoulNourisher.filter, controller.getId(), game)) {
            ContinuousEffect boostEffect = new BoostTargetEffect(counterNumber, counterNumber, Duration.EndOfTurn);
            boostEffect.setTargetPointer(new FixedTarget(permanent.getId()));
            game.addEffect(boostEffect, source);
        }

        return true;
    }

    private BessSoulNourisherAddCountersEffect(final BessSoulNourisherAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new BessSoulNourisherAddCountersEffect(this);
    }
}