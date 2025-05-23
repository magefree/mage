package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DidNotAttackThisTurnEnchantedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Aggression extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Wall creature");

    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public Aggression(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant non-Wall creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature has first strike and trample.
        Ability ability = new SimpleStaticAbility(
                new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA)
        );
        ability.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(), AttachmentType.AURA
        ).setText("and trample"));
        this.addAbility(ability);

        // At the beginning of the end step of enchanted creature's controller, destroy that creature if it didn't attack this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.CONTROLLER_ATTACHED_TO,
                new ConditionalOneShotEffect(
                        new DestroyAttachedToEffect(""), DidNotAttackThisTurnEnchantedCondition.instance,
                        "destroy that creature if it didn't attack this turn"
                ), false
        ));
    }

    private Aggression(final Aggression card) {
        super(card);
    }

    @Override
    public Aggression copy() {
        return new Aggression(this);
    }
}
