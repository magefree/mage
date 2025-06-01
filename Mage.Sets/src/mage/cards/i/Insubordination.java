package mage.cards.i;

import mage.abilities.condition.common.DidNotAttackThisTurnEnchantedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Insubordination extends CardImpl {

    public Insubordination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the end step of enchanted creature's controller, Insubordination deals 2 damage to that player unless that creature attacked this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.CONTROLLER_ATTACHED_TO,
                new ConditionalOneShotEffect(
                        new DamageAttachedControllerEffect(2), DidNotAttackThisTurnEnchantedCondition.instance,
                        "{this} deals 2 damage to that player unless that creature attacked this turn"
                ), false
        ).setTriggerPhrase("At the beginning of the end step of enchanted creature's controller, "));
    }

    private Insubordination(final Insubordination card) {
        super(card);
    }

    @Override
    public Insubordination copy() {
        return new Insubordination(this);
    }
}
