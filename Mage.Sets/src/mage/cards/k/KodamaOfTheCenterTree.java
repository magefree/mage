
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.SoulshiftAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class KodamaOfTheCenterTree extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Spirits you control");

    static {
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public KodamaOfTheCenterTree(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Kodama of the Center Tree's power and toughness are each equal to the number of Spirits you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter))));
        
        // Kodama of the Center Tree has soulshift X, where X is the number of Spirits you control.
        this.addAbility(new SoulshiftAbility(new PermanentsOnBattlefieldCount(filter)));
    }

    private KodamaOfTheCenterTree(final KodamaOfTheCenterTree card) {
        super(card);
    }

    @Override
    public KodamaOfTheCenterTree copy() {
        return new KodamaOfTheCenterTree(this);
    }
}
