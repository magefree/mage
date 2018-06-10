
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Styxo
 */
public final class TerentatekCub extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Hunter or Rogue card");

    static {
        filter.add(Predicates.or(new SubtypePredicate(SubType.JEDI), new SubtypePredicate(SubType.SITH)));
    }

    public TerentatekCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as an opponent controls a Jedi or Sith, {this} gets +1/+1 and attacks each turn if able 
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.Custom),
                new OpponentControlsPermanentCondition(filter),
                "As long as an opponent controls a Jedi or Sith, {this} gets +1/+1 ")
        ));
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new AttacksIfAbleSourceEffect(Duration.Custom),
                new OpponentControlsPermanentCondition(filter),
                "and attacks each turn if able.")
        ));
    }

    public TerentatekCub(final TerentatekCub card) {
        super(card);
    }

    @Override
    public TerentatekCub copy() {
        return new TerentatekCub(this);
    }
}
