package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RaidCondition;
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
public final class MarduHeartPiercer extends CardImpl {

    public MarduHeartPiercer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // <em>Raid</em> - When Mardu Heart-Piercer enters the battlefield, if you attacked this turn, Mardu Heart-Piercer deals 2 damage to any target.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2)).withInterveningIf(RaidCondition.instance);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability.setAbilityWord(AbilityWord.RAID).addHint(RaidHint.instance), new PlayerAttackedWatcher());
    }

    private MarduHeartPiercer(final MarduHeartPiercer card) {
        super(card);
    }

    @Override
    public MarduHeartPiercer copy() {
        return new MarduHeartPiercer(this);
    }
}
