package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CastNoncreatureSpellThisTurnCondition;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class LockjawSlobberingTeleporter extends CardImpl {

    private static final FilterCreaturePermanent filterOther = new FilterCreaturePermanent("another target creature");

    static {
        filterOther.add(AnotherPredicate.instance);
    }

    public LockjawSlobberingTeleporter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INHUMAN);
        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // At the beginning of combat on your turn, if you've cast a noncreature spell this turn, put a +1/+1 counter on Lockjaw.
        // When you do, Lockjaw and up to one other target creature you control can’t be blocked this turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()))
                .withInterveningIf (CastNoncreatureSpellThisTurnCondition.instance)
                .addHint(CastNoncreatureSpellThisTurnCondition.getHint());
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("{this}"));
        ability.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn)
                .setText("and up to one other target creature can't be blocked this turn"));
        ability.addTarget(new TargetPermanent(0,1,filterOther));
        this.addAbility(ability);
    }

    private LockjawSlobberingTeleporter(final LockjawSlobberingTeleporter card) {
        super(card);
    }

    @Override
    public LockjawSlobberingTeleporter copy() {
        return new LockjawSlobberingTeleporter(this);
    }
}