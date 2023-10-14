package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CourtOfIre extends CardImpl {

    public CourtOfIre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // When Court of Ire enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()));

        // At the beginning of your upkeep, Court of Ire deals 2 damage to any target. If you're the monarch, it deals 7 damage to that player or permanent instead.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new ConditionalOneShotEffect(
                new DamageTargetEffect(7), new DamageTargetEffect(2),
                MonarchIsSourceControllerCondition.instance, "{this} deals 2 damage to any target. " +
                "If you're the monarch, it deals 7 damage instead"
        ), TargetController.YOU, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CourtOfIre(final CourtOfIre card) {
        super(card);
    }

    @Override
    public CourtOfIre copy() {
        return new CourtOfIre(this);
    }
}
