package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.VanishingAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CrackInTime extends CardImpl {

    public CrackInTime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Crack in Time enters the battlefield and at the beginning of your precombat main phase, exile target creature an opponent controls until Crack in Time leaves the battlefield.
        Ability ability = new OrTriggeredAbility(Zone.BATTLEFIELD, new ExileUntilSourceLeavesEffect(), false,
                "When {this} enters the battlefield and at the beginning of your precombat main phase, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BeginningOfPreCombatMainTriggeredAbility(null, TargetController.YOU, false)
        );
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private CrackInTime(final CrackInTime card) {
        super(card);
    }

    @Override
    public CrackInTime copy() {
        return new CrackInTime(this);
    }
}
