package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.GoadAttachedAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PsychicImpetus extends CardImpl {

    public PsychicImpetus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and is goaded.
        this.addAbility(new GoadAttachedAbility(new BoostEnchantedEffect(2, 2)));

        // Whenever enchanted creature attacks, you scry 2.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new ScryEffect(2, false).setText("you scry 2"), AttachmentType.AURA, false
        ));
    }

    private PsychicImpetus(final PsychicImpetus card) {
        super(card);
    }

    @Override
    public PsychicImpetus copy() {
        return new PsychicImpetus(this);
    }
}
