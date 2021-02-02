
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect.HandSizeModification;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class MinamoScrollkeeper extends CardImpl {

    public MinamoScrollkeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // Your maximum hand size is increased by one.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new MaximumHandSizeControllerEffect(1, Duration.WhileOnBattlefield, HandSizeModification.INCREASE)));
    }

    private MinamoScrollkeeper(final MinamoScrollkeeper card) {
        super(card);
    }

    @Override
    public MinamoScrollkeeper copy() {
        return new MinamoScrollkeeper(this);
    }
}
