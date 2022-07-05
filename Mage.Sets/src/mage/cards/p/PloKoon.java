
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.ActivateAbilitiesAnyTimeYouCouldCastInstantEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;

/**
 *
 * @author Styxo
 */
public final class PloKoon extends CardImpl {

    public PloKoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KELDOR);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // You may activate meditate abilities any time you could cast an instant.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ActivateAbilitiesAnyTimeYouCouldCastInstantEffect(MeditateAbility.class, "meditate abilities")));

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private PloKoon(final PloKoon card) {
        super(card);
    }

    @Override
    public PloKoon copy() {
        return new PloKoon(this);
    }
}
