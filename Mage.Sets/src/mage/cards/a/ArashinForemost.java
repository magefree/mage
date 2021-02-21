
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ArashinForemost extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another target Warrior creature you control");

    static {
        filter.add(SubType.WARRIOR.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ArashinForemost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever Arashin Foremost enters the battlefield or attacks, another target Warrior creature you control gains double strike until end of turn.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GainAbilityTargetEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn));
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ArashinForemost(final ArashinForemost card) {
        super(card);
    }

    @Override
    public ArashinForemost copy() {
        return new ArashinForemost(this);
    }
}
