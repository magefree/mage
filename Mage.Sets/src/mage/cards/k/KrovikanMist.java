
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class KrovikanMist extends CardImpl {

    private static final FilterPermanent illusionsFilter = new FilterPermanent("Illusions on the battlefield");

    static {
        illusionsFilter.add(SubType.ILLUSION.getPredicate());
    }

    public KrovikanMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Krovikan Mist's power and toughness are each equal to the number of Illusions on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL,
                new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(illusionsFilter), Duration.EndOfGame)));

    }

    private KrovikanMist(final KrovikanMist card) {
        super(card);
    }

    @Override
    public KrovikanMist copy() {
        return new KrovikanMist(this);
    }
}
