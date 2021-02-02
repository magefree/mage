
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Backfir3
 */
public final class CitanulHierophants extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public CitanulHierophants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        //Creatures you control have "{T}: Add {G}."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(new GreenManaAbility(), Duration.WhileOnBattlefield, filter, false)));
    }

    private CitanulHierophants(final CitanulHierophants card) {
        super(card);
    }

    @Override
    public CitanulHierophants copy() {
        return new CitanulHierophants(this);
    }
}
