
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author spjspj
 */
public final class TenaciousHunter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature has a -1/-1 counter on it");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public TenaciousHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as a creature has a -1/-1 counter on it, Tenacious Hunter has vigilance and deathtouch.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilitySourceEffect(VigilanceAbility.getInstance()),
                        new PermanentsOnTheBattlefieldCondition(filter, false),
                        "As long as a creature has a -1/-1 counter on it, {this} has vigilance"));

        ability.addEffect(new ConditionalContinuousEffect(new GainAbilitySourceEffect(DeathtouchAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter, false),
                "and deathtouch"));

        this.addAbility(ability);
    }

    private TenaciousHunter(final TenaciousHunter card) {
        super(card);
    }

    @Override
    public TenaciousHunter copy() {
        return new TenaciousHunter(this);
    }
}
