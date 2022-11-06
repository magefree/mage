package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
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
public final class TakeFlight extends CardImpl {

    public TakeFlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+0 and has flying and "Whenever this creature attacks, draw a card."
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                FlyingAbility.getInstance(), AttachmentType.AURA
        ).setText("and has flying"));
        ability.addEffect(new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1))
                        .setTriggerPhrase("Whenever this creature attacks, "),
                AttachmentType.AURA
        ).setText("and \"Whenever this creature attacks, draw a card.\""));
        this.addAbility(ability);
    }

    private TakeFlight(final TakeFlight card) {
        super(card);
    }

    @Override
    public TakeFlight copy() {
        return new TakeFlight(this);
    }
}
