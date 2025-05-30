package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UrdnanDromokaWarrior extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("attacking creature with a +1/+1 counter on it");

    static {
        filter.add(CounterType.P1P1.getPredicate());
    }

    public UrdnanDromokaWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Urdnan, Dromoka Warrior enters, put a +1/+1 counter on target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever you attack, target attacking creature with a +1/+1 counter on it gains first strike until end of turn. If that creature has two or more +1/+1 counters on it, it gains double strike until end of turn instead.
        ability = new AttacksWithCreaturesTriggeredAbility(new UrdnanDromokaWarriorEffect(), 1);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private UrdnanDromokaWarrior(final UrdnanDromokaWarrior card) {
        super(card);
    }

    @Override
    public UrdnanDromokaWarrior copy() {
        return new UrdnanDromokaWarrior(this);
    }
}

class UrdnanDromokaWarriorEffect extends OneShotEffect {

    UrdnanDromokaWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "target attacking creature with a +1/+1 counter on it gains first strike until end of turn. " +
                "If that creature has two or more +1/+1 counters on it, it gains double strike until end of turn instead";
    }

    private UrdnanDromokaWarriorEffect(final UrdnanDromokaWarriorEffect effect) {
        super(effect);
    }

    @Override
    public UrdnanDromokaWarriorEffect copy() {
        return new UrdnanDromokaWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(
                permanent.getCounters(game).getCount(CounterType.P1P1) >= 2
                        ? DoubleStrikeAbility.getInstance()
                        : FirstStrikeAbility.getInstance()
        ).setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
