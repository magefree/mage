package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetOpponent;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormFleetArsonist extends CardImpl {

    public StormFleetArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Raid - When Storm Fleet Arsonist enters the battlefield, if you attacked this turn, target opponent sacrifices a permanent.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENT_A, 1, "target opponent")
        ).withInterveningIf(RaidCondition.instance);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private StormFleetArsonist(final StormFleetArsonist card) {
        super(card);
    }

    @Override
    public StormFleetArsonist copy() {
        return new StormFleetArsonist(this);
    }
}
