package mage.cards.a;

import java.util.UUID;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.common.DidNotAttackThisTurnEnchantedCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class Aggression extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

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
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has first strike and trample.
        Ability ability2 = new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(
                        FirstStrikeAbility.getInstance(),
                        AttachmentType.AURA));
        ability2.addEffect(new GainAbilityAttachedEffect(
                TrampleAbility.getInstance(),
                AttachmentType.AURA));
        this.addAbility(ability2);

        // At the beginning of the end step of enchanted creature's controller, destroy that creature if it didn't attack this turn.
        this.addAbility(new ConditionalTriggeredAbility(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                        new DestroyAttachedToEffect("enchanted"),
                        TargetController.CONTROLLER_ATTACHED_TO),
                DidNotAttackThisTurnEnchantedCondition.instance,
                "At the beginning of the end step of enchanted creature's controller, destroy that creature if it didn't attack this turn."),
                new AttackedThisTurnWatcher());

    }

    private Aggression(final Aggression card) {
        super(card);
    }

    @Override
    public Aggression copy() {
        return new Aggression(this);
    }
}
