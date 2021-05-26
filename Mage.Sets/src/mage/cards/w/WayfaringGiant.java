
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class WayfaringGiant extends CardImpl {

    public WayfaringGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Domain - Wayfaring Giant gets +1/+1 for each basic land type among lands you control.
        DomainValue dv = new DomainValue();
        Effect effect = new BoostSourceEffect(dv, dv, Duration.WhileOnBattlefield);
        effect.setText("<i>Domain</i> &mdash; {this} gets +1/+1 for each basic land type among lands you control.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect).addHint(DomainHint.instance));
    }

    private WayfaringGiant(final WayfaringGiant card) {
        super(card);
    }

    @Override
    public WayfaringGiant copy() {
        return new WayfaringGiant(this);
    }
}
