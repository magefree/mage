
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Archer262
 */
public final class DauntlessAven extends CardImpl {

    public DauntlessAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Dauntless Aven attacks, untap target creature you control.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect(), false);
        ability.addTarget(new TargetControlledCreaturePermanent(1, 1));
        this.addAbility(ability);
    }

    private DauntlessAven(final DauntlessAven card) {
        super(card);
    }

    @Override
    public DauntlessAven copy() {
        return new DauntlessAven(this);
    }
}
