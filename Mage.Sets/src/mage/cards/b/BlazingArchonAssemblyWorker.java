
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author mschatz
 */
public final class BlazingArchonAssemblyWorker extends CardImpl {

    public BlazingArchonAssemblyWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{W}{W}{W}");
        this.subtype.add(SubType.ARCHON);
        this.subtype.add(SubType.ASSEMBLY_WORKER); // Apply Olivia Voldaren

        // Absorb initial Infest and Dread of Night Black
        this.power = new MageInt(7);
        this.toughness = new MageInt(8);

        // Apply Prismatic Lace
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures can't attack you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouAllEffect(Duration.WhileOnBattlefield)));
    }

    private BlazingArchonAssemblyWorker(final BlazingArchonAssemblyWorker card) {
        super(card);
    }

    @Override
    public BlazingArchonAssemblyWorker copy() {
        return new BlazingArchonAssemblyWorker(this);
    }
}
