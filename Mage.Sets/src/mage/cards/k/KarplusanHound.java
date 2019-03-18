
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class KarplusanHound extends CardImpl {

    public KarplusanHound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HOUND);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent("a Chandra planeswalker");
        filter.add(new SubtypePredicate(SubType.CHANDRA));
        // Whenever Karplusan Hound attacks, if you control a Chandra planeswalker, this creature deals 2 damage to any target.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DamageTargetEffect(2), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "if you control a Chandra planeswalker, "
                + "this creature deals 2 damage to any target"
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public KarplusanHound(final KarplusanHound card) {
        super(card);
    }

    @Override
    public KarplusanHound copy() {
        return new KarplusanHound(this);
    }
}
