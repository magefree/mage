
package mage.cards.j;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.SupportEffect;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JubilantMascot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JubilantMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, you may pay {3}{W}. If you do, support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(
                        new SupportEffect(this, 2, true),
                        new ManaCostsImpl<>("{3}{W}")
                ));
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);
    }

    private JubilantMascot(final JubilantMascot card) {
        super(card);
    }

    @Override
    public JubilantMascot copy() {
        return new JubilantMascot(this);
    }
}
