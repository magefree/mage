package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.HumanToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CatharsCall extends CardImpl {

    public CatharsCall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has vigilance and "At the beginning of your end step, create a 1/1 white Human creature token."
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA));
        ability.addEffect(new GainAbilityAttachedEffect(
                new BeginningOfEndStepTriggeredAbility(
                        new CreateTokenEffect(new HumanToken()),
                        TargetController.YOU, false
                ), AttachmentType.AURA
        ).setText("and \"At the beginning of your end step, create a 1/1 white Human creature token.\""));
        this.addAbility(ability);
    }

    private CatharsCall(final CatharsCall card) {
        super(card);
    }

    @Override
    public CatharsCall copy() {
        return new CatharsCall(this);
    }
}
