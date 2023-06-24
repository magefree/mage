
package mage.cards.p;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ParasiticBond extends CardImpl {

    public ParasiticBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));
        // At the beginning of the upkeep of enchanted creature's controller, Parasitic Bond deals 2 damage to that player.
        Effect effect = new DamageTargetEffect(2);
        effect.setText("{this} deals 2 damage to that player");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect,
            TargetController.CONTROLLER_ATTACHED_TO, false, true));
    }

    private ParasiticBond(final ParasiticBond card) {
        super(card);
    }

    @Override
    public ParasiticBond copy() {
        return new ParasiticBond(this);
    }
}
