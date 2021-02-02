
package mage.cards.m;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRequirementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.AttacksIfAbleSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 * @author jeffwadsworth
 */
public final class MaraudingMaulhorn extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature named Advocate of the Beast");

    static {
        filter.add(new NamePredicate("Advocate of the Beast"));
    }

    public MaraudingMaulhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Marauding Maulhorn attacks each combat if able unless you control a creature named Advocate of the Beast.
        Effect effect = new ConditionalRequirementEffect(
                new AttacksIfAbleSourceEffect(Duration.WhileOnBattlefield, true),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.FEWER_THAN, 1));
        effect.setText("{this} attacks each combat if able unless you control a creature named Advocate of the Beast");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private MaraudingMaulhorn(final MaraudingMaulhorn card) {
        super(card);
    }

    @Override
    public MaraudingMaulhorn copy() {
        return new MaraudingMaulhorn(this);
    }
}
