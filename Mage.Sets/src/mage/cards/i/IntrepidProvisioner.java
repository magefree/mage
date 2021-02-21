
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.TrampleAbility;
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
 * @author LevelX2
 */
public final class IntrepidProvisioner extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(SubType.HUMAN, "another target Human you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public IntrepidProvisioner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Intrepid Provisioner enters the battlefield, another target Human you control gets +2/+2 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(2, 2, Duration.EndOfTurn), false);
        ability.addTarget(new TargetControlledCreaturePermanent(filter));
        this.addAbility(ability);

    }

    private IntrepidProvisioner(final IntrepidProvisioner card) {
        super(card);
    }

    @Override
    public IntrepidProvisioner copy() {
        return new IntrepidProvisioner(this);
    }
}
