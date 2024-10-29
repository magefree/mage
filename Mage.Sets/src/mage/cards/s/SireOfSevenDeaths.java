package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SireOfSevenDeaths extends CardImpl {

    public SireOfSevenDeaths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Ward--Pay 7 life.
        this.addAbility(new WardAbility(new PayLifeCost(7)));
    }

    private SireOfSevenDeaths(final SireOfSevenDeaths card) {
        super(card);
    }

    @Override
    public SireOfSevenDeaths copy() {
        return new SireOfSevenDeaths(this);
    }
}
