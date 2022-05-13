package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.Effect;
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

import java.util.UUID;

/**
 * @author Styxo
 */
public final class TerentatekCub extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Jedi or Sith");

    static {
        filter.add(Predicates.or(SubType.JEDI.getPredicate(), SubType.SITH.getPredicate()));
    }

    public TerentatekCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // As long as an opponent controls a Jedi or Sith, {this} gets +1/+1 and attacks each turn if able.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new OpponentControlsPermanentCondition(filter),
                "As long as an opponent controls a Jedi or Sith, {this} gets +1/+1"));
        Effect effect = new ConditionalRequirementEffect(new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield), new OpponentControlsPermanentCondition(filter));
        effect.setText("and attacks each combat if able");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private TerentatekCub(final TerentatekCub card) {
        super(card);
    }

    @Override
    public TerentatekCub copy() {
        return new TerentatekCub(this);
    }
}
