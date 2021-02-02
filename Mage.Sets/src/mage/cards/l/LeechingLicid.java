
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.LicidAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author emerald000
 */
public final class LeechingLicid extends CardImpl {

    public LeechingLicid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.LICID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {B}, {tap}: Leeching Licid loses this ability and becomes an Aura enchantment with enchant creature. Attach it to target creature. You may pay {B} to end this effect.
        this.addAbility(new LicidAbility(new ColoredManaCost(ColoredManaSymbol.B), new ColoredManaCost(ColoredManaSymbol.B)));
        
        // At the beginning of the upkeep of enchanted creature's controller, Leeching Licid deals 1 damage to that player.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to that player");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.CONTROLLER_ATTACHED_TO, false, true));
    }

    private LeechingLicid(final LeechingLicid card) {
        super(card);
    }

    @Override
    public LeechingLicid copy() {
        return new LeechingLicid(this);
    }
}
