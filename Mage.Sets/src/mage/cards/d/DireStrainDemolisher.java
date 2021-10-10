package mage.cards.d;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.NightboundAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireStrainDemolisher extends CardImpl {

    public DireStrainDemolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);
        this.color.setGreen(true);
        this.transformable = true;
        this.nightCard = true;

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}")));

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private DireStrainDemolisher(final DireStrainDemolisher card) {
        super(card);
    }

    @Override
    public DireStrainDemolisher copy() {
        return new DireStrainDemolisher(this);
    }
}
