package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutCounterOnCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetOpponentsCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class KrosDefenseContractor extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }

    public KrosDefenseContractor(UUID ownerID, CardSetInfo setInfo) {
        super(ownerID, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, put a shield counter on target creature an opponent controls.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.SHIELD.createInstance()), TargetController.YOU, false);
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);

        // Whenever you put one or more counters on a creature you don't control, tap that creature and goad it. It gains trample until your next turn.
        this.addAbility(new PutCounterOnCreatureTriggeredAbility(new KrosDefenseContractorEffect(), null, filter, true));
    }

    private KrosDefenseContractor(final KrosDefenseContractor card) {
        super(card);
    }

    @Override
    public KrosDefenseContractor copy() {
        return new KrosDefenseContractor(this);
    }
}

class KrosDefenseContractorEffect extends OneShotEffect {

    KrosDefenseContractorEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "tap that creature and goad it. It gains trample until your next turn.";
    }

    private KrosDefenseContractorEffect(final KrosDefenseContractorEffect effect) {
        super(effect);
    }

    @Override
    public KrosDefenseContractorEffect copy() {
        return new KrosDefenseContractorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (creature == null) {
            return false;
        }
        creature.tap(source, game);
        game.addEffect(new GainAbilityTargetEffect(
                TrampleAbility.getInstance(), Duration.UntilYourNextTurn
        ).setTargetPointer(new FixedTarget(creature, game)), source);
        game.addEffect(new GoadTargetEffect().setTargetPointer(new FixedTarget(creature, game)), source);
        return true;
    }
}
