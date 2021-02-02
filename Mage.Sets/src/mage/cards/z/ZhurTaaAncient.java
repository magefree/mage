
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ZhurTaaAncient extends CardImpl {

    public ZhurTaaAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                new FilterLandPermanent("a player taps a land"),
                SetTargetPointer.PERMANENT));
    }

    private ZhurTaaAncient(final ZhurTaaAncient card) {
        super(card);
    }

    @Override
    public ZhurTaaAncient copy() {
        return new ZhurTaaAncient(this);
    }
}
