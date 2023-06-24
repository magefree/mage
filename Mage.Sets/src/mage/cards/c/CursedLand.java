
package mage.cards.c;

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
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class CursedLand extends CardImpl {

    public CursedLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));
        // At the beginning of the upkeep of enchanted land's controller, Cursed Land deals 1 damage to that player.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("{this} deals 1 damage to that player");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect,
            TargetController.CONTROLLER_ATTACHED_TO, false, true));
    }

    private CursedLand(final CursedLand card) {
        super(card);
    }

    @Override
    public CursedLand copy() {
        return new CursedLand(this);
    }
}
