
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author BursegSardaukar
 */
public final class MoggSquad extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature on the battlefield");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public MoggSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Mogg Squad gets -1/-1 for each other creature on the battlefield.
        DynamicValue amount = new SignInversionDynamicValue(new PermanentsOnBattlefieldCount(filter));
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(amount, amount, Duration.WhileOnBattlefield));
        this.addAbility(ability);
    }

    private MoggSquad(final MoggSquad card) {
        super(card);
    }

    @Override
    public MoggSquad copy() {
        return new MoggSquad(this);
    }
}
