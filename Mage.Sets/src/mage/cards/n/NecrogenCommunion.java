package mage.cards.n;

import java.util.UUID;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.constants.AttachmentType;
import mage.constants.SubType;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class NecrogenCommunion extends CardImpl {

    public NecrogenCommunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has toxic 2.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityAttachedEffect(new ToxicAbility(2), AttachmentType.EQUIPMENT)
        ));

        // When enchanted creature dies, return that card to the battlefield under your control.
        this.addAbility(new DiesAttachedTriggeredAbility(
                new ReturnToBattlefieldUnderYourControlAttachedEffect(), "enchanted creature"
        ));
    }

    private NecrogenCommunion(final NecrogenCommunion card) {
        super(card);
    }

    @Override
    public NecrogenCommunion copy() {
        return new NecrogenCommunion(this);
    }
}
