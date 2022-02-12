
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class AtzocanArcher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AtzocanArcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Atzocan Archer enters the battlefield, you may have it fight another target creature.
        Effect effect = new FightTargetSourceEffect();
        effect.setText("you may have it fight another target creature. " +
                "<i>(Each deals damage equal to its power to the other.)</i>");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private AtzocanArcher(final AtzocanArcher card) {
        super(card);
    }

    @Override
    public AtzocanArcher copy() {
        return new AtzocanArcher(this);
    }
}
