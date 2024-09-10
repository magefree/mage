package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.TargetsPermanentPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LegolasMasterArcher extends CardImpl {

    private static final DynamicValue xValue = new SourcePermanentPowerCount();
    private static final FilterSpell filter = new FilterSpell("a spell that targets a creature you don't control");

    static {
        filter.add(new TargetsPermanentPredicate(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    public LegolasMasterArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you cast a spell that targets Legolas, Master Archer, put a +1/+1 counter on Legolas.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance())).setAbilityWord(null));

        // Whenever you cast a spell that targets a creature you don't control, Legolas deals damage equal to its power to up to one target creature.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(xValue)
                .setText("{this} deals damage equal to its power to up to one target creature"), filter, false);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private LegolasMasterArcher(final LegolasMasterArcher card) {
        super(card);
    }

    @Override
    public LegolasMasterArcher copy() {
        return new LegolasMasterArcher(this);
    }
}
