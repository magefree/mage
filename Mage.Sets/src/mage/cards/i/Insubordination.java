package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.DidNotAttackThisTurnEnchantedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageAttachedControllerEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
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
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of the end step of enchanted creature's controller, Insubordination deals 2 damage to that player unless that creature attacked this turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new DamageAttachedControllerEffect(2),
                        TargetController.CONTROLLER_ATTACHED_TO),
                DidNotAttackThisTurnEnchantedCondition.instance,
                "At the beginning of the end step of enchanted creature's controller, {this} deals 2 damage to that player unless that creature attacked this turn."),
                new AttackedThisTurnWatcher());
    }

    private Insubordination(final Insubordination card) {
        super(card);
    }

    @Override
    public Insubordination copy() {
        return new Insubordination(this);
    }
}
