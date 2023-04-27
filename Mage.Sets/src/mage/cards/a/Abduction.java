
package mage.cards.a;

import java.util.UUID;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlAttachedEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class Abduction extends CardImpl {

    public Abduction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        this.addAbility(new EnchantAbility(auraTarget));
        
        // When Abduction enters the battlefield, untap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapEnchantedEffect()));
        
        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));
        
        // When enchanted creature dies, return that card to the battlefield under its owner's control.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderOwnerControlAttachedEffect(), "enchanted creature", false));
    }

    private Abduction(final Abduction card) {
        super(card);
    }

    @Override
    public Abduction copy() {
        return new Abduction(this);
    }
}
