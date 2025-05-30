package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ModeChoice;
import mage.filter.StaticFilters;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OutpostSiege extends CardImpl {

    public OutpostSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // As Outpost Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.KHANS, ModeChoice.DRAGONS)));

        // * Khans - At the beginning of your upkeep, exile the top card of your library. Until end of turn, you may play that card.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                new BeginningOfUpkeepTriggeredAbility(
                        new ExileTopXMayPlayUntilEffect(1, Duration.EndOfTurn)
                ), ModeChoice.KHANS
        )));

        // * Dragons - Whenever a creature you control leaves the battlefield, Outpost Siege deals 1 damage to any target.
        Ability ability = new LeavesBattlefieldAllTriggeredAbility(
                new DamageTargetEffect(1), StaticFilters.FILTER_CONTROLLED_A_CREATURE
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.DRAGONS)));
    }

    private OutpostSiege(final OutpostSiege card) {
        super(card);
    }

    @Override
    public OutpostSiege copy() {
        return new OutpostSiege(this);
    }
}
