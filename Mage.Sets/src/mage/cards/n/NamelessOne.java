
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LoneFox
 */
public final class NamelessOne extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Wizards on the battlefield");

    static {
        filter.add(SubType.WIZARD.getPredicate());
    }

    public NamelessOne(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Nameless One's power and toughness are each equal to the number of Wizards on the battlefield.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        // Morph {2}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private NamelessOne(final NamelessOne card) {
        super(card);
    }

    @Override
    public NamelessOne copy() {
        return new NamelessOne(this);
    }
}
