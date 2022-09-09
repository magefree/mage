package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class CloakingDevice extends CardImpl {

    public CloakingDevice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(new CantBeBlockedAttachedEffect(AttachmentType.AURA)));

        // Whenever enchanted creature attacks, defending player loses 1 life.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new LoseLifeTargetEffect(1).setText("defending player loses 1 life"),
                AttachmentType.AURA,
                false,
                SetTargetPointer.PLAYER
        ));
    }

    private CloakingDevice(final CloakingDevice card) {
        super(card);
    }

    @Override
    public CloakingDevice copy() {
        return new CloakingDevice(this);
    }
}
