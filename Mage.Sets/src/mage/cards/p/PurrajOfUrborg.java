
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class PurrajOfUrborg extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a black spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public PurrajOfUrborg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Purraj of Urborg has first strike as long as it's attacking.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                        new GainAbilitySourceEffect(FirstStrikeAbility.getInstance()),
                        SourceAttackingCondition.instance,
                        "{this} has first strike as long as it's attacking"
                )
        ));

        // Whenever a player casts a black spell, you may pay {B}. If you do, put a +1/+1 counter on Purraj of Urborg.
        this.addAbility(new SpellCastAllTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                        new ManaCostsImpl<>("{B}")
                ),
                filter, false
        ));
    }

    private PurrajOfUrborg(final PurrajOfUrborg card) {
        super(card);
    }

    @Override
    public PurrajOfUrborg copy() {
        return new PurrajOfUrborg(this);
    }
}
