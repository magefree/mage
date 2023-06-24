
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LustForWar extends CardImpl {

    public LustForWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature becomes tapped, Lust for War deals 3 damage to that creature's controller.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new DamageAttachedControllerEffect(3), "enchanted creature"));

        // Enchanted creature attacks each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
            new AttacksIfAbleAttachedEffect(Duration.WhileOnBattlefield, AttachmentType.AURA)));
    }

    private LustForWar(final LustForWar card) {
        super(card);
    }

    @Override
    public LustForWar copy() {
        return new LustForWar(this);
    }
}
