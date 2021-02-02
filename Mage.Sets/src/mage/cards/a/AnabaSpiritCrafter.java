
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class AnabaSpiritCrafter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Minotaur creatures");

    static {
        filter.add(SubType.MINOTAUR.getPredicate());
    }

    public AnabaSpiritCrafter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Minotaur creatures get +1/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, filter, false)));
    }

    private AnabaSpiritCrafter(final AnabaSpiritCrafter card) {
        super(card);
    }

    @Override
    public AnabaSpiritCrafter copy() {
        return new AnabaSpiritCrafter(this);
    }
}
