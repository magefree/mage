
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CanBlockAsThoughtItHadShadowEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class HeartwoodDryad extends CardImpl {

    public HeartwoodDryad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DRYAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heartwood Dryad can block creatures with shadow as though Heartwood Dryad had shadow.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAsThoughtItHadShadowEffect(Duration.WhileOnBattlefield)));
    }

    private HeartwoodDryad(final HeartwoodDryad card) {
        super(card);
    }

    @Override
    public HeartwoodDryad copy() {
        return new HeartwoodDryad(this);
    }
}