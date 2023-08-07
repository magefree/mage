
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;

/**
 * @author TheElk801
 */
public final class ScaldingSalamander extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature without flying defending player controls");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public ScaldingSalamander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Scalding Salamander attacks, you may have it deal 1 damage to each creature without flying defending player controls.
        this.addAbility(new AttacksTriggeredAbility(
                new DamageAllEffect(1, filter), true,
                "Whenever Scalding Salamander attacks, you may have it deal 1 damage to each creature without flying defending player controls."
        ));
    }

    private ScaldingSalamander(final ScaldingSalamander card) {
        super(card);
    }

    @Override
    public ScaldingSalamander copy() {
        return new ScaldingSalamander(this);
    }
}
