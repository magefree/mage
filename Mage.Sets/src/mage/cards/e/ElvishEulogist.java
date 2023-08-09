
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author Loki
 */
public final class ElvishEulogist extends CardImpl {

    private static final FilterCard filter = new FilterCard("Elf");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public ElvishEulogist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(new CardsInControllerGraveyardCount(filter, 1))
                .setText("you gain 1 life for each Elf card in your graveyard"), new SacrificeSourceCost()));
    }

    private ElvishEulogist(final ElvishEulogist card) {
        super(card);
    }

    @Override
    public ElvishEulogist copy() {
        return new ElvishEulogist(this);
    }
}
