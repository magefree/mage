
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author michael.napoleon@gmail.com
 */
public final class ScavengedWeaponry extends CardImpl {

    public ScavengedWeaponry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

         // Enchant creature
         TargetPermanent auraTarget = new TargetCreaturePermanent();
         this.getSpellAbility().addTarget(auraTarget);
         this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
         Ability ability = new EnchantAbility(auraTarget);
         this.addAbility(ability);
         
         // When Scavenged Weaponry enters the battlefield, draw a card.
         this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
         
         // Enchanted creature gets +1/+1.
         this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));
    }

    private ScavengedWeaponry(final ScavengedWeaponry card) {
        super(card);
    }

    @Override
    public ScavengedWeaponry copy() {
        return new ScavengedWeaponry(this);
    }
}
