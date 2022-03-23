package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.RaidHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.PlayerAttackedWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class StormFleetPyromancer extends CardImpl {

    public StormFleetPyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Raid - When Storm Fleet Pyromancer enters the battlefield, if you attacked this turn, Storm Fleet Pyromancer deals 2 damage to any target.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2)),
                RaidCondition.instance,
                "When {this} enters the battlefield, if you attacked this turn, {this} deals 2 damage to any target.");
        ability.addTarget(new TargetAnyTarget());
        ability.setAbilityWord(AbilityWord.RAID);
        ability.addHint(RaidHint.instance);
        this.addAbility(ability, new PlayerAttackedWatcher());
    }

    private StormFleetPyromancer(final StormFleetPyromancer card) {
        super(card);
    }

    @Override
    public StormFleetPyromancer copy() {
        return new StormFleetPyromancer(this);
    }
}
