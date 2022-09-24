package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

/**
 *
 * @author awjackson
 */
public final class CuriousObsession extends CardImpl {

    public CuriousObsession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has "Whenever this creature deals combat damage to a player, you may draw a card.
        ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Ability gainedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), true);
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA);
        effect.setText("and has \"Whenever this creature deals combat damage to a player, you may draw a card.\"");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of your end step, if you didn't attack with a creature this turn sacrifice Curious Obsession.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfYourEndStepTriggeredAbility(new SacrificeSourceEffect(), false),
                new InvertCondition(RaidCondition.instance),
                "At the beginning of your end step, if you didn't attack with a creature this turn, sacrifice {this}."
        );
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private CuriousObsession(final CuriousObsession card) {
        super(card);
    }

    @Override
    public CuriousObsession copy() {
        return new CuriousObsession(this);
    }
}
