package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GyreEngineer extends CardImpl {

    public GyreEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Add {G}{U}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new Mana(0, 1, 0, 0, 1, 0, 0, 0), new TapSourceCost()));
    }

    private GyreEngineer(final GyreEngineer card) {
        super(card);
    }

    @Override
    public GyreEngineer copy() {
        return new GyreEngineer(this);
    }
}
