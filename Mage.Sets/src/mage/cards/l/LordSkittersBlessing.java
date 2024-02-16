package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateRoleAttachedTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.RoleType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LordSkittersBlessing extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(EnchantedPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control an enchanted creaeture");

    public LordSkittersBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // When Lord Skitter's Blessing enters the battlefield, create a Wicked Role token attached to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateRoleAttachedTargetEffect(RoleType.WICKED));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your draw step, if you control an enchanted creature, you lose 1 life and you draw an additional card.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfDrawTriggeredAbility(
                        new LoseLifeSourceControllerEffect(1), TargetController.YOU, false
                ), condition, "At the beginning of your draw step, if you control " +
                "an enchanted creature, you lose 1 life and you draw an additional card."
        );
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);
    }

    private LordSkittersBlessing(final LordSkittersBlessing card) {
        super(card);
    }

    @Override
    public LordSkittersBlessing copy() {
        return new LordSkittersBlessing(this);
    }
}
