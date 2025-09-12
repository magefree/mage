package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.hint.common.RaidHint;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author awjackson
 */
public final class CuriousObsession extends CardImpl {

    private static final Condition condition = new InvertCondition(RaidCondition.instance, "you didn't attack with a creature this turn");

    public CuriousObsession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1 and has "Whenever this creature deals combat damage to a player, you may draw a card.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), true
                ), AttachmentType.AURA
        ).setText("and has \"Whenever this creature deals combat damage to a player, you may draw a card.\""));
        this.addAbility(ability);

        // At the beginning of your end step, if you didn't attack with a creature this turn sacrifice Curious Obsession.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect())
                .withInterveningIf(condition).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private CuriousObsession(final CuriousObsession card) {
        super(card);
    }

    @Override
    public CuriousObsession copy() {
        return new CuriousObsession(this);
    }
}
