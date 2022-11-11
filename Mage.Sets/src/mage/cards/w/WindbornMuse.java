
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class WindbornMuse extends CardImpl {

    public WindbornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures can't attack you unless their controller pays {2} for each creature they control that's attacking you.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackYouUnlessPayAllEffect(new ManaCostsImpl<>("{2}"))));

    }

    private WindbornMuse(final WindbornMuse card) {
        super(card);
    }

    @Override
    public WindbornMuse copy() {
        return new WindbornMuse(this);
    }
}
