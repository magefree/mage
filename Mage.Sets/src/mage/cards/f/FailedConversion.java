package mage.cards.f;

import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FailedConversion extends CardImpl {

    public FailedConversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets -4/-4.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(-4, -4)));

        // When enchanted creature dies, surveil 2.
        this.addAbility(new DiesAttachedTriggeredAbility(new SurveilEffect(2), "enchanted creature"));
    }

    private FailedConversion(final FailedConversion card) {
        super(card);
    }

    @Override
    public FailedConversion copy() {
        return new FailedConversion(this);
    }
}
