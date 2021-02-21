
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class ThrashOfRaptors extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another Dinosaur");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ThrashOfRaptors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as you control another Dinosaur, Thrash of Raptors gets +2/+0 and has trample.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(2, 0, Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(filter),
                "As long as you control another Dinosaur, {this} gets +2/+0"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(TrampleAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "and has trample"));
        this.addAbility(ability);
    }

    private ThrashOfRaptors(final ThrashOfRaptors card) {
        super(card);
    }

    @Override
    public ThrashOfRaptors copy() {
        return new ThrashOfRaptors(this);
    }
}
