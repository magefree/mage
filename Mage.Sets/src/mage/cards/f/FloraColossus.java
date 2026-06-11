package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FloraColossus extends CardImpl {

    public FloraColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Flora Colossus's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
            Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)
        ));
    }

    private FloraColossus(final FloraColossus card) {
        super(card);
    }

    @Override
    public FloraColossus copy() {
        return new FloraColossus(this);
    }
}
