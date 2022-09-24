package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.mana.ActivateOncePerTurnManaAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 *
 * @author weirddan455
 */
public final class SalvagedManaworker extends CardImpl {

    public SalvagedManaworker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}: Add one mana of any color. Activate only once each turn.
        this.addAbility(new ActivateOncePerTurnManaAbility(
                Zone.BATTLEFIELD,
                new AddManaOfAnyColorEffect(),
                new GenericManaCost(1)
        ));
    }

    private SalvagedManaworker(final SalvagedManaworker card) {
        super(card);
    }

    @Override
    public SalvagedManaworker copy() {
        return new SalvagedManaworker(this);
    }
}
