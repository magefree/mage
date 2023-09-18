package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class DictateOfKarametra extends CardImpl {

    public DictateOfKarametra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{G}{G}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                new FilterLandPermanent("a player taps a land"),
                SetTargetPointer.PERMANENT));
        
    }

    private DictateOfKarametra(final DictateOfKarametra card) {
        super(card);
    }

    @Override
    public DictateOfKarametra copy() {
        return new DictateOfKarametra(this);
    }
}
