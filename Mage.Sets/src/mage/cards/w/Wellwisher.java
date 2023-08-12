
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author LevelX2
 */
public final class Wellwisher extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Elf on the battlefield");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public Wellwisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: You gain 1 life for each Elf on the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("you gain 1 life for each Elf on the battlefield"), new TapSourceCost()));

    }

    private Wellwisher(final Wellwisher card) {
        super(card);
    }

    @Override
    public Wellwisher copy() {
        return new Wellwisher(this);
    }
}
