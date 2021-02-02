
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author Styxo
 */
public final class SubterraneanShambler extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SubterraneanShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Echo {3}{R}
        this.addAbility(new EchoAbility("{3}{R}"));

        // When Subterranean Shambler enters the battlefield or leaves the battlefield, it deals 1 damage to each creature without flying.
        this.addAbility(new EntersBattlefieldOrLeavesSourceTriggeredAbility(new DamageAllEffect(1, "it", filter), false));

    }

    private SubterraneanShambler(final SubterraneanShambler card) {
        super(card);
    }

    @Override
    public SubterraneanShambler copy() {
        return new SubterraneanShambler(this);
    }
}
