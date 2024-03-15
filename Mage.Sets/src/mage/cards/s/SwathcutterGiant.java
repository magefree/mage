package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class SwathcutterGiant extends CardImpl {

    public SwathcutterGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Swathcutter Giant attacks, it deals 1 damage to each creature defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new DamageAllControlledTargetEffect(1)
                .setText("it deals 1 damage to each creature defending player controls"),
                false, null, SetTargetPointer.PLAYER));
    }

    private SwathcutterGiant(final SwathcutterGiant card) {
        super(card);
    }

    @Override
    public SwathcutterGiant copy() {
        return new SwathcutterGiant(this);
    }
}
