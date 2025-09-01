package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class AdventurersInn extends CardImpl {

    public AdventurersInn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        
        this.subtype.add(SubType.TOWN);

        // When this land enters, you gain 2 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(2)));
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private AdventurersInn(final AdventurersInn card) {
        super(card);
    }

    @Override
    public AdventurersInn copy() {
        return new AdventurersInn(this);
    }
}
