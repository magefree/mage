package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GeothermalBog extends CardImpl {

    public GeothermalBog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // ({T}: Add {B} or {R}.)
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());

        // Geothermal Bog enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private GeothermalBog(final GeothermalBog card) {
        super(card);
    }

    @Override
    public GeothermalBog copy() {
        return new GeothermalBog(this);
    }
}
