package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.ActivateOncePerTurnManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShireScarecrow extends CardImpl {

    public ShireScarecrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.SCARECROW);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {1}: Add one mana of any color. Activate only once each turn.
        this.addAbility(new ActivateOncePerTurnManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(), new GenericManaCost(1)
        ));
    }

    private ShireScarecrow(final ShireScarecrow card) {
        super(card);
    }

    @Override
    public ShireScarecrow copy() {
        return new ShireScarecrow(this);
    }
}
