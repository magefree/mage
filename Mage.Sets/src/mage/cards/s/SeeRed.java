
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.ControllerAttackedThisTurnCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author LevelX2
 */
public final class SeeRed extends CardImpl {

    public SeeRed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +2/+1 and has first strike.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has first strike");
        ability.addEffect(effect);
        this.addAbility(ability);

        // At the beginning of your end step, if you didn't attack with a creature this turn, sacrifice See Red.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU),
                new InvertCondition(ControllerAttackedThisTurnCondition.instance),
                "At the beginning of your end step, if you didn't attack with a creature this turn, sacrifice {this}."), new AttackedThisTurnWatcher());
    }

    private SeeRed(final SeeRed card) {
        super(card);
    }

    @Override
    public SeeRed copy() {
        return new SeeRed(this);
    }
}
