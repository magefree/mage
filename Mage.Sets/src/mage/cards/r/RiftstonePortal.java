
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author emerald000
 */
public final class RiftstonePortal extends CardImpl {

    public RiftstonePortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
 
        // As long as Riftstone Portal is in your graveyard, lands you control have "{T}: Add {G} or {W}."
        ContinuousEffect effect = new GainAbilityControlledEffect(new GreenManaAbility(),
                Duration.WhileOnBattlefield, new FilterControlledLandPermanent());
        effect.setText("As long as Riftstone Portal is in your graveyard, lands you control have \"{T}: Add {G} or {W}.\"");
        Ability ability = new SimpleStaticAbility(Zone.GRAVEYARD, effect);
        effect = new GainAbilityControlledEffect(new WhiteManaAbility(),
                Duration.WhileOnBattlefield, new FilterControlledLandPermanent());
        effect.setText("");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private RiftstonePortal(final RiftstonePortal card) {
        super(card);
    }

    @Override
    public RiftstonePortal copy() {
        return new RiftstonePortal(this);
    }
}
