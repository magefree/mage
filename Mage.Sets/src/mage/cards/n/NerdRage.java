package mage.cards.n;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.constants.*;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 * @author Cguy7777
 */
public final class NerdRage extends CardImpl {

    public NerdRage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Nerd Rage enters the battlefield, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(2)));

        // Enchanted creature has "You have no maximum hand size" and "Whenever this creature attacks,
        // if you have ten or more cards in hand, it gets +10/+10 until end of turn."
        SimpleStaticAbility noMaxHandSizeAbility = new SimpleStaticAbility(new MaximumHandSizeControllerEffect(
                Integer.MAX_VALUE,
                Duration.WhileOnBattlefield,
                MaximumHandSizeControllerEffect.HandSizeModification.SET));
        ConditionalInterveningIfTriggeredAbility attacksBoostAbility = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostSourceEffect(10, 10, Duration.EndOfTurn)),
                new CardsInHandCondition(ComparisonType.MORE_THAN, 9),
                "Whenever {this} attacks, if you have ten or more cards in hand, it gets +10/+10 until end of turn.");

        Effect gainNoMaxHandSizeEffect = new GainAbilityAttachedEffect(noMaxHandSizeAbility, AttachmentType.AURA)
                .setText("Enchanted creature has \"You have no maximum hand size\"");
        Effect gainAttacksBoostEffect = new GainAbilityAttachedEffect(attacksBoostAbility, AttachmentType.AURA)
                .setText("\"Whenever this creature attacks, if you have ten or more cards in hand, it gets +10/+10 until end of turn.\"");

        Ability ability = new SimpleStaticAbility(gainNoMaxHandSizeEffect);
        ability.addEffect(gainAttacksBoostEffect.concatBy("and"));
        this.addAbility(ability);
    }

    private NerdRage(final NerdRage card) {
        super(card);
    }

    @Override
    public NerdRage copy() {
        return new NerdRage(this);
    }
}
